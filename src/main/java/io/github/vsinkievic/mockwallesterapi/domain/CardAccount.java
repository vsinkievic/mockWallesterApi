package io.github.vsinkievic.mockwallesterapi.domain;

import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountCloseReason;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

/**
 * Represents a customer Account.
 */
@Entity
@Table(name = "card_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CardAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false)
    private CurrencyCode currencyCode;

    @NotNull
    @Column(name = "balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal balance;

    @NotNull
    @Column(name = "available_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal availableAmount;

    @NotNull
    @Column(name = "blocked_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal blockedAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "cards_count")
    private Integer cardsCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "close_reason")
    private AccountCloseReason closeReason;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Column(name = "closed_by")
    private String closedBy;

    @Column(name = "company_id")
    private UUID companyId;

    @Column(name = "credit_limit", precision = 21, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "name")
    private String name;

    @Column(name = "person_id")
    private UUID personId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "used_credit", precision = 21, scale = 2)
    private BigDecimal usedCredit;

    @Column(name = "viban")
    private String viban;

    // Limits
    @Column(name = "daily_contactless_purchase", precision = 21, scale = 2)
    private BigDecimal dailyContactlessPurchase;

    @Column(name = "daily_internet_purchase", precision = 21, scale = 2)
    private BigDecimal dailyInternetPurchase;

    @Column(name = "daily_purchase", precision = 21, scale = 2)
    private BigDecimal dailyPurchase;

    @Column(name = "daily_withdrawal", precision = 21, scale = 2)
    private BigDecimal dailyWithdrawal;

    @Column(name = "monthly_contactless_purchase", precision = 21, scale = 2)
    private BigDecimal monthlyContactlessPurchase;

    @Column(name = "monthly_internet_purchase", precision = 21, scale = 2)
    private BigDecimal monthlyInternetPurchase;

    @Column(name = "monthly_purchase", precision = 21, scale = 2)
    private BigDecimal monthlyPurchase;

    @Column(name = "monthly_withdrawal", precision = 21, scale = 2)
    private BigDecimal monthlyWithdrawal;

    @Column(name = "weekly_contactless_purchase", precision = 21, scale = 2)
    private BigDecimal weeklyContactlessPurchase;

    @Column(name = "weekly_internet_purchase", precision = 21, scale = 2)
    private BigDecimal weeklyInternetPurchase;

    @Column(name = "weekly_purchase", precision = 21, scale = 2)
    private BigDecimal weeklyPurchase;

    @Column(name = "weekly_withdrawal", precision = 21, scale = 2)
    private BigDecimal weeklyWithdrawal;

    // Top-up details
    @Column(name = "bank_address")
    private String bankAddress;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "payment_details")
    private String paymentDetails;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "swift_code")
    private String swiftCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public CardAccount id(UUID id) {
        this.setId(id);
        return this;
    }

    public CardAccount companyId(UUID companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public CardAccount externalId(String externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public CardAccount currencyCode(CurrencyCode currencyCode) {
        this.setCurrencyCode(currencyCode);
        return this;
    }

    public CardAccount balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public CardAccount availableAmount(BigDecimal availableAmount) {
        this.setAvailableAmount(availableAmount);
        return this;
    }

    public CardAccount blockedAmount(BigDecimal blockedAmount) {
        this.setBlockedAmount(blockedAmount);
        return this;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public CardAccount status(AccountStatus status) {
        this.setStatus(status);
        return this;
    }

    public CardAccount createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public CardAccount updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardAccount)) {
            return false;
        }
        return getId() != null && getId().equals(((CardAccount) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardAccount{" +
            "id=" + getId() +
            ", externalId='" + getExternalId() + "'" +
            ", status='" + getStatus() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", balance=" + getBalance() +
            ", availableAmount=" + getAvailableAmount() +
            ", blockedAmount=" + getBlockedAmount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
