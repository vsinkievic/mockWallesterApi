package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCompanyLimits {

    @JsonProperty("daily_purchase")
    private BigDecimal dailyPurchase;

    @JsonProperty("daily_withdrawal")
    private BigDecimal dailyWithdrawal;

    @JsonProperty("daily_internet_purchase")
    private BigDecimal dailyInternetPurchase;

    @JsonProperty("daily_contactless_purchase")
    private BigDecimal dailyContactlessPurchase;

    @JsonProperty("weekly_purchase")
    private BigDecimal weeklyPurchase;

    @JsonProperty("weekly_withdrawal")
    private BigDecimal weeklyWithdrawal;

    @JsonProperty("weekly_internet_purchase")
    private BigDecimal weeklyInternetPurchase;

    @JsonProperty("weekly_contactless_purchase")
    private BigDecimal weeklyContactlessPurchase;

    @JsonProperty("monthly_purchase")
    private BigDecimal monthlyPurchase;

    @JsonProperty("monthly_withdrawal")
    private BigDecimal monthlyWithdrawal;

    @JsonProperty("monthly_internet_purchase")
    private BigDecimal monthlyInternetPurchase;

    @JsonProperty("monthly_contactless_purchase")
    private BigDecimal monthlyContactlessPurchase;
}
