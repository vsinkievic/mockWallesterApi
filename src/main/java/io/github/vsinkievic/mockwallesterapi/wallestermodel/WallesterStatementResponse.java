package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterStatementResponse {

    @JsonProperty("records")
    private List<WallesterStatementRecord> records;

    @JsonProperty("total_records_number")
    private Integer totalRecordsNumber;

    @JsonProperty("credit_turnover")
    private BigDecimal creditTurnover;

    @JsonProperty("debit_turnover")
    private BigDecimal debitTurnover;

    @JsonProperty("pending_amount")
    private BigDecimal pendingAmount;

    public WallesterStatementResponse(List<WallesterStatementRecord> records) {
        this.records = records;
        this.totalRecordsNumber = records.size();
        this.creditTurnover = records
            .stream()
            .filter(record -> record.getAccountAmount().compareTo(BigDecimal.ZERO) > 0)
            .map(record -> record.getAccountAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.debitTurnover = records
            .stream()
            .filter(record -> record.getAccountAmount().compareTo(BigDecimal.ZERO) < 0)
            .map(record -> record.getAccountAmount().negate())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
