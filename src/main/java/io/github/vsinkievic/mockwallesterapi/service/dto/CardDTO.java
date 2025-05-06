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
import lombok.Data;

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.Card} entity.
 */
@Data
@Schema(description = "Represents a Card.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CardDTO implements Serializable {

    private UUID id;

    private UUID predecessorCardId;

    private UUID accountId;

    private UUID personId;

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
