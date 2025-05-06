package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.repository.CardRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.github.vsinkievic.mockwallesterapi.domain.Card}.
 */
@Service
@Transactional
public class CardService {

    private static final Logger LOG = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    /**
     * Save a card.
     *
     * @param cardDTO the entity to save.
     * @return the persisted entity.
     */
    public CardDTO save(CardDTO cardDTO) {
        LOG.debug("Request to save Card : {}", cardDTO);
        Card card = cardMapper.toEntity(cardDTO);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }

    /**
     * Update a card.
     *
     * @param cardDTO the entity to save.
     * @return the persisted entity.
     */
    public CardDTO update(CardDTO cardDTO) {
        LOG.debug("Request to update Card : {}", cardDTO);
        Card card = cardMapper.toEntity(cardDTO);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }

    /**
     * Partially update a card.
     *
     * @param cardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CardDTO> partialUpdate(CardDTO cardDTO) {
        LOG.debug("Request to partially update Card : {}", cardDTO);

        return cardRepository
            .findById(cardDTO.getId())
            .map(existingCard -> {
                cardMapper.partialUpdate(existingCard, cardDTO);

                return existingCard;
            })
            .map(cardRepository::save)
            .map(cardMapper::toDto);
    }

    /**
     * Get all the cards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Cards");
        return cardRepository.findAll(pageable).map(cardMapper::toDto);
    }

    /**
     * Get one card by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CardDTO> findOne(UUID id) {
        LOG.debug("Request to get Card : {}", id);
        return cardRepository.findById(id).map(cardMapper::toDto);
    }

    /**
     * Get one card by externalId.
     *
     * @param externalId the externalId of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CardDTO> findOneByExternalId(String externalId) {
        LOG.debug("Request to get Card by externalId : {}", externalId);
        return cardRepository.findByExternalId(externalId).map(cardMapper::toDto);
    }

    /**
     * Delete the card by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete Card : {}", id);
        cardRepository.deleteById(id);
    }
}
