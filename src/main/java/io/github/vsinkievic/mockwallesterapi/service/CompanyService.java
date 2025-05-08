package io.github.vsinkievic.mockwallesterapi.service;

import io.github.vsinkievic.mockwallesterapi.domain.Company;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CompanyStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.KybStatus;
import io.github.vsinkievic.mockwallesterapi.repository.CompanyRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CompanyMapper;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.github.vsinkievic.mockwallesterapi.domain.Company}.
 */
@Service
@Transactional
public class CompanyService {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);
    private static final BigDecimal DEFAULT_LIMIT_VALUE = new BigDecimal(1000);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyDTO save(CompanyDTO companyDTO) {
        LOG.debug("Request to save Company : {}", companyDTO);

        if (companyDTO.getId() == null) {
            if (companyDTO.getRegistrationNumber() == null || companyDTO.getRegistrationNumber().trim().isEmpty()) {
                throw new WallesterApiException(400, 400L, "BAD_REQUEST", "Registration number is required");
            }

            if (companyRepository.findByRegistrationNumber(companyDTO.getRegistrationNumber(), Pageable.unpaged()).hasContent()) {
                throw new WallesterApiException(409, 409L, "CONFLICT", "Company with this registration number already exists");
            }
        }

        Company company = companyMapper.toEntity(companyDTO);

        if (company.getId() == null) {
            if (company.getLimitDailyPurchase() == null) {
                company.setLimitDailyPurchase(DEFAULT_LIMIT_VALUE);
            }

            if (company.getLimitDailyWithdrawal() == null) {
                company.setLimitDailyWithdrawal(DEFAULT_LIMIT_VALUE);
            }

            if (company.getLimitMonthlyPurchase() == null) {
                company.setLimitMonthlyPurchase(DEFAULT_LIMIT_VALUE);
            }

            if (company.getLimitMonthlyWithdrawal() == null) {
                company.setLimitMonthlyWithdrawal(DEFAULT_LIMIT_VALUE);
            }

            company.setKybStatus(KybStatus.Verified);
            company.setStatus(CompanyStatus.Active);
        }
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Update a company.
     *
     * @param companyDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyDTO update(CompanyDTO companyDTO) {
        LOG.debug("Request to update Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Partially update a company.
     *
     * @param companyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompanyDTO> partialUpdate(CompanyDTO companyDTO) {
        LOG.debug("Request to partially update Company : {}", companyDTO);

        return companyRepository
            .findById(companyDTO.getId())
            .map(existingCompany -> {
                companyMapper.partialUpdate(existingCompany, companyDTO);

                return existingCompany;
            })
            .map(companyRepository::save)
            .map(companyMapper::toDto);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Companies");
        return companyRepository.findAll(pageable).map(companyMapper::toDto);
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyDTO> findOne(UUID id) {
        LOG.debug("Request to get Company : {}", id);
        return companyRepository.findById(id).map(companyMapper::toDto);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }

    /**
     * Find companies by registration number.
     *
     * @param registrationNumber the registration number to search for.
     * @param pageable the pagination information.
     * @return the page of companies with matching registration number.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByRegistrationNumber(String registrationNumber, Pageable pageable) {
        LOG.debug("Request to find Companies by registration number: {}", registrationNumber);
        return companyRepository.findByRegistrationNumber(registrationNumber, pageable).map(companyMapper::toDto);
    }
}
