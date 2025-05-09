package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCompanyResponse {

    private WallesterCompany company;

    public WallesterCompanyResponse(WallesterCompany company) {
        this.company = company;
    }
}
