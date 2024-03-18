package models.orders;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The OrdersTestSuite class serves as a test suite for running all tests related to orders in Risk.
 * It includes tests for Advance, Airlift, Blockade, Bomb, and Diplomacy orders.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AdvanceTest.class, AirliftTest.class, BlockadeTest.class, BombTest.class, DiplomacyTest.class})
public class OrdersTestSuite {
}
