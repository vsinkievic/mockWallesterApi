package io.github.vsinkievic.mockwallesterapi.web.rest;

import static io.github.vsinkievic.mockwallesterapi.domain.CardAsserts.*;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.createUpdateProxyForBean;
import static io.github.vsinkievic.mockwallesterapi.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vsinkievic.mockwallesterapi.IntegrationTest;
import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardBlockType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardCloseReason;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CarrierType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.DispatchMethod;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.DisposableType;
import io.github.vsinkievic.mockwallesterapi.repository.CardRepository;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardDTO;
import io.github.vsinkievic.mockwallesterapi.service.mapper.CardMapper;
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
 * Integration tests for the {@link CardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CardResourceIT {

    private static final UUID DEFAULT_PREDECESSOR_CARD_ID = UUID.randomUUID();
    private static final UUID UPDATED_PREDECESSOR_CARD_ID = UUID.randomUUID();

    private static final UUID DEFAULT_ACCOUNT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ACCOUNT_ID = UUID.randomUUID();

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final CardType DEFAULT_TYPE = CardType.ChipAndPin;
    private static final CardType UPDATED_TYPE = CardType.ChipAndPinAnonymous;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MASKED_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MASKED_CARD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CardBlockType DEFAULT_BLOCK_TYPE = CardBlockType.BlockedByClient;
    private static final CardBlockType UPDATED_BLOCK_TYPE = CardBlockType.BlockedByClient;

    private static final Instant DEFAULT_BLOCKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BLOCKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BLOCKED_BY = "AAAAAAAAAA";
    private static final String UPDATED_BLOCKED_BY = "BBBBBBBBBB";

    private static final CardStatus DEFAULT_STATUS = CardStatus.Active;
    private static final CardStatus UPDATED_STATUS = CardStatus.AwaitingRenewal;

    private static final String DEFAULT_EMBOSSING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMBOSSING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMBOSSING_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMBOSSING_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMBOSSING_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMBOSSING_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMBOSSING_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMBOSSING_COMPANY_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LIMIT_DAILY_PURCHASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_DAILY_PURCHASE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_DAILY_WITHDRAWAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_DAILY_WITHDRAWAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_MONTHLY_PURCHASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_MONTHLY_PURCHASE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_MONTHLY_WITHDRAWAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_MONTHLY_WITHDRAWAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIMIT_TRANSACTION_PURCHASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_TRANSACTION_PURCHASE = new BigDecimal(2);

    private static final String DEFAULT_SECURE_3_D_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_SECURE_3_D_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_SECURE_3_D_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SECURE_3_D_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SECURE_3_D_OUT_OF_BAND_ENABLED = false;
    private static final Boolean UPDATED_SECURE_3_D_OUT_OF_BAND_ENABLED = true;

    private static final String DEFAULT_SECURE_3_D_OUT_OF_BAND_ID = "AAAAAAAAAA";
    private static final String UPDATED_SECURE_3_D_OUT_OF_BAND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_CITY = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_CITY = "BBBBBBBBBB";

    private static final CountryCode DEFAULT_DELIVERY_COUNTRY_CODE = CountryCode.ABW;
    private static final CountryCode UPDATED_DELIVERY_COUNTRY_CODE = CountryCode.AFG;

    private static final DispatchMethod DEFAULT_DELIVERY_DISPATCH_METHOD = DispatchMethod.StandardMail;
    private static final DispatchMethod UPDATED_DELIVERY_DISPATCH_METHOD = DispatchMethod.DHLExpress;

    private static final String DEFAULT_DELIVERY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENROLLED_FOR_3_D_SECURE = false;
    private static final Boolean UPDATED_IS_ENROLLED_FOR_3_D_SECURE = true;

    private static final Boolean DEFAULT_IS_CARD_3_D_SECURE_ACTIVATED = false;
    private static final Boolean UPDATED_IS_CARD_3_D_SECURE_ACTIVATED = true;

    private static final Boolean DEFAULT_RENEW_AUTOMATICALLY = false;
    private static final Boolean UPDATED_RENEW_AUTOMATICALLY = true;

    private static final Boolean DEFAULT_IS_DISPOSABLE = false;
    private static final Boolean UPDATED_IS_DISPOSABLE = true;

    private static final Boolean DEFAULT_SECURITY_CONTACTLESS_ENABLED = false;
    private static final Boolean UPDATED_SECURITY_CONTACTLESS_ENABLED = true;

    private static final Boolean DEFAULT_SECURITY_WITHDRAWAL_ENABLED = false;
    private static final Boolean UPDATED_SECURITY_WITHDRAWAL_ENABLED = true;

    private static final Boolean DEFAULT_SECURITY_INTERNET_PURCHASE_ENABLED = false;
    private static final Boolean UPDATED_SECURITY_INTERNET_PURCHASE_ENABLED = true;

    private static final Boolean DEFAULT_SECURITY_OVERALL_LIMITS_ENABLED = false;
    private static final Boolean UPDATED_SECURITY_OVERALL_LIMITS_ENABLED = true;

    private static final Boolean DEFAULT_SECURITY_ALL_TIME_LIMITS_ENABLED = false;
    private static final Boolean UPDATED_SECURITY_ALL_TIME_LIMITS_ENABLED = true;

    private static final String DEFAULT_PERSONALIZATION_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PERSONALIZATION_PRODUCT_CODE = "BBBBBBBBBB";

    private static final CarrierType DEFAULT_CARRIER_TYPE = CarrierType.Standard;
    private static final CarrierType UPDATED_CARRIER_TYPE = CarrierType.Custom;

    private static final String DEFAULT_CARD_METADATA_PROFILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_METADATA_PROFILE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTIVATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLOSED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CLOSED_BY = "BBBBBBBBBB";

    private static final CardCloseReason DEFAULT_CLOSE_REASON = CardCloseReason.ClosedByCardholder;
    private static final CardCloseReason UPDATED_CLOSE_REASON = CardCloseReason.ClosedByClient;

    private static final UUID DEFAULT_COMPANY_ID = UUID.randomUUID();

    private static final Instant DEFAULT_DISPATCHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISPATCHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_NOTIFICATION_RECEIPTS_REMINDER_ENABLED = false;
    private static final Boolean UPDATED_NOTIFICATION_RECEIPTS_REMINDER_ENABLED = true;

    private static final Boolean DEFAULT_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED = false;
    private static final Boolean UPDATED_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED = true;

    private static final DisposableType DEFAULT_DISPOSABLE_TYPE = DisposableType.SingleUse;
    private static final DisposableType UPDATED_DISPOSABLE_TYPE = DisposableType.CustomExpiryDate;

    private static final String ENTITY_API_URL = "/api/cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardMockMvc;

    private Card card;

    private Card insertedCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createEntity() {
        return new Card()
            .predecessorCardId(DEFAULT_PREDECESSOR_CARD_ID)
            .accountId(DEFAULT_ACCOUNT_ID)
            .externalId(DEFAULT_EXTERNAL_ID)
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .maskedCardNumber(DEFAULT_MASKED_CARD_NUMBER)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .blockType(DEFAULT_BLOCK_TYPE)
            .blockedAt(DEFAULT_BLOCKED_AT)
            .blockedBy(DEFAULT_BLOCKED_BY)
            .status(DEFAULT_STATUS)
            .embossingName(DEFAULT_EMBOSSING_NAME)
            .embossingFirstName(DEFAULT_EMBOSSING_FIRST_NAME)
            .embossingLastName(DEFAULT_EMBOSSING_LAST_NAME)
            .embossingCompanyName(DEFAULT_EMBOSSING_COMPANY_NAME)
            .limitDailyPurchase(DEFAULT_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(DEFAULT_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(DEFAULT_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(DEFAULT_LIMIT_MONTHLY_WITHDRAWAL)
            .limitTransactionPurchase(DEFAULT_LIMIT_TRANSACTION_PURCHASE)
            .secure3DMobile(DEFAULT_SECURE_3_D_MOBILE)
            .secure3DEmail(DEFAULT_SECURE_3_D_EMAIL)
            .secure3DOutOfBandEnabled(DEFAULT_SECURE_3_D_OUT_OF_BAND_ENABLED)
            .secure3DOutOfBandId(DEFAULT_SECURE_3_D_OUT_OF_BAND_ID)
            .deliveryFirstName(DEFAULT_DELIVERY_FIRST_NAME)
            .deliveryLastName(DEFAULT_DELIVERY_LAST_NAME)
            .deliveryCompanyName(DEFAULT_DELIVERY_COMPANY_NAME)
            .deliveryAddress1(DEFAULT_DELIVERY_ADDRESS_1)
            .deliveryAddress2(DEFAULT_DELIVERY_ADDRESS_2)
            .deliveryPostalCode(DEFAULT_DELIVERY_POSTAL_CODE)
            .deliveryCity(DEFAULT_DELIVERY_CITY)
            .deliveryCountryCode(DEFAULT_DELIVERY_COUNTRY_CODE)
            .deliveryDispatchMethod(DEFAULT_DELIVERY_DISPATCH_METHOD)
            .deliveryPhone(DEFAULT_DELIVERY_PHONE)
            .deliveryTrackingNumber(DEFAULT_DELIVERY_TRACKING_NUMBER)
            .isEnrolledFor3DSecure(DEFAULT_IS_ENROLLED_FOR_3_D_SECURE)
            .isCard3DSecureActivated(DEFAULT_IS_CARD_3_D_SECURE_ACTIVATED)
            .renewAutomatically(DEFAULT_RENEW_AUTOMATICALLY)
            .isDisposable(DEFAULT_IS_DISPOSABLE)
            .securityContactlessEnabled(DEFAULT_SECURITY_CONTACTLESS_ENABLED)
            .securityWithdrawalEnabled(DEFAULT_SECURITY_WITHDRAWAL_ENABLED)
            .securityInternetPurchaseEnabled(DEFAULT_SECURITY_INTERNET_PURCHASE_ENABLED)
            .securityOverallLimitsEnabled(DEFAULT_SECURITY_OVERALL_LIMITS_ENABLED)
            .securityAllTimeLimitsEnabled(DEFAULT_SECURITY_ALL_TIME_LIMITS_ENABLED)
            .personalizationProductCode(DEFAULT_PERSONALIZATION_PRODUCT_CODE)
            .carrierType(DEFAULT_CARRIER_TYPE)
            .cardMetadataProfileId(DEFAULT_CARD_METADATA_PROFILE_ID)
            .activatedAt(DEFAULT_ACTIVATED_AT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .closedAt(DEFAULT_CLOSED_AT)
            .closedBy(DEFAULT_CLOSED_BY)
            .closeReason(DEFAULT_CLOSE_REASON)
            .companyId(DEFAULT_COMPANY_ID)
            .dispatchedAt(DEFAULT_DISPATCHED_AT)
            .notificationReceiptsReminderEnabled(DEFAULT_NOTIFICATION_RECEIPTS_REMINDER_ENABLED)
            .notificationInstantSpendUpdateEnabled(DEFAULT_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED)
            .disposableType(DEFAULT_DISPOSABLE_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createUpdatedEntity() {
        return new Card()
            .predecessorCardId(UPDATED_PREDECESSOR_CARD_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .maskedCardNumber(UPDATED_MASKED_CARD_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .blockType(UPDATED_BLOCK_TYPE)
            .blockedAt(UPDATED_BLOCKED_AT)
            .blockedBy(UPDATED_BLOCKED_BY)
            .status(UPDATED_STATUS)
            .embossingName(UPDATED_EMBOSSING_NAME)
            .embossingFirstName(UPDATED_EMBOSSING_FIRST_NAME)
            .embossingLastName(UPDATED_EMBOSSING_LAST_NAME)
            .embossingCompanyName(UPDATED_EMBOSSING_COMPANY_NAME)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(UPDATED_LIMIT_MONTHLY_WITHDRAWAL)
            .limitTransactionPurchase(UPDATED_LIMIT_TRANSACTION_PURCHASE)
            .secure3DMobile(UPDATED_SECURE_3_D_MOBILE)
            .secure3DEmail(UPDATED_SECURE_3_D_EMAIL)
            .secure3DOutOfBandEnabled(UPDATED_SECURE_3_D_OUT_OF_BAND_ENABLED)
            .secure3DOutOfBandId(UPDATED_SECURE_3_D_OUT_OF_BAND_ID)
            .deliveryFirstName(UPDATED_DELIVERY_FIRST_NAME)
            .deliveryLastName(UPDATED_DELIVERY_LAST_NAME)
            .deliveryCompanyName(UPDATED_DELIVERY_COMPANY_NAME)
            .deliveryAddress1(UPDATED_DELIVERY_ADDRESS_1)
            .deliveryAddress2(UPDATED_DELIVERY_ADDRESS_2)
            .deliveryPostalCode(UPDATED_DELIVERY_POSTAL_CODE)
            .deliveryCity(UPDATED_DELIVERY_CITY)
            .deliveryCountryCode(UPDATED_DELIVERY_COUNTRY_CODE)
            .deliveryDispatchMethod(UPDATED_DELIVERY_DISPATCH_METHOD)
            .deliveryPhone(UPDATED_DELIVERY_PHONE)
            .deliveryTrackingNumber(UPDATED_DELIVERY_TRACKING_NUMBER)
            .isEnrolledFor3DSecure(UPDATED_IS_ENROLLED_FOR_3_D_SECURE)
            .isCard3DSecureActivated(UPDATED_IS_CARD_3_D_SECURE_ACTIVATED)
            .renewAutomatically(UPDATED_RENEW_AUTOMATICALLY)
            .isDisposable(UPDATED_IS_DISPOSABLE)
            .securityContactlessEnabled(UPDATED_SECURITY_CONTACTLESS_ENABLED)
            .securityWithdrawalEnabled(UPDATED_SECURITY_WITHDRAWAL_ENABLED)
            .securityInternetPurchaseEnabled(UPDATED_SECURITY_INTERNET_PURCHASE_ENABLED)
            .securityOverallLimitsEnabled(UPDATED_SECURITY_OVERALL_LIMITS_ENABLED)
            .securityAllTimeLimitsEnabled(UPDATED_SECURITY_ALL_TIME_LIMITS_ENABLED)
            .personalizationProductCode(UPDATED_PERSONALIZATION_PRODUCT_CODE)
            .carrierType(UPDATED_CARRIER_TYPE)
            .cardMetadataProfileId(UPDATED_CARD_METADATA_PROFILE_ID)
            .activatedAt(UPDATED_ACTIVATED_AT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .closedAt(UPDATED_CLOSED_AT)
            .closedBy(UPDATED_CLOSED_BY)
            .closeReason(UPDATED_CLOSE_REASON)
            .companyId(DEFAULT_COMPANY_ID)
            .dispatchedAt(UPDATED_DISPATCHED_AT)
            .notificationReceiptsReminderEnabled(UPDATED_NOTIFICATION_RECEIPTS_REMINDER_ENABLED)
            .notificationInstantSpendUpdateEnabled(UPDATED_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED)
            .disposableType(UPDATED_DISPOSABLE_TYPE);
    }

    @BeforeEach
    void initTest() {
        card = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCard != null) {
            cardRepository.delete(insertedCard);
            insertedCard = null;
        }
    }

    @Test
    @Transactional
    void createCard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);
        var returnedCardDTO = om.readValue(
            restCardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CardDTO.class
        );

        // Validate the Card in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCard = cardMapper.toEntity(returnedCardDTO);
        assertCardUpdatableFieldsEquals(returnedCard, getPersistedCard(returnedCard));

        insertedCard = returnedCard;
    }

    @Test
    @Transactional
    void createCardWithExistingId() throws Exception {
        // Create the Card with an existing ID
        insertedCard = cardRepository.saveAndFlush(card);
        CardDTO cardDTO = cardMapper.toDto(card);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCards() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().toString())))
            .andExpect(jsonPath("$.[*].predecessorCardId").value(hasItem(DEFAULT_PREDECESSOR_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.toString())))
            //            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].maskedCardNumber").value(hasItem(DEFAULT_MASKED_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].blockType").value(hasItem(DEFAULT_BLOCK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].blockedAt").value(hasItem(DEFAULT_BLOCKED_AT.toString())))
            .andExpect(jsonPath("$.[*].blockedBy").value(hasItem(DEFAULT_BLOCKED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].embossingName").value(hasItem(DEFAULT_EMBOSSING_NAME)))
            .andExpect(jsonPath("$.[*].embossingFirstName").value(hasItem(DEFAULT_EMBOSSING_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].embossingLastName").value(hasItem(DEFAULT_EMBOSSING_LAST_NAME)))
            .andExpect(jsonPath("$.[*].embossingCompanyName").value(hasItem(DEFAULT_EMBOSSING_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].limitDailyPurchase").value(hasItem(sameNumber(DEFAULT_LIMIT_DAILY_PURCHASE))))
            .andExpect(jsonPath("$.[*].limitDailyWithdrawal").value(hasItem(sameNumber(DEFAULT_LIMIT_DAILY_WITHDRAWAL))))
            .andExpect(jsonPath("$.[*].limitMonthlyPurchase").value(hasItem(sameNumber(DEFAULT_LIMIT_MONTHLY_PURCHASE))))
            .andExpect(jsonPath("$.[*].limitMonthlyWithdrawal").value(hasItem(sameNumber(DEFAULT_LIMIT_MONTHLY_WITHDRAWAL))))
            .andExpect(jsonPath("$.[*].limitTransactionPurchase").value(hasItem(sameNumber(DEFAULT_LIMIT_TRANSACTION_PURCHASE))))
            .andExpect(jsonPath("$.[*].secure3DMobile").value(hasItem(DEFAULT_SECURE_3_D_MOBILE)))
            .andExpect(jsonPath("$.[*].secure3DEmail").value(hasItem(DEFAULT_SECURE_3_D_EMAIL)))
            .andExpect(jsonPath("$.[*].secure3DOutOfBandEnabled").value(hasItem(DEFAULT_SECURE_3_D_OUT_OF_BAND_ENABLED)))
            .andExpect(jsonPath("$.[*].secure3DOutOfBandId").value(hasItem(DEFAULT_SECURE_3_D_OUT_OF_BAND_ID)))
            .andExpect(jsonPath("$.[*].deliveryFirstName").value(hasItem(DEFAULT_DELIVERY_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].deliveryLastName").value(hasItem(DEFAULT_DELIVERY_LAST_NAME)))
            .andExpect(jsonPath("$.[*].deliveryCompanyName").value(hasItem(DEFAULT_DELIVERY_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].deliveryAddress1").value(hasItem(DEFAULT_DELIVERY_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].deliveryAddress2").value(hasItem(DEFAULT_DELIVERY_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].deliveryPostalCode").value(hasItem(DEFAULT_DELIVERY_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].deliveryCity").value(hasItem(DEFAULT_DELIVERY_CITY)))
            .andExpect(jsonPath("$.[*].deliveryCountryCode").value(hasItem(DEFAULT_DELIVERY_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].deliveryDispatchMethod").value(hasItem(DEFAULT_DELIVERY_DISPATCH_METHOD.toString())))
            .andExpect(jsonPath("$.[*].deliveryPhone").value(hasItem(DEFAULT_DELIVERY_PHONE)))
            .andExpect(jsonPath("$.[*].deliveryTrackingNumber").value(hasItem(DEFAULT_DELIVERY_TRACKING_NUMBER)))
            .andExpect(jsonPath("$.[*].isEnrolledFor3DSecure").value(hasItem(DEFAULT_IS_ENROLLED_FOR_3_D_SECURE)))
            .andExpect(jsonPath("$.[*].isCard3DSecureActivated").value(hasItem(DEFAULT_IS_CARD_3_D_SECURE_ACTIVATED)))
            .andExpect(jsonPath("$.[*].renewAutomatically").value(hasItem(DEFAULT_RENEW_AUTOMATICALLY)))
            .andExpect(jsonPath("$.[*].isDisposable").value(hasItem(DEFAULT_IS_DISPOSABLE)))
            .andExpect(jsonPath("$.[*].securityContactlessEnabled").value(hasItem(DEFAULT_SECURITY_CONTACTLESS_ENABLED)))
            .andExpect(jsonPath("$.[*].securityWithdrawalEnabled").value(hasItem(DEFAULT_SECURITY_WITHDRAWAL_ENABLED)))
            .andExpect(jsonPath("$.[*].securityInternetPurchaseEnabled").value(hasItem(DEFAULT_SECURITY_INTERNET_PURCHASE_ENABLED)))
            .andExpect(jsonPath("$.[*].securityOverallLimitsEnabled").value(hasItem(DEFAULT_SECURITY_OVERALL_LIMITS_ENABLED)))
            .andExpect(jsonPath("$.[*].securityAllTimeLimitsEnabled").value(hasItem(DEFAULT_SECURITY_ALL_TIME_LIMITS_ENABLED)))
            .andExpect(jsonPath("$.[*].personalizationProductCode").value(hasItem(DEFAULT_PERSONALIZATION_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].carrierType").value(hasItem(DEFAULT_CARRIER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardMetadataProfileId").value(hasItem(DEFAULT_CARD_METADATA_PROFILE_ID)))
            .andExpect(jsonPath("$.[*].activatedAt").value(hasItem(DEFAULT_ACTIVATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].closedAt").value(hasItem(DEFAULT_CLOSED_AT.toString())))
            .andExpect(jsonPath("$.[*].closedBy").value(hasItem(DEFAULT_CLOSED_BY)))
            .andExpect(jsonPath("$.[*].closeReason").value(hasItem(DEFAULT_CLOSE_REASON.toString())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.toString())))
            .andExpect(jsonPath("$.[*].dispatchedAt").value(hasItem(DEFAULT_DISPATCHED_AT.toString())))
            .andExpect(jsonPath("$.[*].notificationReceiptsReminderEnabled").value(hasItem(DEFAULT_NOTIFICATION_RECEIPTS_REMINDER_ENABLED)))
            .andExpect(
                jsonPath("$.[*].notificationInstantSpendUpdateEnabled").value(hasItem(DEFAULT_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED))
            )
            .andExpect(jsonPath("$.[*].disposableType").value(hasItem(DEFAULT_DISPOSABLE_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCard() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc
            .perform(get(ENTITY_API_URL_ID, card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().toString()))
            .andExpect(jsonPath("$.predecessorCardId").value(DEFAULT_PREDECESSOR_CARD_ID.toString()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID.toString()))
            //            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.maskedCardNumber").value(DEFAULT_MASKED_CARD_NUMBER))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.blockType").value(DEFAULT_BLOCK_TYPE.toString()))
            .andExpect(jsonPath("$.blockedAt").value(DEFAULT_BLOCKED_AT.toString()))
            .andExpect(jsonPath("$.blockedBy").value(DEFAULT_BLOCKED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.embossingName").value(DEFAULT_EMBOSSING_NAME))
            .andExpect(jsonPath("$.embossingFirstName").value(DEFAULT_EMBOSSING_FIRST_NAME))
            .andExpect(jsonPath("$.embossingLastName").value(DEFAULT_EMBOSSING_LAST_NAME))
            .andExpect(jsonPath("$.embossingCompanyName").value(DEFAULT_EMBOSSING_COMPANY_NAME))
            .andExpect(jsonPath("$.limitDailyPurchase").value(sameNumber(DEFAULT_LIMIT_DAILY_PURCHASE)))
            .andExpect(jsonPath("$.limitDailyWithdrawal").value(sameNumber(DEFAULT_LIMIT_DAILY_WITHDRAWAL)))
            .andExpect(jsonPath("$.limitMonthlyPurchase").value(sameNumber(DEFAULT_LIMIT_MONTHLY_PURCHASE)))
            .andExpect(jsonPath("$.limitMonthlyWithdrawal").value(sameNumber(DEFAULT_LIMIT_MONTHLY_WITHDRAWAL)))
            .andExpect(jsonPath("$.limitTransactionPurchase").value(sameNumber(DEFAULT_LIMIT_TRANSACTION_PURCHASE)))
            .andExpect(jsonPath("$.secure3DMobile").value(DEFAULT_SECURE_3_D_MOBILE))
            .andExpect(jsonPath("$.secure3DEmail").value(DEFAULT_SECURE_3_D_EMAIL))
            .andExpect(jsonPath("$.secure3DOutOfBandEnabled").value(DEFAULT_SECURE_3_D_OUT_OF_BAND_ENABLED))
            .andExpect(jsonPath("$.secure3DOutOfBandId").value(DEFAULT_SECURE_3_D_OUT_OF_BAND_ID))
            .andExpect(jsonPath("$.deliveryFirstName").value(DEFAULT_DELIVERY_FIRST_NAME))
            .andExpect(jsonPath("$.deliveryLastName").value(DEFAULT_DELIVERY_LAST_NAME))
            .andExpect(jsonPath("$.deliveryCompanyName").value(DEFAULT_DELIVERY_COMPANY_NAME))
            .andExpect(jsonPath("$.deliveryAddress1").value(DEFAULT_DELIVERY_ADDRESS_1))
            .andExpect(jsonPath("$.deliveryAddress2").value(DEFAULT_DELIVERY_ADDRESS_2))
            .andExpect(jsonPath("$.deliveryPostalCode").value(DEFAULT_DELIVERY_POSTAL_CODE))
            .andExpect(jsonPath("$.deliveryCity").value(DEFAULT_DELIVERY_CITY))
            .andExpect(jsonPath("$.deliveryCountryCode").value(DEFAULT_DELIVERY_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.deliveryDispatchMethod").value(DEFAULT_DELIVERY_DISPATCH_METHOD.toString()))
            .andExpect(jsonPath("$.deliveryPhone").value(DEFAULT_DELIVERY_PHONE))
            .andExpect(jsonPath("$.deliveryTrackingNumber").value(DEFAULT_DELIVERY_TRACKING_NUMBER))
            .andExpect(jsonPath("$.isEnrolledFor3DSecure").value(DEFAULT_IS_ENROLLED_FOR_3_D_SECURE))
            .andExpect(jsonPath("$.isCard3DSecureActivated").value(DEFAULT_IS_CARD_3_D_SECURE_ACTIVATED))
            .andExpect(jsonPath("$.renewAutomatically").value(DEFAULT_RENEW_AUTOMATICALLY))
            .andExpect(jsonPath("$.isDisposable").value(DEFAULT_IS_DISPOSABLE))
            .andExpect(jsonPath("$.securityContactlessEnabled").value(DEFAULT_SECURITY_CONTACTLESS_ENABLED))
            .andExpect(jsonPath("$.securityWithdrawalEnabled").value(DEFAULT_SECURITY_WITHDRAWAL_ENABLED))
            .andExpect(jsonPath("$.securityInternetPurchaseEnabled").value(DEFAULT_SECURITY_INTERNET_PURCHASE_ENABLED))
            .andExpect(jsonPath("$.securityOverallLimitsEnabled").value(DEFAULT_SECURITY_OVERALL_LIMITS_ENABLED))
            .andExpect(jsonPath("$.securityAllTimeLimitsEnabled").value(DEFAULT_SECURITY_ALL_TIME_LIMITS_ENABLED))
            .andExpect(jsonPath("$.personalizationProductCode").value(DEFAULT_PERSONALIZATION_PRODUCT_CODE))
            .andExpect(jsonPath("$.carrierType").value(DEFAULT_CARRIER_TYPE.toString()))
            .andExpect(jsonPath("$.cardMetadataProfileId").value(DEFAULT_CARD_METADATA_PROFILE_ID))
            .andExpect(jsonPath("$.activatedAt").value(DEFAULT_ACTIVATED_AT.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.closedAt").value(DEFAULT_CLOSED_AT.toString()))
            .andExpect(jsonPath("$.closedBy").value(DEFAULT_CLOSED_BY))
            .andExpect(jsonPath("$.closeReason").value(DEFAULT_CLOSE_REASON.toString()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.toString()))
            .andExpect(jsonPath("$.dispatchedAt").value(DEFAULT_DISPATCHED_AT.toString()))
            .andExpect(jsonPath("$.notificationReceiptsReminderEnabled").value(DEFAULT_NOTIFICATION_RECEIPTS_REMINDER_ENABLED))
            .andExpect(jsonPath("$.notificationInstantSpendUpdateEnabled").value(DEFAULT_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED))
            .andExpect(jsonPath("$.disposableType").value(DEFAULT_DISPOSABLE_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCard() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .predecessorCardId(UPDATED_PREDECESSOR_CARD_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            //            .personId(UPDATED_PERSON_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .maskedCardNumber(UPDATED_MASKED_CARD_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .blockType(UPDATED_BLOCK_TYPE)
            .blockedAt(UPDATED_BLOCKED_AT)
            .blockedBy(UPDATED_BLOCKED_BY)
            .status(UPDATED_STATUS)
            .embossingName(UPDATED_EMBOSSING_NAME)
            .embossingFirstName(UPDATED_EMBOSSING_FIRST_NAME)
            .embossingLastName(UPDATED_EMBOSSING_LAST_NAME)
            .embossingCompanyName(UPDATED_EMBOSSING_COMPANY_NAME)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(UPDATED_LIMIT_MONTHLY_WITHDRAWAL)
            .limitTransactionPurchase(UPDATED_LIMIT_TRANSACTION_PURCHASE)
            .secure3DMobile(UPDATED_SECURE_3_D_MOBILE)
            .secure3DEmail(UPDATED_SECURE_3_D_EMAIL)
            .secure3DOutOfBandEnabled(UPDATED_SECURE_3_D_OUT_OF_BAND_ENABLED)
            .secure3DOutOfBandId(UPDATED_SECURE_3_D_OUT_OF_BAND_ID)
            .deliveryFirstName(UPDATED_DELIVERY_FIRST_NAME)
            .deliveryLastName(UPDATED_DELIVERY_LAST_NAME)
            .deliveryCompanyName(UPDATED_DELIVERY_COMPANY_NAME)
            .deliveryAddress1(UPDATED_DELIVERY_ADDRESS_1)
            .deliveryAddress2(UPDATED_DELIVERY_ADDRESS_2)
            .deliveryPostalCode(UPDATED_DELIVERY_POSTAL_CODE)
            .deliveryCity(UPDATED_DELIVERY_CITY)
            .deliveryCountryCode(UPDATED_DELIVERY_COUNTRY_CODE)
            .deliveryDispatchMethod(UPDATED_DELIVERY_DISPATCH_METHOD)
            .deliveryPhone(UPDATED_DELIVERY_PHONE)
            .deliveryTrackingNumber(UPDATED_DELIVERY_TRACKING_NUMBER)
            .isEnrolledFor3DSecure(UPDATED_IS_ENROLLED_FOR_3_D_SECURE)
            .isCard3DSecureActivated(UPDATED_IS_CARD_3_D_SECURE_ACTIVATED)
            .renewAutomatically(UPDATED_RENEW_AUTOMATICALLY)
            .isDisposable(UPDATED_IS_DISPOSABLE)
            .securityContactlessEnabled(UPDATED_SECURITY_CONTACTLESS_ENABLED)
            .securityWithdrawalEnabled(UPDATED_SECURITY_WITHDRAWAL_ENABLED)
            .securityInternetPurchaseEnabled(UPDATED_SECURITY_INTERNET_PURCHASE_ENABLED)
            .securityOverallLimitsEnabled(UPDATED_SECURITY_OVERALL_LIMITS_ENABLED)
            .securityAllTimeLimitsEnabled(UPDATED_SECURITY_ALL_TIME_LIMITS_ENABLED)
            .personalizationProductCode(UPDATED_PERSONALIZATION_PRODUCT_CODE)
            .carrierType(UPDATED_CARRIER_TYPE)
            .cardMetadataProfileId(UPDATED_CARD_METADATA_PROFILE_ID)
            .activatedAt(UPDATED_ACTIVATED_AT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .closedAt(UPDATED_CLOSED_AT)
            .closedBy(UPDATED_CLOSED_BY)
            .closeReason(UPDATED_CLOSE_REASON)
            .companyId(DEFAULT_COMPANY_ID)
            .dispatchedAt(UPDATED_DISPATCHED_AT)
            .notificationReceiptsReminderEnabled(UPDATED_NOTIFICATION_RECEIPTS_REMINDER_ENABLED)
            .notificationInstantSpendUpdateEnabled(UPDATED_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED)
            .disposableType(UPDATED_DISPOSABLE_TYPE);
        CardDTO cardDTO = cardMapper.toDto(updatedCard);

        restCardMockMvc
            .perform(put(ENTITY_API_URL_ID, cardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCardToMatchAllProperties(updatedCard);
    }

    @Test
    @Transactional
    void putNonExistingCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(UUID.randomUUID());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(put(ENTITY_API_URL_ID, cardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(UUID.randomUUID());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(UUID.randomUUID());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCardWithPatch() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the card using partial update
        Card partialUpdatedCard = new Card();
        partialUpdatedCard.setId(card.getId());

        partialUpdatedCard
            .predecessorCardId(UPDATED_PREDECESSOR_CARD_ID)
            //            .personId(UPDATED_PERSON_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .blockedAt(UPDATED_BLOCKED_AT)
            .embossingName(UPDATED_EMBOSSING_NAME)
            .embossingFirstName(UPDATED_EMBOSSING_FIRST_NAME)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitTransactionPurchase(UPDATED_LIMIT_TRANSACTION_PURCHASE)
            .secure3DMobile(UPDATED_SECURE_3_D_MOBILE)
            .secure3DOutOfBandEnabled(UPDATED_SECURE_3_D_OUT_OF_BAND_ENABLED)
            .secure3DOutOfBandId(UPDATED_SECURE_3_D_OUT_OF_BAND_ID)
            .deliveryFirstName(UPDATED_DELIVERY_FIRST_NAME)
            .deliveryCity(UPDATED_DELIVERY_CITY)
            .deliveryPhone(UPDATED_DELIVERY_PHONE)
            .deliveryTrackingNumber(UPDATED_DELIVERY_TRACKING_NUMBER)
            .renewAutomatically(UPDATED_RENEW_AUTOMATICALLY)
            .securityContactlessEnabled(UPDATED_SECURITY_CONTACTLESS_ENABLED)
            .securityWithdrawalEnabled(UPDATED_SECURITY_WITHDRAWAL_ENABLED)
            .securityInternetPurchaseEnabled(UPDATED_SECURITY_INTERNET_PURCHASE_ENABLED)
            .securityAllTimeLimitsEnabled(UPDATED_SECURITY_ALL_TIME_LIMITS_ENABLED)
            .personalizationProductCode(UPDATED_PERSONALIZATION_PRODUCT_CODE)
            .carrierType(UPDATED_CARRIER_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .closedAt(UPDATED_CLOSED_AT)
            .closedBy(UPDATED_CLOSED_BY)
            .closeReason(UPDATED_CLOSE_REASON)
            .companyId(DEFAULT_COMPANY_ID)
            .dispatchedAt(UPDATED_DISPATCHED_AT);

        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCard, card), getPersistedCard(card));
    }

    @Test
    @Transactional
    void fullUpdateCardWithPatch() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the card using partial update
        Card partialUpdatedCard = new Card();
        partialUpdatedCard.setId(card.getId());

        partialUpdatedCard
            .predecessorCardId(UPDATED_PREDECESSOR_CARD_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            //            .personId(UPDATED_PERSON_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .maskedCardNumber(UPDATED_MASKED_CARD_NUMBER)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .blockType(UPDATED_BLOCK_TYPE)
            .blockedAt(UPDATED_BLOCKED_AT)
            .blockedBy(UPDATED_BLOCKED_BY)
            .status(UPDATED_STATUS)
            .embossingName(UPDATED_EMBOSSING_NAME)
            .embossingFirstName(UPDATED_EMBOSSING_FIRST_NAME)
            .embossingLastName(UPDATED_EMBOSSING_LAST_NAME)
            .embossingCompanyName(UPDATED_EMBOSSING_COMPANY_NAME)
            .limitDailyPurchase(UPDATED_LIMIT_DAILY_PURCHASE)
            .limitDailyWithdrawal(UPDATED_LIMIT_DAILY_WITHDRAWAL)
            .limitMonthlyPurchase(UPDATED_LIMIT_MONTHLY_PURCHASE)
            .limitMonthlyWithdrawal(UPDATED_LIMIT_MONTHLY_WITHDRAWAL)
            .limitTransactionPurchase(UPDATED_LIMIT_TRANSACTION_PURCHASE)
            .secure3DMobile(UPDATED_SECURE_3_D_MOBILE)
            .secure3DEmail(UPDATED_SECURE_3_D_EMAIL)
            .secure3DOutOfBandEnabled(UPDATED_SECURE_3_D_OUT_OF_BAND_ENABLED)
            .secure3DOutOfBandId(UPDATED_SECURE_3_D_OUT_OF_BAND_ID)
            .deliveryFirstName(UPDATED_DELIVERY_FIRST_NAME)
            .deliveryLastName(UPDATED_DELIVERY_LAST_NAME)
            .deliveryCompanyName(UPDATED_DELIVERY_COMPANY_NAME)
            .deliveryAddress1(UPDATED_DELIVERY_ADDRESS_1)
            .deliveryAddress2(UPDATED_DELIVERY_ADDRESS_2)
            .deliveryPostalCode(UPDATED_DELIVERY_POSTAL_CODE)
            .deliveryCity(UPDATED_DELIVERY_CITY)
            .deliveryCountryCode(UPDATED_DELIVERY_COUNTRY_CODE)
            .deliveryDispatchMethod(UPDATED_DELIVERY_DISPATCH_METHOD)
            .deliveryPhone(UPDATED_DELIVERY_PHONE)
            .deliveryTrackingNumber(UPDATED_DELIVERY_TRACKING_NUMBER)
            .isEnrolledFor3DSecure(UPDATED_IS_ENROLLED_FOR_3_D_SECURE)
            .isCard3DSecureActivated(UPDATED_IS_CARD_3_D_SECURE_ACTIVATED)
            .renewAutomatically(UPDATED_RENEW_AUTOMATICALLY)
            .isDisposable(UPDATED_IS_DISPOSABLE)
            .securityContactlessEnabled(UPDATED_SECURITY_CONTACTLESS_ENABLED)
            .securityWithdrawalEnabled(UPDATED_SECURITY_WITHDRAWAL_ENABLED)
            .securityInternetPurchaseEnabled(UPDATED_SECURITY_INTERNET_PURCHASE_ENABLED)
            .securityOverallLimitsEnabled(UPDATED_SECURITY_OVERALL_LIMITS_ENABLED)
            .securityAllTimeLimitsEnabled(UPDATED_SECURITY_ALL_TIME_LIMITS_ENABLED)
            .personalizationProductCode(UPDATED_PERSONALIZATION_PRODUCT_CODE)
            .carrierType(UPDATED_CARRIER_TYPE)
            .cardMetadataProfileId(UPDATED_CARD_METADATA_PROFILE_ID)
            .activatedAt(UPDATED_ACTIVATED_AT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .closedAt(UPDATED_CLOSED_AT)
            .closedBy(UPDATED_CLOSED_BY)
            .closeReason(UPDATED_CLOSE_REASON)
            .companyId(DEFAULT_COMPANY_ID)
            .dispatchedAt(UPDATED_DISPATCHED_AT)
            .notificationReceiptsReminderEnabled(UPDATED_NOTIFICATION_RECEIPTS_REMINDER_ENABLED)
            .notificationInstantSpendUpdateEnabled(UPDATED_NOTIFICATION_INSTANT_SPEND_UPDATE_ENABLED)
            .disposableType(UPDATED_DISPOSABLE_TYPE);

        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCardUpdatableFieldsEquals(partialUpdatedCard, getPersistedCard(partialUpdatedCard));
    }

    @Test
    @Transactional
    void patchNonExistingCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(UUID.randomUUID());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cardDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(UUID.randomUUID());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(UUID.randomUUID());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCard() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the card
        restCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, card.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cardRepository.count();
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

    protected Card getPersistedCard(Card card) {
        return cardRepository.findById(card.getId()).orElseThrow();
    }

    protected void assertPersistedCardToMatchAllProperties(Card expectedCard) {
        assertCardAllPropertiesEquals(expectedCard, getPersistedCard(expectedCard));
    }

    protected void assertPersistedCardToMatchUpdatableProperties(Card expectedCard) {
        assertCardAllUpdatablePropertiesEquals(expectedCard, getPersistedCard(expectedCard));
    }
}
