package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CompanyStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.KybStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.LanguageCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.RiskProfile;
import java.time.LocalDate;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterCompanyRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("registration_address")
    private WallesterAddress registrationAddress;

    @JsonProperty("headquarter_address")
    private WallesterAddress headquarterAddress;

    @JsonProperty("risk_profile")
    private RiskProfile riskProfile;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("industry_type")
    private String industryType;

    @JsonProperty("date_of_incorporation")
    private LocalDate dateOfIncorporation;

    @JsonProperty("business_relationship_purpose")
    private String businessRelationshipPurpose;

    @JsonProperty("is_sanctions_related")
    private Boolean isSanctionsRelated;

    @JsonProperty("is_adverse_media_involved")
    private Boolean isAdverseMediaInvolved;

    @JsonProperty("employees_quantity")
    private String employeesQuantity;

    @JsonProperty("card_spending_amount")
    private String cardSpendingAmount;

    @JsonProperty("limits")
    private WallesterCompanyLimits limits;

    @JsonProperty("kyb_status")
    private KybStatus kybStatus;

    @JsonProperty("status")
    private CompanyStatus status;

    @JsonProperty("push_notifications_enabled")
    private Boolean pushNotificationsEnabled;

    @JsonProperty("preferred_language_code")
    private LanguageCode preferredLanguageCode;

    @JsonProperty("vat_number")
    private String vatNumber;
}
