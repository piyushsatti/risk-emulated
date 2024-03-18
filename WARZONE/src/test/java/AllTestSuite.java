import controller.ControllerTestSuite;
import models.ModelsTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Javadoc for the AllTestSuite class.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ControllerTestSuite.class, ModelsTestSuite.class})
public class AllTestSuite {
}