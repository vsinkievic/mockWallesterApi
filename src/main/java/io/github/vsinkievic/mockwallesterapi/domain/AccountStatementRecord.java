package io.github.vsinkievic.mockwallesterapi.domain;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents a single record in an Account Statement.
 */
@Entity
@Table(name = "account_statement_record")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class AccountStatementRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "external_id")
    private String externalId;

    @NotNull(message = "Account ID is required")
    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "card_id")
    private UUID cardId;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "trn_type")
    private AccountStatementRecordType type;

    @NotNull(message = "Group is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "trn_group")
    private AccountStatementRecordGroup group;

    @NotNull(message = "Date is required")
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

    @Column(name = "merchant_category_code")
    private String merchantCategoryCode;

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
    private UUID originalAuthorizationId;

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

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatementRecordStatus status;

    @NotNull(message = "Response is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "response")
    private AccountStatementRecordResponse response;

    @Column(name = "response_code")
    private String responseCode;

    //    @Column(name = "account_external_id")
    //    private String accountExternalId;

    //    @Column(name = "masked_card_number")
    //    private String maskedCardNumber;

    @Column(name = "has_payment_document_files")
    private Boolean hasPaymentDocumentFiles;

    @Column(name = "has_payment_notes")
    private Boolean hasPaymentNotes;

    //    @Column(name = "card_name")
    //    private String cardName;

    //    @Column(name = "embossing_name")
    //    private String embossingName;

    //    @Column(name = "embossing_first_name")
    //    private String embossingFirstName;

    //    @Column(name = "embossing_last_name")
    //    private String embossingLastName;

    //    @Column(name = "embossing_company_name")
    //    private String embossingCompanyName;

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

    public void validateAndPrepareForSave() {
        if (this.isCleared == null) this.isCleared = false;
        if (this.isDeclined == null) this.isDeclined = false;
        if (this.isReversal == null) this.isReversal = false;

        if (this.getTransactionAmount() != null) this.transactionAmount = this.transactionAmount.setScale(2, RoundingMode.HALF_UP);
        if (this.getAccountAmount() != null) this.accountAmount = this.accountAmount.setScale(2, RoundingMode.HALF_UP);

        if (this.getDate() == null) this.date = Instant.now();

        if (this.accountCurrencyCode == null) this.accountCurrencyCode = CurrencyCode.EUR;
        if (this.transactionCurrencyCode == null) this.transactionCurrencyCode = this.accountCurrencyCode;
        if (Objects.equals(this.accountCurrencyCode, this.transactionCurrencyCode)) {
            this.exchangeRate = BigDecimal.ONE;
            if (this.transactionAmount != null && this.accountAmount == null) this.accountAmount = this.transactionAmount;
            else if (this.transactionAmount == null && this.accountAmount != null) this.transactionAmount = this.accountAmount;

            if (this.transactionAmount.compareTo(this.accountAmount) != 0) {
                this.transactionAmount = this.accountAmount;
            }
        }

        switch (this.getType()) {
            case Authorization:
                if (AccountStatementRecordStatus.Canceled.equals(this.getStatus())) {
                    this.setResponse(AccountStatementRecordResponse.Declined);
                    this.isDeclined = true;
                } else if (
                    AccountStatementRecordStatus.Completed.equals(this.getStatus()) ||
                    AccountStatementRecordStatus.Pending.equals(this.getStatus())
                ) {
                    this.setResponse(AccountStatementRecordResponse.Approved);
                } else {
                    this.setStatus(AccountStatementRecordStatus.Pending);
                    this.setResponse(AccountStatementRecordResponse.Approved);
                }
                this.purchaseDate = null;

                if (AccountStatementRecordGroup.Refund.equals(this.getGroup())) {
                    this.transactionAmount = positive(this.transactionAmount);
                    this.accountAmount = positive(this.accountAmount);
                } else {
                    this.transactionAmount = negative(this.transactionAmount);
                    this.accountAmount = negative(this.accountAmount);
                }
                if (this.isReversal) {
                    if (this.transactionAmount != null) this.transactionAmount = this.transactionAmount.negate();
                    if (this.accountAmount != null) this.accountAmount = this.accountAmount.negate();
                }
                break;
            case Transaction:
                if (this.isReversal) throw new WallesterApiException(422, "Transaction cannot be reversal");
                if (this.isDeclined) throw new WallesterApiException(422, "Transaction cannot be declined");
                this.setStatus(AccountStatementRecordStatus.Completed);
                this.setResponse(AccountStatementRecordResponse.Approved);

                if (
                    !AccountStatementRecordGroup.Other.equals(this.getGroup()) &&
                    !AccountStatementRecordGroup.Refund.equals(this.getGroup())
                ) {
                    if (this.getTransactionAmount() != null) this.transactionAmount = this.transactionAmount.abs().negate();
                    if (this.getAccountAmount() != null) this.accountAmount = this.accountAmount.abs().negate();
                }

                break;
            case Fee:
                if (this.isReversal) throw new WallesterApiException(422, "Fee cannot be reversal");

                if (AccountStatementRecordStatus.Canceled.equals(this.getStatus())) {
                    this.setResponse(AccountStatementRecordResponse.Declined);
                    this.isDeclined = true;
                    this.isCleared = false;
                } else if (AccountStatementRecordStatus.Pending.equals(this.getStatus())) {
                    this.setResponse(AccountStatementRecordResponse.Approved);
                    this.isDeclined = false;
                    this.isCleared = false;
                } else if (AccountStatementRecordStatus.Completed.equals(this.getStatus())) {
                    this.isCleared = AccountStatementRecordResponse.Approved.equals(this.getResponse());
                    this.isDeclined = false;
                } else {
                    this.setStatus(AccountStatementRecordStatus.Pending);
                    this.setResponse(AccountStatementRecordResponse.Approved);
                    this.isDeclined = false;
                    this.isCleared = false;
                }

                this.transactionAmount = this.accountAmount;
                this.transactionCurrencyCode = this.accountCurrencyCode;

                if (this.getTransactionAmount() != null) this.transactionAmount = this.transactionAmount.abs().negate();
                if (this.getAccountAmount() != null) this.accountAmount = this.accountAmount.abs().negate();

                break;
            case AccountAdjustment:
                if (this.isReversal) throw new WallesterApiException(422, "Account adjustment cannot be reversal");
                if (this.isDeclined) throw new WallesterApiException(422, "Transaction cannot be declined");

                if (StringUtils.isBlank(this.getDescription())) this.setDescription("Account adjustment");

                if (this.getTransactionAmount() == null) this.transactionAmount = this.accountAmount;
                else if (this.getAccountAmount() == null) this.accountAmount = this.transactionAmount;

                if (this.getAccountAmount() != null) {
                    this.group = this.accountAmount.compareTo(BigDecimal.ZERO) < 0
                        ? AccountStatementRecordGroup.Withdraw
                        : AccountStatementRecordGroup.Deposit;
                }

                this.setStatus(AccountStatementRecordStatus.Completed);
                this.setResponse(AccountStatementRecordResponse.Approved);
                this.purchaseDate = null;

                break;
            default:
                throw new WallesterApiException(
                    422,
                    "Invalid type for account statement record (allowed types: Authorization, Transaction, Fee, AccountAdjustment)"
                );
        }
    }

    private BigDecimal positive(BigDecimal value) {
        if (value == null) return null;
        return value.abs();
    }

    private BigDecimal negative(BigDecimal value) {
        if (value == null) return null;
        return value.abs().negate();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public AccountStatementRecord id(UUID id) {
        this.setId(id);
        return this;
    }

    public AccountStatementRecord externalId(String externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public AccountStatementRecord accountId(UUID accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public AccountStatementRecord cardId(UUID cardId) {
        this.setCardId(cardId);
        return this;
    }

    public AccountStatementRecord type(AccountStatementRecordType type) {
        this.setType(type);
        return this;
    }

    public AccountStatementRecord group(AccountStatementRecordGroup group) {
        this.setGroup(group);
        return this;
    }

    public AccountStatementRecord date(Instant date) {
        this.setDate(date);
        return this;
    }

    public AccountStatementRecord transactionAmount(BigDecimal transactionAmount) {
        this.setTransactionAmount(transactionAmount);
        return this;
    }

    public AccountStatementRecord transactionCurrencyCode(CurrencyCode transactionCurrencyCode) {
        this.setTransactionCurrencyCode(transactionCurrencyCode);
        return this;
    }

    public AccountStatementRecord accountAmount(BigDecimal accountAmount) {
        this.setAccountAmount(accountAmount);
        return this;
    }

    public AccountStatementRecord accountCurrencyCode(CurrencyCode accountCurrencyCode) {
        this.setAccountCurrencyCode(accountCurrencyCode);
        return this;
    }

    public AccountStatementRecord merchantCategoryCode(String merchantCategoryCode) {
        this.setMerchantCategoryCode(merchantCategoryCode);
        return this;
    }

    public AccountStatementRecord merchantId(String merchantId) {
        this.setMerchantId(merchantId);
        return this;
    }

    public AccountStatementRecord terminalId(String terminalId) {
        this.setTerminalId(terminalId);
        return this;
    }

    public AccountStatementRecord merchantName(String merchantName) {
        this.setMerchantName(merchantName);
        return this;
    }

    public AccountStatementRecord merchantCity(String merchantCity) {
        this.setMerchantCity(merchantCity);
        return this;
    }

    public AccountStatementRecord merchantCountryCode(CountryCode merchantCountryCode) {
        this.setMerchantCountryCode(merchantCountryCode);
        return this;
    }

    public AccountStatementRecord description(String description) {
        this.setDescription(description);
        return this;
    }

    public AccountStatementRecord originalAuthorizationId(UUID originalAuthorizationId) {
        this.setOriginalAuthorizationId(originalAuthorizationId);
        return this;
    }

    public AccountStatementRecord isReversal(Boolean isReversal) {
        this.setIsReversal(isReversal);
        return this;
    }

    public AccountStatementRecord isDeclined(Boolean isDeclined) {
        this.setIsDeclined(isDeclined);
        return this;
    }

    public AccountStatementRecord isCleared(Boolean isCleared) {
        this.setIsCleared(isCleared);
        return this;
    }

    public AccountStatementRecord markedForDisputeAt(Instant markedForDisputeAt) {
        this.setMarkedForDisputeAt(markedForDisputeAt);
        return this;
    }

    public AccountStatementRecord markedForDisputeBy(String markedForDisputeBy) {
        this.setMarkedForDisputeBy(markedForDisputeBy);
        return this;
    }

    public AccountStatementRecord status(AccountStatementRecordStatus status) {
        this.setStatus(status);
        return this;
    }

    public AccountStatementRecord response(AccountStatementRecordResponse response) {
        this.setResponse(response);
        return this;
    }

    public AccountStatementRecord responseCode(String responseCode) {
        this.setResponseCode(responseCode);
        return this;
    }

    //    public AccountStatementRecord accountExternalId(String accountExternalId) {
    //        this.setAccountExternalId(accountExternalId);
    //        return this;
    //    }

    //    public AccountStatementRecord maskedCardNumber(String maskedCardNumber) {
    //        this.setMaskedCardNumber(maskedCardNumber);
    //        return this;
    //    }

    public AccountStatementRecord hasPaymentDocumentFiles(Boolean hasPaymentDocumentFiles) {
        this.setHasPaymentDocumentFiles(hasPaymentDocumentFiles);
        return this;
    }

    public AccountStatementRecord hasPaymentNotes(Boolean hasPaymentNotes) {
        this.setHasPaymentNotes(hasPaymentNotes);
        return this;
    }

    //    public AccountStatementRecord cardName(String cardName) {
    //        this.setCardName(cardName);
    //        return this;
    //    }

    //    public AccountStatementRecord embossingName(String embossingName) {
    //        this.setEmbossingName(embossingName);
    //        return this;
    //    }

    //    public AccountStatementRecord embossingFirstName(String embossingFirstName) {
    //        this.setEmbossingFirstName(embossingFirstName);
    //        return this;
    //    }

    //    public AccountStatementRecord embossingLastName(String embossingLastName) {
    //        this.setEmbossingLastName(embossingLastName);
    //        return this;
    //    }

    //    public AccountStatementRecord embossingCompanyName(String embossingCompanyName) {
    //        this.setEmbossingCompanyName(embossingCompanyName);
    //        return this;
    //    }

    public AccountStatementRecord subType(String subType) {
        this.setSubType(subType);
        return this;
    }

    public AccountStatementRecord purchaseDate(Instant purchaseDate) {
        this.setPurchaseDate(purchaseDate);
        return this;
    }

    public AccountStatementRecord exchangeRate(BigDecimal exchangeRate) {
        this.setExchangeRate(exchangeRate);
        return this;
    }

    public AccountStatementRecord enrichedMerchantName(String enrichedMerchantName) {
        this.setEnrichedMerchantName(enrichedMerchantName);
        return this;
    }

    public AccountStatementRecord enrichedMerchantUrl(String enrichedMerchantUrl) {
        this.setEnrichedMerchantUrl(enrichedMerchantUrl);
        return this;
    }

    public AccountStatementRecord enrichedMerchantDomain(String enrichedMerchantDomain) {
        this.setEnrichedMerchantDomain(enrichedMerchantDomain);
        return this;
    }

    public AccountStatementRecord enrichedMerchantTelephone(String enrichedMerchantTelephone) {
        this.setEnrichedMerchantTelephone(enrichedMerchantTelephone);
        return this;
    }

    public AccountStatementRecord enrichedMerchantIconUrl(String enrichedMerchantIconUrl) {
        this.setEnrichedMerchantIconUrl(enrichedMerchantIconUrl);
        return this;
    }

    public AccountStatementRecord totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
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
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionCurrencyCode='" + getTransactionCurrencyCode() + "'" +
            ", accountAmount=" + getAccountAmount() +
            ", accountCurrencyCode='" + getAccountCurrencyCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", response='" + getResponse() + "'" +
            ", responseCode='" + getResponseCode() + "'" +
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
            ", hasPaymentDocumentFiles='" + getHasPaymentDocumentFiles() + "'" +
            ", hasPaymentNotes='" + getHasPaymentNotes() + "'" +
            ", subType='" + getSubType() + "'" +
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
