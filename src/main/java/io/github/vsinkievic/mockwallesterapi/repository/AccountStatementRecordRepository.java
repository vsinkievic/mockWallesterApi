package io.github.vsinkievic.mockwallesterapi.repository;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountStatementRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountStatementRecordRepository extends JpaRepository<AccountStatementRecord, UUID> {
    Page<AccountStatementRecord> findByAccountIdAndDateBetween(UUID accountId, Instant fromDate, Instant toDate, Pageable pageable);
    List<AccountStatementRecord> findByAccountId(UUID accountId);
    Optional<AccountStatementRecord> findByExternalId(String externalId);
}
