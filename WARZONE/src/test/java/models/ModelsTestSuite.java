package models;

import models.orders.OrdersTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The ModelsTestSuite class serves as a test suite for running all tests related to the models in the Risk game.
 * It includes the OrdersTestSuite, which encompasses tests for various orders.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({OrdersTestSuite.class})
public class ModelsTestSuite {
}
