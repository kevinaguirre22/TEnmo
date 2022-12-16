package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.techelevator.tenmo.dao.JdbcTransferDao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private JdbcAccountDao jdbcAccountDao;
    private JdbcUserDao jdbcUserDao;
    private JdbcTransferDao jdbcTransferDao;

    public AccountController(JdbcAccountDao jdbcAccountDao, JdbcUserDao jdbcUserDao, JdbcTransferDao jdbcTransferDao) {
        this.jdbcAccountDao = jdbcAccountDao;
        this.jdbcUserDao =  jdbcUserDao;
        this.jdbcTransferDao = jdbcTransferDao;
    }


    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){

        BigDecimal balance = new BigDecimal(String.valueOf(jdbcAccountDao.getBalance(principal.getName())));

//        if(balance.equals(null)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        } else {
//            return balance;
//        }
        return balance;
    }

    @RequestMapping ( path = "/users", method = RequestMethod.GET)
    public List<String>listAllUsers (){
      //  List<User> users= new ArrayList<>();
     // users.add((User) jdbcUserDao.findAll());
      return jdbcUserDao.findAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public Transfer createTransfer (@RequestBody Transfer transfer){
      //  Transfer createdTransfer = new Transfer();
        jdbcTransferDao.withdraw(transfer.getTransferFrom(), transfer.getAmount());
        jdbcTransferDao.deposit(transfer.getTransferTo(), transfer.getAmount());
        jdbcTransferDao.createTransfer(transfer.getTransferId(), transfer.getTransferTo(), transfer.getTransferFrom(), transfer.getAmount());

        if(transfer.getAmount() > jdbcAccountDao.getBalanceById(1001)){

        }
        return transfer;
    }
}
