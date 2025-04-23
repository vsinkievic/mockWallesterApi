package io.github.vsinkievic.mockwallesterapi.domain;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.KybStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.LanguageCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.RiskProfile;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a Company.
 */
@Entity
@Table(name = "company")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "reg_address_country_code")
    private CountryCode regAddressCountryCode;

    @Column(name = "reg_address_1")
    private String regAddress1;

    @Column(name = "reg_address_2")
    private String regAddress2;

    @Column(name = "reg_address_city")
    private String regAddressCity;

    @Column(name = "reg_address_postal_code")
    private String regAddressPostalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "hq_address_country_code")
    private CountryCode hqAddressCountryCode;

    @Column(name = "hq_address_1")
    private String hqAddress1;

    @Column(name = "hq_address_2")
    private String hqAddress2;

    @Column(name = "hq_address_city")
    private String hqAddressCity;

    @Column(name = "hq_address_postal_code")
    private String hqAddressPostalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_profile")
    private RiskProfile riskProfile;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "date_of_incorporation")
    private Instant dateOfIncorporation;

    @Column(name = "business_relationship_purpose")
    private String businessRelationshipPurpose;

    @Column(name = "is_sanctions_related")
    private Boolean isSanctionsRelated;

    @Column(name = "is_adverse_media_involved")
    private Boolean isAdverseMediaInvolved;

    @Column(name = "employees_quantity")
    private String employeesQuantity;

    @Column(name = "card_spending_amount")
    private String cardSpendingAmount;

    @Column(name = "limit_daily_purchase", precision = 21, scale = 2)
    private BigDecimal limitDailyPurchase;

    @Column(name = "limit_daily_withdrawal", precision = 21, scale = 2)
    private BigDecimal limitDailyWithdrawal;

    @Column(name = "limit_monthly_purchase", precision = 21, scale = 2)
    private BigDecimal limitMonthlyPurchase;

    @Column(name = "limit_monthly_withdrawal", precision = 21, scale = 2)
    private BigDecimal limitMonthlyWithdrawal;

    @Enumerated(EnumType.STRING)
    @Column(name = "kyb_status")
    private KybStatus kybStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Column(name = "push_notifications_enabled")
    private Boolean pushNotificationsEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_language_code")
    private LanguageCode preferredLanguageCode;

    @Column(name = "vat_number")
    private String vatNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Company id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Company registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public CountryCode getRegAddressCountryCode() {
        return this.regAddressCountryCode;
    }

    public Company regAddressCountryCode(CountryCode regAddressCountryCode) {
        this.setRegAddressCountryCode(regAddressCountryCode);
        return this;
    }

    public void setRegAddressCountryCode(CountryCode regAddressCountryCode) {
        this.regAddressCountryCode = regAddressCountryCode;
    }

    public String getRegAddress1() {
        return this.regAddress1;
    }

    public Company regAddress1(String regAddress1) {
        this.setRegAddress1(regAddress1);
        return this;
    }

    public void setRegAddress1(String regAddress1) {
        this.regAddress1 = regAddress1;
    }

    public String getRegAddress2() {
        return this.regAddress2;
    }

    public Company regAddress2(String regAddress2) {
        this.setRegAddress2(regAddress2);
        return this;
    }

    public void setRegAddress2(String regAddress2) {
        this.regAddress2 = regAddress2;
    }

    public String getRegAddressCity() {
        return this.regAddressCity;
    }

    public Company regAddressCity(String regAddressCity) {
        this.setRegAddressCity(regAddressCity);
        return this;
    }

    public void setRegAddressCity(String regAddressCity) {
        this.regAddressCity = regAddressCity;
    }

    public String getRegAddressPostalCode() {
        return this.regAddressPostalCode;
    }

    public Company regAddressPostalCode(String regAddressPostalCode) {
        this.setRegAddressPostalCode(regAddressPostalCode);
        return this;
    }

    public void setRegAddressPostalCode(String regAddressPostalCode) {
        this.regAddressPostalCode = regAddressPostalCode;
    }

    public CountryCode getHqAddressCountryCode() {
        return this.hqAddressCountryCode;
    }

    public Company hqAddressCountryCode(CountryCode hqAddressCountryCode) {
        this.setHqAddressCountryCode(hqAddressCountryCode);
        return this;
    }

    public void setHqAddressCountryCode(CountryCode hqAddressCountryCode) {
        this.hqAddressCountryCode = hqAddressCountryCode;
    }

    public String getHqAddress1() {
        return this.hqAddress1;
    }

    public Company hqAddress1(String hqAddress1) {
        this.setHqAddress1(hqAddress1);
        return this;
    }

    public void setHqAddress1(String hqAddress1) {
        this.hqAddress1 = hqAddress1;
    }

    public String getHqAddress2() {
        return this.hqAddress2;
    }

    public Company hqAddress2(String hqAddress2) {
        this.setHqAddress2(hqAddress2);
        return this;
    }

    public void setHqAddress2(String hqAddress2) {
        this.hqAddress2 = hqAddress2;
    }

    public String getHqAddressCity() {
        return this.hqAddressCity;
    }

    public Company hqAddressCity(String hqAddressCity) {
        this.setHqAddressCity(hqAddressCity);
        return this;
    }

    public void setHqAddressCity(String hqAddressCity) {
        this.hqAddressCity = hqAddressCity;
    }

    public String getHqAddressPostalCode() {
        return this.hqAddressPostalCode;
    }

    public Company hqAddressPostalCode(String hqAddressPostalCode) {
        this.setHqAddressPostalCode(hqAddressPostalCode);
        return this;
    }

    public void setHqAddressPostalCode(String hqAddressPostalCode) {
        this.hqAddressPostalCode = hqAddressPostalCode;
    }

    public RiskProfile getRiskProfile() {
        return this.riskProfile;
    }

    public Company riskProfile(RiskProfile riskProfile) {
        this.setRiskProfile(riskProfile);
        return this;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Company mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public Company email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Company createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Company createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Company updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Company updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Company deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public Company deletedBy(String deletedBy) {
        this.setDeletedBy(deletedBy);
        return this;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getIndustryType() {
        return this.industryType;
    }

    public Company industryType(String industryType) {
        this.setIndustryType(industryType);
        return this;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public Instant getDateOfIncorporation() {
        return this.dateOfIncorporation;
    }

    public Company dateOfIncorporation(Instant dateOfIncorporation) {
        this.setDateOfIncorporation(dateOfIncorporation);
        return this;
    }

    public void setDateOfIncorporation(Instant dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getBusinessRelationshipPurpose() {
        return this.businessRelationshipPurpose;
    }

    public Company businessRelationshipPurpose(String businessRelationshipPurpose) {
        this.setBusinessRelationshipPurpose(businessRelationshipPurpose);
        return this;
    }

    public void setBusinessRelationshipPurpose(String businessRelationshipPurpose) {
        this.businessRelationshipPurpose = businessRelationshipPurpose;
    }

    public Boolean getIsSanctionsRelated() {
        return this.isSanctionsRelated;
    }

    public Company isSanctionsRelated(Boolean isSanctionsRelated) {
        this.setIsSanctionsRelated(isSanctionsRelated);
        return this;
    }

    public void setIsSanctionsRelated(Boolean isSanctionsRelated) {
        this.isSanctionsRelated = isSanctionsRelated;
    }

    public Boolean getIsAdverseMediaInvolved() {
        return this.isAdverseMediaInvolved;
    }

    public Company isAdverseMediaInvolved(Boolean isAdverseMediaInvolved) {
        this.setIsAdverseMediaInvolved(isAdverseMediaInvolved);
        return this;
    }

    public void setIsAdverseMediaInvolved(Boolean isAdverseMediaInvolved) {
        this.isAdverseMediaInvolved = isAdverseMediaInvolved;
    }

    public String getEmployeesQuantity() {
        return this.employeesQuantity;
    }

    public Company employeesQuantity(String employeesQuantity) {
        this.setEmployeesQuantity(employeesQuantity);
        return this;
    }

    public void setEmployeesQuantity(String employeesQuantity) {
        this.employeesQuantity = employeesQuantity;
    }

    public String getCardSpendingAmount() {
        return this.cardSpendingAmount;
    }

    public Company cardSpendingAmount(String cardSpendingAmount) {
        this.setCardSpendingAmount(cardSpendingAmount);
        return this;
    }

    public void setCardSpendingAmount(String cardSpendingAmount) {
        this.cardSpendingAmount = cardSpendingAmount;
    }

    public BigDecimal getLimitDailyPurchase() {
        return this.limitDailyPurchase;
    }

    public Company limitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.setLimitDailyPurchase(limitDailyPurchase);
        return this;
    }

    public void setLimitDailyPurchase(BigDecimal limitDailyPurchase) {
        this.limitDailyPurchase = limitDailyPurchase;
    }

    public BigDecimal getLimitDailyWithdrawal() {
        return this.limitDailyWithdrawal;
    }

    public Company limitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.setLimitDailyWithdrawal(limitDailyWithdrawal);
        return this;
    }

    public void setLimitDailyWithdrawal(BigDecimal limitDailyWithdrawal) {
        this.limitDailyWithdrawal = limitDailyWithdrawal;
    }

    public BigDecimal getLimitMonthlyPurchase() {
        return this.limitMonthlyPurchase;
    }

    public Company limitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.setLimitMonthlyPurchase(limitMonthlyPurchase);
        return this;
    }

    public void setLimitMonthlyPurchase(BigDecimal limitMonthlyPurchase) {
        this.limitMonthlyPurchase = limitMonthlyPurchase;
    }

    public BigDecimal getLimitMonthlyWithdrawal() {
        return this.limitMonthlyWithdrawal;
    }

    public Company limitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.setLimitMonthlyWithdrawal(limitMonthlyWithdrawal);
        return this;
    }

    public void setLimitMonthlyWithdrawal(BigDecimal limitMonthlyWithdrawal) {
        this.limitMonthlyWithdrawal = limitMonthlyWithdrawal;
    }

    public KybStatus getKybStatus() {
        return this.kybStatus;
    }

    public Company kybStatus(KybStatus kybStatus) {
        this.setKybStatus(kybStatus);
        return this;
    }

    public void setKybStatus(KybStatus kybStatus) {
        this.kybStatus = kybStatus;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public Company status(AccountStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Boolean getPushNotificationsEnabled() {
        return this.pushNotificationsEnabled;
    }

    public Company pushNotificationsEnabled(Boolean pushNotificationsEnabled) {
        this.setPushNotificationsEnabled(pushNotificationsEnabled);
        return this;
    }

    public void setPushNotificationsEnabled(Boolean pushNotificationsEnabled) {
        this.pushNotificationsEnabled = pushNotificationsEnabled;
    }

    public LanguageCode getPreferredLanguageCode() {
        return this.preferredLanguageCode;
    }

    public Company preferredLanguageCode(LanguageCode preferredLanguageCode) {
        this.setPreferredLanguageCode(preferredLanguageCode);
        return this;
    }

    public void setPreferredLanguageCode(LanguageCode preferredLanguageCode) {
        this.preferredLanguageCode = preferredLanguageCode;
    }

    public String getVatNumber() {
        return this.vatNumber;
    }

    public Company vatNumber(String vatNumber) {
        this.setVatNumber(vatNumber);
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return getId() != null && getId().equals(((Company) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
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
