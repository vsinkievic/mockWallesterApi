package io.github.vsinkievic.mockwallesterapi.repository;

import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Card entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByExternalId(String externalId);
    Optional<Card> findFirstByAccountIdAndStatusOrderByUpdatedAtDesc(UUID accountId, CardStatus status);
    Optional<Card> findFirstByAccountIdOrderByUpdatedAtDesc(UUID accountId);
}
