package io.github.vsinkievic.mockwallesterapi.domain;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.BlockType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardCloseReason;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CarrierType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.DispatchMethod;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.DisposableType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.LanguageCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.Secure3DType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a Card.
 */
@Entity
@Table(name = "card")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "predecessor_card_id")
    private UUID predecessorCardId;

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "external_id")
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CardType type;

    @Column(name = "name")
    private String name;

    @Column(name = "masked_card_number")
    private String maskedCardNumber;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "block_type")
    private BlockType blockType;

    @Column(name = "blocked_at")
    private Instant blockedAt;

    @Column(name = "blocked_by")
    private String blockedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CardStatus status;

    @Column(name = "embossing_name")
    private String embossingName;

    @Column(name = "embossing_first_name")
    private String embossingFirstName;

    @Column(name = "embossing_last_name")
    private String embossingLastName;

    @Column(name = "embossing_company_name")
    private String embossingCompanyName;

    @Column(name = "limit_daily_purchase", precision = 21, scale = 2)
    private BigDecimal limitDailyPurchase;

    @Column(name = "limit_daily_withdrawal", precision = 21, scale = 2)
    private BigDecimal limitDailyWithdrawal;

    @Column(name = "limit_monthly_purchase", precision = 21, scale = 2)
    private BigDecimal limitMonthlyPurchase;

    @Column(name = "limit_monthly_withdrawal", precision = 21, scale = 2)
    private BigDecimal limitMonthlyWithdrawal;

    @Column(name = "limit_transaction_purchase", precision = 21, scale = 2)
    private BigDecimal limitTransactionPurchase;

    @Enumerated(EnumType.STRING)
    @Column(name = "secure_3_d_type")
    private Secure3DType secure3DType;

    @Column(name = "secure_3_d_mobile")
    private String secure3DMobile;

    @Column(name = "secure_3_d_email")
    private String secure3DEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "secure_3_d_language_code")
    private LanguageCode secure3DLanguageCode;

    @Column(name = "secure_3_d_out_of_band_enabled")
    private Boolean secure3DOutOfBandEnabled;

    @Column(name = "secure_3_d_out_of_band_id")
    private String secure3DOutOfBandId;

    @Column(name = "delivery_first_name")
    private String deliveryFirstName;

    @Column(name = "delivery_last_name")
    private String deliveryLastName;

    @Column(name = "delivery_company_name")
    private String deliveryCompanyName;

    @Column(name = "delivery_address_1")
    private String deliveryAddress1;

    @Column(name = "delivery_address_2")
    private String deliveryAddress2;

    @Column(name = "delivery_postal_code")
    private String deliveryPostalCode;

    @Column(name = "delivery_city")
    private String deliveryCity;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_country_code")
    private CountryCode deliveryCountryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_dispatch_method")
    private DispatchMethod deliveryDispatchMethod;

    @Column(name = "delivery_phone")
    private String deliveryPhone;

    @Column(name = "delivery_tracking_number")
    private String deliveryTrackingNumber;

    @Column(name = "is_enrolled_for_3_d_secure")
    private Boolean isEnrolledFor3DSecure;

    @Column(name = "is_card_3_d_secure_activated")
    private Boolean isCard3DSecureActivated;

    @Column(name = "renew_automatically")
    private Boolean renewAutomatically;

    @Column(name = "is_disposable")
    private Boolean isDisposable;

    @Column(name = "security_contactless_enabled")
    private Boolean securityContactlessEnabled;

    @Column(name = "security_withdrawal_enabled")
    private Boolean securityWithdrawalEnabled;

    @Column(name = "security_internet_purchase_enabled")
    private Boolean securityInternetPurchaseEnabled;

    @Column(name = "security_overall_limits_enabled")
    private Boolean securityOverallLimitsEnabled;

    @Column(name = "security_all_time_limits_enabled")
    private Boolean securityAllTimeLimitsEnabled;

    @Column(name = "personalization_product_code")
    private String personalizationProductCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier_type")
    private CarrierType carrierType;

    @Column(name = "card_metadata_profile_id")
    private String cardMetadataProfileId;

    @Column(name = "activated_at")
    private Instant activatedAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Column(name = "closed_by")
    private String closedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "close_reason")
    private CardCloseReason closeReason;

    @Column(name = "company_id")
    private UUID companyId;

    @Column(name = "dispatched_at")
    private Instant dispatchedAt;

    @Column(name = "notification_receipts_reminder_enabled")
    private Boolean notificationReceiptsReminderEnabled;

    @Column(name = "notification_instant_spend_update_enabled")
    private Boolean notificationInstantSpendUpdateEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "disposable_type")
    private DisposableType disposableType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Card id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPredecessorCardId() {
        return this.predecessorCardId;
    }

    public Card predecessorCardId(UUID predecessorCardId) {
        this.setPredecessorCardId(predecessorCardId);
        return this;
    }

    public void setPredecessorCardId(UUID predecessorCardId) {
        this.predecessorCardId = predecessorCardId;
    }

    public UUID getAccountId() {
        return this.accountId;
    }

    public Card accountId(UUID accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getPersonId() {
        return this.personId;
    }

    public Card personId(String personId) {
        this.setPersonId(personId);
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public Card externalId(String externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public CardType getType() {
        return this.type;
    }

    public Card type(CardType type) {
        this.setType(type);
        return this;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Card name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaskedCardNumber() {
        return this.maskedCardNumber;
    }

    public Card maskedCardNumber(String maskedCardNumber) {
        this.setMaskedCardNumber(maskedCardNumber);
        return this;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public Card referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public Card expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BlockType getBlockType() {
        return this.blockType;
    }

    public Card blockType(BlockType blockType) {
        this.setBlockType(blockType);
        return this;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public Instant getBlockedAt() {
        return this.blockedAt;
    }

    public Card blockedAt(Instant blockedAt) {
        this.setBlockedAt(blockedAt);
        return this;
    }

    public void setBlockedAt(Instant blockedAt) {
        this.blockedAt = blockedAt;
    }

    public String getBlockedBy() {
        return this.blockedBy;
    }

    public Card blockedBy(String blockedBy) {
        this.setBlockedBy(blockedBy);
        return this;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }

    public CardStatus getStatus() {
        return this.status;
    }

    public Card status(CardStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public String getEmbossingName() {
        return this.embossingName;
    }

    public Card embossingName(String embossingName) {
        this.setEmbossingName(embossingName);
        return this;
    }

    public void setEmbossingName(String embossingName) {
        this.embossingName = embossingName;
    }

    public String getEmbossingFirstName() {
        return this.embossingFirstName;
    }

    public Card embossingFirstName(String embossingFirstName) {
        this.setEmbossingFirstName(embossingFirstName);
        return this;
    }

    public void setEmbossingFirstName(String embossingFirstName) {
        this.embossingFirstName = embossingFirstName;
    }

    public String getEmbossingLastName() {
        return this.embossingLastName;
    }

    public Card embossingLastName(String embossingLastName) {
        this.setEmbossingLastName(embossingLastName);
        return this;
    }

    public void setEmbossingLastName(String embossingLastName) {
        this.embossingLastName = embossingLastName;
    }

    public String getEmbossingCompanyName() {
        return this.embossingCompanyName;
    }

    public Card embossingCompanyName(String embossingCompanyName) {
        this.setEmbossingCompanyName(embossingCompanyName);
        return this;
    }

    public void setEmbossingCompanyName(String embossingCompanyName) {
        this.embossingCompanyName = embossingCompanyName;
    }

    public BigDecimal getLimitDailyPurchase() {
        return this.limitDailyPurchase;
    }

    public Card limitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.setLimitDailyPurchase(limitDailyPurchase);
        return this;
    }

    public void setLimitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.limitDailyPurchase = limitDailyPurchase;
    }

    public BigDecimal getLimitDailyWithdrawal() {
        return this.limitDailyWithdrawal;
    }

    public Card limitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.setLimitDailyWithdrawal(limitDailyWithdrawal);
        return this;
    }

    public void setLimitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.limitDailyWithdrawal = limitDailyWithdrawal;
    }

    public BigDecimal getLimitMonthlyPurchase() {
        return this.limitMonthlyPurchase;
    }

    public Card limitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.setLimitMonthlyPurchase(limitMonthlyPurchase);
        return this;
    }

    public void setLimitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.limitMonthlyPurchase = limitMonthlyPurchase;
    }

    public BigDecimal getLimitMonthlyWithdrawal() {
        return this.limitMonthlyWithdrawal;
    }

    public Card limitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.setLimitMonthlyWithdrawal(limitMonthlyWithdrawal);
        return this;
    }

    public void setLimitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.limitMonthlyWithdrawal = limitMonthlyWithdrawal;
    }

    public BigDecimal getLimitTransactionPurchase() {
        return this.limitTransactionPurchase;
    }

    public Card limitTransactionPurchase(BigDecimal limitTransactionPurchase) {
        this.setLimitTransactionPurchase(limitTransactionPurchase);
        return this;
    }

    public void setLimitTransactionPurchase(BigDecimal limitTransactionPurchase) {
        this.limitTransactionPurchase = limitTransactionPurchase;
    }

    public Secure3DType getSecure3DType() {
        return this.secure3DType;
    }

    public Card secure3DType(Secure3DType secure3DType) {
        this.setSecure3DType(secure3DType);
        return this;
    }

    public void setSecure3DType(Secure3DType secure3DType) {
        this.secure3DType = secure3DType;
    }

    public String getSecure3DMobile() {
        return this.secure3DMobile;
    }

    public Card secure3DMobile(String secure3DMobile) {
        this.setSecure3DMobile(secure3DMobile);
        return this;
    }

    public void setSecure3DMobile(String secure3DMobile) {
        this.secure3DMobile = secure3DMobile;
    }

    public String getSecure3DEmail() {
        return this.secure3DEmail;
    }

    public Card secure3DEmail(String secure3DEmail) {
        this.setSecure3DEmail(secure3DEmail);
        return this;
    }

    public void setSecure3DEmail(String secure3DEmail) {
        this.secure3DEmail = secure3DEmail;
    }

    public LanguageCode getSecure3DLanguageCode() {
        return this.secure3DLanguageCode;
    }

    public Card secure3DLanguageCode(LanguageCode secure3DLanguageCode) {
        this.setSecure3DLanguageCode(secure3DLanguageCode);
        return this;
    }

    public void setSecure3DLanguageCode(LanguageCode secure3DLanguageCode) {
        this.secure3DLanguageCode = secure3DLanguageCode;
    }

    public Boolean getSecure3DOutOfBandEnabled() {
        return this.secure3DOutOfBandEnabled;
    }

    public Card secure3DOutOfBandEnabled(Boolean secure3DOutOfBandEnabled) {
        this.setSecure3DOutOfBandEnabled(secure3DOutOfBandEnabled);
        return this;
    }

    public void setSecure3DOutOfBandEnabled(Boolean secure3DOutOfBandEnabled) {
        this.secure3DOutOfBandEnabled = secure3DOutOfBandEnabled;
    }

    public String getSecure3DOutOfBandId() {
        return this.secure3DOutOfBandId;
    }

    public Card secure3DOutOfBandId(String secure3DOutOfBandId) {
        this.setSecure3DOutOfBandId(secure3DOutOfBandId);
        return this;
    }

    public void setSecure3DOutOfBandId(String secure3DOutOfBandId) {
        this.secure3DOutOfBandId = secure3DOutOfBandId;
    }

    public String getDeliveryFirstName() {
        return this.deliveryFirstName;
    }

    public Card deliveryFirstName(String deliveryFirstName) {
        this.setDeliveryFirstName(deliveryFirstName);
        return this;
    }

    public void setDeliveryFirstName(String deliveryFirstName) {
        this.deliveryFirstName = deliveryFirstName;
    }

    public String getDeliveryLastName() {
        return this.deliveryLastName;
    }

    public Card deliveryLastName(String deliveryLastName) {
        this.setDeliveryLastName(deliveryLastName);
        return this;
    }

    public void setDeliveryLastName(String deliveryLastName) {
        this.deliveryLastName = deliveryLastName;
    }

    public String getDeliveryCompanyName() {
        return this.deliveryCompanyName;
    }

    public Card deliveryCompanyName(String deliveryCompanyName) {
        this.setDeliveryCompanyName(deliveryCompanyName);
        return this;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getDeliveryAddress1() {
        return this.deliveryAddress1;
    }

    public Card deliveryAddress1(String deliveryAddress1) {
        this.setDeliveryAddress1(deliveryAddress1);
        return this;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return this.deliveryAddress2;
    }

    public Card deliveryAddress2(String deliveryAddress2) {
        this.setDeliveryAddress2(deliveryAddress2);
        return this;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryPostalCode() {
        return this.deliveryPostalCode;
    }

    public Card deliveryPostalCode(String deliveryPostalCode) {
        this.setDeliveryPostalCode(deliveryPostalCode);
        return this;
    }

    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
    }

    public String getDeliveryCity() {
        return this.deliveryCity;
    }

    public Card deliveryCity(String deliveryCity) {
        this.setDeliveryCity(deliveryCity);
        return this;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public CountryCode getDeliveryCountryCode() {
        return this.deliveryCountryCode;
    }

    public Card deliveryCountryCode(CountryCode deliveryCountryCode) {
        this.setDeliveryCountryCode(deliveryCountryCode);
        return this;
    }

    public void setDeliveryCountryCode(CountryCode deliveryCountryCode) {
        this.deliveryCountryCode = deliveryCountryCode;
    }

    public DispatchMethod getDeliveryDispatchMethod() {
        return this.deliveryDispatchMethod;
    }

    public Card deliveryDispatchMethod(DispatchMethod deliveryDispatchMethod) {
        this.setDeliveryDispatchMethod(deliveryDispatchMethod);
        return this;
    }

    public void setDeliveryDispatchMethod(DispatchMethod deliveryDispatchMethod) {
        this.deliveryDispatchMethod = deliveryDispatchMethod;
    }

    public String getDeliveryPhone() {
        return this.deliveryPhone;
    }

    public Card deliveryPhone(String deliveryPhone) {
        this.setDeliveryPhone(deliveryPhone);
        return this;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryTrackingNumber() {
        return this.deliveryTrackingNumber;
    }

    public Card deliveryTrackingNumber(String deliveryTrackingNumber) {
        this.setDeliveryTrackingNumber(deliveryTrackingNumber);
        return this;
    }

    public void setDeliveryTrackingNumber(String deliveryTrackingNumber) {
        this.deliveryTrackingNumber = deliveryTrackingNumber;
    }

    public Boolean getIsEnrolledFor3DSecure() {
        return this.isEnrolledFor3DSecure;
    }

    public Card isEnrolledFor3DSecure(Boolean isEnrolledFor3DSecure) {
        this.setIsEnrolledFor3DSecure(isEnrolledFor3DSecure);
        return this;
    }

    public void setIsEnrolledFor3DSecure(Boolean isEnrolledFor3DSecure) {
        this.isEnrolledFor3DSecure = isEnrolledFor3DSecure;
    }

    public Boolean getIsCard3DSecureActivated() {
        return this.isCard3DSecureActivated;
    }

    public Card isCard3DSecureActivated(Boolean isCard3DSecureActivated) {
        this.setIsCard3DSecureActivated(isCard3DSecureActivated);
        return this;
    }

    public void setIsCard3DSecureActivated(Boolean isCard3DSecureActivated) {
        this.isCard3DSecureActivated = isCard3DSecureActivated;
    }

    public Boolean getRenewAutomatically() {
        return this.renewAutomatically;
    }

    public Card renewAutomatically(Boolean renewAutomatically) {
        this.setRenewAutomatically(renewAutomatically);
        return this;
    }

    public void setRenewAutomatically(Boolean renewAutomatically) {
        this.renewAutomatically = renewAutomatically;
    }

    public Boolean getIsDisposable() {
        return this.isDisposable;
    }

    public Card isDisposable(Boolean isDisposable) {
        this.setIsDisposable(isDisposable);
        return this;
    }

    public void setIsDisposable(Boolean isDisposable) {
        this.isDisposable = isDisposable;
    }

    public Boolean getSecurityContactlessEnabled() {
        return this.securityContactlessEnabled;
    }

    public Card securityContactlessEnabled(Boolean securityContactlessEnabled) {
        this.setSecurityContactlessEnabled(securityContactlessEnabled);
        return this;
    }

    public void setSecurityContactlessEnabled(Boolean securityContactlessEnabled) {
        this.securityContactlessEnabled = securityContactlessEnabled;
    }

    public Boolean getSecurityWithdrawalEnabled() {
        return this.securityWithdrawalEnabled;
    }

    public Card securityWithdrawalEnabled(Boolean securityWithdrawalEnabled) {
        this.setSecurityWithdrawalEnabled(securityWithdrawalEnabled);
        return this;
    }

    public void setSecurityWithdrawalEnabled(Boolean securityWithdrawalEnabled) {
        this.securityWithdrawalEnabled = securityWithdrawalEnabled;
    }

    public Boolean getSecurityInternetPurchaseEnabled() {
        return this.securityInternetPurchaseEnabled;
    }

    public Card securityInternetPurchaseEnabled(Boolean securityInternetPurchaseEnabled) {
        this.setSecurityInternetPurchaseEnabled(securityInternetPurchaseEnabled);
        return this;
    }

    public void setSecurityInternetPurchaseEnabled(Boolean securityInternetPurchaseEnabled) {
        this.securityInternetPurchaseEnabled = securityInternetPurchaseEnabled;
    }

    public Boolean getSecurityOverallLimitsEnabled() {
        return this.securityOverallLimitsEnabled;
    }

    public Card securityOverallLimitsEnabled(Boolean securityOverallLimitsEnabled) {
        this.setSecurityOverallLimitsEnabled(securityOverallLimitsEnabled);
        return this;
    }

    public void setSecurityOverallLimitsEnabled(Boolean securityOverallLimitsEnabled) {
        this.securityOverallLimitsEnabled = securityOverallLimitsEnabled;
    }

    public Boolean getSecurityAllTimeLimitsEnabled() {
        return this.securityAllTimeLimitsEnabled;
    }

    public Card securityAllTimeLimitsEnabled(Boolean securityAllTimeLimitsEnabled) {
        this.setSecurityAllTimeLimitsEnabled(securityAllTimeLimitsEnabled);
        return this;
    }

    public void setSecurityAllTimeLimitsEnabled(Boolean securityAllTimeLimitsEnabled) {
        this.securityAllTimeLimitsEnabled = securityAllTimeLimitsEnabled;
    }

    public String getPersonalizationProductCode() {
        return this.personalizationProductCode;
    }

    public Card personalizationProductCode(String personalizationProductCode) {
        this.setPersonalizationProductCode(personalizationProductCode);
        return this;
    }

    public void setPersonalizationProductCode(String personalizationProductCode) {
        this.personalizationProductCode = personalizationProductCode;
    }

    public CarrierType getCarrierType() {
        return this.carrierType;
    }

    public Card carrierType(CarrierType carrierType) {
        this.setCarrierType(carrierType);
        return this;
    }

    public void setCarrierType(CarrierType carrierType) {
        this.carrierType = carrierType;
    }

    public String getCardMetadataProfileId() {
        return this.cardMetadataProfileId;
    }

    public Card cardMetadataProfileId(String cardMetadataProfileId) {
        this.setCardMetadataProfileId(cardMetadataProfileId);
        return this;
    }

    public void setCardMetadataProfileId(String cardMetadataProfileId) {
        this.cardMetadataProfileId = cardMetadataProfileId;
    }

    public Instant getActivatedAt() {
        return this.activatedAt;
    }

    public Card activatedAt(Instant activatedAt) {
        this.setActivatedAt(activatedAt);
        return this;
    }

    public void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Card createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Card updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getClosedAt() {
        return this.closedAt;
    }

    public Card closedAt(Instant closedAt) {
        this.setClosedAt(closedAt);
        return this;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public String getClosedBy() {
        return this.closedBy;
    }

    public Card closedBy(String closedBy) {
        this.setClosedBy(closedBy);
        return this;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public CardCloseReason getCloseReason() {
        return this.closeReason;
    }

    public Card closeReason(CardCloseReason closeReason) {
        this.setCloseReason(closeReason);
        return this;
    }

    public void setCloseReason(CardCloseReason closeReason) {
        this.closeReason = closeReason;
    }

    public UUID getCompanyId() {
        return this.companyId;
    }

    public Card companyId(UUID companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public Instant getDispatchedAt() {
        return this.dispatchedAt;
    }

    public Card dispatchedAt(Instant dispatchedAt) {
        this.setDispatchedAt(dispatchedAt);
        return this;
    }

    public void setDispatchedAt(Instant dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Boolean getNotificationReceiptsReminderEnabled() {
        return this.notificationReceiptsReminderEnabled;
    }

    public Card notificationReceiptsReminderEnabled(Boolean notificationReceiptsReminderEnabled) {
        this.setNotificationReceiptsReminderEnabled(notificationReceiptsReminderEnabled);
        return this;
    }

    public void setNotificationReceiptsReminderEnabled(Boolean notificationReceiptsReminderEnabled) {
        this.notificationReceiptsReminderEnabled = notificationReceiptsReminderEnabled;
    }

    public Boolean getNotificationInstantSpendUpdateEnabled() {
        return this.notificationInstantSpendUpdateEnabled;
    }

    public Card notificationInstantSpendUpdateEnabled(Boolean notificationInstantSpendUpdateEnabled) {
        this.setNotificationInstantSpendUpdateEnabled(notificationInstantSpendUpdateEnabled);
        return this;
    }

    public void setNotificationInstantSpendUpdateEnabled(Boolean notificationInstantSpendUpdateEnabled) {
        this.notificationInstantSpendUpdateEnabled = notificationInstantSpendUpdateEnabled;
    }

    public DisposableType getDisposableType() {
        return this.disposableType;
    }

    public Card disposableType(DisposableType disposableType) {
        this.setDisposableType(disposableType);
        return this;
    }

    public void setDisposableType(DisposableType disposableType) {
        this.disposableType = disposableType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        return getId() != null && getId().equals(((Card) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", predecessorCardId='" + getPredecessorCardId() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", maskedCardNumber='" + getMaskedCardNumber() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", blockType='" + getBlockType() + "'" +
            ", blockedAt='" + getBlockedAt() + "'" +
            ", blockedBy='" + getBlockedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", embossingName='" + getEmbossingName() + "'" +
            ", embossingFirstName='" + getEmbossingFirstName() + "'" +
            ", embossingLastName='" + getEmbossingLastName() + "'" +
            ", embossingCompanyName='" + getEmbossingCompanyName() + "'" +
            ", limitDailyPurchase=" + getLimitDailyPurchase() +
            ", limitDailyWithdrawal=" + getLimitDailyWithdrawal() +
            ", limitMonthlyPurchase=" + getLimitMonthlyPurchase() +
            ", limitMonthlyWithdrawal=" + getLimitMonthlyWithdrawal() +
            ", limitTransactionPurchase=" + getLimitTransactionPurchase() +
            ", secure3DType='" + getSecure3DType() + "'" +
            ", secure3DMobile='" + getSecure3DMobile() + "'" +
            ", secure3DEmail='" + getSecure3DEmail() + "'" +
            ", secure3DLanguageCode='" + getSecure3DLanguageCode() + "'" +
            ", secure3DOutOfBandEnabled='" + getSecure3DOutOfBandEnabled() + "'" +
            ", secure3DOutOfBandId='" + getSecure3DOutOfBandId() + "'" +
            ", deliveryFirstName='" + getDeliveryFirstName() + "'" +
            ", deliveryLastName='" + getDeliveryLastName() + "'" +
            ", deliveryCompanyName='" + getDeliveryCompanyName() + "'" +
            ", deliveryAddress1='" + getDeliveryAddress1() + "'" +
            ", deliveryAddress2='" + getDeliveryAddress2() + "'" +
            ", deliveryPostalCode='" + getDeliveryPostalCode() + "'" +
            ", deliveryCity='" + getDeliveryCity() + "'" +
            ", deliveryCountryCode='" + getDeliveryCountryCode() + "'" +
            ", deliveryDispatchMethod='" + getDeliveryDispatchMethod() + "'" +
            ", deliveryPhone='" + getDeliveryPhone() + "'" +
            ", deliveryTrackingNumber='" + getDeliveryTrackingNumber() + "'" +
            ", isEnrolledFor3DSecure='" + getIsEnrolledFor3DSecure() + "'" +
            ", isCard3DSecureActivated='" + getIsCard3DSecureActivated() + "'" +
            ", renewAutomatically='" + getRenewAutomatically() + "'" +
            ", isDisposable='" + getIsDisposable() + "'" +
            ", securityContactlessEnabled='" + getSecurityContactlessEnabled() + "'" +
            ", securityWithdrawalEnabled='" + getSecurityWithdrawalEnabled() + "'" +
            ", securityInternetPurchaseEnabled='" + getSecurityInternetPurchaseEnabled() + "'" +
            ", securityOverallLimitsEnabled='" + getSecurityOverallLimitsEnabled() + "'" +
            ", securityAllTimeLimitsEnabled='" + getSecurityAllTimeLimitsEnabled() + "'" +
            ", personalizationProductCode='" + getPersonalizationProductCode() + "'" +
            ", carrierType='" + getCarrierType() + "'" +
            ", cardMetadataProfileId='" + getCardMetadataProfileId() + "'" +
            ", activatedAt='" + getActivatedAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            ", closedBy='" + getClosedBy() + "'" +
            ", closeReason='" + getCloseReason() + "'" +
            ", companyId='" + getCompanyId() + "'" +
            ", dispatchedAt='" + getDispatchedAt() + "'" +
            ", notificationReceiptsReminderEnabled='" + getNotificationReceiptsReminderEnabled() + "'" +
            ", notificationInstantSpendUpdateEnabled='" + getNotificationInstantSpendUpdateEnabled() + "'" +
            ", disposableType='" + getDisposableType() + "'" +
            "}";
    }
}
