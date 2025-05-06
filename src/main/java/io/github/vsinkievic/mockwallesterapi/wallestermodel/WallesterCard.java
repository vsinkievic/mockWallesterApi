package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardBlockType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardCloseReason;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CarrierType;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardDTO;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCard {

    private UUID id;

    @JsonProperty("company_id")
    private UUID companyId;

    @JsonProperty("person_id")
    private UUID personId;

    @JsonProperty("account_id")
    private UUID accountId;

    private String name;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("masked_card_number")
    private String maskedCardNumber;

    @JsonProperty("status")
    private CardStatus status;

    @JsonProperty("type")
    private CardType type;

    @JsonProperty("activated_at")
    private Instant activatedAt;

    @JsonProperty("block_type")
    private CardBlockType blockType;

    @JsonProperty("blocked_by")
    private String blockedBy;

    @JsonProperty("blocked_at")
    private Instant blockedAt;

    @JsonProperty("card_notification_settings")
    private WallesterCardNotificationSettings cardNotificationSettings;

    @JsonProperty("carrier_type")
    private CarrierType carrierType;

    @JsonProperty("close_reason")
    private CardCloseReason closeReason;

    @JsonProperty("closed_at")
    private Instant closedAt;

    @JsonProperty("closed_by")
    private String closedBy;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("dispatched_at")
    private Instant dispatchedAt;

    @JsonProperty("embossing_name")
    private String embossingName;

    @JsonProperty("expiry_date")
    private Instant expiryDate;

    private WallesterCardLimits limits;

    @JsonProperty("delivery_address")
    private WallesterCardDeliveryAddress deliveryAddress;

    @JsonProperty("secure_3d_settings")
    private WallesterCard3dSecureSettings secure3DSettings;

    @JsonProperty("predecessor_card_id")
    private UUID predecessorCardId;

    @JsonProperty("is_disposable")
    private Boolean isDisposable;

    @JsonProperty("is_enrolled_for_3d_secure")
    private Boolean isEnrolledFor3dSecure;

    @JsonProperty("is_card_3d_secure_activated")
    private Boolean isCard3dSecureActivated;

    @JsonProperty("reference_number")
    private String referenceNumber;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    @JsonProperty("disable_automatic_renewal")
    private Boolean disableAutomaticRenewal;

    @JsonProperty("embossing_company_name")
    private String embossingCompanyName;

    @JsonProperty("encrypted_pin")
    private String encryptedPin;

    @JsonProperty("expiry_days")
    private Integer expiryDays;

    @JsonProperty("expiry_days_round")
    private Boolean expiryDaysRound;

    @JsonProperty("personalization_product_code")
    private String personalizationProductCode;

    private WallesterCardSecurity security;

    @JsonProperty("separated_embossing_name")
    private WallesterCardSeparatedEmbossingName separatedEmbossingName;

    public WallesterCard(CardDTO cardDTO) {
        // Direct mappings
        this.id = cardDTO.getId();
        this.accountId = cardDTO.getAccountId();
        this.personId = cardDTO.getPersonId();
        this.companyId = cardDTO.getCompanyId();
        this.name = cardDTO.getName();
        this.externalId = cardDTO.getExternalId();
        this.maskedCardNumber = cardDTO.getMaskedCardNumber();
        this.status = cardDTO.getStatus();
        this.type = cardDTO.getType();
        this.activatedAt = cardDTO.getActivatedAt();
        this.blockType = cardDTO.getBlockType();
        this.blockedBy = cardDTO.getBlockedBy();
        this.blockedAt = cardDTO.getBlockedAt();
        this.carrierType = cardDTO.getCarrierType();
        this.closeReason = cardDTO.getCloseReason();
        this.closedAt = cardDTO.getClosedAt();
        this.closedBy = cardDTO.getClosedBy();
        this.createdAt = cardDTO.getCreatedAt();
        this.dispatchedAt = cardDTO.getDispatchedAt();
        this.embossingName = cardDTO.getEmbossingName();
        this.expiryDate = cardDTO.getExpiryDate();
        this.predecessorCardId = cardDTO.getPredecessorCardId();
        this.referenceNumber = cardDTO.getReferenceNumber();
        this.updatedAt = cardDTO.getUpdatedAt();
        this.isDisposable = cardDTO.getIsDisposable();
        this.isEnrolledFor3dSecure = cardDTO.getIsEnrolledFor3DSecure();
        this.isCard3dSecureActivated = cardDTO.getIsCard3DSecureActivated();
        this.embossingCompanyName = cardDTO.getEmbossingCompanyName();
        this.personalizationProductCode = cardDTO.getPersonalizationProductCode();
        if (cardDTO.getRenewAutomatically() != null) this.disableAutomaticRenewal = !cardDTO.getRenewAutomatically();

        // Complex object mappings
        if (
            cardDTO.getLimitDailyPurchase() != null ||
            cardDTO.getLimitDailyWithdrawal() != null ||
            cardDTO.getLimitMonthlyPurchase() != null ||
            cardDTO.getLimitMonthlyWithdrawal() != null ||
            cardDTO.getLimitTransactionPurchase() != null
        ) {
            this.limits = WallesterCardLimits.builder()
                .dailyPurchase(cardDTO.getLimitDailyPurchase())
                .dailyWithdrawal(cardDTO.getLimitDailyWithdrawal())
                .monthlyPurchase(cardDTO.getLimitMonthlyPurchase())
                .monthlyWithdrawal(cardDTO.getLimitMonthlyWithdrawal())
                .transactionPurchase(cardDTO.getLimitTransactionPurchase())
                .build();
        }

        if (
            cardDTO.getDeliveryFirstName() != null ||
            cardDTO.getDeliveryLastName() != null ||
            cardDTO.getDeliveryCompanyName() != null ||
            cardDTO.getDeliveryAddress1() != null ||
            cardDTO.getDeliveryAddress2() != null ||
            cardDTO.getDeliveryPostalCode() != null ||
            cardDTO.getDeliveryCity() != null ||
            cardDTO.getDeliveryCountryCode() != null ||
            cardDTO.getDeliveryDispatchMethod() != null ||
            cardDTO.getDeliveryPhone() != null ||
            cardDTO.getDeliveryTrackingNumber() != null
        ) {
            this.deliveryAddress = new WallesterCardDeliveryAddress();
            this.deliveryAddress.firstName(cardDTO.getDeliveryFirstName())
                .lastName(cardDTO.getDeliveryLastName())
                .companyName(cardDTO.getDeliveryCompanyName())
                .address1(cardDTO.getDeliveryAddress1())
                .address2(cardDTO.getDeliveryAddress2())
                .postalCode(cardDTO.getDeliveryPostalCode())
                .city(cardDTO.getDeliveryCity())
                .countryCode(cardDTO.getDeliveryCountryCode())
                .dispatchMethod(cardDTO.getDeliveryDispatchMethod())
                .phone(cardDTO.getDeliveryPhone())
                .trackingNumber(cardDTO.getDeliveryTrackingNumber());
        }

        if (
            cardDTO.getSecure3DMobile() != null ||
            cardDTO.getSecure3DEmail() != null ||
            cardDTO.getSecure3DOutOfBandEnabled() != null ||
            cardDTO.getSecure3DOutOfBandId() != null
        ) {
            this.secure3DSettings = new WallesterCard3dSecureSettings();
            this.secure3DSettings.mobile(cardDTO.getSecure3DMobile())
                .email(cardDTO.getSecure3DEmail())
                .outOfBandEnabled(cardDTO.getSecure3DOutOfBandEnabled())
                .outOfBandId(cardDTO.getSecure3DOutOfBandId());
        }

        // Map notification settings
        if (cardDTO.getNotificationReceiptsReminderEnabled() != null || cardDTO.getNotificationInstantSpendUpdateEnabled() != null) {
            this.cardNotificationSettings = new WallesterCardNotificationSettings();
            this.cardNotificationSettings.setReceiptsReminderEnabled(cardDTO.getNotificationReceiptsReminderEnabled());
            this.cardNotificationSettings.setInstantSpendUpdateEnabled(cardDTO.getNotificationInstantSpendUpdateEnabled());
        }

        // Map security settings
        if (
            cardDTO.getSecurityContactlessEnabled() != null ||
            cardDTO.getSecurityWithdrawalEnabled() != null ||
            cardDTO.getSecurityInternetPurchaseEnabled() != null ||
            cardDTO.getSecurityOverallLimitsEnabled() != null ||
            cardDTO.getSecurityAllTimeLimitsEnabled() != null
        ) {
            this.security = new WallesterCardSecurity();
            this.security.setContactlessEnabled(cardDTO.getSecurityContactlessEnabled());
            this.security.setWithdrawalEnabled(cardDTO.getSecurityWithdrawalEnabled());
            this.security.setInternetPurchaseEnabled(cardDTO.getSecurityInternetPurchaseEnabled());
            this.security.setOverallLimitsEnabled(cardDTO.getSecurityOverallLimitsEnabled());
            this.security.setAllTimeLimitsEnabled(cardDTO.getSecurityAllTimeLimitsEnabled());
        }

        // Map separated embossing name
        if (cardDTO.getEmbossingFirstName() != null || cardDTO.getEmbossingLastName() != null) {
            this.separatedEmbossingName = new WallesterCardSeparatedEmbossingName();
            this.separatedEmbossingName.setFirstName(cardDTO.getEmbossingFirstName());
            this.separatedEmbossingName.setLastName(cardDTO.getEmbossingLastName());
        }
    }
}
