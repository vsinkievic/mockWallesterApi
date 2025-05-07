package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardRepository;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterStatementRecord;
import jakarta.transaction.Transactional;
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

    public AccountStatementService(
        AccountStatementRecordRepository statementRepository,
        CardAccountRepository cardAccountRepository,
        CardRepository cardRepository
    ) {
        this.statementRepository = statementRepository;
        this.cardAccountRepository = cardAccountRepository;
        this.cardRepository = cardRepository;
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
}
