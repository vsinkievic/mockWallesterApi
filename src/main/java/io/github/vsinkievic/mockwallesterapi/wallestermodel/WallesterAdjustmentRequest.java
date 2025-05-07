package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import java.math.BigDecimal;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class WallesterAdjustmentRequest {

    @JsonProperty("allow_negative_balance")
    private Boolean allowNegativeBalance;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("sender_name")
    private String senderName;

    public void validate() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new WallesterApiException(400, "Amount is required");
        }
        if (allowNegativeBalance == null) {
            throw new WallesterApiException(400, "Allow negative balance is required");
        }
        if (StringUtils.isBlank(description)) {
            throw new WallesterApiException(400, "Description is required");
        }
    }
}
