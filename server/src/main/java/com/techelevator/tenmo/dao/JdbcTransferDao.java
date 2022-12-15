package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void withdraw(int fromUser, BigDecimal amount) {
        String sql = "UPDATE account SET balance = (balance-amount) WHERE account_id=?";
        jdbcTemplate.update(sql, fromUser, amount);

    }

    @Override
    public void deposit(int toUser, BigDecimal amount) {
        String sql = "UPDATE account SET balance = (balance+amount) WHERE account_id=?";
        jdbcTemplate.update(sql, toUser, amount);
    }
}
