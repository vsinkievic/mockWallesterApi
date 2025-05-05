package io.github.vsinkievic.mockwallesterapi.repository;

import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CardAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardAccountRepository extends JpaRepository<CardAccount, UUID> {
    Optional<CardAccount> findByExternalId(String externalId);

    Page<CardAccount> findByCompanyId(UUID companyId, Pageable pageable);

    Page<CardAccount> findByPersonId(UUID personId, Pageable pageable);

    Page<CardAccount> findByStatus(AccountStatus status, Pageable pageable);

    Page<CardAccount> findByCompanyIdAndStatus(UUID companyId, AccountStatus status, Pageable pageable);

    Page<CardAccount> findByPersonIdAndStatus(UUID personId, AccountStatus status, Pageable pageable);
}
