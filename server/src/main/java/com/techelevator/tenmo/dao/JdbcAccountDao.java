package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

  //  public boolean create(int accountId, int userId){
  //  }

    public BigDecimal getBalance(int id){
        String sql = "SELECT balance " +
                     "FROM account " +
                     "WHERE user_id = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        BigDecimal balance = new BigDecimal(0.00);

        if (result.next()){
            balance = result.getBigDecimal("balance");
        }
        return balance;
    }
}

