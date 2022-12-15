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

    public BigDecimal getBalance(String username){
        String sql = "SELECT balance " +
                     "FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                     "WHERE username = ?;";


        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        BigDecimal balance = new BigDecimal(0.00);

        if (result.next()){
            balance = result.getBigDecimal("balance");
        }
        return balance;
    }
}

//principal, - get name to get username
  //      use jdbc template to get rowset
    //    now with maptoaccount(result) you have an account( find balance)


