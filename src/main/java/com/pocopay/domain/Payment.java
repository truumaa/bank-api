package com.pocopay.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Payment implements Serializable {

    private Integer id;
    private Integer sourceAccountId;
    private Integer destinationAccountId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Integer sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Integer getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Integer destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp timestamp) {
        this.transactionDate = timestamp.toLocalDateTime();
    }
}
