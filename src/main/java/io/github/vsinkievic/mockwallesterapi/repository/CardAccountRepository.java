package io.github.vsinkievic.mockwallesterapi.repository;

import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CardAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardAccountRepository extends JpaRepository<CardAccount, UUID> {}
