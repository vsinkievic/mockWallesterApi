package io.github.vsinkievic.mockwallesterapi.service.dto;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.MerchantCategoryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord} entity.
 */
@Schema(description = "Represents a single record in an Account Statement.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountStatementRecordDTO implements Serializable {

    private UUID id;

    private UUID cardId;

    private AccountStatementRecordType type;

    private AccountStatementRecordGroup group;

    private Instant date;

    private BigDecimal transactionAmount;

    private CurrencyCode transactionCurrencyCode;

    private BigDecimal accountAmount;

    private CurrencyCode accountCurrencyCode;

    private MerchantCategoryCode merchantCategoryCode;

    private String merchantId;

    private String terminalId;

    private String merchantName;

    private String merchantCity;

    private CountryCode merchantCountryCode;

    private String description;

    private String originalAuthorizationId;

    private Boolean isReversal;

    private Boolean isDeclined;

    private Boolean isCleared;

    private Instant markedForDisputeAt;

    private String markedForDisputeBy;

    private AccountStatementRecordStatus status;

    private AccountStatementRecordResponse response;

    private String responseCode;

    private String accountExternalId;

    private String maskedCardNumber;

    private Boolean hasPaymentDocumentFiles;

    private Boolean hasPaymentNotes;

    private String cardName;

    private String embossingName;

    private String embossingFirstName;

    private String embossingLastName;

    private String embossingCompanyName;

    private String subType;

    private Instant purchaseDate;

    private BigDecimal exchangeRate;

    private String enrichedMerchantName;

    private String enrichedMerchantUrl;

    private String enrichedMerchantDomain;

    private String enrichedMerchantTelephone;

    private String enrichedMerchantIconUrl;

    private BigDecimal totalAmount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public AccountStatementRecordType getType() {
        return type;
    }

    public void setType(AccountStatementRecordType type) {
        this.type = type;
    }

    public AccountStatementRecordGroup getGroup() {
        return group;
    }

    public void setGroup(AccountStatementRecordGroup group) {
        this.group = group;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public CurrencyCode getTransactionCurrencyCode() {
        return transactionCurrencyCode;
    }

    public void setTransactionCurrencyCode(CurrencyCode transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public CurrencyCode getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(CurrencyCode accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public MerchantCategoryCode getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(MerchantCategoryCode merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public CountryCode getMerchantCountryCode() {
        return merchantCountryCode;
    }

    public void setMerchantCountryCode(CountryCode merchantCountryCode) {
        this.merchantCountryCode = merchantCountryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalAuthorizationId() {
        return originalAuthorizationId;
    }

    public void setOriginalAuthorizationId(String originalAuthorizationId) {
        this.originalAuthorizationId = originalAuthorizationId;
    }

    public Boolean getIsReversal() {
        return isReversal;
    }

    public void setIsReversal(Boolean isReversal) {
        this.isReversal = isReversal;
    }

    public Boolean getIsDeclined() {
        return isDeclined;
    }

    public void setIsDeclined(Boolean isDeclined) {
        this.isDeclined = isDeclined;
    }

    public Boolean getIsCleared() {
        return isCleared;
    }

    public void setIsCleared(Boolean isCleared) {
        this.isCleared = isCleared;
    }

    public Instant getMarkedForDisputeAt() {
        return markedForDisputeAt;
    }

    public void setMarkedForDisputeAt(Instant markedForDisputeAt) {
        this.markedForDisputeAt = markedForDisputeAt;
    }

    public String getMarkedForDisputeBy() {
        return markedForDisputeBy;
    }

    public void setMarkedForDisputeBy(String markedForDisputeBy) {
        this.markedForDisputeBy = markedForDisputeBy;
    }

    public AccountStatementRecordStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatementRecordStatus status) {
        this.status = status;
    }

    public AccountStatementRecordResponse getResponse() {
        return response;
    }

    public void setResponse(AccountStatementRecordResponse response) {
        this.response = response;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getAccountExternalId() {
        return accountExternalId;
    }

    public void setAccountExternalId(String accountExternalId) {
        this.accountExternalId = accountExternalId;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public Boolean getHasPaymentDocumentFiles() {
        return hasPaymentDocumentFiles;
    }

    public void setHasPaymentDocumentFiles(Boolean hasPaymentDocumentFiles) {
        this.hasPaymentDocumentFiles = hasPaymentDocumentFiles;
    }

    public Boolean getHasPaymentNotes() {
        return hasPaymentNotes;
    }

    public void setHasPaymentNotes(Boolean hasPaymentNotes) {
        this.hasPaymentNotes = hasPaymentNotes;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getEmbossingName() {
        return embossingName;
    }

    public void setEmbossingName(String embossingName) {
        this.embossingName = embossingName;
    }

    public String getEmbossingFirstName() {
        return embossingFirstName;
    }

    public void setEmbossingFirstName(String embossingFirstName) {
        this.embossingFirstName = embossingFirstName;
    }

    public String getEmbossingLastName() {
        return embossingLastName;
    }

    public void setEmbossingLastName(String embossingLastName) {
        this.embossingLastName = embossingLastName;
    }

    public String getEmbossingCompanyName() {
        return embossingCompanyName;
    }

    public void setEmbossingCompanyName(String embossingCompanyName) {
        this.embossingCompanyName = embossingCompanyName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getEnrichedMerchantName() {
        return enrichedMerchantName;
    }

    public void setEnrichedMerchantName(String enrichedMerchantName) {
        this.enrichedMerchantName = enrichedMerchantName;
    }

    public String getEnrichedMerchantUrl() {
        return enrichedMerchantUrl;
    }

    public void setEnrichedMerchantUrl(String enrichedMerchantUrl) {
        this.enrichedMerchantUrl = enrichedMerchantUrl;
    }

    public String getEnrichedMerchantDomain() {
        return enrichedMerchantDomain;
    }

    public void setEnrichedMerchantDomain(String enrichedMerchantDomain) {
        this.enrichedMerchantDomain = enrichedMerchantDomain;
    }

    public String getEnrichedMerchantTelephone() {
        return enrichedMerchantTelephone;
    }

    public void setEnrichedMerchantTelephone(String enrichedMerchantTelephone) {
        this.enrichedMerchantTelephone = enrichedMerchantTelephone;
    }

    public String getEnrichedMerchantIconUrl() {
        return enrichedMerchantIconUrl;
    }

    public void setEnrichedMerchantIconUrl(String enrichedMerchantIconUrl) {
        this.enrichedMerchantIconUrl = enrichedMerchantIconUrl;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountStatementRecordDTO)) {
            return false;
        }

        AccountStatementRecordDTO accountStatementRecordDTO = (AccountStatementRecordDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accountStatementRecordDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountStatementRecordDTO{" +
            "id='" + getId() + "'" +
            ", cardId='" + getCardId() + "'" +
            ", type='" + getType() + "'" +
            ", group='" + getGroup() + "'" +
            ", date='" + getDate() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionCurrencyCode='" + getTransactionCurrencyCode() + "'" +
            ", accountAmount=" + getAccountAmount() +
            ", accountCurrencyCode='" + getAccountCurrencyCode() + "'" +
            ", merchantCategoryCode='" + getMerchantCategoryCode() + "'" +
            ", merchantId='" + getMerchantId() + "'" +
            ", terminalId='" + getTerminalId() + "'" +
            ", merchantName='" + getMerchantName() + "'" +
            ", merchantCity='" + getMerchantCity() + "'" +
            ", merchantCountryCode='" + getMerchantCountryCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", originalAuthorizationId='" + getOriginalAuthorizationId() + "'" +
            ", isReversal='" + getIsReversal() + "'" +
            ", isDeclined='" + getIsDeclined() + "'" +
            ", isCleared='" + getIsCleared() + "'" +
            ", markedForDisputeAt='" + getMarkedForDisputeAt() + "'" +
            ", markedForDisputeBy='" + getMarkedForDisputeBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", response='" + getResponse() + "'" +
            ", responseCode='" + getResponseCode() + "'" +
            ", accountExternalId='" + getAccountExternalId() + "'" +
            ", maskedCardNumber='" + getMaskedCardNumber() + "'" +
            ", hasPaymentDocumentFiles='" + getHasPaymentDocumentFiles() + "'" +
            ", hasPaymentNotes='" + getHasPaymentNotes() + "'" +
            ", cardName='" + getCardName() + "'" +
            ", embossingName='" + getEmbossingName() + "'" +
            ", embossingFirstName='" + getEmbossingFirstName() + "'" +
            ", embossingLastName='" + getEmbossingLastName() + "'" +
            ", embossingCompanyName='" + getEmbossingCompanyName() + "'" +
            ", subType='" + getSubType() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", exchangeRate=" + getExchangeRate() +
            ", enrichedMerchantName='" + getEnrichedMerchantName() + "'" +
            ", enrichedMerchantUrl='" + getEnrichedMerchantUrl() + "'" +
            ", enrichedMerchantDomain='" + getEnrichedMerchantDomain() + "'" +
            ", enrichedMerchantTelephone='" + getEnrichedMerchantTelephone() + "'" +
            ", enrichedMerchantIconUrl='" + getEnrichedMerchantIconUrl() + "'" +
            ", totalAmount=" + getTotalAmount() +
            "}";
    }
}
