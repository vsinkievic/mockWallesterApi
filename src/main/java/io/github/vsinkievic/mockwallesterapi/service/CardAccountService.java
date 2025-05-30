package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.domain.Company;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CompanyRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardAccountMapper;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import io.micrometer.common.util.StringUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.github.vsinkievic.mockwallesterapi.domain.CardAccount}.
 */
@Service
@Transactional
public class CardAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(CardAccountService.class);

    private final CardAccountRepository cardAccountRepository;
    private final CompanyRepository companyRepository;

    private final CardAccountMapper cardAccountMapper;
    private final AccountStatementRecordRepository statementRepository;

    public CardAccountService(
        CardAccountRepository cardAccountRepository,
        CardAccountMapper cardAccountMapper,
        CompanyRepository companyRepository,
        AccountStatementRecordRepository statementRepository
    ) {
        this.cardAccountRepository = cardAccountRepository;
        this.cardAccountMapper = cardAccountMapper;
        this.companyRepository = companyRepository;
        this.statementRepository = statementRepository;
    }

    /**
     * Save a cardAccount.
     *
     * @param cardAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public CardAccountDTO save(CardAccountDTO cardAccountDTO) {
        LOG.debug("Request to save CardAccount : {}", cardAccountDTO);
        CardAccount cardAccount = cardAccountMapper.toEntity(cardAccountDTO);

        if (cardAccount.getId() == null) {
            Company company = null;
            if (cardAccount.getCompanyId() != null) {
                company = companyRepository
                    .findById(cardAccount.getCompanyId())
                    .orElseThrow(() -> new WallesterApiException(422, "Company not found"));
            } else throw new WallesterApiException(422, "Company ID is required");

            if (StringUtils.isNotBlank(cardAccount.getExternalId())) {
                cardAccountRepository
                    .findByExternalId(cardAccount.getExternalId())
                    .ifPresent(existingAccount -> {
                        throw new WallesterApiException(422, "Account with such external ID already exists");
                    });
            }

            if (cardAccount.getDailyContactlessPurchase() == null) cardAccount.setDailyContactlessPurchase(company.getLimitDailyPurchase());
            if (cardAccount.getDailyInternetPurchase() == null) cardAccount.setDailyInternetPurchase(company.getLimitDailyPurchase());
            if (cardAccount.getDailyPurchase() == null) cardAccount.setDailyPurchase(company.getLimitDailyPurchase());
            if (cardAccount.getDailyWithdrawal() == null) cardAccount.setDailyWithdrawal(company.getLimitDailyWithdrawal());
            if (cardAccount.getMonthlyContactlessPurchase() == null) cardAccount.setMonthlyContactlessPurchase(
                company.getLimitMonthlyPurchase()
            );
            if (cardAccount.getMonthlyInternetPurchase() == null) cardAccount.setMonthlyInternetPurchase(company.getLimitMonthlyPurchase());
            if (cardAccount.getMonthlyPurchase() == null) cardAccount.setMonthlyPurchase(company.getLimitMonthlyPurchase());
            if (cardAccount.getMonthlyWithdrawal() == null) cardAccount.setMonthlyWithdrawal(company.getLimitMonthlyWithdrawal());
            if (cardAccount.getWeeklyContactlessPurchase() == null) cardAccount.setWeeklyContactlessPurchase(
                company.getLimitDailyPurchase()
            );
            if (cardAccount.getWeeklyInternetPurchase() == null) cardAccount.setWeeklyInternetPurchase(company.getLimitDailyPurchase());
            if (cardAccount.getWeeklyPurchase() == null) cardAccount.setWeeklyPurchase(company.getLimitDailyPurchase());
            if (cardAccount.getWeeklyWithdrawal() == null) cardAccount.setWeeklyWithdrawal(company.getLimitDailyWithdrawal());

            cardAccount.setBalance(BigDecimal.ZERO);
            cardAccount.setAvailableAmount(BigDecimal.ZERO);
            cardAccount.setBlockedAmount(BigDecimal.ZERO);

            cardAccount.setCreatedAt(Instant.now());
            cardAccount.setStatus(AccountStatus.Active);
        } else {
            if (StringUtils.isNotBlank(cardAccount.getExternalId())) {
                cardAccountRepository
                    .findByExternalIdAndIdNot(cardAccount.getExternalId(), cardAccount.getId())
                    .ifPresent(existingAccount -> {
                        throw new WallesterApiException(422, "Account with such external ID already exists");
                    });
            }
        }

        cardAccount.setUpdatedAt(Instant.now());
        cardAccount = cardAccountRepository.save(cardAccount);
        return cardAccountMapper.toDto(cardAccount);
    }

    /**
     * Update a cardAccount.
     *
     * @param cardAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public CardAccountDTO update(CardAccountDTO cardAccountDTO) {
        LOG.debug("Request to update CardAccount : {}", cardAccountDTO);
        CardAccount cardAccount = cardAccountMapper.toEntity(cardAccountDTO);

        if (StringUtils.isNotBlank(cardAccount.getExternalId())) {
            cardAccountRepository
                .findByExternalIdAndIdNot(cardAccount.getExternalId(), cardAccount.getId())
                .ifPresent(existingAccount -> {
                    throw new WallesterApiException(422, "Account with such external ID already exists");
                });
        }

        cardAccount.setUpdatedAt(Instant.now());
        cardAccount = cardAccountRepository.save(cardAccount);
        return cardAccountMapper.toDto(cardAccount);
    }

    /**
     * Partially update a cardAccount.
     *
     * @param cardAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CardAccountDTO> partialUpdate(CardAccountDTO cardAccountDTO) {
        LOG.debug("Request to partially update CardAccount : {}", cardAccountDTO);

        return cardAccountRepository
            .findById(cardAccountDTO.getId())
            .map(existingCardAccount -> {
                cardAccountMapper.partialUpdate(existingCardAccount, cardAccountDTO);

                // Check external ID uniqueness if it's being updated
                if (StringUtils.isNotBlank(existingCardAccount.getExternalId())) {
                    cardAccountRepository
                        .findByExternalIdAndIdNot(existingCardAccount.getExternalId(), existingCardAccount.getId())
                        .ifPresent(existingAccount -> {
                            throw new WallesterApiException(422, "Account with such external ID already exists");
                        });
                }
                existingCardAccount.setUpdatedAt(Instant.now());
                return existingCardAccount;
            })
            .map(cardAccountRepository::save)
            .map(cardAccountMapper::toDto);
    }

    /**
     * Get all the cardAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardAccountDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CardAccounts");
        return cardAccountRepository.findAll(pageable).map(cardAccountMapper::toDto);
    }

    /**
     * Get one cardAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CardAccountDTO> findOne(UUID id) {
        LOG.debug("Request to get CardAccount : {}", id);
        return cardAccountRepository.findById(id).map(cardAccountMapper::toDto);
    }

    /**
     * Get one cardAccount by externalId.
     *
     * @param externalId the externalId of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CardAccountDTO> findOneByExternalId(String externalId) {
        LOG.debug("Request to get CardAccount by externalId : {}", externalId);
        return cardAccountRepository.findByExternalId(externalId).map(cardAccountMapper::toDto);
    }

    /**
     * Delete the cardAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete CardAccount : {}", id);
        cardAccountRepository.deleteById(id);
    }

    /**
     * Get accounts by company ID.
     *
     * @param companyId the company ID.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardAccountDTO> findByCompanyId(UUID companyId, Pageable pageable) {
        LOG.debug("Request to get CardAccounts by company ID: {}", companyId);
        return cardAccountRepository.findByCompanyId(companyId, pageable).map(cardAccountMapper::toDto);
    }

    /**
     * Get accounts by person ID.
     *
     * @param personId the person ID.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardAccountDTO> findByPersonId(UUID personId, Pageable pageable) {
        LOG.debug("Request to get CardAccounts by person ID: {}", personId);
        return cardAccountRepository.findByPersonId(personId, pageable).map(cardAccountMapper::toDto);
    }

    /**
     * Get accounts by status.
     *
     * @param status the account status.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardAccountDTO> findByStatus(AccountStatus status, Pageable pageable) {
        LOG.debug("Request to get CardAccounts by status: {}", status);
        return cardAccountRepository.findByStatus(status, pageable).map(cardAccountMapper::toDto);
    }

    /**
     * Get accounts by company ID and status.
     *
     * @param companyId the company ID.
     * @param status the account status.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardAccountDTO> findByCompanyIdAndStatus(UUID companyId, AccountStatus status, Pageable pageable) {
        LOG.debug("Request to get CardAccounts by company ID: {} and status: {}", companyId, status);
        return cardAccountRepository.findByCompanyIdAndStatus(companyId, status, pageable).map(cardAccountMapper::toDto);
    }

    /**
     * Get accounts by person ID and status.
     *
     * @param personId the person ID.
     * @param status the account status.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardAccountDTO> findByPersonIdAndStatus(UUID personId, AccountStatus status, Pageable pageable) {
        LOG.debug("Request to get CardAccounts by person ID: {} and status: {}", personId, status);
        return cardAccountRepository.findByPersonIdAndStatus(personId, status, pageable).map(cardAccountMapper::toDto);
    }

    public CardAccountDTO recalculateBalance(UUID accountId) {
        LOG.debug("Request to recalculate balance for CardAccount : {}", accountId);

        CardAccount cardAccount = cardAccountRepository
            .findById(accountId)
            .orElseThrow(() -> new WallesterApiException(404, "Card account not found"));

        List<AccountStatementRecord> records = statementRepository.findByAccountId(accountId);

        BigDecimal balanceAmount = BigDecimal.ZERO;
        BigDecimal blockedAmount = BigDecimal.ZERO;
        Instant lastRecordDate = cardAccount.getCreatedAt();
        if (lastRecordDate == null) lastRecordDate = LocalDate.of(1900, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        LOG.debug("Account_ID: {} - Last record date before recalculation: {}", cardAccount.getId(), lastRecordDate);

        for (AccountStatementRecord record : records) {
            if (record.getIsDeclined()) continue;

            if (record.getDate().isAfter(lastRecordDate)) {
                lastRecordDate = record.getDate();
                LOG.debug("Account_ID: {} - Last record date updated to: {}", cardAccount.getId(), lastRecordDate);
            }

            BigDecimal balanceDelta = null;
            BigDecimal blockedDelta = null;
            switch (record.getType()) {
                case Authorization:
                    if (AccountStatementRecordStatus.Pending.equals(record.getStatus())) {
                        //                        if (record.getAccountAmount().compareTo(BigDecimal.ZERO) < 0) {
                        //                            blockedDelta = record.getAccountAmount().negate();
                        //                        } else {
                        blockedDelta = record.getAccountAmount();
                        //                        }
                    }
                    break;
                case Transaction:
                    balanceDelta = record.getAccountAmount();
                    break;
                case Fee:
                    switch (record.getStatus()) {
                        case Pending:
                            //                            if (record.getAccountAmount().compareTo(BigDecimal.ZERO) < 0) {
                            //                                blockedDelta = record.getAccountAmount().negate();
                            //                            } else {
                            blockedDelta = record.getAccountAmount();
                            //                            }
                            break;
                        case Completed:
                            balanceDelta = record.getAccountAmount();
                            break;
                    }

                    break;
                case AccountAdjustment:
                    balanceDelta = record.getAccountAmount();
                    break;
            }

            if (balanceDelta != null) {
                balanceAmount = balanceAmount.add(balanceDelta);
            }

            if (blockedDelta != null) {
                blockedAmount = blockedAmount.add(blockedDelta.negate());
            }
        }

        boolean isSaveRequired =
            cardAccount.getBalance().compareTo(balanceAmount) != 0 || cardAccount.getBlockedAmount().compareTo(blockedAmount) != 0;

        if (isSaveRequired) {
            if (blockedAmount.compareTo(BigDecimal.ZERO) < 0) {
                blockedAmount = BigDecimal.ZERO;
            }
            cardAccount.setBalance(balanceAmount);
            cardAccount.setBlockedAmount(blockedAmount);
            cardAccount.setAvailableAmount(balanceAmount.subtract(blockedAmount));
            cardAccount.setUpdatedAt(Instant.now());
            cardAccount = cardAccountRepository.save(cardAccount);
        }
        /*
        if (lastRecordDate.isAfter(cardAccount.getUpdatedAt())) {
            LOG.debug(
                "Account_ID: {} - Last record date after recalculation: {} vs account.updatedAt: {}",
                cardAccount.getId(),
                lastRecordDate,
                cardAccount.getUpdatedAt()
            );
            cardAccount.setUpdatedAt(lastRecordDate);
        }
        LOG.debug("Account_ID: {} - Card account updatedAt after recalculation: {}", cardAccount.getId(), cardAccount.getUpdatedAt());
        if (isSaveRequired) {
            cardAccountRepository.save(cardAccount);
        }
*/
        return cardAccountMapper.toDto(cardAccountRepository.save(cardAccount));
    }
}
