package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private JdbcAccountDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getBalance_returns_correct_balance(){
        BigDecimal userBalance = sut.getBalance("user");
        Assert.assertEquals(BigDecimal.valueOf(1000), userBalance);

        BigDecimal bobBalance = sut.getBalance("bob");
        Assert.assertEquals(BigDecimal.valueOf(1000), bobBalance);
    }




}
