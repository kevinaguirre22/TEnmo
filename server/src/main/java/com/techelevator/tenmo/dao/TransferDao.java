package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {
    void withdraw (int from, BigDecimal amount);
    void deposit(int to, BigDecimal amount);

}
