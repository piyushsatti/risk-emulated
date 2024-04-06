package controller;

import controller.middleware.commands.CommandsTestSuite;
import controller.statepattern.gameplay.GamePlayTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Javadoc for the ControllerTestSuite class.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MapInterfaceTest.class, CommandsTestSuite.class, GamePlayTestSuite.class})
public class ControllerTestSuite {
}

