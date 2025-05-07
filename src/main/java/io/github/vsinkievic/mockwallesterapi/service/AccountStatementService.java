package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.AccountStatementRecordDTO;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardAccountMapper;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAccount;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterAdjustmentRequest;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterStatementRecord;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class AccountStatementService {

    private final AccountStatementRecordRepository statementRepository;
    private final CardAccountRepository cardAccountRepository;
    private final CardRepository cardRepository;
    private final AccountStatementRecordService accountStatementRecordService;
    private final CardAccountMapper cardAccountMapper;

    public AccountStatementService(
        AccountStatementRecordRepository statementRepository,
        CardAccountRepository cardAccountRepository,
        CardRepository cardRepository,
        AccountStatementRecordService accountStatementRecordService,
        CardAccountMapper cardAccountMapper
    ) {
        this.statementRepository = statementRepository;
        this.cardAccountRepository = cardAccountRepository;
        this.cardRepository = cardRepository;
        this.accountStatementRecordService = accountStatementRecordService;
        this.cardAccountMapper = cardAccountMapper;
    }

    public Page<WallesterStatementRecord> getAccountStatement(UUID accountId, Instant fromDate, Instant toDate, Pageable pageable) {
        log.info("getAccountStatement(accountId={}, fromDate={}, toDate={}, pageable={})", accountId, fromDate, toDate, pageable);

        // If dates are not provided, use default range (from 2000-01-01 to now)
        if (fromDate == null) {
            fromDate = LocalDateTime.of(2000, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
        }
        if (toDate == null) {
            toDate = Instant.now();
        }

        // Ensure fromDate is before toDate
        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate must be before toDate");
        }

        // Fetch records from repository
        Page<AccountStatementRecord> records = statementRepository.findByAccountIdAndDateBetween(accountId, fromDate, toDate, pageable);

        // Pre-fetch account and card data for all records
        Map<UUID, CardAccount> accountCache = new HashMap<>();
        Map<UUID, Card> cardCache = new HashMap<>();

        // Get unique card IDs from records
        records
            .getContent()
            .stream()
            .map(AccountStatementRecord::getCardId)
            .filter(cardId -> cardId != null)
            .distinct()
            .forEach(cardId -> {
                cardRepository.findById(cardId).ifPresent(card -> cardCache.put(cardId, card));
            });

        // Get account data
        cardAccountRepository.findById(accountId).ifPresent(account -> accountCache.put(accountId, account));

        // Map to WallesterStatementRecord with cached data
        Page<WallesterStatementRecord> wallesterRecords = records.map(record -> {
            CardAccount account = accountCache.get(accountId);
            Card card = record.getCardId() != null ? cardCache.get(record.getCardId()) : null;
            return new WallesterStatementRecord(record, account, card);
        });

        return wallesterRecords;
    }

    public WallesterAccount adjustAccountBalance(UUID accountId, WallesterAdjustmentRequest request) {
        log.info("adjustAccountBalance(accountId={}, request={})", accountId, request);
        request.validate();

        // TODO: Implement balance adjustment logic
        // 1. Validate if negative balance is allowed
        // 2. Create a new statement record for the adjustment
        // 3. Update the account balance
        // 4. Return the created statement record

        CardAccount account = cardAccountRepository
            .findById(accountId)
            .orElseThrow(() -> new WallesterApiException(404, "Account not found"));

        if (!request.getAllowNegativeBalance() && account.getAvailableAmount().add(request.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            throw new WallesterApiException(422, "Negative balance not allowed");
        }

        AccountStatementRecordDTO record = new AccountStatementRecordDTO();
        record.setAccountId(accountId);
        record.setExternalId(request.getExternalId());
        record.setType(AccountStatementRecordType.AccountAdjustment);
        record.setGroup(
            request.getAmount().compareTo(BigDecimal.ZERO) > 0 ? AccountStatementRecordGroup.Deposit : AccountStatementRecordGroup.Withdraw
        );
        record.setStatus(AccountStatementRecordStatus.Completed);
        record.setResponse(AccountStatementRecordResponse.Approved);
        record.setIsCleared(true);
        record.setIsDeclined(false);
        record.setIsReversal(false);
        record.setTransactionAmount(request.getAmount());
        record.setAccountAmount(request.getAmount());
        record.setTransactionCurrencyCode(account.getCurrencyCode());
        record.setAccountCurrencyCode(account.getCurrencyCode());
        record.setDate(Instant.now());
        record.setDescription(request.getDescription());
        record.setMerchantName(request.getSenderName());

        AccountStatementRecordDTO savedRecord = accountStatementRecordService.save(record);

        CardAccount recalculatedAccount = cardAccountRepository
            .findById(accountId)
            .orElseThrow(() -> new WallesterApiException(404, "Account not found"));

        CardAccountDTO recalculatedAccountDTO = cardAccountMapper.toDto(recalculatedAccount);
        return new WallesterAccount(recalculatedAccountDTO);
    }
}
