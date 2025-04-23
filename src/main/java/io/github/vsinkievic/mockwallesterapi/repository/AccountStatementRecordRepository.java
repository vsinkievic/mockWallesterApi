package io.github.vsinkievic.mockwallesterapi.repository;

import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountStatementRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountStatementRecordRepository extends JpaRepository<AccountStatementRecord, UUID> {}
