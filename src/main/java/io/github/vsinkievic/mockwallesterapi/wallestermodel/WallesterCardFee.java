package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardFee {

    @JsonProperty("fixed_part")
    private BigDecimal fixedPart;

    @JsonProperty("min_amount")
    private BigDecimal minAmount;

    @JsonProperty("percentage_part")
    private BigDecimal percentagePart;

    private String type;
}
