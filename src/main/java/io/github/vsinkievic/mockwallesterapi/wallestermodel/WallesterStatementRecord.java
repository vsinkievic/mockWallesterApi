package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord;
import io.github.vsinkievic.mockwallesterapi.domain.Card;
import io.github.vsinkievic.mockwallesterapi.domain.CardAccount;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterStatementRecord {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("account_external_id")
    private String accountExternalId;

    @JsonProperty("card_id")
    private UUID cardId;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("masked_card_number")
    private String maskedCardNumber;

    @JsonProperty("type")
    private AccountStatementRecordType type;

    @JsonProperty("group")
    private AccountStatementRecordGroup group;

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("date")
    private Instant date;

    @JsonProperty("purchase_date")
    private Instant purchaseDate;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonProperty("transaction_currency_code")
    private CurrencyCode transactionCurrencyCode;

    @JsonProperty("account_amount")
    private BigDecimal accountAmount;

    @JsonProperty("account_currency_code")
    private CurrencyCode accountCurrencyCode;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("response")
    private AccountStatementRecordResponse response;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("status")
    private AccountStatementRecordStatus status;

    @JsonProperty("is_cleared")
    private Boolean isCleared;

    @JsonProperty("is_declined")
    private Boolean isDeclined;

    @JsonProperty("is_reversal")
    private Boolean isReversal;

    @JsonProperty("description")
    private String description;

    @JsonProperty("embossing_company_name")
    private String embossingCompanyName;

    @JsonProperty("embossing_first_name")
    private String embossingFirstName;

    @JsonProperty("embossing_last_name")
    private String embossingLastName;

    @JsonProperty("embossing_name")
    private String embossingName;

    @JsonProperty("enriched_merchant_data")
    private WallesterEnrichedMerchantData enrichedMerchantData;

    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;

    @JsonProperty("has_payment_document_files")
    private Boolean hasPaymentDocumentFiles;

    @JsonProperty("has_payment_notes")
    private Boolean hasPaymentNotes;

    @JsonProperty("marked_for_dispute_at")
    private Instant markedForDisputeAt;

    @JsonProperty("marked_for_dispute_by")
    private String markedForDisputeBy;

    @JsonProperty("merchant_category_code")
    private String merchantCategoryCode;

    @JsonProperty("merchant_city")
    private String merchantCity;

    @JsonProperty("merchant_country_code")
    private CountryCode merchantCountryCode;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("merchant_name")
    private String merchantName;

    @JsonProperty("original_authorization_id")
    private UUID originalAuthorizationId;

    @JsonProperty("terminal_id")
    private String terminalId;

    public WallesterStatementRecord(AccountStatementRecord record, CardAccount cardAccount, Card card) {
        this.accountAmount = record.getAccountAmount();
        this.accountCurrencyCode = record.getAccountCurrencyCode();
        this.accountExternalId = cardAccount != null ? cardAccount.getExternalId() : null;
        this.cardId = record.getCardId();
        this.cardName = card != null ? card.getName() : null;
        this.date = record.getDate();
        this.description = record.getDescription();
        this.embossingCompanyName = card != null ? card.getEmbossingCompanyName() : null;
        this.embossingFirstName = card != null ? card.getEmbossingFirstName() : null;
        this.embossingLastName = card != null ? card.getEmbossingLastName() : null;
        this.embossingName = card != null ? card.getEmbossingName() : null;
        this.maskedCardNumber = card != null ? card.getMaskedCardNumber() : null;

        // Only create enrichedMerchantData if at least one field has a non-blank value
        String enrichedDomain = record.getEnrichedMerchantDomain();
        String enrichedIconUrl = record.getEnrichedMerchantIconUrl();
        String enrichedName = record.getEnrichedMerchantName();
        String enrichedTelephone = record.getEnrichedMerchantTelephone();
        String enrichedUrl = record.getEnrichedMerchantUrl();

        if (
            StringUtils.isNotBlank(enrichedDomain) ||
            StringUtils.isNotBlank(enrichedIconUrl) ||
            StringUtils.isNotBlank(enrichedName) ||
            StringUtils.isNotBlank(enrichedTelephone) ||
            StringUtils.isNotBlank(enrichedUrl)
        ) {
            this.enrichedMerchantData = new WallesterEnrichedMerchantData()
                .domain(enrichedDomain)
                .iconUrl(enrichedIconUrl)
                .name(enrichedName)
                .telephoneNumber(enrichedTelephone)
                .url(enrichedUrl);
        }

        this.exchangeRate = record.getExchangeRate();
        this.group = record.getGroup();
        this.hasPaymentDocumentFiles = record.getHasPaymentDocumentFiles() != null ? record.getHasPaymentDocumentFiles() : false;
        this.hasPaymentNotes = record.getHasPaymentNotes() != null ? record.getHasPaymentNotes() : false;
        this.id = record.getId();
        this.isCleared = record.getIsCleared();
        this.isDeclined = record.getIsDeclined();
        this.isReversal = record.getIsReversal();
        this.markedForDisputeAt = record.getMarkedForDisputeAt();
        this.markedForDisputeBy = record.getMarkedForDisputeBy();
        this.merchantCategoryCode = record.getMerchantCategoryCode();
        this.merchantCity = record.getMerchantCity();
        this.merchantCountryCode = record.getMerchantCountryCode();
        this.merchantId = record.getMerchantId();
        this.merchantName = record.getMerchantName();
        this.originalAuthorizationId = record.getOriginalAuthorizationId();
        this.purchaseDate = record.getPurchaseDate();
        this.response = record.getResponse();
        this.responseCode = record.getResponseCode();
        this.status = record.getStatus();
        this.subType = record.getSubType();
        this.terminalId = record.getTerminalId();
        this.totalAmount = record.getTotalAmount();
        this.transactionAmount = record.getTransactionAmount();
        this.transactionCurrencyCode = record.getTransactionCurrencyCode();
        this.type = record.getType();
    }

    // Builder-style setters
    public WallesterStatementRecord accountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
        return this;
    }

    public WallesterStatementRecord accountCurrencyCode(CurrencyCode accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
        return this;
    }

    public WallesterStatementRecord accountExternalId(String accountExternalId) {
        this.accountExternalId = accountExternalId;
        return this;
    }

    public WallesterStatementRecord cardId(UUID cardId) {
        this.cardId = cardId;
        return this;
    }

    public WallesterStatementRecord cardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public WallesterStatementRecord date(Instant date) {
        this.date = date;
        return this;
    }

    public WallesterStatementRecord description(String description) {
        this.description = description;
        return this;
    }

    public WallesterStatementRecord embossingCompanyName(String embossingCompanyName) {
        this.embossingCompanyName = embossingCompanyName;
        return this;
    }

    public WallesterStatementRecord embossingFirstName(String embossingFirstName) {
        this.embossingFirstName = embossingFirstName;
        return this;
    }

    public WallesterStatementRecord embossingLastName(String embossingLastName) {
        this.embossingLastName = embossingLastName;
        return this;
    }

    public WallesterStatementRecord embossingName(String embossingName) {
        this.embossingName = embossingName;
        return this;
    }

    public WallesterStatementRecord enrichedMerchantData(WallesterEnrichedMerchantData enrichedMerchantData) {
        this.enrichedMerchantData = enrichedMerchantData;
        return this;
    }

    public WallesterStatementRecord exchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public WallesterStatementRecord group(AccountStatementRecordGroup group) {
        this.group = group;
        return this;
    }

    public WallesterStatementRecord hasPaymentDocumentFiles(Boolean hasPaymentDocumentFiles) {
        this.hasPaymentDocumentFiles = hasPaymentDocumentFiles;
        return this;
    }

    public WallesterStatementRecord hasPaymentNotes(Boolean hasPaymentNotes) {
        this.hasPaymentNotes = hasPaymentNotes;
        return this;
    }

    public WallesterStatementRecord id(UUID id) {
        this.id = id;
        return this;
    }

    public WallesterStatementRecord isCleared(Boolean isCleared) {
        this.isCleared = isCleared;
        return this;
    }

    public WallesterStatementRecord isDeclined(Boolean isDeclined) {
        this.isDeclined = isDeclined;
        return this;
    }

    public WallesterStatementRecord isReversal(Boolean isReversal) {
        this.isReversal = isReversal;
        return this;
    }

    public WallesterStatementRecord markedForDisputeAt(Instant markedForDisputeAt) {
        this.markedForDisputeAt = markedForDisputeAt;
        return this;
    }

    public WallesterStatementRecord markedForDisputeBy(String markedForDisputeBy) {
        this.markedForDisputeBy = markedForDisputeBy;
        return this;
    }

    public WallesterStatementRecord maskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
        return this;
    }

    public WallesterStatementRecord merchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
        return this;
    }

    public WallesterStatementRecord merchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
        return this;
    }

    public WallesterStatementRecord merchantCountryCode(CountryCode merchantCountryCode) {
        this.merchantCountryCode = merchantCountryCode;
        return this;
    }

    public WallesterStatementRecord merchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public WallesterStatementRecord merchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public WallesterStatementRecord originalAuthorizationId(UUID originalAuthorizationId) {
        this.originalAuthorizationId = originalAuthorizationId;
        return this;
    }

    public WallesterStatementRecord purchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public WallesterStatementRecord response(AccountStatementRecordResponse response) {
        this.response = response;
        return this;
    }

    public WallesterStatementRecord responseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public WallesterStatementRecord status(AccountStatementRecordStatus status) {
        this.status = status;
        return this;
    }

    public WallesterStatementRecord subType(String subType) {
        this.subType = subType;
        return this;
    }

    public WallesterStatementRecord terminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }

    public WallesterStatementRecord totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public WallesterStatementRecord transactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public WallesterStatementRecord transactionCurrencyCode(CurrencyCode transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
        return this;
    }

    public WallesterStatementRecord type(AccountStatementRecordType type) {
        this.type = type;
        return this;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WallesterEnrichedMerchantData {

        @JsonProperty("domain")
        private String domain;

        @JsonProperty("icon_url")
        private String iconUrl;

        @JsonProperty("name")
        private String name;

        @JsonProperty("telephone_number")
        private String telephoneNumber;

        @JsonProperty("url")
        private String url;

        // Builder-style setters
        public WallesterEnrichedMerchantData domain(String domain) {
            this.domain = domain;
            return this;
        }

        public WallesterEnrichedMerchantData iconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public WallesterEnrichedMerchantData name(String name) {
            this.name = name;
            return this;
        }

        public WallesterEnrichedMerchantData telephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public WallesterEnrichedMerchantData url(String url) {
            this.url = url;
            return this;
        }
    }
}
