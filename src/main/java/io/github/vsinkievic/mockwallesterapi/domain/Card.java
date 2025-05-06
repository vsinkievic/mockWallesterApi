package io.github.vsinkievic.mockwallesterapi.domain;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardBlockType;
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
import lombok.Data;

/**
 * Represents a Card.
 */
@Entity
@Table(name = "card")
@Data
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
    private UUID personId;

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
    private CardBlockType blockType;

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

    // Builder-style setters
    public Card id(UUID id) {
        this.id = id;
        return this;
    }

    public Card predecessorCardId(UUID predecessorCardId) {
        this.predecessorCardId = predecessorCardId;
        return this;
    }

    public Card accountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public Card personId(UUID personId) {
        this.personId = personId;
        return this;
    }

    public Card externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public Card type(CardType type) {
        this.type = type;
        return this;
    }

    public Card name(String name) {
        this.name = name;
        return this;
    }

    public Card maskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
        return this;
    }

    public Card referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public Card expiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Card blockType(CardBlockType blockType) {
        this.blockType = blockType;
        return this;
    }

    public Card blockedAt(Instant blockedAt) {
        this.blockedAt = blockedAt;
        return this;
    }

    public Card blockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
        return this;
    }

    public Card status(CardStatus status) {
        this.status = status;
        return this;
    }

    public Card embossingName(String embossingName) {
        this.embossingName = embossingName;
        return this;
    }

    public Card embossingFirstName(String embossingFirstName) {
        this.embossingFirstName = embossingFirstName;
        return this;
    }

    public Card embossingLastName(String embossingLastName) {
        this.embossingLastName = embossingLastName;
        return this;
    }

    public Card embossingCompanyName(String embossingCompanyName) {
        this.embossingCompanyName = embossingCompanyName;
        return this;
    }

    public Card limitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.limitDailyPurchase = limitDailyPurchase;
        return this;
    }

    public Card limitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.limitDailyWithdrawal = limitDailyWithdrawal;
        return this;
    }

    public Card limitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.limitMonthlyPurchase = limitMonthlyPurchase;
        return this;
    }

    public Card limitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.limitMonthlyWithdrawal = limitMonthlyWithdrawal;
        return this;
    }

    public Card limitTransactionPurchase(BigDecimal limitTransactionPurchase) {
        this.limitTransactionPurchase = limitTransactionPurchase;
        return this;
    }

    public Card secure3DType(Secure3DType secure3DType) {
        this.secure3DType = secure3DType;
        return this;
    }

    public Card secure3DMobile(String secure3DMobile) {
        this.secure3DMobile = secure3DMobile;
        return this;
    }

    public Card secure3DEmail(String secure3DEmail) {
        this.secure3DEmail = secure3DEmail;
        return this;
    }

    public Card secure3DLanguageCode(LanguageCode secure3DLanguageCode) {
        this.secure3DLanguageCode = secure3DLanguageCode;
        return this;
    }

    public Card secure3DOutOfBandEnabled(Boolean secure3DOutOfBandEnabled) {
        this.secure3DOutOfBandEnabled = secure3DOutOfBandEnabled;
        return this;
    }

    public Card secure3DOutOfBandId(String secure3DOutOfBandId) {
        this.secure3DOutOfBandId = secure3DOutOfBandId;
        return this;
    }

    public Card deliveryFirstName(String deliveryFirstName) {
        this.deliveryFirstName = deliveryFirstName;
        return this;
    }

    public Card deliveryLastName(String deliveryLastName) {
        this.deliveryLastName = deliveryLastName;
        return this;
    }

    public Card deliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
        return this;
    }

    public Card deliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
        return this;
    }

    public Card deliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
        return this;
    }

    public Card deliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
        return this;
    }

    public Card deliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
        return this;
    }

    public Card deliveryCountryCode(CountryCode deliveryCountryCode) {
        this.deliveryCountryCode = deliveryCountryCode;
        return this;
    }

    public Card deliveryDispatchMethod(DispatchMethod deliveryDispatchMethod) {
        this.deliveryDispatchMethod = deliveryDispatchMethod;
        return this;
    }

    public Card deliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
        return this;
    }

    public Card deliveryTrackingNumber(String deliveryTrackingNumber) {
        this.deliveryTrackingNumber = deliveryTrackingNumber;
        return this;
    }

    public Card isEnrolledFor3DSecure(Boolean isEnrolledFor3DSecure) {
        this.isEnrolledFor3DSecure = isEnrolledFor3DSecure;
        return this;
    }

    public Card isCard3DSecureActivated(Boolean isCard3DSecureActivated) {
        this.isCard3DSecureActivated = isCard3DSecureActivated;
        return this;
    }

    public Card renewAutomatically(Boolean renewAutomatically) {
        this.renewAutomatically = renewAutomatically;
        return this;
    }

    public Card isDisposable(Boolean isDisposable) {
        this.isDisposable = isDisposable;
        return this;
    }

    public Card securityContactlessEnabled(Boolean securityContactlessEnabled) {
        this.securityContactlessEnabled = securityContactlessEnabled;
        return this;
    }

    public Card securityWithdrawalEnabled(Boolean securityWithdrawalEnabled) {
        this.securityWithdrawalEnabled = securityWithdrawalEnabled;
        return this;
    }

    public Card securityInternetPurchaseEnabled(Boolean securityInternetPurchaseEnabled) {
        this.securityInternetPurchaseEnabled = securityInternetPurchaseEnabled;
        return this;
    }

    public Card securityOverallLimitsEnabled(Boolean securityOverallLimitsEnabled) {
        this.securityOverallLimitsEnabled = securityOverallLimitsEnabled;
        return this;
    }

    public Card securityAllTimeLimitsEnabled(Boolean securityAllTimeLimitsEnabled) {
        this.securityAllTimeLimitsEnabled = securityAllTimeLimitsEnabled;
        return this;
    }

    public Card personalizationProductCode(String personalizationProductCode) {
        this.personalizationProductCode = personalizationProductCode;
        return this;
    }

    public Card carrierType(CarrierType carrierType) {
        this.carrierType = carrierType;
        return this;
    }

    public Card cardMetadataProfileId(String cardMetadataProfileId) {
        this.cardMetadataProfileId = cardMetadataProfileId;
        return this;
    }

    public Card activatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
        return this;
    }

    public Card createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Card updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Card closedAt(Instant closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public Card closedBy(String closedBy) {
        this.closedBy = closedBy;
        return this;
    }

    public Card closeReason(CardCloseReason closeReason) {
        this.closeReason = closeReason;
        return this;
    }

    public Card companyId(UUID companyId) {
        this.companyId = companyId;
        return this;
    }

    public Card dispatchedAt(Instant dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
        return this;
    }

    public Card notificationReceiptsReminderEnabled(Boolean notificationReceiptsReminderEnabled) {
        this.notificationReceiptsReminderEnabled = notificationReceiptsReminderEnabled;
        return this;
    }

    public Card notificationInstantSpendUpdateEnabled(Boolean notificationInstantSpendUpdateEnabled) {
        this.notificationInstantSpendUpdateEnabled = notificationInstantSpendUpdateEnabled;
        return this;
    }

    public Card disposableType(DisposableType disposableType) {
        this.disposableType = disposableType;
        return this;
    }

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
