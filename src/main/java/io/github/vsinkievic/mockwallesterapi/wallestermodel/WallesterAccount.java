package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.AccountStatus;
import io.github.vsinkievic.mockwallesterapi.domain.enumeration.CurrencyCode;
import io.github.vsinkievic.mockwallesterapi.service.dto.CardAccountDTO;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class WallesterAccount {

    private UUID id;

    @JsonProperty("company_id")
    private UUID companyId;

    @JsonProperty("person_id")
    private UUID personId;

    @JsonProperty("available_amount")
    private BigDecimal availableAmount;

    private BigDecimal balance;

    @JsonProperty("blocked_amount")
    private BigDecimal blockedAmount;

    @JsonProperty("cards_count")
    private Integer cardsCount;

    @JsonProperty("close_reason")
    private String closeReason;

    @JsonProperty("closed_at")
    private Instant closedAt;

    @JsonProperty("closed_by")
    private String closedBy;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("credit_limit")
    private BigDecimal creditLimit;

    @JsonProperty("currency_code")
    private CurrencyCode currencyCode;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("is_main")
    private Boolean isMain;

    private WallesterAccountLimits limits;

    private String name;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("reference_number")
    private String referenceNumber;

    private AccountStatus status;

    @JsonProperty("top_up_details")
    private WallesterAccountTopupDetails topUpDetails;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    @JsonProperty("used_credit")
    private BigDecimal usedCredit;

    private String viban;

    public WallesterAccount(CardAccountDTO dto) {
        this.availableAmount = dto.getAvailableAmount();
        this.balance = dto.getBalance();
        this.blockedAmount = dto.getBlockedAmount();
        this.cardsCount = dto.getCardsCount();
        this.closeReason = dto.getCloseReason();
        this.closedAt = dto.getClosedAt();
        this.closedBy = dto.getClosedBy();
        this.companyId = dto.getCompanyId();
        this.createdAt = dto.getCreatedAt();
        this.creditLimit = dto.getCreditLimit();
        this.currencyCode = dto.getCurrencyCode();
        this.externalId = dto.getExternalId();
        this.id = dto.getId();
        this.isMain = dto.getIsMain();
        this.name = dto.getName();
        this.personId = dto.getPersonId();
        this.productId = dto.getProductId() != null ? dto.getProductId().toString() : null;
        this.referenceNumber = dto.getReferenceNumber();
        this.status = dto.getStatus();
        this.updatedAt = dto.getUpdatedAt();
        this.usedCredit = dto.getUsedCredit();
        this.viban = dto.getViban();

        // Set limits
        this.limits = new WallesterAccountLimits();
        this.limits.setDailyContactlessPurchase(dto.getDailyContactlessPurchase());
        this.limits.setDailyInternetPurchase(dto.getDailyInternetPurchase());
        this.limits.setDailyPurchase(dto.getDailyPurchase());
        this.limits.setDailyWithdrawal(dto.getDailyWithdrawal());
        this.limits.setMonthlyContactlessPurchase(dto.getMonthlyContactlessPurchase());
        this.limits.setMonthlyInternetPurchase(dto.getMonthlyInternetPurchase());
        this.limits.setMonthlyPurchase(dto.getMonthlyPurchase());
        this.limits.setMonthlyWithdrawal(dto.getMonthlyWithdrawal());
        this.limits.setWeeklyContactlessPurchase(dto.getWeeklyContactlessPurchase());
        this.limits.setWeeklyInternetPurchase(dto.getWeeklyInternetPurchase());
        this.limits.setWeeklyPurchase(dto.getWeeklyPurchase());
        this.limits.setWeeklyWithdrawal(dto.getWeeklyWithdrawal());

        // Set top-up details
        this.topUpDetails = new WallesterAccountTopupDetails();
        this.topUpDetails.setBankAddress(dto.getBankAddress());
        this.topUpDetails.setBankName(dto.getBankName());
        this.topUpDetails.setIban(dto.getIban());
        this.topUpDetails.setPaymentDetails(dto.getPaymentDetails());
        this.topUpDetails.setReceiverName(dto.getReceiverName());
        this.topUpDetails.setRegistrationNumber(dto.getRegistrationNumber());
        this.topUpDetails.setSwiftCode(dto.getSwiftCode());
    }
}
