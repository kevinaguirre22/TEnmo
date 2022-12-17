package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    BigDecimal withdraw (int from, BigDecimal amount);

    BigDecimal deposit(int to, BigDecimal amount);

    void transfer(String username, BigDecimal amount);

    List<Transfer> listAllTransfers(String username);

    Transfer getTransferByTransferId(int transferId);
}
