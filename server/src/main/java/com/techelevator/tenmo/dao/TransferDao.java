package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {

    void withdraw (int from, BigDecimal amount);

    void deposit(int to, BigDecimal amount);

    void transfer(String username, BigDecimal amount);
}
