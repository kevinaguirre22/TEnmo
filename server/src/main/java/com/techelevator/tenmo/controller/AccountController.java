package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import com.techelevator.tenmo.dao.JdbcTransferDao;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        this.jdbcUserDao = jdbcUserDao;
        this.jdbcTransferDao = jdbcTransferDao;
    }


    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {

        BigDecimal balance = new BigDecimal(String.valueOf(jdbcAccountDao.getBalance(principal.getName())));

//        if(balance.equals(null)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        } else {
//            return balance;
//        }
        return balance;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> listAllUsers() {
        //  List<User> users= new ArrayList<>();
        // users.add((User) jdbcUserDao.findAll());
        return jdbcUserDao.findAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public Transfer createTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        //  Transfer createdTransfer = new Transfer();
        //BigDecimal withdrawAmount = jdbcTransferDao.withdraw(transfer.getTransferFrom(), transfer.getAmount());
        //BigDecimal depositAmount = jdbcTransferDao.deposit(transfer.getTransferTo(), transfer.getAmount());
        String username = principal.getName();
        int userId = jdbcUserDao.findIdByUsername(username);
        int accountFrom = transfer.getTransferFrom();
        int accountTo = transfer.getTransferTo();
        BigDecimal transferAmount = transfer.getAmount();
        BigDecimal senderBalance = jdbcAccountDao.getBalance(username);
        //System.out.println(senderBalance);

        if (transferAmount.compareTo(senderBalance) <= 0) {
            jdbcTransferDao.withdraw(accountFrom, transferAmount);
            jdbcTransferDao.deposit(accountTo, transferAmount);
            jdbcTransferDao.createTransfer(transfer);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return transfer;
    }


    @RequestMapping(path = "/listing-transfers", method = RequestMethod.GET)
    public List<Transfer> transferList(Principal principal){
        String username = principal.getName();
        List<Transfer> transfers = jdbcTransferDao.listAllTransfers(username);

        //transfers.add((Transfer) jdbcTransferDao.listAllTransfers(username));
        return transfers;
    }

    @RequestMapping(path = "/listing-transfers/{id}", method = RequestMethod.GET)
    public Transfer transferById(@PathVariable int transferId){
        //String username = principal.getName();

        return jdbcTransferDao.getTransferByTransferId(transferId);
    }

}
// if(sender's balance >= withdraw amount) {
//    create the transfer
//  } else {
//     throw exception
