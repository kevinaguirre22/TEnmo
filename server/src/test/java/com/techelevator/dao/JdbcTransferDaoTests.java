package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {

    private static final Transfer TRANSFER1 = new Transfer(3001, 2002, 2001, BigDecimal.valueOf(100), "Approved");
    private static final Transfer TRANSFER2 = new Transfer(3002, 2001, 2002, BigDecimal.valueOf(250), "Approved");

    private JdbcTransferDao sut;
    private Transfer testTransfer;
    private Transfer testTransfer2;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
        testTransfer = new Transfer(3003, 2002, 2001, BigDecimal.valueOf(300), "Approved");
        testTransfer2 = new Transfer(3004, 2001, 2002, BigDecimal.valueOf(400), "Approved");
    }

    @Test
    public void withdraw_returns_correct_value(){
        BigDecimal amount = sut.withdraw(1001, BigDecimal.valueOf(100));
        Assert.assertEquals(BigDecimal.valueOf(100), amount);

        BigDecimal amount2 = sut.withdraw(1001, BigDecimal.valueOf(200));
        Assert.assertEquals(BigDecimal.valueOf(200), amount2);
    }

    @Test
    public void deposit_returns_correct_value(){
        BigDecimal amount = sut.deposit(1001, BigDecimal.valueOf(100));
        Assert.assertEquals(BigDecimal.valueOf(100), amount);

        BigDecimal amount2 = sut.deposit(1001, BigDecimal.valueOf(200));
        Assert.assertEquals(BigDecimal.valueOf(200), amount2);
    }

    @Test
    public void listAllTransfers_returns_list_of_correct_size(){
        List<Transfer> list = sut.listAllTransfers("bob");
        Assert.assertEquals(0, list.size());

        List<Transfer> list2 = sut.listAllTransfers("user");
        Assert.assertEquals(0, list2.size());
    }

//    @Test
//    public void createTransfer_has_expected_values_when_retrieved(){
//        Transfer insertedTransfer = sut.createTransfer(testTransfer);
//
//    }

    @Test
    public void getTransferByTransferId_returns_correct_transfer(){
        Transfer retrievedTransfer = sut.getTransferByTransferId(3001);
        assertTransfersMatch(TRANSFER1, retrievedTransfer);

        Transfer retrievedTransfer2 = sut.getTransferByTransferId(3002);
        assertTransfersMatch(TRANSFER2, retrievedTransfer2);
    }


    private void assertTransfersMatch(Transfer expected, Transfer actual){
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferTo(), actual.getTransferTo());
        Assert.assertEquals(expected.getTransferFrom(), actual.getTransferFrom());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }

}
