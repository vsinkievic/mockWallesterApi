package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardNotificationSettings {

    @JsonProperty("instant_spend_update_enabled")
    private Boolean instantSpendUpdateEnabled;

    @JsonProperty("receipts_reminder_enabled")
    private Boolean receiptsReminderEnabled;
}
