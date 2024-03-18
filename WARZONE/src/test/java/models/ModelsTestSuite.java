package models;

import models.orders.OrdersTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({OrdersTestSuite.class})
public class ModelsTestSuite {
}
