package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallesterAccountSearchResponse {

    private List<WallesterAccount> accounts;

    @JsonProperty("total_records_number")
    private Integer totalRecordsNumber;
}
