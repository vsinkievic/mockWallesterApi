package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterAccountTopupDetails {

    @JsonProperty("bank_address")
    private String bankAddress;

    @JsonProperty("bank_name")
    private String bankName;

    private String iban;

    @JsonProperty("payment_details")
    private String paymentDetails;

    @JsonProperty("receiver_name")
    private String receiverName;

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("swift_code")
    private String swiftCode;
}
