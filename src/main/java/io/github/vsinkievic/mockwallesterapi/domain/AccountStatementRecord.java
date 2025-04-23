package io.github.vsinkievic.mockwallesterapi.domain;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.MerchantCategoryCode;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a single record in an Account Statement.
 */
@Entity
@Table(name = "account_statement_record")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountStatementRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "card_id")
    private UUID cardId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccountStatementRecordType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_group")
    private AccountStatementRecordGroup group;

    @Column(name = "date")
    private Instant date;

    @Column(name = "transaction_amount", precision = 21, scale = 2)
    private BigDecimal transactionAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_currency_code")
    private CurrencyCode transactionCurrencyCode;

    @Column(name = "account_amount", precision = 21, scale = 2)
    private BigDecimal accountAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_currency_code")
    private CurrencyCode accountCurrencyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "merchant_category_code")
    private MerchantCategoryCode merchantCategoryCode;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "merchant_city")
    private String merchantCity;

    @Enumerated(EnumType.STRING)
    @Column(name = "merchant_country_code")
    private CountryCode merchantCountryCode;

    @Column(name = "description")
    private String description;

    @Column(name = "original_authorization_id")
    private String originalAuthorizationId;

    @Column(name = "is_reversal")
    private Boolean isReversal;

    @Column(name = "is_declined")
    private Boolean isDeclined;

    @Column(name = "is_cleared")
    private Boolean isCleared;

    @Column(name = "marked_for_dispute_at")
    private Instant markedForDisputeAt;

    @Column(name = "marked_for_dispute_by")
    private String markedForDisputeBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatementRecordStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "response")
    private AccountStatementRecordResponse response;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "account_external_id")
    private String accountExternalId;

    @Column(name = "masked_card_number")
    private String maskedCardNumber;

    @Column(name = "has_payment_document_files")
    private Boolean hasPaymentDocumentFiles;

    @Column(name = "has_payment_notes")
    private Boolean hasPaymentNotes;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "embossing_name")
    private String embossingName;

    @Column(name = "embossing_first_name")
    private String embossingFirstName;

    @Column(name = "embossing_last_name")
    private String embossingLastName;

    @Column(name = "embossing_company_name")
    private String embossingCompanyName;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "purchase_date")
    private Instant purchaseDate;

    @Column(name = "exchange_rate", precision = 21, scale = 2)
    private BigDecimal exchangeRate;

    @Column(name = "enriched_merchant_name")
    private String enrichedMerchantName;

    @Column(name = "enriched_merchant_url")
    private String enrichedMerchantUrl;

    @Column(name = "enriched_merchant_domain")
    private String enrichedMerchantDomain;

    @Column(name = "enriched_merchant_telephone")
    private String enrichedMerchantTelephone;

    @Column(name = "enriched_merchant_icon_url")
    private String enrichedMerchantIconUrl;

    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public AccountStatementRecord id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCardId() {
        return this.cardId;
    }

    public AccountStatementRecord cardId(UUID cardId) {
        this.setCardId(cardId);
        return this;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public AccountStatementRecordType getType() {
        return this.type;
    }

    public AccountStatementRecord type(AccountStatementRecordType type) {
        this.setType(type);
        return this;
    }

    public void setType(AccountStatementRecordType type) {
        this.type = type;
    }

    public AccountStatementRecordGroup getGroup() {
        return this.group;
    }

    public AccountStatementRecord group(AccountStatementRecordGroup group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(AccountStatementRecordGroup group) {
        this.group = group;
    }

    public Instant getDate() {
        return this.date;
    }

    public AccountStatementRecord date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }

    public AccountStatementRecord transactionAmount(BigDecimal transactionAmount) {
        this.setTransactionAmount(transactionAmount);
        return this;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public CurrencyCode getTransactionCurrencyCode() {
        return this.transactionCurrencyCode;
    }

    public AccountStatementRecord transactionCurrencyCode(CurrencyCode transactionCurrencyCode) {
        this.setTransactionCurrencyCode(transactionCurrencyCode);
        return this;
    }

    public void setTransactionCurrencyCode(CurrencyCode transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public BigDecimal getAccountAmount() {
        return this.accountAmount;
    }

    public AccountStatementRecord accountAmount(BigDecimal accountAmount) {
        this.setAccountAmount(accountAmount);
        return this;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public CurrencyCode getAccountCurrencyCode() {
        return this.accountCurrencyCode;
    }

    public AccountStatementRecord accountCurrencyCode(CurrencyCode accountCurrencyCode) {
        this.setAccountCurrencyCode(accountCurrencyCode);
        return this;
    }

    public void setAccountCurrencyCode(CurrencyCode accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public MerchantCategoryCode getMerchantCategoryCode() {
        return this.merchantCategoryCode;
    }

    public AccountStatementRecord merchantCategoryCode(MerchantCategoryCode merchantCategoryCode) {
        this.setMerchantCategoryCode(merchantCategoryCode);
        return this;
    }

    public void setMerchantCategoryCode(MerchantCategoryCode merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public AccountStatementRecord merchantId(String merchantId) {
        this.setMerchantId(merchantId);
        return this;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return this.terminalId;
    }

    public AccountStatementRecord terminalId(String terminalId) {
        this.setTerminalId(terminalId);
        return this;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public AccountStatementRecord merchantName(String merchantName) {
        this.setMerchantName(merchantName);
        return this;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCity() {
        return this.merchantCity;
    }

    public AccountStatementRecord merchantCity(String merchantCity) {
        this.setMerchantCity(merchantCity);
        return this;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public CountryCode getMerchantCountryCode() {
        return this.merchantCountryCode;
    }

    public AccountStatementRecord merchantCountryCode(CountryCode merchantCountryCode) {
        this.setMerchantCountryCode(merchantCountryCode);
        return this;
    }

    public void setMerchantCountryCode(CountryCode merchantCountryCode) {
        this.merchantCountryCode = merchantCountryCode;
    }

    public String getDescription() {
        return this.description;
    }

    public AccountStatementRecord description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalAuthorizationId() {
        return this.originalAuthorizationId;
    }

    public AccountStatementRecord originalAuthorizationId(String originalAuthorizationId) {
        this.setOriginalAuthorizationId(originalAuthorizationId);
        return this;
    }

    public void setOriginalAuthorizationId(String originalAuthorizationId) {
        this.originalAuthorizationId = originalAuthorizationId;
    }

    public Boolean getIsReversal() {
        return this.isReversal;
    }

    public AccountStatementRecord isReversal(Boolean isReversal) {
        this.setIsReversal(isReversal);
        return this;
    }

    public void setIsReversal(Boolean isReversal) {
        this.isReversal = isReversal;
    }

    public Boolean getIsDeclined() {
        return this.isDeclined;
    }

    public AccountStatementRecord isDeclined(Boolean isDeclined) {
        this.setIsDeclined(isDeclined);
        return this;
    }

    public void setIsDeclined(Boolean isDeclined) {
        this.isDeclined = isDeclined;
    }

    public Boolean getIsCleared() {
        return this.isCleared;
    }

    public AccountStatementRecord isCleared(Boolean isCleared) {
        this.setIsCleared(isCleared);
        return this;
    }

    public void setIsCleared(Boolean isCleared) {
        this.isCleared = isCleared;
    }

    public Instant getMarkedForDisputeAt() {
        return this.markedForDisputeAt;
    }

    public AccountStatementRecord markedForDisputeAt(Instant markedForDisputeAt) {
        this.setMarkedForDisputeAt(markedForDisputeAt);
        return this;
    }

    public void setMarkedForDisputeAt(Instant markedForDisputeAt) {
        this.markedForDisputeAt = markedForDisputeAt;
    }

    public String getMarkedForDisputeBy() {
        return this.markedForDisputeBy;
    }

    public AccountStatementRecord markedForDisputeBy(String markedForDisputeBy) {
        this.setMarkedForDisputeBy(markedForDisputeBy);
        return this;
    }

    public void setMarkedForDisputeBy(String markedForDisputeBy) {
        this.markedForDisputeBy = markedForDisputeBy;
    }

    public AccountStatementRecordStatus getStatus() {
        return this.status;
    }

    public AccountStatementRecord status(AccountStatementRecordStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AccountStatementRecordStatus status) {
        this.status = status;
    }

    public AccountStatementRecordResponse getResponse() {
        return this.response;
    }

    public AccountStatementRecord response(AccountStatementRecordResponse response) {
        this.setResponse(response);
        return this;
    }

    public void setResponse(AccountStatementRecordResponse response) {
        this.response = response;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public AccountStatementRecord responseCode(String responseCode) {
        this.setResponseCode(responseCode);
        return this;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getAccountExternalId() {
        return this.accountExternalId;
    }

    public AccountStatementRecord accountExternalId(String accountExternalId) {
        this.setAccountExternalId(accountExternalId);
        return this;
    }

    public void setAccountExternalId(String accountExternalId) {
        this.accountExternalId = accountExternalId;
    }

    public String getMaskedCardNumber() {
        return this.maskedCardNumber;
    }

    public AccountStatementRecord maskedCardNumber(String maskedCardNumber) {
        this.setMaskedCardNumber(maskedCardNumber);
        return this;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public Boolean getHasPaymentDocumentFiles() {
        return this.hasPaymentDocumentFiles;
    }

    public AccountStatementRecord hasPaymentDocumentFiles(Boolean hasPaymentDocumentFiles) {
        this.setHasPaymentDocumentFiles(hasPaymentDocumentFiles);
        return this;
    }

    public void setHasPaymentDocumentFiles(Boolean hasPaymentDocumentFiles) {
        this.hasPaymentDocumentFiles = hasPaymentDocumentFiles;
    }

    public Boolean getHasPaymentNotes() {
        return this.hasPaymentNotes;
    }

    public AccountStatementRecord hasPaymentNotes(Boolean hasPaymentNotes) {
        this.setHasPaymentNotes(hasPaymentNotes);
        return this;
    }

    public void setHasPaymentNotes(Boolean hasPaymentNotes) {
        this.hasPaymentNotes = hasPaymentNotes;
    }

    public String getCardName() {
        return this.cardName;
    }

    public AccountStatementRecord cardName(String cardName) {
        this.setCardName(cardName);
        return this;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getEmbossingName() {
        return this.embossingName;
    }

    public AccountStatementRecord embossingName(String embossingName) {
        this.setEmbossingName(embossingName);
        return this;
    }

    public void setEmbossingName(String embossingName) {
        this.embossingName = embossingName;
    }

    public String getEmbossingFirstName() {
        return this.embossingFirstName;
    }

    public AccountStatementRecord embossingFirstName(String embossingFirstName) {
        this.setEmbossingFirstName(embossingFirstName);
        return this;
    }

    public void setEmbossingFirstName(String embossingFirstName) {
        this.embossingFirstName = embossingFirstName;
    }

    public String getEmbossingLastName() {
        return this.embossingLastName;
    }

    public AccountStatementRecord embossingLastName(String embossingLastName) {
        this.setEmbossingLastName(embossingLastName);
        return this;
    }

    public void setEmbossingLastName(String embossingLastName) {
        this.embossingLastName = embossingLastName;
    }

    public String getEmbossingCompanyName() {
        return this.embossingCompanyName;
    }

    public AccountStatementRecord embossingCompanyName(String embossingCompanyName) {
        this.setEmbossingCompanyName(embossingCompanyName);
        return this;
    }

    public void setEmbossingCompanyName(String embossingCompanyName) {
        this.embossingCompanyName = embossingCompanyName;
    }

    public String getSubType() {
        return this.subType;
    }

    public AccountStatementRecord subType(String subType) {
        this.setSubType(subType);
        return this;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Instant getPurchaseDate() {
        return this.purchaseDate;
    }

    public AccountStatementRecord purchaseDate(Instant purchaseDate) {
        this.setPurchaseDate(purchaseDate);
        return this;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public AccountStatementRecord exchangeRate(BigDecimal exchangeRate) {
        this.setExchangeRate(exchangeRate);
        return this;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getEnrichedMerchantName() {
        return this.enrichedMerchantName;
    }

    public AccountStatementRecord enrichedMerchantName(String enrichedMerchantName) {
        this.setEnrichedMerchantName(enrichedMerchantName);
        return this;
    }

    public void setEnrichedMerchantName(String enrichedMerchantName) {
        this.enrichedMerchantName = enrichedMerchantName;
    }

    public String getEnrichedMerchantUrl() {
        return this.enrichedMerchantUrl;
    }

    public AccountStatementRecord enrichedMerchantUrl(String enrichedMerchantUrl) {
        this.setEnrichedMerchantUrl(enrichedMerchantUrl);
        return this;
    }

    public void setEnrichedMerchantUrl(String enrichedMerchantUrl) {
        this.enrichedMerchantUrl = enrichedMerchantUrl;
    }

    public String getEnrichedMerchantDomain() {
        return this.enrichedMerchantDomain;
    }

    public AccountStatementRecord enrichedMerchantDomain(String enrichedMerchantDomain) {
        this.setEnrichedMerchantDomain(enrichedMerchantDomain);
        return this;
    }

    public void setEnrichedMerchantDomain(String enrichedMerchantDomain) {
        this.enrichedMerchantDomain = enrichedMerchantDomain;
    }

    public String getEnrichedMerchantTelephone() {
        return this.enrichedMerchantTelephone;
    }

    public AccountStatementRecord enrichedMerchantTelephone(String enrichedMerchantTelephone) {
        this.setEnrichedMerchantTelephone(enrichedMerchantTelephone);
        return this;
    }

    public void setEnrichedMerchantTelephone(String enrichedMerchantTelephone) {
        this.enrichedMerchantTelephone = enrichedMerchantTelephone;
    }

    public String getEnrichedMerchantIconUrl() {
        return this.enrichedMerchantIconUrl;
    }

    public AccountStatementRecord enrichedMerchantIconUrl(String enrichedMerchantIconUrl) {
        this.setEnrichedMerchantIconUrl(enrichedMerchantIconUrl);
        return this;
    }

    public void setEnrichedMerchantIconUrl(String enrichedMerchantIconUrl) {
        this.enrichedMerchantIconUrl = enrichedMerchantIconUrl;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public AccountStatementRecord totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountStatementRecord)) {
            return false;
        }
        return getId() != null && getId().equals(((AccountStatementRecord) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountStatementRecord{" +
            "id=" + getId() +
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
