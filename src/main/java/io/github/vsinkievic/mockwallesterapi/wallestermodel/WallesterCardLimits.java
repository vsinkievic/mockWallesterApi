package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardLimits {

    @JsonProperty("all_time_contactless_purchase")
    private BigDecimal allTimeContactlessPurchase;

    @JsonProperty("all_time_internet_purchase")
    private BigDecimal allTimeInternetPurchase;

    @JsonProperty("all_time_purchase")
    private BigDecimal allTimePurchase;

    @JsonProperty("all_time_withdrawal")
    private BigDecimal allTimeWithdrawal;

    @JsonProperty("daily_contactless_purchase")
    private BigDecimal dailyContactlessPurchase;

    @JsonProperty("daily_internet_purchase")
    private BigDecimal dailyInternetPurchase;

    @JsonProperty("daily_overall_purchase")
    private BigDecimal dailyOverallPurchase;

    @JsonProperty("daily_purchase")
    private BigDecimal dailyPurchase;

    @JsonProperty("daily_withdrawal")
    private BigDecimal dailyWithdrawal;

    @JsonProperty("monthly_contactless_purchase")
    private BigDecimal monthlyContactlessPurchase;

    @JsonProperty("monthly_internet_purchase")
    private BigDecimal monthlyInternetPurchase;

    @JsonProperty("monthly_overall_purchase")
    private BigDecimal monthlyOverallPurchase;

    @JsonProperty("monthly_purchase")
    private BigDecimal monthlyPurchase;

    @JsonProperty("monthly_withdrawal")
    private BigDecimal monthlyWithdrawal;

    @JsonProperty("transaction_contactless_purchase")
    private BigDecimal transactionContactlessPurchase;

    @JsonProperty("transaction_internet_purchase")
    private BigDecimal transactionInternetPurchase;

    @JsonProperty("transaction_purchase")
    private BigDecimal transactionPurchase;

    @JsonProperty("transaction_withdrawal")
    private BigDecimal transactionWithdrawal;

    @JsonProperty("weekly_contactless_purchase")
    private BigDecimal weeklyContactlessPurchase;

    @JsonProperty("weekly_internet_purchase")
    private BigDecimal weeklyInternetPurchase;

    @JsonProperty("weekly_overall_purchase")
    private BigDecimal weeklyOverallPurchase;

    @JsonProperty("weekly_purchase")
    private BigDecimal weeklyPurchase;

    @JsonProperty("weekly_withdrawal")
    private BigDecimal weeklyWithdrawal;
}
