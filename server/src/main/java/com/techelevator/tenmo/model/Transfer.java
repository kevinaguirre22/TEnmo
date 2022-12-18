package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class Transfer {


    private int transferId;
    private int transferTo;
    private int transferFrom;
    @Min(value = 0, message = "Cannot transfer less than zero dollar amount")
    private BigDecimal amount;
    private String status;

    public Transfer() {
    }

    public Transfer(int transferId, int transferTo, int transferFrom, BigDecimal amount, String status) {
        this.transferId = transferId;
        this.transferTo = transferTo;
        this.transferFrom = transferFrom;
        this.amount = amount;
        this.status = status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(int transferTo) {
        this.transferTo = transferTo;
    }

    public int getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(int transferFrom) {
        this.transferFrom = transferFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
