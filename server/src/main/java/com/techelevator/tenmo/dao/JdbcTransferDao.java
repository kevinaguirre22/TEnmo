package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void withdraw(int fromUser, BigDecimal amount) {
        String sql = "UPDATE account " +
                     "SET balance = balance - ? " +
                     "WHERE account_id = ?";
        jdbcTemplate.update(sql, amount, fromUser);

    }

    @Override
    public void deposit(int toUser, BigDecimal amount) {
        String sql = "UPDATE account " +
                     "SET balance = balance + ? " +
                     "WHERE account_id = ?";
        jdbcTemplate.update(sql, amount, toUser);
    }

    @Override
    public void transfer(String username, BigDecimal amount){
        String sql = "BEGIN TRANSACTION; " +
                     "UPDATE account SET balance = balance - ? " +
                     "WHERE account_id = ?; " +
                     "UPDATE account SET balance = balance + ? " +
                     "WHERE account_id = ?; " +
                     "COMMIT;";

        jdbcTemplate.update(sql, amount, "kevin", amount, "katie");
    }

    public void createTransfer(int id, int to, int from, BigDecimal amount){
        //Transfer transfer = new Transfer();

        String sql = "INSERT INTO transfer (transfer_to, transfer_from, amount) " +
                     "VALUES (?, ?, ?);";

        jdbcTemplate.update(sql, to, from, amount);
    }
}
