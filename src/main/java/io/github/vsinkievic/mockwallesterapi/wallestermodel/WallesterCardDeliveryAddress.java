package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.DispatchMethod;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCardDeliveryAddress {

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("country_code")
    private CountryCode countryCode;

    @JsonProperty("dispatch_method")
    private DispatchMethod dispatchMethod;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    public WallesterCardDeliveryAddress address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public WallesterCardDeliveryAddress address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public WallesterCardDeliveryAddress city(String city) {
        this.city = city;
        return this;
    }

    public WallesterCardDeliveryAddress companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public WallesterCardDeliveryAddress countryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public WallesterCardDeliveryAddress dispatchMethod(DispatchMethod dispatchMethod) {
        this.dispatchMethod = dispatchMethod;
        return this;
    }

    public WallesterCardDeliveryAddress firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public WallesterCardDeliveryAddress lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public WallesterCardDeliveryAddress phone(String phone) {
        this.phone = phone;
        return this;
    }

    public WallesterCardDeliveryAddress postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public WallesterCardDeliveryAddress trackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }
}
