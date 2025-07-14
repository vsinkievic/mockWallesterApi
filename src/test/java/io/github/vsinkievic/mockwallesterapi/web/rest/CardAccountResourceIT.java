package io.github.vsinkievic.mockwallesterapi.web.rest;

import static io.github.vsinkievic.mockwallesterapi.domain.CardAccountAsserts.*;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.createUpdateProxyForBean;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vsinkievic.mockwallesterapi.IntegrationTest;
import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.domain.Company;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CompanyStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.github.vsinkievic.mockwallesterapi.repository.CardAccountRepository;
import io.github.vsinkievic.mockwallesterapi.repository.CompanyRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardAccountMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CardAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CardAccountResourceIT {

    private static UUID DEFAULT_COMPANY_ID = null;

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final CurrencyCode DEFAULT_CURRENCY = CurrencyCode.AED;
    private static final CurrencyCode UPDATED_CURRENCY = CurrencyCode.AFN;

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AVAILABLE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVAILABLE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BLOCKED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BLOCKED_AMOUNT = new BigDecimal(2);

    private static final AccountStatus DEFAULT_STATUS = AccountStatus.Active;
    private static final AccountStatus UPDATED_STATUS = AccountStatus.Blocked;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/card-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CardAccountRepository cardAccountRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CardAccountMapper cardAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardAccountMockMvc;

    private CardAccount cardAccount;

    private CardAccount insertedCardAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardAccount createEntity() {
        return new CardAccount()
            .companyId(DEFAULT_COMPANY_ID)
            .externalId(DEFAULT_EXTERNAL_ID)
            .currencyCode(DEFAULT_CURRENCY)
            .balance(DEFAULT_BALANCE)
            .availableAmount(DEFAULT_AVAILABLE_AMOUNT)
            .blockedAmount(DEFAULT_BLOCKED_AMOUNT)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardAccount createUpdatedEntity() {
        return new CardAccount()
            .companyId(DEFAULT_COMPANY_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .currencyCode(UPDATED_CURRENCY)
            .balance(UPDATED_BALANCE)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .blockedAmount(UPDATED_BLOCKED_AMOUNT)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
    }

    @BeforeEach
    void initTest() {
        cardAccountRepository.deleteAll();
        companyRepository.deleteAll();

        // Create test company
        Company company = new Company()
            .name("Test Company")
            .registrationNumber("123456")
            .status(CompanyStatus.Active)
            .limitDailyPurchase(new BigDecimal("1000"))
            .limitDailyWithdrawal(new BigDecimal("500"))
            .limitMonthlyPurchase(new BigDecimal("10000"))
            .limitMonthlyWithdrawal(new BigDecimal("5000"));
        company = companyRepository.saveAndFlush(company);

        // Update the DEFAULT_COMPANY_ID to match the generated company ID
        DEFAULT_COMPANY_ID = company.getId();

        cardAccount = createEntity();
    }

    @AfterEach
    void cleanup() {
        cardAccountRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    @Transactional
    void createCardAccount() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);
        cardAccountDTO.setExternalId(UUID.randomUUID().toString());
        var returnedCardAccountDTO = om.readValue(
            restCardAccountMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CardAccountDTO.class
        );

        // Validate the CardAccount in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCardAccount = cardAccountMapper.toEntity(returnedCardAccountDTO);
        assertCardAccountUpdatableFieldsEquals(returnedCardAccount, getPersistedCardAccount(returnedCardAccount));

        insertedCardAccount = returnedCardAccount;
    }

    @Test
    @Transactional
    void createCardAccountWithExistingId() throws Exception {
        // Create the CardAccount with an existing ID
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCurrencyCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setCurrencyCode(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setBalance(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvailableAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setAvailableAmount(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBlockedAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setBlockedAmount(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setStatus(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setCreatedAt(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cardAccount.setUpdatedAt(null);

        // Create the CardAccount, which fails.
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        restCardAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCardAccounts() throws Exception {
        // Initialize the database
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);

        // Get all the cardAccountList
        restCardAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardAccount.getId().toString())))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].availableAmount").value(hasItem(sameNumber(DEFAULT_AVAILABLE_AMOUNT))))
            .andExpect(jsonPath("$.[*].blockedAmount").value(hasItem(sameNumber(DEFAULT_BLOCKED_AMOUNT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCardAccount() throws Exception {
        // Initialize the database
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);

        // Get the cardAccount
        restCardAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, cardAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cardAccount.getId().toString()))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.availableAmount").value(sameNumber(DEFAULT_AVAILABLE_AMOUNT)))
            .andExpect(jsonPath("$.blockedAmount").value(sameNumber(DEFAULT_BLOCKED_AMOUNT)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCardAccount() throws Exception {
        // Get the cardAccount
        restCardAccountMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCardAccount() throws Exception {
        // Initialize the database
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cardAccount
        CardAccount updatedCardAccount = cardAccountRepository.findById(cardAccount.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCardAccount are not directly saved in db
        em.detach(updatedCardAccount);
        updatedCardAccount
            .externalId(UPDATED_EXTERNAL_ID)
            .currencyCode(UPDATED_CURRENCY)
            .balance(UPDATED_BALANCE)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .blockedAmount(UPDATED_BLOCKED_AMOUNT)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(updatedCardAccount);

        restCardAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cardAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cardAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCardAccountToMatchAllProperties(updatedCardAccount);
    }

    @Test
    @Transactional
    void putNonExistingCardAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cardAccount.setId(UUID.randomUUID());

        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cardAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cardAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCardAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cardAccount.setId(UUID.randomUUID());

        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cardAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCardAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cardAccount.setId(UUID.randomUUID());

        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCardAccountWithPatch() throws Exception {
        // Initialize the database
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cardAccount using partial update
        CardAccount partialUpdatedCardAccount = new CardAccount();
        partialUpdatedCardAccount.setId(cardAccount.getId());

        partialUpdatedCardAccount.balance(UPDATED_BALANCE).blockedAmount(UPDATED_BLOCKED_AMOUNT).availableAmount(UPDATED_AVAILABLE_AMOUNT);
        //            .updatedAt(UPDATED_UPDATED_AT);

        restCardAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCardAccount))
            )
            .andExpect(status().isOk());

        // Validate the CardAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCardAccountUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCardAccount, cardAccount),
            getPersistedCardAccount(cardAccount)
        );
    }

    @Test
    @Transactional
    void fullUpdateCardAccountWithPatch() throws Exception {
        // Initialize the database
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cardAccount using partial update
        CardAccount partialUpdatedCardAccount = new CardAccount();
        partialUpdatedCardAccount.setId(cardAccount.getId());

        partialUpdatedCardAccount
            .externalId(UPDATED_EXTERNAL_ID)
            .currencyCode(UPDATED_CURRENCY)
            .balance(UPDATED_BALANCE)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .blockedAmount(UPDATED_BLOCKED_AMOUNT)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCardAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCardAccount))
            )
            .andExpect(status().isOk());

        // Validate the CardAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCardAccountUpdatableFieldsEquals(partialUpdatedCardAccount, getPersistedCardAccount(partialUpdatedCardAccount));
    }

    @Test
    @Transactional
    void patchNonExistingCardAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cardAccount.setId(UUID.randomUUID());

        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cardAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cardAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCardAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cardAccount.setId(UUID.randomUUID());

        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cardAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCardAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cardAccount.setId(UUID.randomUUID());

        // Create the CardAccount
        CardAccountDTO cardAccountDTO = cardAccountMapper.toDto(cardAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardAccountMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cardAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CardAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCardAccount() throws Exception {
        // Initialize the database
        insertedCardAccount = cardAccountRepository.saveAndFlush(cardAccount);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cardAccount
        restCardAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, cardAccount.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cardAccountRepository.count();
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

    protected CardAccount getPersistedCardAccount(CardAccount cardAccount) {
        return cardAccountRepository.findById(cardAccount.getId()).orElseThrow();
    }

    protected void assertPersistedCardAccountToMatchAllProperties(CardAccount expectedCardAccount) {
        assertCardAccountAllPropertiesEquals(expectedCardAccount, getPersistedCardAccount(expectedCardAccount));
    }

    protected void assertPersistedCardAccountToMatchUpdatableProperties(CardAccount expectedCardAccount) {
        assertCardAccountAllUpdatablePropertiesEquals(expectedCardAccount, getPersistedCardAccount(expectedCardAccount));
    }
}
