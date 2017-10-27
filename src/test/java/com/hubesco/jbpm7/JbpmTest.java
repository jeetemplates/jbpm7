package com.hubesco.jbpm7;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import javax.naming.NamingException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JbpmTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @BeforeClass
    public static void init() throws IllegalStateException, NamingException {
        PoolingDataSource ds = new PoolingDataSource();
        ds.setUniqueName("jbpm/jbpmDS");
        ds.setClassName("org.h2.jdbcx.JdbcDataSource");
        ds.setMaxPoolSize(10);
        ds.setAllowLocalTransactions(true);
        ds.getDriverProperties().put("user", "sa");
        ds.getDriverProperties().put("password", "sasa");
        ds.getDriverProperties().put("URL", "jdbc:h2:mem:jbpm");
        ds.init();
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("jdbc/jbpmDS", ds);
        builder.activate();
    }

    @Test
    public void testStartProcess() throws InterruptedException {
        String queueName = "startProcessQueue";
        jmsTemplate.convertAndSend(queueName, "StartProcessSample");
        Thread.sleep(2000);
    }

}
