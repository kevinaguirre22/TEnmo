package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int transferTo;
    private int transferFrom;
    private BigDecimal amount;

    public Transfer() {
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
}
