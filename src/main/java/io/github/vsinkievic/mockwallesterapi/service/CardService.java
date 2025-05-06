package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardType;
import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CardRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardMapper;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
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
    private final CardAccountRepository cardAccountRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper, CardAccountRepository cardAccountRepository) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.cardAccountRepository = cardAccountRepository;
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

        if (card.getId() == null) {
            if (card.getAccountId() == null) {
                throw new WallesterApiException(422, "Account ID is required");
            } else {
                CardAccount cardAccount = cardAccountRepository
                    .findById(card.getAccountId())
                    .orElseThrow(() -> new WallesterApiException(422, "Account not found"));

                card.setCompanyId(cardAccount.getCompanyId());
                card.setPersonId(cardAccount.getPersonId());

                if (card.getLimitAllTimeContactlessPurchase() == null) card.setLimitAllTimeContactlessPurchase(
                    cardAccount.getMonthlyContactlessPurchase()
                );

                if (card.getLimitDailyContactlessPurchase() == null) card.setLimitDailyContactlessPurchase(
                    cardAccount.getDailyContactlessPurchase()
                );

                if (card.getLimitDailyInternetPurchase() == null) card.setLimitDailyInternetPurchase(
                    cardAccount.getDailyInternetPurchase()
                );

                if (card.getLimitDailyPurchase() == null) card.setLimitDailyPurchase(cardAccount.getDailyPurchase());

                if (card.getLimitDailyWithdrawal() == null) card.setLimitDailyWithdrawal(cardAccount.getDailyWithdrawal());

                if (card.getLimitMonthlyContactlessPurchase() == null) card.setLimitMonthlyContactlessPurchase(
                    cardAccount.getMonthlyContactlessPurchase()
                );

                if (card.getLimitMonthlyInternetPurchase() == null) card.setLimitMonthlyInternetPurchase(
                    cardAccount.getMonthlyInternetPurchase()
                );

                if (card.getLimitMonthlyPurchase() == null) card.setLimitMonthlyPurchase(cardAccount.getMonthlyPurchase());

                if (card.getLimitMonthlyWithdrawal() == null) card.setLimitMonthlyWithdrawal(cardAccount.getMonthlyWithdrawal());

                if (card.getLimitWeeklyContactlessPurchase() == null) card.setLimitWeeklyContactlessPurchase(
                    cardAccount.getWeeklyContactlessPurchase()
                );

                if (card.getLimitWeeklyInternetPurchase() == null) card.setLimitWeeklyInternetPurchase(
                    cardAccount.getWeeklyInternetPurchase()
                );

                if (card.getLimitWeeklyPurchase() == null) card.setLimitWeeklyPurchase(cardAccount.getWeeklyPurchase());

                if (card.getLimitWeeklyWithdrawal() == null) card.setLimitWeeklyWithdrawal(cardAccount.getWeeklyWithdrawal());
            }

            card.setMaskedCardNumber(generateRandomCardNumber());
            card.setExpiryDate(calculateExpiryDate());
            card.setStatus(card.getType() == CardType.Virtual ? CardStatus.Active : CardStatus.Personalized);
            card.setCreatedAt(Instant.now());
            card.setUpdatedAt(Instant.now());
        }
        if (StringUtils.isNotBlank(card.getExternalId())) {
            cardRepository
                .findByExternalId(card.getExternalId())
                .ifPresent(existingCard -> {
                    throw new WallesterApiException(422, "Card with such external ID already exists");
                });
        }

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

        if (StringUtils.isNotBlank(card.getExternalId())) {
            cardRepository
                .findByExternalId(card.getExternalId())
                .ifPresent(existingCard -> {
                    throw new WallesterApiException(422, "Card with such external ID already exists");
                });
        }
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

        if (StringUtils.isNotBlank(cardDTO.getExternalId())) {
            cardRepository
                .findByExternalId(cardDTO.getExternalId())
                .ifPresent(existingCard -> {
                    throw new WallesterApiException(422, "Card with such external ID already exists");
                });
        }

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

    private String generateRandomCardNumber() {
        String bin = "474362";
        String masked = "******";
        int randomLast4 = (int) (Math.random() * 10000); // 0 to 9999
        String last4 = String.format("%04d", randomLast4); // pad with zeros if needed
        return bin + masked + last4;
    }

    private Instant calculateExpiryDate() {
        LocalDate now = LocalDate.now();
        LocalDate expiryDate = now.plusYears(3).with(java.time.temporal.TemporalAdjusters.lastDayOfMonth());
        LocalDateTime expiryDateTime = expiryDate.atTime(23, 59, 59);
        return expiryDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
