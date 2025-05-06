package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardSecurity {

    @JsonProperty("all_time_limits_enabled")
    private Boolean allTimeLimitsEnabled;

    @JsonProperty("contactless_enabled")
    private Boolean contactlessEnabled;

    @JsonProperty("internet_purchase_enabled")
    private Boolean internetPurchaseEnabled;

    @JsonProperty("overall_limits_enabled")
    private Boolean overallLimitsEnabled;

    @JsonProperty("withdrawal_enabled")
    private Boolean withdrawalEnabled;
}
