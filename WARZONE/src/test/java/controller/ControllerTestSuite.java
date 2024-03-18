package controller;

import controller.middlewarecommands.CommandsTestSuite;
import controller.statepattern.gameplay.GamePlayTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MapInterfaceTest.class, CommandsTestSuite.class, GamePlayTestSuite.class})
public class ControllerTestSuite {
}
