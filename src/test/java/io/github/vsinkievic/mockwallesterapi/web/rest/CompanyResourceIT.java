package io.github.vsinkievic.mockwallesterapi.web.rest;

import static io.github.vsinkievic.mockwallesterapi.domain.CompanyAsserts.*;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.createUpdateProxyForBean;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vsinkievic.mockwallesterapi.IntegrationTest;
import io.github.vsinkievic.mockwallesterapi.domain.Company;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.KybStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CompanyStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.LanguageCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.RiskProfile;
import io.github.vsinkievic.mockwallesterapi.repository.CompanyRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CompanyMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@Disabled
class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final CountryCode DEFAULT_REG_ADDRESS_COUNTRY_CODE = CountryCode.ABW;
    private static final CountryCode UPDATED_REG_ADDRESS_COUNTRY_CODE = CountryCode.AFG;

    private static final String DEFAULT_REG_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_REG_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_REG_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_REG_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_REG_ADDRESS_CITY = "AAAAAAAAAA";
    private static final String UPDATED_REG_ADDRESS_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_REG_ADDRESS_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REG_ADDRESS_POSTAL_CODE = "BBBBBBBBBB";

    private static final CountryCode DEFAULT_HQ_ADDRESS_COUNTRY_CODE = CountryCode.ABW;
    private static final CountryCode UPDATED_HQ_ADDRESS_COUNTRY_CODE = CountryCode.AFG;

    private static final String DEFAULT_HQ_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_HQ_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HQ_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_HQ_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_HQ_ADDRESS_CITY = "AAAAAAAAAA";
    private static final String UPDATED_HQ_ADDRESS_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_HQ_ADDRESS_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HQ_ADDRESS_POSTAL_CODE = "BBBBBBBBBB";

    private static final RiskProfile DEFAULT_RISK_PROFILE = RiskProfile.Low;
    private static final RiskProfile UPDATED_RISK_PROFILE = RiskProfile.Medium;

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DELETED_BY = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_INDUSTRY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_INCORPORATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_INCORPORATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BUSINESS_RELATIONSHIP_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_RELATIONSHIP_PURPOSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SANCTIONS_RELATED = false;
    private static final Boolean UPDATED_IS_SANCTIONS_RELATED = true;

    private static final Boolean DEFAULT_IS_ADVERSE_MEDIA_INVOLVED = false;
    private static final Boolean UPDATED_IS_ADVERSE_MEDIA_INVOLVED = true;

    private static final String DEFAULT_EMPLOYEES_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEES_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_SPENDING_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CARD_SPENDING_AMOUNT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LIMIT_DAILY_PURCHASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_DAILY_PURCHASE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_DAILY_WITHDRAWAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_DAILY_WITHDRAWAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_MONTHLY_PURCHASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_MONTHLY_PURCHASE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_MONTHLY_WITHDRAWAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_MONTHLY_WITHDRAWAL = new BigDecimal(2);

    private static final KybStatus DEFAULT_KYB_STATUS = KybStatus.PendingManual;
    private static final KybStatus UPDATED_KYB_STATUS = KybStatus.Pending;

    private static final CompanyStatus DEFAULT_STATUS = CompanyStatus.Active;
    private static final CompanyStatus UPDATED_STATUS = CompanyStatus.Deactivated;

    private static final Boolean DEFAULT_PUSH_NOTIFICATIONS_ENABLED = false;
    private static final Boolean UPDATED_PUSH_NOTIFICATIONS_ENABLED = true;

    private static final LanguageCode DEFAULT_PREFERRED_LANGUAGE_CODE = LanguageCode.AAR;
    private static final LanguageCode UPDATED_PREFERRED_LANGUAGE_CODE = LanguageCode.ENG;

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    private Company insertedCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity() {
        return new Company()
            .name(DEFAULT_NAME)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .regAddressCountryCode(DEFAULT_REG_ADDRESS_COUNTRY_CODE)
            .regAddress1(DEFAULT_REG_ADDRESS_1)
            .regAddress2(DEFAULT_REG_ADDRESS_2)
            .regAddressCity(DEFAULT_REG_ADDRESS_CITY)
            .regAddressPostalCode(DEFAULT_REG_ADDRESS_POSTAL_CODE)
            .hqAddressCountryCode(DEFAULT_HQ_ADDRESS_COUNTRY_CODE)
            .hqAddress1(DEFAULT_HQ_ADDRESS_1)
            .hqAddress2(DEFAULT_HQ_ADDRESS_2)
            .hqAddressCity(DEFAULT_HQ_ADDRESS_CITY)
            .hqAddressPostalCode(DEFAULT_HQ_ADDRESS_POSTAL_CODE)
            .riskProfile(DEFAULT_RISK_PROFILE)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .deletedAt(DEFAULT_DELETED_AT)
            .deletedBy(DEFAULT_DELETED_BY)
            .industryType(DEFAULT_INDUSTRY_TYPE)
            .dateOfIncorporation(DEFAULT_DATE_OF_INCORPORATION)
            .businessRelationshipPurpose(DEFAULT_BUSINESS_RELATIONSHIP_PURPOSE)
            .isSanctionsRelated(DEFAULT_IS_SANCTIONS_RELATED)
            .isAdverseMediaInvolved(DEFAULT_IS_ADVERSE_MEDIA_INVOLVED)
            .employeesQuantity(DEFAULT_EMPLOYEES_QUANTITY)
            .cardSpendingAmount(DEFAULT_CARD_SPENDING_AMOUNT)
            .limitDailyPurchase(DEFAULT_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(DEFAULT_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(DEFAULT_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(DEFAULT_LIMIT_MONTHLY_WITHDRAWAL)
            .kybStatus(DEFAULT_KYB_STATUS)
            .status(DEFAULT_STATUS)
            .pushNotificationsEnabled(DEFAULT_PUSH_NOTIFICATIONS_ENABLED)
            .preferredLanguageCode(DEFAULT_PREFERRED_LANGUAGE_CODE)
            .vatNumber(DEFAULT_VAT_NUMBER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity() {
        return new Company()
            .name(UPDATED_NAME)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .regAddressCountryCode(UPDATED_REG_ADDRESS_COUNTRY_CODE)
            .regAddress1(UPDATED_REG_ADDRESS_1)
            .regAddress2(UPDATED_REG_ADDRESS_2)
            .regAddressCity(UPDATED_REG_ADDRESS_CITY)
            .regAddressPostalCode(UPDATED_REG_ADDRESS_POSTAL_CODE)
            .hqAddressCountryCode(UPDATED_HQ_ADDRESS_COUNTRY_CODE)
            .hqAddress1(UPDATED_HQ_ADDRESS_1)
            .hqAddress2(UPDATED_HQ_ADDRESS_2)
            .hqAddressCity(UPDATED_HQ_ADDRESS_CITY)
            .hqAddressPostalCode(UPDATED_HQ_ADDRESS_POSTAL_CODE)
            .riskProfile(UPDATED_RISK_PROFILE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedBy(UPDATED_DELETED_BY)
            .industryType(UPDATED_INDUSTRY_TYPE)
            .dateOfIncorporation(UPDATED_DATE_OF_INCORPORATION)
            .businessRelationshipPurpose(UPDATED_BUSINESS_RELATIONSHIP_PURPOSE)
            .isSanctionsRelated(UPDATED_IS_SANCTIONS_RELATED)
            .isAdverseMediaInvolved(UPDATED_IS_ADVERSE_MEDIA_INVOLVED)
            .employeesQuantity(UPDATED_EMPLOYEES_QUANTITY)
            .cardSpendingAmount(UPDATED_CARD_SPENDING_AMOUNT)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(UPDATED_LIMIT_MONTHLY_WITHDRAWAL)
            .kybStatus(UPDATED_KYB_STATUS)
            .status(UPDATED_STATUS)
            .pushNotificationsEnabled(UPDATED_PUSH_NOTIFICATIONS_ENABLED)
            .preferredLanguageCode(UPDATED_PREFERRED_LANGUAGE_CODE)
            .vatNumber(UPDATED_VAT_NUMBER);
    }

    @BeforeEach
    void initTest() {
        company = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCompany != null) {
            companyRepository.delete(insertedCompany);
            insertedCompany = null;
        }
    }

    @Test
    @Transactional
    void createCompany() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        var returnedCompanyDTO = om.readValue(
            restCompanyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(companyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CompanyDTO.class
        );

        // Validate the Company in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCompany = companyMapper.toEntity(returnedCompanyDTO);
        assertCompanyUpdatableFieldsEquals(returnedCompany, getPersistedCompany(returnedCompany));

        insertedCompany = returnedCompany;
    }

    @Test
    @Transactional
    void createCompanyWithExistingId() throws Exception {
        // Create the Company with an existing ID
        insertedCompany = companyRepository.saveAndFlush(company);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        insertedCompany = companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].regAddressCountryCode").value(hasItem(DEFAULT_REG_ADDRESS_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].regAddress1").value(hasItem(DEFAULT_REG_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].regAddress2").value(hasItem(DEFAULT_REG_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].regAddressCity").value(hasItem(DEFAULT_REG_ADDRESS_CITY)))
            .andExpect(jsonPath("$.[*].regAddressPostalCode").value(hasItem(DEFAULT_REG_ADDRESS_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].hqAddressCountryCode").value(hasItem(DEFAULT_HQ_ADDRESS_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].hqAddress1").value(hasItem(DEFAULT_HQ_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].hqAddress2").value(hasItem(DEFAULT_HQ_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].hqAddressCity").value(hasItem(DEFAULT_HQ_ADDRESS_CITY)))
            .andExpect(jsonPath("$.[*].hqAddressPostalCode").value(hasItem(DEFAULT_HQ_ADDRESS_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].riskProfile").value(hasItem(DEFAULT_RISK_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedBy").value(hasItem(DEFAULT_DELETED_BY)))
            .andExpect(jsonPath("$.[*].industryType").value(hasItem(DEFAULT_INDUSTRY_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfIncorporation").value(hasItem(DEFAULT_DATE_OF_INCORPORATION.toString())))
            .andExpect(jsonPath("$.[*].businessRelationshipPurpose").value(hasItem(DEFAULT_BUSINESS_RELATIONSHIP_PURPOSE)))
            .andExpect(jsonPath("$.[*].isSanctionsRelated").value(hasItem(DEFAULT_IS_SANCTIONS_RELATED)))
            .andExpect(jsonPath("$.[*].isAdverseMediaInvolved").value(hasItem(DEFAULT_IS_ADVERSE_MEDIA_INVOLVED)))
            .andExpect(jsonPath("$.[*].employeesQuantity").value(hasItem(DEFAULT_EMPLOYEES_QUANTITY)))
            .andExpect(jsonPath("$.[*].cardSpendingAmount").value(hasItem(DEFAULT_CARD_SPENDING_AMOUNT)))
            .andExpect(jsonPath("$.[*].limitDailyPurchase").value(hasItem(sameNumber(DEFAULT_LIMIT_DAILY_PURCHASE))))
            .andExpect(jsonPath("$.[*].limitDailyWithdrawal").value(hasItem(sameNumber(DEFAULT_LIMIT_DAILY_WITHDRAWAL))))
            .andExpect(jsonPath("$.[*].limitMonthlyPurchase").value(hasItem(sameNumber(DEFAULT_LIMIT_MONTHLY_PURCHASE))))
            .andExpect(jsonPath("$.[*].limitMonthlyWithdrawal").value(hasItem(sameNumber(DEFAULT_LIMIT_MONTHLY_WITHDRAWAL))))
            .andExpect(jsonPath("$.[*].kybStatus").value(hasItem(DEFAULT_KYB_STATUS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].pushNotificationsEnabled").value(hasItem(DEFAULT_PUSH_NOTIFICATIONS_ENABLED)))
            .andExpect(jsonPath("$.[*].preferredLanguageCode").value(hasItem(DEFAULT_PREFERRED_LANGUAGE_CODE.toString())))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER)));
    }

    @Test
    @Transactional
    void getCompany() throws Exception {
        // Initialize the database
        insertedCompany = companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.regAddressCountryCode").value(DEFAULT_REG_ADDRESS_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.regAddress1").value(DEFAULT_REG_ADDRESS_1))
            .andExpect(jsonPath("$.regAddress2").value(DEFAULT_REG_ADDRESS_2))
            .andExpect(jsonPath("$.regAddressCity").value(DEFAULT_REG_ADDRESS_CITY))
            .andExpect(jsonPath("$.regAddressPostalCode").value(DEFAULT_REG_ADDRESS_POSTAL_CODE))
            .andExpect(jsonPath("$.hqAddressCountryCode").value(DEFAULT_HQ_ADDRESS_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.hqAddress1").value(DEFAULT_HQ_ADDRESS_1))
            .andExpect(jsonPath("$.hqAddress2").value(DEFAULT_HQ_ADDRESS_2))
            .andExpect(jsonPath("$.hqAddressCity").value(DEFAULT_HQ_ADDRESS_CITY))
            .andExpect(jsonPath("$.hqAddressPostalCode").value(DEFAULT_HQ_ADDRESS_POSTAL_CODE))
            .andExpect(jsonPath("$.riskProfile").value(DEFAULT_RISK_PROFILE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.deletedBy").value(DEFAULT_DELETED_BY))
            .andExpect(jsonPath("$.industryType").value(DEFAULT_INDUSTRY_TYPE))
            .andExpect(jsonPath("$.dateOfIncorporation").value(DEFAULT_DATE_OF_INCORPORATION.toString()))
            .andExpect(jsonPath("$.businessRelationshipPurpose").value(DEFAULT_BUSINESS_RELATIONSHIP_PURPOSE))
            .andExpect(jsonPath("$.isSanctionsRelated").value(DEFAULT_IS_SANCTIONS_RELATED))
            .andExpect(jsonPath("$.isAdverseMediaInvolved").value(DEFAULT_IS_ADVERSE_MEDIA_INVOLVED))
            .andExpect(jsonPath("$.employeesQuantity").value(DEFAULT_EMPLOYEES_QUANTITY))
            .andExpect(jsonPath("$.cardSpendingAmount").value(DEFAULT_CARD_SPENDING_AMOUNT))
            .andExpect(jsonPath("$.limitDailyPurchase").value(sameNumber(DEFAULT_LIMIT_DAILY_PURCHASE)))
            .andExpect(jsonPath("$.limitDailyWithdrawal").value(sameNumber(DEFAULT_LIMIT_DAILY_WITHDRAWAL)))
            .andExpect(jsonPath("$.limitMonthlyPurchase").value(sameNumber(DEFAULT_LIMIT_MONTHLY_PURCHASE)))
            .andExpect(jsonPath("$.limitMonthlyWithdrawal").value(sameNumber(DEFAULT_LIMIT_MONTHLY_WITHDRAWAL)))
            .andExpect(jsonPath("$.kybStatus").value(DEFAULT_KYB_STATUS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.pushNotificationsEnabled").value(DEFAULT_PUSH_NOTIFICATIONS_ENABLED))
            .andExpect(jsonPath("$.preferredLanguageCode").value(DEFAULT_PREFERRED_LANGUAGE_CODE.toString()))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompany() throws Exception {
        // Initialize the database
        insertedCompany = companyRepository.saveAndFlush(company);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .regAddressCountryCode(UPDATED_REG_ADDRESS_COUNTRY_CODE)
            .regAddress1(UPDATED_REG_ADDRESS_1)
            .regAddress2(UPDATED_REG_ADDRESS_2)
            .regAddressCity(UPDATED_REG_ADDRESS_CITY)
            .regAddressPostalCode(UPDATED_REG_ADDRESS_POSTAL_CODE)
            .hqAddressCountryCode(UPDATED_HQ_ADDRESS_COUNTRY_CODE)
            .hqAddress1(UPDATED_HQ_ADDRESS_1)
            .hqAddress2(UPDATED_HQ_ADDRESS_2)
            .hqAddressCity(UPDATED_HQ_ADDRESS_CITY)
            .hqAddressPostalCode(UPDATED_HQ_ADDRESS_POSTAL_CODE)
            .riskProfile(UPDATED_RISK_PROFILE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedBy(UPDATED_DELETED_BY)
            .industryType(UPDATED_INDUSTRY_TYPE)
            .dateOfIncorporation(UPDATED_DATE_OF_INCORPORATION)
            .businessRelationshipPurpose(UPDATED_BUSINESS_RELATIONSHIP_PURPOSE)
            .isSanctionsRelated(UPDATED_IS_SANCTIONS_RELATED)
            .isAdverseMediaInvolved(UPDATED_IS_ADVERSE_MEDIA_INVOLVED)
            .employeesQuantity(UPDATED_EMPLOYEES_QUANTITY)
            .cardSpendingAmount(UPDATED_CARD_SPENDING_AMOUNT)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(UPDATED_LIMIT_MONTHLY_WITHDRAWAL)
            .kybStatus(UPDATED_KYB_STATUS)
            .status(UPDATED_STATUS)
            .pushNotificationsEnabled(UPDATED_PUSH_NOTIFICATIONS_ENABLED)
            .preferredLanguageCode(UPDATED_PREFERRED_LANGUAGE_CODE)
            .vatNumber(UPDATED_VAT_NUMBER);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(companyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCompanyToMatchAllProperties(updatedCompany);
    }

    @Test
    @Transactional
    void putNonExistingCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        company.setId(UUID.randomUUID());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        company.setId(UUID.randomUUID());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        company.setId(UUID.randomUUID());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(companyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        insertedCompany = companyRepository.saveAndFlush(company);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .regAddressCountryCode(UPDATED_REG_ADDRESS_COUNTRY_CODE)
            .regAddressCity(UPDATED_REG_ADDRESS_CITY)
            .hqAddressCountryCode(UPDATED_HQ_ADDRESS_COUNTRY_CODE)
            .hqAddressCity(UPDATED_HQ_ADDRESS_CITY)
            .hqAddressPostalCode(UPDATED_HQ_ADDRESS_POSTAL_CODE)
            .riskProfile(UPDATED_RISK_PROFILE)
            .mobile(UPDATED_MOBILE)
            .createdAt(UPDATED_CREATED_AT)
            .deletedBy(UPDATED_DELETED_BY)
            .dateOfIncorporation(UPDATED_DATE_OF_INCORPORATION)
            .isSanctionsRelated(UPDATED_IS_SANCTIONS_RELATED)
            .isAdverseMediaInvolved(UPDATED_IS_ADVERSE_MEDIA_INVOLVED)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .kybStatus(UPDATED_KYB_STATUS)
            .preferredLanguageCode(UPDATED_PREFERRED_LANGUAGE_CODE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompanyUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCompany, company), getPersistedCompany(company));
    }

    @Test
    @Transactional
    void fullUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        insertedCompany = companyRepository.saveAndFlush(company);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .name(UPDATED_NAME)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .regAddressCountryCode(UPDATED_REG_ADDRESS_COUNTRY_CODE)
            .regAddress1(UPDATED_REG_ADDRESS_1)
            .regAddress2(UPDATED_REG_ADDRESS_2)
            .regAddressCity(UPDATED_REG_ADDRESS_CITY)
            .regAddressPostalCode(UPDATED_REG_ADDRESS_POSTAL_CODE)
            .hqAddressCountryCode(UPDATED_HQ_ADDRESS_COUNTRY_CODE)
            .hqAddress1(UPDATED_HQ_ADDRESS_1)
            .hqAddress2(UPDATED_HQ_ADDRESS_2)
            .hqAddressCity(UPDATED_HQ_ADDRESS_CITY)
            .hqAddressPostalCode(UPDATED_HQ_ADDRESS_POSTAL_CODE)
            .riskProfile(UPDATED_RISK_PROFILE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .deletedAt(UPDATED_DELETED_AT)
            .deletedBy(UPDATED_DELETED_BY)
            .industryType(UPDATED_INDUSTRY_TYPE)
            .dateOfIncorporation(UPDATED_DATE_OF_INCORPORATION)
            .businessRelationshipPurpose(UPDATED_BUSINESS_RELATIONSHIP_PURPOSE)
            .isSanctionsRelated(UPDATED_IS_SANCTIONS_RELATED)
            .isAdverseMediaInvolved(UPDATED_IS_ADVERSE_MEDIA_INVOLVED)
            .employeesQuantity(UPDATED_EMPLOYEES_QUANTITY)
            .cardSpendingAmount(UPDATED_CARD_SPENDING_AMOUNT)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(UPDATED_LIMIT_MONTHLY_WITHDRAWAL)
            .kybStatus(UPDATED_KYB_STATUS)
            .status(UPDATED_STATUS)
            .pushNotificationsEnabled(UPDATED_PUSH_NOTIFICATIONS_ENABLED)
            .preferredLanguageCode(UPDATED_PREFERRED_LANGUAGE_CODE)
            .vatNumber(UPDATED_VAT_NUMBER);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompanyUpdatableFieldsEquals(partialUpdatedCompany, getPersistedCompany(partialUpdatedCompany));
    }

    @Test
    @Transactional
    void patchNonExistingCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        company.setId(UUID.randomUUID());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        company.setId(UUID.randomUUID());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompany() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        company.setId(UUID.randomUUID());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(companyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompany() throws Exception {
        // Initialize the database
        insertedCompany = companyRepository.saveAndFlush(company);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the company
        restCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, company.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return companyRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Company getPersistedCompany(Company company) {
        return companyRepository.findById(company.getId()).orElseThrow();
    }

    protected void assertPersistedCompanyToMatchAllProperties(Company expectedCompany) {
        assertCompanyAllPropertiesEquals(expectedCompany, getPersistedCompany(expectedCompany));
    }

    protected void assertPersistedCompanyToMatchUpdatableProperties(Company expectedCompany) {
        assertCompanyAllUpdatablePropertiesEquals(expectedCompany, getPersistedCompany(expectedCompany));
    }
}
