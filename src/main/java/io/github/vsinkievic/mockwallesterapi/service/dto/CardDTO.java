package io.github.vsinkievic.mockwallesterapi.service.dto;

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
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.Card} entity.
 */
@Schema(description = "Represents a Card.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CardDTO implements Serializable {

    private UUID id;

    private UUID predecessorCardId;

    private UUID accountId;

    private String personId;

    private String externalId;

    private CardType type;

    private String name;

    private String maskedCardNumber;

    private String referenceNumber;

    private Instant expiryDate;

    private BlockType blockType;

    private Instant blockedAt;

    private String blockedBy;

    private CardStatus status;

    private String embossingName;

    private String embossingFirstName;

    private String embossingLastName;

    private String embossingCompanyName;

    private BigDecimal limitDailyPurchase;

    private BigDecimal limitDailyWithdrawal;

    private BigDecimal limitMonthlyPurchase;

    private BigDecimal limitMonthlyWithdrawal;

    private BigDecimal limitTransactionPurchase;

    private Secure3DType secure3DType;

    private String secure3DMobile;

    private String secure3DEmail;

    private LanguageCode secure3DLanguageCode;

    private Boolean secure3DOutOfBandEnabled;

    private String secure3DOutOfBandId;

    private String deliveryFirstName;

    private String deliveryLastName;

    private String deliveryCompanyName;

    private String deliveryAddress1;

    private String deliveryAddress2;

    private String deliveryPostalCode;

    private String deliveryCity;

    private CountryCode deliveryCountryCode;

    private DispatchMethod deliveryDispatchMethod;

    private String deliveryPhone;

    private String deliveryTrackingNumber;

    private Boolean isEnrolledFor3DSecure;

    private Boolean isCard3DSecureActivated;

    private Boolean renewAutomatically;

    private Boolean isDisposable;

    private Boolean securityContactlessEnabled;

    private Boolean securityWithdrawalEnabled;

    private Boolean securityInternetPurchaseEnabled;

    private Boolean securityOverallLimitsEnabled;

    private Boolean securityAllTimeLimitsEnabled;

    private String personalizationProductCode;

    private CarrierType carrierType;

    private String cardMetadataProfileId;

    private Instant activatedAt;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant closedAt;

    private String closedBy;

    private CardCloseReason closeReason;

    private UUID companyId;

    private Instant dispatchedAt;

    private Boolean notificationReceiptsReminderEnabled;

    private Boolean notificationInstantSpendUpdateEnabled;

    private DisposableType disposableType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPredecessorCardId() {
        return predecessorCardId;
    }

    public void setPredecessorCardId(UUID predecessorCardId) {
        this.predecessorCardId = predecessorCardId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public Instant getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(Instant blockedAt) {
        this.blockedAt = blockedAt;
    }

    public String getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public String getEmbossingName() {
        return embossingName;
    }

    public void setEmbossingName(String embossingName) {
        this.embossingName = embossingName;
    }

    public String getEmbossingFirstName() {
        return embossingFirstName;
    }

    public void setEmbossingFirstName(String embossingFirstName) {
        this.embossingFirstName = embossingFirstName;
    }

    public String getEmbossingLastName() {
        return embossingLastName;
    }

    public void setEmbossingLastName(String embossingLastName) {
        this.embossingLastName = embossingLastName;
    }

    public String getEmbossingCompanyName() {
        return embossingCompanyName;
    }

    public void setEmbossingCompanyName(String embossingCompanyName) {
        this.embossingCompanyName = embossingCompanyName;
    }

    public BigDecimal getLimitDailyPurchase() {
        return limitDailyPurchase;
    }

    public void setLimitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.limitDailyPurchase = limitDailyPurchase;
    }

    public BigDecimal getLimitDailyWithdrawal() {
        return limitDailyWithdrawal;
    }

    public void setLimitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.limitDailyWithdrawal = limitDailyWithdrawal;
    }

    public BigDecimal getLimitMonthlyPurchase() {
        return limitMonthlyPurchase;
    }

    public void setLimitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.limitMonthlyPurchase = limitMonthlyPurchase;
    }

    public BigDecimal getLimitMonthlyWithdrawal() {
        return limitMonthlyWithdrawal;
    }

    public void setLimitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.limitMonthlyWithdrawal = limitMonthlyWithdrawal;
    }

    public BigDecimal getLimitTransactionPurchase() {
        return limitTransactionPurchase;
    }

    public void setLimitTransactionPurchase(BigDecimal limitTransactionPurchase) {
        this.limitTransactionPurchase = limitTransactionPurchase;
    }

    public Secure3DType getSecure3DType() {
        return secure3DType;
    }

    public void setSecure3DType(Secure3DType secure3DType) {
        this.secure3DType = secure3DType;
    }

    public String getSecure3DMobile() {
        return secure3DMobile;
    }

    public void setSecure3DMobile(String secure3DMobile) {
        this.secure3DMobile = secure3DMobile;
    }

    public String getSecure3DEmail() {
        return secure3DEmail;
    }

    public void setSecure3DEmail(String secure3DEmail) {
        this.secure3DEmail = secure3DEmail;
    }

    public LanguageCode getSecure3DLanguageCode() {
        return secure3DLanguageCode;
    }

    public void setSecure3DLanguageCode(LanguageCode secure3DLanguageCode) {
        this.secure3DLanguageCode = secure3DLanguageCode;
    }

    public Boolean getSecure3DOutOfBandEnabled() {
        return secure3DOutOfBandEnabled;
    }

    public void setSecure3DOutOfBandEnabled(Boolean secure3DOutOfBandEnabled) {
        this.secure3DOutOfBandEnabled = secure3DOutOfBandEnabled;
    }

    public String getSecure3DOutOfBandId() {
        return secure3DOutOfBandId;
    }

    public void setSecure3DOutOfBandId(String secure3DOutOfBandId) {
        this.secure3DOutOfBandId = secure3DOutOfBandId;
    }

    public String getDeliveryFirstName() {
        return deliveryFirstName;
    }

    public void setDeliveryFirstName(String deliveryFirstName) {
        this.deliveryFirstName = deliveryFirstName;
    }

    public String getDeliveryLastName() {
        return deliveryLastName;
    }

    public void setDeliveryLastName(String deliveryLastName) {
        this.deliveryLastName = deliveryLastName;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryPostalCode() {
        return deliveryPostalCode;
    }

    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public CountryCode getDeliveryCountryCode() {
        return deliveryCountryCode;
    }

    public void setDeliveryCountryCode(CountryCode deliveryCountryCode) {
        this.deliveryCountryCode = deliveryCountryCode;
    }

    public DispatchMethod getDeliveryDispatchMethod() {
        return deliveryDispatchMethod;
    }

    public void setDeliveryDispatchMethod(DispatchMethod deliveryDispatchMethod) {
        this.deliveryDispatchMethod = deliveryDispatchMethod;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryTrackingNumber() {
        return deliveryTrackingNumber;
    }

    public void setDeliveryTrackingNumber(String deliveryTrackingNumber) {
        this.deliveryTrackingNumber = deliveryTrackingNumber;
    }

    public Boolean getIsEnrolledFor3DSecure() {
        return isEnrolledFor3DSecure;
    }

    public void setIsEnrolledFor3DSecure(Boolean isEnrolledFor3DSecure) {
        this.isEnrolledFor3DSecure = isEnrolledFor3DSecure;
    }

    public Boolean getIsCard3DSecureActivated() {
        return isCard3DSecureActivated;
    }

    public void setIsCard3DSecureActivated(Boolean isCard3DSecureActivated) {
        this.isCard3DSecureActivated = isCard3DSecureActivated;
    }

    public Boolean getRenewAutomatically() {
        return renewAutomatically;
    }

    public void setRenewAutomatically(Boolean renewAutomatically) {
        this.renewAutomatically = renewAutomatically;
    }

    public Boolean getIsDisposable() {
        return isDisposable;
    }

    public void setIsDisposable(Boolean isDisposable) {
        this.isDisposable = isDisposable;
    }

    public Boolean getSecurityContactlessEnabled() {
        return securityContactlessEnabled;
    }

    public void setSecurityContactlessEnabled(Boolean securityContactlessEnabled) {
        this.securityContactlessEnabled = securityContactlessEnabled;
    }

    public Boolean getSecurityWithdrawalEnabled() {
        return securityWithdrawalEnabled;
    }

    public void setSecurityWithdrawalEnabled(Boolean securityWithdrawalEnabled) {
        this.securityWithdrawalEnabled = securityWithdrawalEnabled;
    }

    public Boolean getSecurityInternetPurchaseEnabled() {
        return securityInternetPurchaseEnabled;
    }

    public void setSecurityInternetPurchaseEnabled(Boolean securityInternetPurchaseEnabled) {
        this.securityInternetPurchaseEnabled = securityInternetPurchaseEnabled;
    }

    public Boolean getSecurityOverallLimitsEnabled() {
        return securityOverallLimitsEnabled;
    }

    public void setSecurityOverallLimitsEnabled(Boolean securityOverallLimitsEnabled) {
        this.securityOverallLimitsEnabled = securityOverallLimitsEnabled;
    }

    public Boolean getSecurityAllTimeLimitsEnabled() {
        return securityAllTimeLimitsEnabled;
    }

    public void setSecurityAllTimeLimitsEnabled(Boolean securityAllTimeLimitsEnabled) {
        this.securityAllTimeLimitsEnabled = securityAllTimeLimitsEnabled;
    }

    public String getPersonalizationProductCode() {
        return personalizationProductCode;
    }

    public void setPersonalizationProductCode(String personalizationProductCode) {
        this.personalizationProductCode = personalizationProductCode;
    }

    public CarrierType getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(CarrierType carrierType) {
        this.carrierType = carrierType;
    }

    public String getCardMetadataProfileId() {
        return cardMetadataProfileId;
    }

    public void setCardMetadataProfileId(String cardMetadataProfileId) {
        this.cardMetadataProfileId = cardMetadataProfileId;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public CardCloseReason getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(CardCloseReason closeReason) {
        this.closeReason = closeReason;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public Instant getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Instant dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Boolean getNotificationReceiptsReminderEnabled() {
        return notificationReceiptsReminderEnabled;
    }

    public void setNotificationReceiptsReminderEnabled(Boolean notificationReceiptsReminderEnabled) {
        this.notificationReceiptsReminderEnabled = notificationReceiptsReminderEnabled;
    }

    public Boolean getNotificationInstantSpendUpdateEnabled() {
        return notificationInstantSpendUpdateEnabled;
    }

    public void setNotificationInstantSpendUpdateEnabled(Boolean notificationInstantSpendUpdateEnabled) {
        this.notificationInstantSpendUpdateEnabled = notificationInstantSpendUpdateEnabled;
    }

    public DisposableType getDisposableType() {
        return disposableType;
    }

    public void setDisposableType(DisposableType disposableType) {
        this.disposableType = disposableType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardDTO)) {
            return false;
        }

        CardDTO cardDTO = (CardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardDTO{" +
            "id='" + getId() + "'" +
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
