package io.github.vsinkievic.mockwallesterapi.web.rest;

import static io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecordAsserts.*;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.createUpdateProxyForBean;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vsinkievic.mockwallesterapi.IntegrationTest;
import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.MerchantCategoryCode;
import io.github.vsinkievic.mockwallesterapi.repository.AccountStatementRecordRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.AccountStatementRecordDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.AccountStatementRecordMapper;
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
 * Integration tests for the {@link AccountStatementRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@Disabled("Disabled until we have a way to create a card")
class AccountStatementRecordResourceIT {

    private static final UUID DEFAULT_ACCOUNT_ID = UUID.randomUUID();

    private static final UUID DEFAULT_CARD_ID = UUID.randomUUID();
    private static final UUID UPDATED_CARD_ID = UUID.randomUUID();

    private static final AccountStatementRecordType DEFAULT_TYPE = AccountStatementRecordType.AccountAdjustment;
    private static final AccountStatementRecordType UPDATED_TYPE = AccountStatementRecordType.Authorization;

    private static final AccountStatementRecordGroup DEFAULT_GROUP = AccountStatementRecordGroup.AccountClosureFee;
    private static final AccountStatementRecordGroup UPDATED_GROUP = AccountStatementRecordGroup.AccountTransferFee;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);

    private static final CurrencyCode DEFAULT_TRANSACTION_CURRENCY_CODE = CurrencyCode.AED;
    private static final CurrencyCode UPDATED_TRANSACTION_CURRENCY_CODE = CurrencyCode.AFN;

    private static final BigDecimal DEFAULT_ACCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT_AMOUNT = new BigDecimal(2);

    private static final CurrencyCode DEFAULT_ACCOUNT_CURRENCY_CODE = CurrencyCode.AED;
    private static final CurrencyCode UPDATED_ACCOUNT_CURRENCY_CODE = CurrencyCode.AFN;

    private static final String DEFAULT_MERCHANT_CATEGORY_CODE = "0742";
    private static final String UPDATED_MERCHANT_CATEGORY_CODE = "0763";

    private static final String DEFAULT_MERCHANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TERMINAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_TERMINAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_CITY = "BBBBBBBBBB";

    private static final CountryCode DEFAULT_MERCHANT_COUNTRY_CODE = CountryCode.ABW;
    private static final CountryCode UPDATED_MERCHANT_COUNTRY_CODE = CountryCode.AFG;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_ORIGINAL_AUTHORIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORIGINAL_AUTHORIZATION_ID = UUID.randomUUID();

    private static final Boolean DEFAULT_IS_REVERSAL = false;
    private static final Boolean UPDATED_IS_REVERSAL = true;

    private static final Boolean DEFAULT_IS_DECLINED = false;
    private static final Boolean UPDATED_IS_DECLINED = true;

    private static final Boolean DEFAULT_IS_CLEARED = false;
    private static final Boolean UPDATED_IS_CLEARED = true;

    private static final Instant DEFAULT_MARKED_FOR_DISPUTE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MARKED_FOR_DISPUTE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MARKED_FOR_DISPUTE_BY = "AAAAAAAAAA";
    private static final String UPDATED_MARKED_FOR_DISPUTE_BY = "BBBBBBBBBB";

    private static final AccountStatementRecordStatus DEFAULT_STATUS = AccountStatementRecordStatus.Canceled;
    private static final AccountStatementRecordStatus UPDATED_STATUS = AccountStatementRecordStatus.Completed;

    private static final AccountStatementRecordResponse DEFAULT_RESPONSE = AccountStatementRecordResponse.Approved;
    private static final AccountStatementRecordResponse UPDATED_RESPONSE = AccountStatementRecordResponse.Declined;

    private static final String DEFAULT_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_PAYMENT_DOCUMENT_FILES = false;
    private static final Boolean UPDATED_HAS_PAYMENT_DOCUMENT_FILES = true;

    private static final Boolean DEFAULT_HAS_PAYMENT_NOTES = false;
    private static final Boolean UPDATED_HAS_PAYMENT_NOTES = true;

    private static final String DEFAULT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_EXCHANGE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXCHANGE_RATE = new BigDecimal(2);

    private static final String DEFAULT_ENRICHED_MERCHANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENRICHED_MERCHANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENRICHED_MERCHANT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ENRICHED_MERCHANT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ENRICHED_MERCHANT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_ENRICHED_MERCHANT_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_ENRICHED_MERCHANT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_ENRICHED_MERCHANT_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ENRICHED_MERCHANT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ENRICHED_MERCHANT_ICON_URL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/account-statement-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AccountStatementRecordRepository accountStatementRecordRepository;

    @Autowired
    private AccountStatementRecordMapper accountStatementRecordMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountStatementRecordMockMvc;

    private AccountStatementRecord accountStatementRecord;

    private AccountStatementRecord insertedAccountStatementRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountStatementRecord createEntity() {
        return new AccountStatementRecord()
            .accountId(DEFAULT_ACCOUNT_ID)
            .cardId(DEFAULT_CARD_ID)
            .type(DEFAULT_TYPE)
            .group(DEFAULT_GROUP)
            .date(DEFAULT_DATE)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .transactionCurrencyCode(DEFAULT_TRANSACTION_CURRENCY_CODE)
            .accountAmount(DEFAULT_ACCOUNT_AMOUNT)
            .accountCurrencyCode(DEFAULT_ACCOUNT_CURRENCY_CODE)
            .merchantCategoryCode(DEFAULT_MERCHANT_CATEGORY_CODE)
            .merchantId(DEFAULT_MERCHANT_ID)
            .terminalId(DEFAULT_TERMINAL_ID)
            .merchantName(DEFAULT_MERCHANT_NAME)
            .merchantCity(DEFAULT_MERCHANT_CITY)
            .merchantCountryCode(DEFAULT_MERCHANT_COUNTRY_CODE)
            .description(DEFAULT_DESCRIPTION)
            .originalAuthorizationId(DEFAULT_ORIGINAL_AUTHORIZATION_ID)
            .isReversal(DEFAULT_IS_REVERSAL)
            .isDeclined(DEFAULT_IS_DECLINED)
            .isCleared(DEFAULT_IS_CLEARED)
            .markedForDisputeAt(DEFAULT_MARKED_FOR_DISPUTE_AT)
            .markedForDisputeBy(DEFAULT_MARKED_FOR_DISPUTE_BY)
            .status(DEFAULT_STATUS)
            .response(DEFAULT_RESPONSE)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .hasPaymentDocumentFiles(DEFAULT_HAS_PAYMENT_DOCUMENT_FILES)
            .hasPaymentNotes(DEFAULT_HAS_PAYMENT_NOTES)
            .subType(DEFAULT_SUB_TYPE)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .enrichedMerchantName(DEFAULT_ENRICHED_MERCHANT_NAME)
            .enrichedMerchantUrl(DEFAULT_ENRICHED_MERCHANT_URL)
            .enrichedMerchantDomain(DEFAULT_ENRICHED_MERCHANT_DOMAIN)
            .enrichedMerchantTelephone(DEFAULT_ENRICHED_MERCHANT_TELEPHONE)
            .enrichedMerchantIconUrl(DEFAULT_ENRICHED_MERCHANT_ICON_URL)
            .totalAmount(DEFAULT_TOTAL_AMOUNT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountStatementRecord createUpdatedEntity() {
        return new AccountStatementRecord()
            .accountId(DEFAULT_ACCOUNT_ID)
            .cardId(UPDATED_CARD_ID)
            .type(UPDATED_TYPE)
            .group(UPDATED_GROUP)
            .date(UPDATED_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrencyCode(UPDATED_TRANSACTION_CURRENCY_CODE)
            .accountAmount(UPDATED_ACCOUNT_AMOUNT)
            .accountCurrencyCode(UPDATED_ACCOUNT_CURRENCY_CODE)
            .merchantCategoryCode(UPDATED_MERCHANT_CATEGORY_CODE)
            .merchantId(UPDATED_MERCHANT_ID)
            .terminalId(UPDATED_TERMINAL_ID)
            .merchantName(UPDATED_MERCHANT_NAME)
            .merchantCity(UPDATED_MERCHANT_CITY)
            .merchantCountryCode(UPDATED_MERCHANT_COUNTRY_CODE)
            .description(UPDATED_DESCRIPTION)
            .originalAuthorizationId(UPDATED_ORIGINAL_AUTHORIZATION_ID)
            .isReversal(UPDATED_IS_REVERSAL)
            .isDeclined(UPDATED_IS_DECLINED)
            .isCleared(UPDATED_IS_CLEARED)
            .markedForDisputeAt(UPDATED_MARKED_FOR_DISPUTE_AT)
            .markedForDisputeBy(UPDATED_MARKED_FOR_DISPUTE_BY)
            .status(UPDATED_STATUS)
            .response(UPDATED_RESPONSE)
            .responseCode(UPDATED_RESPONSE_CODE)
            .hasPaymentDocumentFiles(UPDATED_HAS_PAYMENT_DOCUMENT_FILES)
            .hasPaymentNotes(UPDATED_HAS_PAYMENT_NOTES)
            .subType(UPDATED_SUB_TYPE)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .enrichedMerchantName(UPDATED_ENRICHED_MERCHANT_NAME)
            .enrichedMerchantUrl(UPDATED_ENRICHED_MERCHANT_URL)
            .enrichedMerchantDomain(UPDATED_ENRICHED_MERCHANT_DOMAIN)
            .enrichedMerchantTelephone(UPDATED_ENRICHED_MERCHANT_TELEPHONE)
            .enrichedMerchantIconUrl(UPDATED_ENRICHED_MERCHANT_ICON_URL)
            .totalAmount(UPDATED_TOTAL_AMOUNT);
    }

    @BeforeEach
    void initTest() {
        accountStatementRecord = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAccountStatementRecord != null) {
            accountStatementRecordRepository.delete(insertedAccountStatementRecord);
            insertedAccountStatementRecord = null;
        }
    }

    @Test
    @Transactional
    void createAccountStatementRecord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);
        var returnedAccountStatementRecordDTO = om.readValue(
            restAccountStatementRecordMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accountStatementRecordDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AccountStatementRecordDTO.class
        );

        // Validate the AccountStatementRecord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAccountStatementRecord = accountStatementRecordMapper.toEntity(returnedAccountStatementRecordDTO);
        assertAccountStatementRecordUpdatableFieldsEquals(
            returnedAccountStatementRecord,
            getPersistedAccountStatementRecord(returnedAccountStatementRecord)
        );

        insertedAccountStatementRecord = returnedAccountStatementRecord;
    }

    @Test
    @Transactional
    void createAccountStatementRecordWithExistingId() throws Exception {
        // Create the AccountStatementRecord with an existing ID
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountStatementRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accountStatementRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountStatementRecords() throws Exception {
        // Initialize the database
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);

        // Get all the accountStatementRecordList
        restAccountStatementRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountStatementRecord.getId().toString())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(sameNumber(DEFAULT_TRANSACTION_AMOUNT))))
            .andExpect(jsonPath("$.[*].transactionCurrencyCode").value(hasItem(DEFAULT_TRANSACTION_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].accountAmount").value(hasItem(sameNumber(DEFAULT_ACCOUNT_AMOUNT))))
            .andExpect(jsonPath("$.[*].accountCurrencyCode").value(hasItem(DEFAULT_ACCOUNT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].merchantCategoryCode").value(hasItem(DEFAULT_MERCHANT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].merchantId").value(hasItem(DEFAULT_MERCHANT_ID)))
            .andExpect(jsonPath("$.[*].terminalId").value(hasItem(DEFAULT_TERMINAL_ID)))
            .andExpect(jsonPath("$.[*].merchantName").value(hasItem(DEFAULT_MERCHANT_NAME)))
            .andExpect(jsonPath("$.[*].merchantCity").value(hasItem(DEFAULT_MERCHANT_CITY)))
            .andExpect(jsonPath("$.[*].merchantCountryCode").value(hasItem(DEFAULT_MERCHANT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].originalAuthorizationId").value(hasItem(DEFAULT_ORIGINAL_AUTHORIZATION_ID.toString())))
            .andExpect(jsonPath("$.[*].isReversal").value(hasItem(DEFAULT_IS_REVERSAL)))
            .andExpect(jsonPath("$.[*].isDeclined").value(hasItem(DEFAULT_IS_DECLINED)))
            .andExpect(jsonPath("$.[*].isCleared").value(hasItem(DEFAULT_IS_CLEARED)))
            .andExpect(jsonPath("$.[*].markedForDisputeAt").value(hasItem(DEFAULT_MARKED_FOR_DISPUTE_AT.toString())))
            .andExpect(jsonPath("$.[*].markedForDisputeBy").value(hasItem(DEFAULT_MARKED_FOR_DISPUTE_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE.toString())))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].hasPaymentDocumentFiles").value(hasItem(DEFAULT_HAS_PAYMENT_DOCUMENT_FILES)))
            .andExpect(jsonPath("$.[*].hasPaymentNotes").value(hasItem(DEFAULT_HAS_PAYMENT_NOTES)))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(sameNumber(DEFAULT_EXCHANGE_RATE))))
            .andExpect(jsonPath("$.[*].enrichedMerchantName").value(hasItem(DEFAULT_ENRICHED_MERCHANT_NAME)))
            .andExpect(jsonPath("$.[*].enrichedMerchantUrl").value(hasItem(DEFAULT_ENRICHED_MERCHANT_URL)))
            .andExpect(jsonPath("$.[*].enrichedMerchantDomain").value(hasItem(DEFAULT_ENRICHED_MERCHANT_DOMAIN)))
            .andExpect(jsonPath("$.[*].enrichedMerchantTelephone").value(hasItem(DEFAULT_ENRICHED_MERCHANT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].enrichedMerchantIconUrl").value(hasItem(DEFAULT_ENRICHED_MERCHANT_ICON_URL)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_AMOUNT))));
    }

    @Test
    @Transactional
    void getAccountStatementRecord() throws Exception {
        // Initialize the database
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);

        // Get the accountStatementRecord
        restAccountStatementRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, accountStatementRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountStatementRecord.getId().toString()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.transactionAmount").value(sameNumber(DEFAULT_TRANSACTION_AMOUNT)))
            .andExpect(jsonPath("$.transactionCurrencyCode").value(DEFAULT_TRANSACTION_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.accountAmount").value(sameNumber(DEFAULT_ACCOUNT_AMOUNT)))
            .andExpect(jsonPath("$.accountCurrencyCode").value(DEFAULT_ACCOUNT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.merchantCategoryCode").value(DEFAULT_MERCHANT_CATEGORY_CODE))
            .andExpect(jsonPath("$.merchantId").value(DEFAULT_MERCHANT_ID))
            .andExpect(jsonPath("$.terminalId").value(DEFAULT_TERMINAL_ID))
            .andExpect(jsonPath("$.merchantName").value(DEFAULT_MERCHANT_NAME))
            .andExpect(jsonPath("$.merchantCity").value(DEFAULT_MERCHANT_CITY))
            .andExpect(jsonPath("$.merchantCountryCode").value(DEFAULT_MERCHANT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.originalAuthorizationId").value(DEFAULT_ORIGINAL_AUTHORIZATION_ID.toString()))
            .andExpect(jsonPath("$.isReversal").value(DEFAULT_IS_REVERSAL))
            .andExpect(jsonPath("$.isDeclined").value(DEFAULT_IS_DECLINED))
            .andExpect(jsonPath("$.isCleared").value(DEFAULT_IS_CLEARED))
            .andExpect(jsonPath("$.markedForDisputeAt").value(DEFAULT_MARKED_FOR_DISPUTE_AT.toString()))
            .andExpect(jsonPath("$.markedForDisputeBy").value(DEFAULT_MARKED_FOR_DISPUTE_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE.toString()))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.hasPaymentDocumentFiles").value(DEFAULT_HAS_PAYMENT_DOCUMENT_FILES))
            .andExpect(jsonPath("$.hasPaymentNotes").value(DEFAULT_HAS_PAYMENT_NOTES))
            .andExpect(jsonPath("$.subType").value(DEFAULT_SUB_TYPE))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.exchangeRate").value(sameNumber(DEFAULT_EXCHANGE_RATE)))
            .andExpect(jsonPath("$.enrichedMerchantName").value(DEFAULT_ENRICHED_MERCHANT_NAME))
            .andExpect(jsonPath("$.enrichedMerchantUrl").value(DEFAULT_ENRICHED_MERCHANT_URL))
            .andExpect(jsonPath("$.enrichedMerchantDomain").value(DEFAULT_ENRICHED_MERCHANT_DOMAIN))
            .andExpect(jsonPath("$.enrichedMerchantTelephone").value(DEFAULT_ENRICHED_MERCHANT_TELEPHONE))
            .andExpect(jsonPath("$.enrichedMerchantIconUrl").value(DEFAULT_ENRICHED_MERCHANT_ICON_URL))
            .andExpect(jsonPath("$.totalAmount").value(sameNumber(DEFAULT_TOTAL_AMOUNT)));
    }

    @Test
    @Transactional
    void getNonExistingAccountStatementRecord() throws Exception {
        // Get the accountStatementRecord
        restAccountStatementRecordMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountStatementRecord() throws Exception {
        // Initialize the database
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accountStatementRecord
        AccountStatementRecord updatedAccountStatementRecord = accountStatementRecordRepository
            .findById(accountStatementRecord.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAccountStatementRecord are not directly saved in db
        em.detach(updatedAccountStatementRecord);
        updatedAccountStatementRecord
            .accountId(DEFAULT_ACCOUNT_ID)
            .cardId(UPDATED_CARD_ID)
            .type(UPDATED_TYPE)
            .group(UPDATED_GROUP)
            .date(UPDATED_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrencyCode(UPDATED_TRANSACTION_CURRENCY_CODE)
            .accountAmount(UPDATED_ACCOUNT_AMOUNT)
            .accountCurrencyCode(UPDATED_ACCOUNT_CURRENCY_CODE)
            .merchantCategoryCode(UPDATED_MERCHANT_CATEGORY_CODE)
            .merchantId(UPDATED_MERCHANT_ID)
            .terminalId(UPDATED_TERMINAL_ID)
            .merchantName(UPDATED_MERCHANT_NAME)
            .merchantCity(UPDATED_MERCHANT_CITY)
            .merchantCountryCode(UPDATED_MERCHANT_COUNTRY_CODE)
            .description(UPDATED_DESCRIPTION)
            .originalAuthorizationId(UPDATED_ORIGINAL_AUTHORIZATION_ID)
            .isReversal(UPDATED_IS_REVERSAL)
            .isDeclined(UPDATED_IS_DECLINED)
            .isCleared(UPDATED_IS_CLEARED)
            .markedForDisputeAt(UPDATED_MARKED_FOR_DISPUTE_AT)
            .markedForDisputeBy(UPDATED_MARKED_FOR_DISPUTE_BY)
            .status(UPDATED_STATUS)
            .response(UPDATED_RESPONSE)
            .responseCode(UPDATED_RESPONSE_CODE)
            .hasPaymentDocumentFiles(UPDATED_HAS_PAYMENT_DOCUMENT_FILES)
            .hasPaymentNotes(UPDATED_HAS_PAYMENT_NOTES)
            .subType(UPDATED_SUB_TYPE)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .enrichedMerchantName(UPDATED_ENRICHED_MERCHANT_NAME)
            .enrichedMerchantUrl(UPDATED_ENRICHED_MERCHANT_URL)
            .enrichedMerchantDomain(UPDATED_ENRICHED_MERCHANT_DOMAIN)
            .enrichedMerchantTelephone(UPDATED_ENRICHED_MERCHANT_TELEPHONE)
            .enrichedMerchantIconUrl(UPDATED_ENRICHED_MERCHANT_ICON_URL)
            .totalAmount(UPDATED_TOTAL_AMOUNT);
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(updatedAccountStatementRecord);

        restAccountStatementRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountStatementRecordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accountStatementRecordDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAccountStatementRecordToMatchAllProperties(updatedAccountStatementRecord);
    }

    @Test
    @Transactional
    void putNonExistingAccountStatementRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accountStatementRecord.setId(UUID.randomUUID());

        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountStatementRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountStatementRecordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accountStatementRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountStatementRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accountStatementRecord.setId(UUID.randomUUID());

        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountStatementRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accountStatementRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountStatementRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accountStatementRecord.setId(UUID.randomUUID());

        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountStatementRecordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accountStatementRecordDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountStatementRecordWithPatch() throws Exception {
        // Initialize the database
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accountStatementRecord using partial update
        AccountStatementRecord partialUpdatedAccountStatementRecord = new AccountStatementRecord();
        partialUpdatedAccountStatementRecord.setId(accountStatementRecord.getId());

        partialUpdatedAccountStatementRecord
            .accountId(DEFAULT_ACCOUNT_ID)
            .cardId(UPDATED_CARD_ID)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .accountAmount(UPDATED_ACCOUNT_AMOUNT)
            .merchantCategoryCode(UPDATED_MERCHANT_CATEGORY_CODE)
            .merchantId(UPDATED_MERCHANT_ID)
            .merchantName(UPDATED_MERCHANT_NAME)
            .description(UPDATED_DESCRIPTION)
            .markedForDisputeBy(UPDATED_MARKED_FOR_DISPUTE_BY)
            .status(UPDATED_STATUS)
            .responseCode(UPDATED_RESPONSE_CODE)
            .hasPaymentNotes(UPDATED_HAS_PAYMENT_NOTES)
            .subType(UPDATED_SUB_TYPE)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .enrichedMerchantUrl(UPDATED_ENRICHED_MERCHANT_URL)
            .enrichedMerchantTelephone(UPDATED_ENRICHED_MERCHANT_TELEPHONE);

        restAccountStatementRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountStatementRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccountStatementRecord))
            )
            .andExpect(status().isOk());

        // Validate the AccountStatementRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccountStatementRecordUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAccountStatementRecord, accountStatementRecord),
            getPersistedAccountStatementRecord(accountStatementRecord)
        );
    }

    @Test
    @Transactional
    void fullUpdateAccountStatementRecordWithPatch() throws Exception {
        // Initialize the database
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accountStatementRecord using partial update
        AccountStatementRecord partialUpdatedAccountStatementRecord = new AccountStatementRecord();
        partialUpdatedAccountStatementRecord.setId(accountStatementRecord.getId());

        partialUpdatedAccountStatementRecord
            .accountId(DEFAULT_ACCOUNT_ID)
            .cardId(UPDATED_CARD_ID)
            .type(UPDATED_TYPE)
            .group(UPDATED_GROUP)
            .date(UPDATED_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrencyCode(UPDATED_TRANSACTION_CURRENCY_CODE)
            .accountAmount(UPDATED_ACCOUNT_AMOUNT)
            .accountCurrencyCode(UPDATED_ACCOUNT_CURRENCY_CODE)
            .merchantCategoryCode(UPDATED_MERCHANT_CATEGORY_CODE)
            .merchantId(UPDATED_MERCHANT_ID)
            .terminalId(UPDATED_TERMINAL_ID)
            .merchantName(UPDATED_MERCHANT_NAME)
            .merchantCity(UPDATED_MERCHANT_CITY)
            .merchantCountryCode(UPDATED_MERCHANT_COUNTRY_CODE)
            .description(UPDATED_DESCRIPTION)
            .originalAuthorizationId(UPDATED_ORIGINAL_AUTHORIZATION_ID)
            .isReversal(UPDATED_IS_REVERSAL)
            .isDeclined(UPDATED_IS_DECLINED)
            .isCleared(UPDATED_IS_CLEARED)
            .markedForDisputeAt(UPDATED_MARKED_FOR_DISPUTE_AT)
            .markedForDisputeBy(UPDATED_MARKED_FOR_DISPUTE_BY)
            .status(UPDATED_STATUS)
            .response(UPDATED_RESPONSE)
            .responseCode(UPDATED_RESPONSE_CODE)
            .hasPaymentDocumentFiles(UPDATED_HAS_PAYMENT_DOCUMENT_FILES)
            .hasPaymentNotes(UPDATED_HAS_PAYMENT_NOTES)
            .subType(UPDATED_SUB_TYPE)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .enrichedMerchantName(UPDATED_ENRICHED_MERCHANT_NAME)
            .enrichedMerchantUrl(UPDATED_ENRICHED_MERCHANT_URL)
            .enrichedMerchantDomain(UPDATED_ENRICHED_MERCHANT_DOMAIN)
            .enrichedMerchantTelephone(UPDATED_ENRICHED_MERCHANT_TELEPHONE)
            .enrichedMerchantIconUrl(UPDATED_ENRICHED_MERCHANT_ICON_URL)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restAccountStatementRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountStatementRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccountStatementRecord))
            )
            .andExpect(status().isOk());

        // Validate the AccountStatementRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccountStatementRecordUpdatableFieldsEquals(
            partialUpdatedAccountStatementRecord,
            getPersistedAccountStatementRecord(partialUpdatedAccountStatementRecord)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAccountStatementRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accountStatementRecord.setId(UUID.randomUUID());

        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountStatementRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountStatementRecordDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accountStatementRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountStatementRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accountStatementRecord.setId(UUID.randomUUID());

        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountStatementRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accountStatementRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountStatementRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accountStatementRecord.setId(UUID.randomUUID());

        // Create the AccountStatementRecord
        AccountStatementRecordDTO accountStatementRecordDTO = accountStatementRecordMapper.toDto(accountStatementRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountStatementRecordMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(accountStatementRecordDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountStatementRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountStatementRecord() throws Exception {
        // Initialize the database
        insertedAccountStatementRecord = accountStatementRecordRepository.saveAndFlush(accountStatementRecord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the accountStatementRecord
        restAccountStatementRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountStatementRecord.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return accountStatementRecordRepository.count();
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

    protected AccountStatementRecord getPersistedAccountStatementRecord(AccountStatementRecord accountStatementRecord) {
        return accountStatementRecordRepository.findById(accountStatementRecord.getId()).orElseThrow();
    }

    protected void assertPersistedAccountStatementRecordToMatchAllProperties(AccountStatementRecord expectedAccountStatementRecord) {
        assertAccountStatementRecordAllPropertiesEquals(
            expectedAccountStatementRecord,
            getPersistedAccountStatementRecord(expectedAccountStatementRecord)
        );
    }

    protected void assertPersistedAccountStatementRecordToMatchUpdatableProperties(AccountStatementRecord expectedAccountStatementRecord) {
        assertAccountStatementRecordAllUpdatablePropertiesEquals(
            expectedAccountStatementRecord,
            getPersistedAccountStatementRecord(expectedAccountStatementRecord)
        );
    }
}
