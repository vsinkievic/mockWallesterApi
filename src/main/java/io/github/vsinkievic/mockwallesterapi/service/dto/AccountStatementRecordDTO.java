package io.github.vsinkievic.mockwallesterapi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordGroup;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordResponse;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatementRecordType;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CountryCode;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.AccountStatementRecord} entity.
 */
@Schema(description = "Represents a single record in an Account Statement.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class AccountStatementRecordDTO implements Serializable {

    private UUID id;

    private String externalId;

    @NotNull
    private UUID accountId;

    private UUID cardId;

    private AccountStatementRecordType type;

    private AccountStatementRecordGroup group;

    private Instant date;

    private BigDecimal transactionAmount;

    private CurrencyCode transactionCurrencyCode;

    private BigDecimal accountAmount;

    private CurrencyCode accountCurrencyCode;

    private String merchantCategoryCode;

    private String merchantId;

    private String terminalId;

    private String merchantName;

    private String merchantCity;

    private CountryCode merchantCountryCode;

    private String description;

    private UUID originalAuthorizationId;

    private Boolean isReversal;

    private Boolean isDeclined;

    private Boolean isCleared;

    private Instant markedForDisputeAt;

    private String markedForDisputeBy;

    private AccountStatementRecordStatus status;

    private AccountStatementRecordResponse response;

    private String responseCode;

    //    private String accountExternalId;

    //    private String maskedCardNumber;

    private Boolean hasPaymentDocumentFiles;

    private Boolean hasPaymentNotes;

    //    private String cardName;

    //    private String embossingName;

    //    private String embossingFirstName;

    //    private String embossingLastName;

    //    private String embossingCompanyName;

    private String subType;

    private Instant purchaseDate;

    private BigDecimal exchangeRate;

    private String enrichedMerchantName;

    private String enrichedMerchantUrl;

    private String enrichedMerchantDomain;

    private String enrichedMerchantTelephone;

    private String enrichedMerchantIconUrl;

    private BigDecimal totalAmount;

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
            ", subType='" + getSubType() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", date='" + getDate() + "'" +
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
            ", exchangeRate=" + getExchangeRate() +
            ", enrichedMerchantName='" + getEnrichedMerchantName() + "'" +
            ", enrichedMerchantUrl='" + getEnrichedMerchantUrl() + "'" +
            ", enrichedMerchantDomain='" + getEnrichedMerchantDomain() + "'" +
            ", enrichedMerchantTelephone='" + getEnrichedMerchantTelephone() + "'" +
            ", enrichedMerchantIconUrl='" + getEnrichedMerchantIconUrl() + "'" +
            ", totalAmount=" + getTotalAmount() +
            "}";
    }

    @JsonIgnore
    public boolean isDebit() {
        return this.accountAmount.compareTo(BigDecimal.ZERO) < 0;
    }

    @JsonIgnore
    public boolean isCredit() {
        return this.accountAmount.compareTo(BigDecimal.ZERO) > 0;
    }
}
