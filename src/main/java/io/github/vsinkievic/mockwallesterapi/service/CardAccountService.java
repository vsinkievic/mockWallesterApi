package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardAccountMapper;
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

    private final CardAccountMapper cardAccountMapper;

    public CardAccountService(CardAccountRepository cardAccountRepository, CardAccountMapper cardAccountMapper) {
        this.cardAccountRepository = cardAccountRepository;
        this.cardAccountMapper = cardAccountMapper;
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
}
