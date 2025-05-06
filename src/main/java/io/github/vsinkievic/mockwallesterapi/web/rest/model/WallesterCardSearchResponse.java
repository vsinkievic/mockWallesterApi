package io.github.vsinkievic.mockwallesterapi.web.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.wallestermodel.WallesterCard;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardSearchResponse {

    private List<WallesterCard> cards;

    @JsonProperty("total_records_number")
    private int totalRecordsNumber;
}
