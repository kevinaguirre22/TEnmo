package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private JdbcAccountDao jdbcAccountDao;
    private UserDao userDao;

    public AccountController(JdbcAccountDao jdbcAccountDao) {
        this.jdbcAccountDao = jdbcAccountDao;
    }


    @RequestMapping(path = "/accounts/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id){

        BigDecimal balance = new BigDecimal(String.valueOf(jdbcAccountDao.getBalance(id)));

//        if(balance.equals(null)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        } else {
//            return balance;
//        }
        return balance;
    }
}
