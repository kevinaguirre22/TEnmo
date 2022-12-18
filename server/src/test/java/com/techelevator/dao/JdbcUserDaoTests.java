package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;
    //private JdbcAccountDao jdbcAccountDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

/*    @Test
    public void create_returns_account_with_initial_balance_of_1000() {
        boolean userCreated = sut.create("TEST_USER", "test_password");
        Assert.assertTrue(userCreated);
        BigDecimal balance = jdbcAccountDao.getBalance("TEST_USER");
        Assert.assertEquals(userCreated, balance);
    }*/

    @Test
    public void findAllUsers_returns_list_of_all_users(){
        List<String> usersList = sut.findAllUsers();
        Assert.assertEquals(4, usersList.size());
    }

}
