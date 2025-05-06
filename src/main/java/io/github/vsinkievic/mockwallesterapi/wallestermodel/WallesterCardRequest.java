package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CardType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CarrierType;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardRequest {

    @JsonProperty("3d_secure_settings")
    private WallesterCard3dSecureSettings secure3DSettings;

    @JsonProperty("account_id")
    private UUID accountId;

    @JsonProperty("card_fees")
    private List<WallesterCardFee> cardFees;

    @JsonProperty("card_notification_settings")
    private WallesterCardNotificationSettings cardNotificationSettings;

    @JsonProperty("carrier_type")
    private CarrierType carrierType;

    @JsonProperty("delivery_address")
    private WallesterCardDeliveryAddress deliveryAddress;

    @JsonProperty("disable_automatic_renewal")
    private Boolean disableAutomaticRenewal;

    @JsonProperty("embossing_company_name")
    private String embossingCompanyName;

    @JsonProperty("embossing_name")
    private String embossingName;

    @JsonProperty("encrypted_pin")
    private String encryptedPin;

    @JsonProperty("expiry_days")
    private Integer expiryDays;

    @JsonProperty("expiry_days_round")
    private Boolean expiryDaysRound;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("is_disposable")
    private Boolean isDisposable;

    private WallesterCardLimits limits;

    private String name;

    @JsonProperty("personalization_product_code")
    private String personalizationProductCode;

    @JsonProperty("predecessor_card_id")
    private UUID predecessorCardId;

    private WallesterCardSecurity security;

    @JsonProperty("separated_embossing_name")
    private WallesterCardSeparatedEmbossingName separatedEmbossingName;

    @JsonProperty("type")
    private CardType type;
}
