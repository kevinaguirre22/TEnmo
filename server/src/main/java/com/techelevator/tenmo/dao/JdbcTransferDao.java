package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal withdraw(int fromUser, BigDecimal amount) {
        String sql = "UPDATE account " +
                     "SET balance = balance - ? " +
                     "WHERE account_id = ?";
        jdbcTemplate.update(sql, amount, fromUser);

        return amount;
    }

    @Override
    public BigDecimal deposit(int toUser, BigDecimal amount) {
        String sql = "UPDATE account " +
                     "SET balance = balance + ? " +
                     "WHERE account_id = ?";
        jdbcTemplate.update(sql, amount, toUser);

        return amount;
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

    public void createTransfer(Transfer transfer){
        //Transfer transfer = new Transfer();

        String sql = "INSERT INTO transfer (transfer_to, transfer_from, amount, status) " +
                     "VALUES (?, ?, ?, 'Approved');";

        jdbcTemplate.update(sql, transfer.getTransferTo(), transfer.getTransferFrom(), transfer.getAmount());
    }

    public List<Transfer> listAllTransfers(String username){
        List<Transfer> list = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_to, transfer_from, amount, status " +
                     "FROM transfer " +
                     "JOIN account ON account.account_id = transfer.transfer_from OR account.account_id = transfer.transfer_to " +
                     "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                     "WHERE username = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);

        while(result.next()){
            Transfer transfer = mapRowToUser(result);
            list.add(transfer);
        }
        return list;
    }

    public Transfer getTransferByTransferId(int transferId){
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_to, transfer_from, amount, status " +
                     "FROM transfer " +
                     "JOIN account ON account.account_id = transfer.transfer_from OR account.account_id = transfer.transfer_to " +
                     "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                     "WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        if(result.next()){
            transfer = mapRowToUser(result);
        }
        return transfer;
    }

    private Transfer mapRowToUser(SqlRowSet result){
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setTransferTo(result.getInt("transfer_to"));
        transfer.setTransferFrom(result.getInt("transfer_from"));
        transfer.setAmount(result.getBigDecimal("amount"));
        transfer.setStatus(result.getString("status"));
        return transfer;
    }

}
