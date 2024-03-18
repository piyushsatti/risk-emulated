package models;

import models.orders.OrdersTestSuite;
import models.worldmap.WorldMapTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({OrdersTestSuite.class, WorldMapTestSuite.class})
public class ModelsTestSuite {
}
