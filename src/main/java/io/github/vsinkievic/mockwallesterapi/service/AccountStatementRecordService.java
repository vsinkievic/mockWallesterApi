package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.AccountStatementRecordDTO;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.AccountStatementRecordMapper;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import io.micrometer.common.util.StringUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord}.
 */
@Service
@Transactional
public class AccountStatementRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountStatementRecordService.class);

    private final AccountStatementRecordRepository accountStatementRecordRepository;

    private final AccountStatementRecordMapper accountStatementRecordMapper;
    private final CardAccountService cardAccountService;
    private final CardRepository cardRepository;

    private static final Set<CountryCode> EEA_COUNTRIES = Set.of(
        CountryCode.AUT, // Austria
        CountryCode.BEL, // Belgium
        CountryCode.BGR, // Bulgaria
        CountryCode.HRV, // Croatia
        CountryCode.CYP, // Cyprus
        CountryCode.CZE, // Czech Republic
        CountryCode.DNK, // Denmark
        CountryCode.EST, // Estonia
        CountryCode.FIN, // Finland
        CountryCode.FRA, // France
        CountryCode.DEU, // Germany
        CountryCode.GRC, // Greece
        CountryCode.HUN, // Hungary
        CountryCode.ISL, // Iceland
        CountryCode.IRL, // Ireland
        CountryCode.ITA, // Italy
        CountryCode.LVA, // Latvia
        CountryCode.LIE, // Liechtenstein
        CountryCode.LTU, // Lithuania
        CountryCode.LUX, // Luxembourg
        CountryCode.MLT, // Malta
        CountryCode.NLD, // Netherlands
        CountryCode.NOR, // Norway
        CountryCode.POL, // Poland
        CountryCode.PRT, // Portugal
        CountryCode.ROU, // Romania
        CountryCode.SVK, // Slovakia
        CountryCode.SVN, // Slovenia
        CountryCode.ESP, // Spain
        CountryCode.SWE // Sweden
    );

    public AccountStatementRecordService(
        AccountStatementRecordRepository accountStatementRecordRepository,
        AccountStatementRecordMapper accountStatementRecordMapper,
        CardAccountService cardAccountService,
        CardRepository cardRepository
    ) {
        this.accountStatementRecordRepository = accountStatementRecordRepository;
        this.accountStatementRecordMapper = accountStatementRecordMapper;
        this.cardAccountService = cardAccountService;
        this.cardRepository = cardRepository;
    }

    /**
     * Save a accountStatementRecord.
     *
     * @param accountStatementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountStatementRecordDTO save(AccountStatementRecordDTO accountStatementRecordDTO) {
        LOG.debug("Request to save AccountStatementRecord : {}", accountStatementRecordDTO);

        validateAndPrepareForSave(accountStatementRecordDTO);

        AccountStatementRecord accountStatementRecord = accountStatementRecordMapper.toEntity(accountStatementRecordDTO);
        accountStatementRecord.validateAndPrepareForSave();
        accountStatementRecord = accountStatementRecordRepository.save(accountStatementRecord);
        cardAccountService.recalculateBalance(accountStatementRecord.getAccountId());
        return accountStatementRecordMapper.toDto(accountStatementRecord);
    }

    /**
     * Update a accountStatementRecord.
     *
     * @param accountStatementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountStatementRecordDTO update(AccountStatementRecordDTO accountStatementRecordDTO) {
        LOG.debug("Request to update AccountStatementRecord : {}", accountStatementRecordDTO);
        validateAndPrepareForSave(accountStatementRecordDTO);
        AccountStatementRecord accountStatementRecord = accountStatementRecordMapper.toEntity(accountStatementRecordDTO);
        accountStatementRecord.validateAndPrepareForSave();
        accountStatementRecord = accountStatementRecordRepository.save(accountStatementRecord);
        cardAccountService.recalculateBalance(accountStatementRecord.getAccountId());
        return accountStatementRecordMapper.toDto(accountStatementRecord);
    }

    private void validateAndPrepareForSave(AccountStatementRecordDTO accountStatementRecordDTO) {
        if (accountStatementRecordDTO.getId() == null) {
            if (accountStatementRecordDTO.getExternalId() != null) {
                if (accountStatementRecordRepository.findByExternalId(accountStatementRecordDTO.getExternalId()).isPresent()) {
                    throw new WallesterApiException(
                        422,
                        "Account statement record with external ID " + accountStatementRecordDTO.getExternalId() + " already exists"
                    );
                }
            }
            if (accountStatementRecordDTO.getOriginalAuthorizationId() != null) {
                AccountStatementRecord originalAuthorization = accountStatementRecordRepository
                    .findById(accountStatementRecordDTO.getOriginalAuthorizationId())
                    .orElseThrow(() -> new WallesterApiException(422, "Original authorization not found"));
                if (originalAuthorization.getType() != AccountStatementRecordType.Authorization) {
                    throw new WallesterApiException(422, "Original authorization is not an authorization");
                }
                accountStatementRecordDTO.setAccountId(originalAuthorization.getAccountId());
                accountStatementRecordDTO.setCardId(originalAuthorization.getCardId());
                accountStatementRecordDTO.setPurchaseDate(originalAuthorization.getDate());
                if (accountStatementRecordDTO.getTransactionAmount() == null) accountStatementRecordDTO.setTransactionAmount(
                    originalAuthorization.getTransactionAmount()
                );
                if (accountStatementRecordDTO.getAccountAmount() == null) accountStatementRecordDTO.setAccountAmount(
                    originalAuthorization.getAccountAmount()
                );

                switch (accountStatementRecordDTO.getType()) {
                    case Authorization:
                    case Transaction:
                        if (accountStatementRecordDTO.getGroup() == null) accountStatementRecordDTO.setGroup(
                            originalAuthorization.getGroup()
                        );

                        if (accountStatementRecordDTO.getTransactionAmount() == null) accountStatementRecordDTO.setTransactionAmount(
                            originalAuthorization.getTransactionAmount()
                        );
                        if (accountStatementRecordDTO.getAccountAmount() == null) accountStatementRecordDTO.setAccountAmount(
                            originalAuthorization.getAccountAmount()
                        );
                        if (
                            accountStatementRecordDTO.getTransactionCurrencyCode() == null
                        ) accountStatementRecordDTO.setTransactionCurrencyCode(originalAuthorization.getTransactionCurrencyCode());
                        if (accountStatementRecordDTO.getAccountCurrencyCode() == null) accountStatementRecordDTO.setAccountCurrencyCode(
                            originalAuthorization.getAccountCurrencyCode()
                        );

                        accountStatementRecordDTO.setMerchantId(originalAuthorization.getMerchantId());
                        accountStatementRecordDTO.setTerminalId(originalAuthorization.getTerminalId());
                        accountStatementRecordDTO.setMerchantCategoryCode(originalAuthorization.getMerchantCategoryCode());
                        accountStatementRecordDTO.setMerchantName(originalAuthorization.getMerchantName());
                        accountStatementRecordDTO.setMerchantCity(originalAuthorization.getMerchantCity());
                        accountStatementRecordDTO.setMerchantCountryCode(originalAuthorization.getMerchantCountryCode());
                        accountStatementRecordDTO.setDescription(originalAuthorization.getDescription());

                        break;
                    default:
                }
                if (AccountStatementRecordType.Transaction.equals(accountStatementRecordDTO.getType())) {
                    completeOriginalAuthorization(accountStatementRecordDTO.getOriginalAuthorizationId());
                }
            }

            if (accountStatementRecordDTO.getAccountId() == null && accountStatementRecordDTO.getCardId() != null) {
                Card card = cardRepository
                    .findById(accountStatementRecordDTO.getCardId())
                    .orElseThrow(() -> new WallesterApiException(422, "Card not found"));
                accountStatementRecordDTO.setAccountId(card.getAccountId());
            }
            if (accountStatementRecordDTO.getAccountId() == null) {
                throw new WallesterApiException(422, "Account ID is required");
            }
            CardAccountDTO cardAccount = cardAccountService
                .findOne(accountStatementRecordDTO.getAccountId())
                .orElseThrow(() -> new WallesterApiException(422, "Card account not found"));
            accountStatementRecordDTO.setAccountCurrencyCode(cardAccount.getCurrencyCode());

            if (accountStatementRecordDTO.getTransactionAmount() == null) accountStatementRecordDTO.setTransactionAmount(
                accountStatementRecordDTO.getAccountAmount()
            );
            if (accountStatementRecordDTO.getTransactionCurrencyCode() == null) accountStatementRecordDTO.setTransactionCurrencyCode(
                accountStatementRecordDTO.getAccountCurrencyCode()
            );

            if (accountStatementRecordDTO.getAccountAmount() == null && accountStatementRecordDTO.getTransactionAmount() != null) {
                if (accountStatementRecordDTO.getAccountCurrencyCode().equals(accountStatementRecordDTO.getTransactionCurrencyCode())) {
                    accountStatementRecordDTO.setAccountAmount(accountStatementRecordDTO.getTransactionAmount());
                } else {
                    accountStatementRecordDTO.setExchangeRate(random(0.2, 1.9));
                    accountStatementRecordDTO.setAccountAmount(
                        accountStatementRecordDTO.getTransactionAmount().multiply(accountStatementRecordDTO.getExchangeRate())
                    );
                }
            }
            if (accountStatementRecordDTO.getAccountAmount() != null && accountStatementRecordDTO.getTransactionAmount() != null) {
                accountStatementRecordDTO.setExchangeRate(
                    accountStatementRecordDTO
                        .getTransactionAmount()
                        .divide(accountStatementRecordDTO.getAccountAmount(), 2, RoundingMode.HALF_UP)
                );
            }

            if (accountStatementRecordDTO.getType() == AccountStatementRecordType.Authorization) {
                if (StringUtils.isBlank(accountStatementRecordDTO.getMerchantId())) accountStatementRecordDTO.setMerchantId(
                    RandomStringUtils.randomNumeric(6)
                );
                if (StringUtils.isBlank(accountStatementRecordDTO.getTerminalId())) accountStatementRecordDTO.setTerminalId(
                    RandomStringUtils.randomNumeric(6)
                );
                if (
                    StringUtils.isBlank(accountStatementRecordDTO.getMerchantCategoryCode())
                ) accountStatementRecordDTO.setMerchantCategoryCode(RandomStringUtils.randomNumeric(4));
                if (StringUtils.isBlank(accountStatementRecordDTO.getMerchantName())) accountStatementRecordDTO.setMerchantName(
                    RandomStringUtils.randomAlphabetic(5, 30)
                );
                if (StringUtils.isBlank(accountStatementRecordDTO.getMerchantCity())) accountStatementRecordDTO.setMerchantCity(
                    RandomStringUtils.randomAlphabetic(5, 30)
                );
                if (accountStatementRecordDTO.getMerchantCountryCode() == null) accountStatementRecordDTO.setMerchantCountryCode(
                    CountryCode.LVA
                );
            }

            if (accountStatementRecordDTO.getCardId() == null && accountStatementRecordDTO.getType() != null) {
                if (!AccountStatementRecordType.AccountAdjustment.equals(accountStatementRecordDTO.getType())) {
                    setCardIdIfNotSet(accountStatementRecordDTO);
                }
            }
        } else {
            AccountStatementRecord recordInDb = accountStatementRecordRepository
                .findById(accountStatementRecordDTO.getId())
                .orElseThrow(() -> new WallesterApiException(422, "Account statement record not found"));
            accountStatementRecordDTO.setAccountId(recordInDb.getAccountId());
            accountStatementRecordDTO.setExternalId(recordInDb.getExternalId());
            accountStatementRecordDTO.setCardId(recordInDb.getCardId());
            accountStatementRecordDTO.setType(recordInDb.getType());
            accountStatementRecordDTO.setAccountCurrencyCode(recordInDb.getAccountCurrencyCode());
        }

        if (accountStatementRecordDTO.getAccountId() == null) {
            throw new WallesterApiException(422, "Account ID is required");
        }

        if (accountStatementRecordDTO.getAccountAmount() == null) {
            throw new WallesterApiException(422, "Account amount is required");
        }
        if (accountStatementRecordDTO.getTransactionAmount() == null) {
            throw new WallesterApiException(422, "Transaction amount is required");
        }
    }

    public void completeOriginalAuthorization(UUID originalAuthorizationId) {
        LOG.debug("Completing original authorization: {}", originalAuthorizationId);

        AccountStatementRecord originalAuthorization = accountStatementRecordRepository
            .findById(originalAuthorizationId)
            .orElseThrow(() -> new WallesterApiException(422, "Original authorization not found"));

        if (originalAuthorization.getType() != AccountStatementRecordType.Authorization) {
            throw new WallesterApiException(422, "Original authorization is not an authorization");
        }
        if (originalAuthorization.getStatus() == AccountStatementRecordStatus.Pending) {
            originalAuthorization.setStatus(AccountStatementRecordStatus.Completed);
            originalAuthorization.setResponse(AccountStatementRecordResponse.Approved);
            originalAuthorization.validateAndPrepareForSave();
            accountStatementRecordRepository.save(originalAuthorization);
        }

        List<AccountStatementRecord> otherTransactions = accountStatementRecordRepository.findByOriginalAuthorizationId(
            originalAuthorizationId
        );

        for (AccountStatementRecord other : otherTransactions) {
            switch (other.getType()) {
                case Transaction:
                    continue;
                case Fee:
                case Authorization:
                    if (other.getStatus() == AccountStatementRecordStatus.Pending) {
                        other.setStatus(AccountStatementRecordStatus.Completed);
                        other.setResponse(AccountStatementRecordResponse.Approved);
                        other.validateAndPrepareForSave();
                        accountStatementRecordRepository.save(other);
                    }
                    break;
                default:
            }
        }
    }

    private BigDecimal random(double min, double max) {
        Random random = new Random();
        // Generate a random double between 0.2 (inclusive) and 1.9 (exclusive of 2.0)
        double randomDouble = min + (max * random.nextDouble());
        // Scale the double to the required precision and create a BigDecimal
        return BigDecimal.valueOf(randomDouble);
    }

    /**
     * Partially update a accountStatementRecord.
     *
     * @param accountStatementRecordDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccountStatementRecordDTO> partialUpdate(AccountStatementRecordDTO accountStatementRecordDTO) {
        LOG.debug("Request to partially update AccountStatementRecord : {}", accountStatementRecordDTO);

        return accountStatementRecordRepository
            .findById(accountStatementRecordDTO.getId())
            .map(existingAccountStatementRecord -> {
                accountStatementRecordMapper.partialUpdate(existingAccountStatementRecord, accountStatementRecordDTO);
                existingAccountStatementRecord.validateAndPrepareForSave();
                existingAccountStatementRecord = accountStatementRecordRepository.save(existingAccountStatementRecord);
                return existingAccountStatementRecord;
            })
            .map(accountStatementRecordRepository::save)
            .map(savedRecord -> {
                savedRecord.validateAndPrepareForSave();
                cardAccountService.recalculateBalance(savedRecord.getAccountId());
                return savedRecord;
            })
            .map(accountStatementRecordMapper::toDto);
    }

    /**
     * Get all the accountStatementRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountStatementRecordDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all AccountStatementRecords");
        return accountStatementRecordRepository.findAll(pageable).map(accountStatementRecordMapper::toDto);
    }

    public Page<AccountStatementRecordDTO> findAllByAccountId(UUID accountId, Pageable pageable) {
        LOG.debug("Request to get all AccountStatementRecords for accountId: {}", accountId);
        return accountStatementRecordRepository.findAllByAccountId(accountId, pageable).map(accountStatementRecordMapper::toDto);
    }

    /**
     * Get one accountStatementRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountStatementRecordDTO> findOne(UUID id) {
        LOG.debug("Request to get AccountStatementRecord : {}", id);
        return accountStatementRecordRepository.findById(id).map(accountStatementRecordMapper::toDto);
    }

    /**
     * Delete the accountStatementRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete AccountStatementRecord : {}", id);
        AccountStatementRecord record = accountStatementRecordRepository
            .findById(id)
            .orElseThrow(() -> new WallesterApiException(422, "Account statement record not found"));
        UUID accountId = record.getAccountId();
        accountStatementRecordRepository.deleteById(id);
        accountStatementRecordRepository.flush();
        cardAccountService.recalculateBalance(accountId);
    }

    public void createFeesFor(AccountStatementRecordDTO authorization) {
        if (authorization.getType() != AccountStatementRecordType.Authorization) {
            return;
        }
        if (!isDomestic(authorization.getMerchantCountryCode())) {
            createFee(AccountStatementRecordGroup.AuthorizationInternationalFixedFee, BigDecimal.valueOf(1.99), authorization);
        }
        if (!Objects.equals(authorization.getTransactionCurrencyCode(), authorization.getAccountCurrencyCode())) {
            createFee(
                AccountStatementRecordGroup.CurrencyExchangeFee,
                authorization.getAccountAmount().multiply(BigDecimal.valueOf(0.0075)),
                authorization
            );
        }
        if (Objects.equals(AccountStatementRecordGroup.Withdraw, authorization.getGroup())) {
            createFee(AccountStatementRecordGroup.AuthorizationATMWithdrawalFixedFee, BigDecimal.valueOf(3.00), authorization);
        }
        if (Objects.equals(AccountStatementRecordGroup.Refund, authorization.getGroup())) {
            createFee(
                AccountStatementRecordGroup.AvailableFundsRefundFee,
                authorization.getAccountAmount().multiply(BigDecimal.valueOf(0.005)),
                authorization
            );
        }
    }

    private boolean isDomestic(CountryCode merchantCountryCode) {
        return EEA_COUNTRIES.contains(merchantCountryCode);
    }

    private void createFee(AccountStatementRecordGroup group, BigDecimal amount, AccountStatementRecordDTO authorization) {
        AccountStatementRecordDTO fee = new AccountStatementRecordDTO();
        fee.setAccountId(authorization.getAccountId());
        fee.setCardId(authorization.getCardId());
        fee.setOriginalAuthorizationId(authorization.getId());
        fee.setType(AccountStatementRecordType.Fee);
        fee.setGroup(group);
        fee.setAccountCurrencyCode(authorization.getAccountCurrencyCode());
        fee.setTransactionCurrencyCode(authorization.getAccountCurrencyCode());
        fee.setAccountAmount(amount.setScale(2, RoundingMode.HALF_UP).abs().negate());
        fee.setTransactionAmount(fee.getAccountAmount());
        fee.setDate(authorization.getDate().plusSeconds(1));
        fee.setDescription(group.toString());
        fee.setStatus(AccountStatementRecordStatus.Pending);
        fee.setResponse(AccountStatementRecordResponse.Approved);

        save(fee);
    }

    // get the last updated active card of the account. Active means CardStatus.Active. If there is no active card, use the last updated card despite of the status. Do nothing if nothing found.
    private void setCardIdIfNotSet(AccountStatementRecordDTO accountStatementRecordDTO) {
        if (accountStatementRecordDTO.getCardId() == null && accountStatementRecordDTO.getAccountId() != null) {
            // First try to find an active card
            Card activeCard = cardRepository
                .findFirstByAccountIdAndStatusOrderByUpdatedAtDesc(accountStatementRecordDTO.getAccountId(), CardStatus.Active)
                .orElse(null);

            if (activeCard != null) {
                accountStatementRecordDTO.setCardId(activeCard.getId());
            } else {
                // If no active card found, get the last updated card regardless of status
                Card lastUpdatedCard = cardRepository
                    .findFirstByAccountIdOrderByUpdatedAtDesc(accountStatementRecordDTO.getAccountId())
                    .orElse(null);
                if (lastUpdatedCard != null) {
                    accountStatementRecordDTO.setCardId(lastUpdatedCard.getId());
                }
            }
        }
    }
}
