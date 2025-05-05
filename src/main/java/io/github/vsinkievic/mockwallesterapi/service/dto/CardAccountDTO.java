package io.github.vsinkievic.mockwallesterapi.service.dto;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.CardAccount} entity.
 */
@Schema(description = "Represents a customer Account.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CardAccountDTO implements Serializable {

    private UUID id;

    @NotNull
    private CurrencyCode currencyCode;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private BigDecimal availableAmount;

    @NotNull
    private BigDecimal blockedAmount;

    @NotNull
    private AccountStatus status;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private Integer cardsCount;
    private String closeReason;
    private Instant closedAt;
    private String closedBy;
    private UUID companyId;
    private BigDecimal creditLimit;

    private String externalId;
    private Boolean isMain;
    private String name;
    private UUID personId;
    private UUID productId;
    private String referenceNumber;
    private BigDecimal usedCredit;
    private String viban;

    // Limits
    private BigDecimal dailyContactlessPurchase;
    private BigDecimal dailyInternetPurchase;
    private BigDecimal dailyPurchase;
    private BigDecimal dailyWithdrawal;
    private BigDecimal monthlyContactlessPurchase;
    private BigDecimal monthlyInternetPurchase;
    private BigDecimal monthlyPurchase;
    private BigDecimal monthlyWithdrawal;
    private BigDecimal weeklyContactlessPurchase;
    private BigDecimal weeklyInternetPurchase;
    private BigDecimal weeklyPurchase;
    private BigDecimal weeklyWithdrawal;

    // Top-up details
    private String bankAddress;
    private String bankName;
    private String iban;
    private String paymentDetails;
    private String receiverName;
    private String registrationNumber;
    private String swiftCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardAccountDTO)) {
            return false;
        }

        CardAccountDTO cardAccountDTO = (CardAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cardAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardAccountDTO{" +
            "id='" + getId() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", balance=" + getBalance() +
            ", availableAmount=" + getAvailableAmount() +
            ", blockedAmount=" + getBlockedAmount() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
