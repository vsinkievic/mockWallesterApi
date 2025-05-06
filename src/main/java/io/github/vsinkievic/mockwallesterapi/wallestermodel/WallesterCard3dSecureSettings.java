package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCard3dSecureSettings {

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("out_of_band_enabled")
    private Boolean outOfBandEnabled;

    @JsonProperty("out_of_band_id")
    private String outOfBandId;

    @JsonProperty("password")
    private String password;

    public WallesterCard3dSecureSettings email(String email) {
        this.email = email;
        return this;
    }

    public WallesterCard3dSecureSettings mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public WallesterCard3dSecureSettings outOfBandEnabled(Boolean outOfBandEnabled) {
        this.outOfBandEnabled = outOfBandEnabled;
        return this;
    }

    public WallesterCard3dSecureSettings outOfBandId(String outOfBandId) {
        this.outOfBandId = outOfBandId;
        return this;
    }

    public WallesterCard3dSecureSettings password(String password) {
        this.password = password;
        return this;
    }
}
