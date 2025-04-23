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

/**
 * A DTO for the {@link io.github.vsinkievic.mockwallesterapi.domain.CardAccount} entity.
 */
@Schema(description = "Represents a customer Account.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CardAccountDTO implements Serializable {

    @NotNull
    private UUID id;

    @NotNull
    private String accountNumber;

    @NotNull
    private CurrencyCode currency;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private BigDecimal reservedAmount;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(BigDecimal reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getBlockedAmount() {
        return blockedAmount;
    }

    public void setBlockedAmount(BigDecimal blockedAmount) {
        this.blockedAmount = blockedAmount;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

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
            ", accountNumber='" + getAccountNumber() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", balance=" + getBalance() +
            ", reservedAmount=" + getReservedAmount() +
            ", availableAmount=" + getAvailableAmount() +
            ", blockedAmount=" + getBlockedAmount() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
