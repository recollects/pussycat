package com.alipay.pussycat.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml", "/spring/spring-test.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BaseJunit4Test {

}
