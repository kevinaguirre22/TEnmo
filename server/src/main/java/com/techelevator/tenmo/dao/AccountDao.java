package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    //boolean create(int accountId, int userId);

    BigDecimal getBalance(String username);
}
