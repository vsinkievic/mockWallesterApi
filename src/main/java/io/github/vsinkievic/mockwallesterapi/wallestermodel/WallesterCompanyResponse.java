package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.service.dto.CompanyDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WallesterCompanyResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("registration_address")
    private WallesterAddress registrationAddress;

    @JsonProperty("headquarter_address")
    private WallesterAddress headquarterAddress;

    @JsonProperty("risk_profile")
    private String riskProfile;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("deleted_by")
    private String deletedBy;

    @JsonProperty("industry_type")
    private String industryType;

    @JsonProperty("date_of_incorporation")
    private String dateOfIncorporation;

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
    private String kybStatus;

    @JsonProperty("status")
    private String status;

    @JsonProperty("push_notifications_enabled")
    private Boolean pushNotificationsEnabled;

    @JsonProperty("preferred_language_code")
    private String preferredLanguageCode;

    @JsonProperty("vat_number")
    private String vatNumber;

    public WallesterCompanyResponse(CompanyDTO companyDTO) {
        this.id = companyDTO.getId().toString();
        this.name = companyDTO.getName();
        this.registrationNumber = companyDTO.getRegistrationNumber();
        this.registrationAddress = WallesterAddress.builder()
            .countryCode(companyDTO.getRegAddressCountryCode().name())
            .address1(companyDTO.getRegAddress1())
            .address2(companyDTO.getRegAddress2())
            .city(companyDTO.getRegAddressCity())
            .postalCode(companyDTO.getRegAddressPostalCode())
            .build();
        this.headquarterAddress = WallesterAddress.builder()
            .countryCode(companyDTO.getHqAddressCountryCode().name())
            .address1(companyDTO.getHqAddress1())
            .address2(companyDTO.getHqAddress2())
            .city(companyDTO.getHqAddressCity())
            .postalCode(companyDTO.getHqAddressPostalCode())
            .build();
        this.riskProfile = companyDTO.getRiskProfile().name();
        this.mobile = companyDTO.getMobile();
        this.email = companyDTO.getEmail();
        this.createdAt = companyDTO.getCreatedAt().toString();
        this.createdBy = companyDTO.getCreatedBy();
        this.updatedAt = companyDTO.getUpdatedAt().toString();
        this.updatedBy = companyDTO.getUpdatedBy();
        this.deletedAt = companyDTO.getDeletedAt().toString();
        this.deletedBy = companyDTO.getDeletedBy();
        this.industryType = companyDTO.getIndustryType();
        this.dateOfIncorporation = companyDTO.getDateOfIncorporation().toString();
        this.businessRelationshipPurpose = companyDTO.getBusinessRelationshipPurpose();
        this.isSanctionsRelated = companyDTO.getIsSanctionsRelated();
        this.isAdverseMediaInvolved = companyDTO.getIsAdverseMediaInvolved();
        this.employeesQuantity = companyDTO.getEmployeesQuantity();
        this.cardSpendingAmount = companyDTO.getCardSpendingAmount();
        this.limits = WallesterCompanyLimits.builder()
            .dailyPurchase(companyDTO.getLimitDailyPurchase())
            .dailyWithdrawal(companyDTO.getLimitDailyWithdrawal())
            //            .dailyInternetPurchase(companyDTO.getLimitDailyInternetPurchase())
            //            .dailyContactlessPurchase(companyDTO.getLimitDailyContactlessPurchase())
            //            .weeklyPurchase(companyDTO.getLimitWeeklyPurchase())
            //            .weeklyWithdrawal(companyDTO.getLimitWeeklyWithdrawal())
            //            .weeklyInternetPurchase(companyDTO.getLimitWeeklyInternetPurchase())
            //            .weeklyContactlessPurchase(companyDTO.getLimitWeeklyContactlessPurchase())
            .monthlyPurchase(companyDTO.getLimitMonthlyPurchase())
            .monthlyWithdrawal(companyDTO.getLimitMonthlyWithdrawal())
            //            .monthlyInternetPurchase(companyDTO.getLimitMonthlyInternetPurchase())
            //            .monthlyContactlessPurchase(companyDTO.getLimitMonthlyContactlessPurchase())
            .build();
        this.kybStatus = companyDTO.getKybStatus().name();
        this.status = companyDTO.getStatus().name();
        this.pushNotificationsEnabled = companyDTO.getPushNotificationsEnabled();
        this.preferredLanguageCode = companyDTO.getPreferredLanguageCode().name();
        this.vatNumber = companyDTO.getVatNumber();
    }
}
