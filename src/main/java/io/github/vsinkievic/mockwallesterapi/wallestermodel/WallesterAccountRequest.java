package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class WallesterAccountRequest {

    @JsonProperty("company_id")
    private UUID companyId;

    @JsonProperty("person_id")
    private UUID personId;

    private String name;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("currency_code")
    private CurrencyCode currencyCode;

    private WallesterAccountLimits limits;

    @JsonProperty("top_up_details")
    private WallesterAccountTopupDetails topUpDetails;

    @JsonProperty("credit_limit")
    private BigDecimal creditLimit;

    @JsonProperty("used_credit")
    private BigDecimal usedCredit;
}
