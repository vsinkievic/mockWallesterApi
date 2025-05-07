package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.AccountStatementRecordDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.AccountStatementRecordMapper;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import java.util.Optional;
import java.util.UUID;
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

    public AccountStatementRecordService(
        AccountStatementRecordRepository accountStatementRecordRepository,
        AccountStatementRecordMapper accountStatementRecordMapper,
        CardAccountService cardAccountService
    ) {
        this.accountStatementRecordRepository = accountStatementRecordRepository;
        this.accountStatementRecordMapper = accountStatementRecordMapper;
        this.cardAccountService = cardAccountService;
    }

    /**
     * Save a accountStatementRecord.
     *
     * @param accountStatementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountStatementRecordDTO save(AccountStatementRecordDTO accountStatementRecordDTO) {
        LOG.debug("Request to save AccountStatementRecord : {}", accountStatementRecordDTO);

        if (accountStatementRecordDTO.getId() == null) {
            if (accountStatementRecordRepository.findByExternalId(accountStatementRecordDTO.getExternalId()).isPresent()) {
                throw new WallesterApiException(
                    422,
                    "Account statement record with external ID " + accountStatementRecordDTO.getExternalId() + " already exists"
                );
            }
        }
        AccountStatementRecord accountStatementRecord = accountStatementRecordMapper.toEntity(accountStatementRecordDTO);
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
        AccountStatementRecord accountStatementRecord = accountStatementRecordMapper.toEntity(accountStatementRecordDTO);
        accountStatementRecord = accountStatementRecordRepository.save(accountStatementRecord);
        cardAccountService.recalculateBalance(accountStatementRecord.getAccountId());
        return accountStatementRecordMapper.toDto(accountStatementRecord);
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
                return existingAccountStatementRecord;
            })
            .map(accountStatementRecordRepository::save)
            .map(savedRecord -> {
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
        accountStatementRecordRepository.deleteById(id);
        cardAccountService.recalculateBalance(id);
    }
}
