package io.github.vsinkievic.mockwallesterapi.repository;

import io.github.vsinkievic.mockwallesterapi.domain.Company;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Page<Company> findByRegistrationNumber(String registrationNumber, Pageable pageable);
}
