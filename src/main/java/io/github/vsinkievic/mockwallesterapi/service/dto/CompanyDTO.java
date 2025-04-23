package io.github.vsinkievic.mockwallesterapi.service.dto;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.KybStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.LanguageCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.RiskProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.Company} entity.
 */
@Schema(description = "Represents a Company.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyDTO implements Serializable {

    private UUID id;

    private String name;

    private String registrationNumber;

    private CountryCode regAddressCountryCode;

    private String regAddress1;

    private String regAddress2;

    private String regAddressCity;

    private String regAddressPostalCode;

    private CountryCode hqAddressCountryCode;

    private String hqAddress1;

    private String hqAddress2;

    private String hqAddressCity;

    private String hqAddressPostalCode;

    private RiskProfile riskProfile;

    private String mobile;

    private String email;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

    private Instant deletedAt;

    private String deletedBy;

    private String industryType;

    private Instant dateOfIncorporation;

    private String businessRelationshipPurpose;

    private Boolean isSanctionsRelated;

    private Boolean isAdverseMediaInvolved;

    private String employeesQuantity;

    private String cardSpendingAmount;

    private BigDecimal limitDailyPurchase;

    private BigDecimal limitDailyWithdrawal;

    private BigDecimal limitMonthlyPurchase;

    private BigDecimal limitMonthlyWithdrawal;

    private KybStatus kybStatus;

    private AccountStatus status;

    private Boolean pushNotificationsEnabled;

    private LanguageCode preferredLanguageCode;

    private String vatNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public CountryCode getRegAddressCountryCode() {
        return regAddressCountryCode;
    }

    public void setRegAddressCountryCode(CountryCode regAddressCountryCode) {
        this.regAddressCountryCode = regAddressCountryCode;
    }

    public String getRegAddress1() {
        return regAddress1;
    }

    public void setRegAddress1(String regAddress1) {
        this.regAddress1 = regAddress1;
    }

    public String getRegAddress2() {
        return regAddress2;
    }

    public void setRegAddress2(String regAddress2) {
        this.regAddress2 = regAddress2;
    }

    public String getRegAddressCity() {
        return regAddressCity;
    }

    public void setRegAddressCity(String regAddressCity) {
        this.regAddressCity = regAddressCity;
    }

    public String getRegAddressPostalCode() {
        return regAddressPostalCode;
    }

    public void setRegAddressPostalCode(String regAddressPostalCode) {
        this.regAddressPostalCode = regAddressPostalCode;
    }

    public CountryCode getHqAddressCountryCode() {
        return hqAddressCountryCode;
    }

    public void setHqAddressCountryCode(CountryCode hqAddressCountryCode) {
        this.hqAddressCountryCode = hqAddressCountryCode;
    }

    public String getHqAddress1() {
        return hqAddress1;
    }

    public void setHqAddress1(String hqAddress1) {
        this.hqAddress1 = hqAddress1;
    }

    public String getHqAddress2() {
        return hqAddress2;
    }

    public void setHqAddress2(String hqAddress2) {
        this.hqAddress2 = hqAddress2;
    }

    public String getHqAddressCity() {
        return hqAddressCity;
    }

    public void setHqAddressCity(String hqAddressCity) {
        this.hqAddressCity = hqAddressCity;
    }

    public String getHqAddressPostalCode() {
        return hqAddressPostalCode;
    }

    public void setHqAddressPostalCode(String hqAddressPostalCode) {
        this.hqAddressPostalCode = hqAddressPostalCode;
    }

    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public Instant getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(Instant dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getBusinessRelationshipPurpose() {
        return businessRelationshipPurpose;
    }

    public void setBusinessRelationshipPurpose(String businessRelationshipPurpose) {
        this.businessRelationshipPurpose = businessRelationshipPurpose;
    }

    public Boolean getIsSanctionsRelated() {
        return isSanctionsRelated;
    }

    public void setIsSanctionsRelated(Boolean isSanctionsRelated) {
        this.isSanctionsRelated = isSanctionsRelated;
    }

    public Boolean getIsAdverseMediaInvolved() {
        return isAdverseMediaInvolved;
    }

    public void setIsAdverseMediaInvolved(Boolean isAdverseMediaInvolved) {
        this.isAdverseMediaInvolved = isAdverseMediaInvolved;
    }

    public String getEmployeesQuantity() {
        return employeesQuantity;
    }

    public void setEmployeesQuantity(String employeesQuantity) {
        this.employeesQuantity = employeesQuantity;
    }

    public String getCardSpendingAmount() {
        return cardSpendingAmount;
    }

    public void setCardSpendingAmount(String cardSpendingAmount) {
        this.cardSpendingAmount = cardSpendingAmount;
    }

    public BigDecimal getLimitDailyPurchase() {
        return limitDailyPurchase;
    }

    public void setLimitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.limitDailyPurchase = limitDailyPurchase;
    }

    public BigDecimal getLimitDailyWithdrawal() {
        return limitDailyWithdrawal;
    }

    public void setLimitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.limitDailyWithdrawal = limitDailyWithdrawal;
    }

    public BigDecimal getLimitMonthlyPurchase() {
        return limitMonthlyPurchase;
    }

    public void setLimitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.limitMonthlyPurchase = limitMonthlyPurchase;
    }

    public BigDecimal getLimitMonthlyWithdrawal() {
        return limitMonthlyWithdrawal;
    }

    public void setLimitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.limitMonthlyWithdrawal = limitMonthlyWithdrawal;
    }

    public KybStatus getKybStatus() {
        return kybStatus;
    }

    public void setKybStatus(KybStatus kybStatus) {
        this.kybStatus = kybStatus;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Boolean getPushNotificationsEnabled() {
        return pushNotificationsEnabled;
    }

    public void setPushNotificationsEnabled(Boolean pushNotificationsEnabled) {
        this.pushNotificationsEnabled = pushNotificationsEnabled;
    }

    public LanguageCode getPreferredLanguageCode() {
        return preferredLanguageCode;
    }

    public void setPreferredLanguageCode(LanguageCode preferredLanguageCode) {
        this.preferredLanguageCode = preferredLanguageCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", regAddressCountryCode='" + getRegAddressCountryCode() + "'" +
            ", regAddress1='" + getRegAddress1() + "'" +
            ", regAddress2='" + getRegAddress2() + "'" +
            ", regAddressCity='" + getRegAddressCity() + "'" +
            ", regAddressPostalCode='" + getRegAddressPostalCode() + "'" +
            ", hqAddressCountryCode='" + getHqAddressCountryCode() + "'" +
            ", hqAddress1='" + getHqAddress1() + "'" +
            ", hqAddress2='" + getHqAddress2() + "'" +
            ", hqAddressCity='" + getHqAddressCity() + "'" +
            ", hqAddressPostalCode='" + getHqAddressPostalCode() + "'" +
            ", riskProfile='" + getRiskProfile() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", industryType='" + getIndustryType() + "'" +
            ", dateOfIncorporation='" + getDateOfIncorporation() + "'" +
            ", businessRelationshipPurpose='" + getBusinessRelationshipPurpose() + "'" +
            ", isSanctionsRelated='" + getIsSanctionsRelated() + "'" +
            ", isAdverseMediaInvolved='" + getIsAdverseMediaInvolved() + "'" +
            ", employeesQuantity='" + getEmployeesQuantity() + "'" +
            ", cardSpendingAmount='" + getCardSpendingAmount() + "'" +
            ", limitDailyPurchase=" + getLimitDailyPurchase() +
            ", limitDailyWithdrawal=" + getLimitDailyWithdrawal() +
            ", limitMonthlyPurchase=" + getLimitMonthlyPurchase() +
            ", limitMonthlyWithdrawal=" + getLimitMonthlyWithdrawal() +
            ", kybStatus='" + getKybStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", pushNotificationsEnabled='" + getPushNotificationsEnabled() + "'" +
            ", preferredLanguageCode='" + getPreferredLanguageCode() + "'" +
            ", vatNumber='" + getVatNumber() + "'" +
            "}";
    }
}
