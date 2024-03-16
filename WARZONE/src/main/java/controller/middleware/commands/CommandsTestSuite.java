package controller.middleware.commands;

import controller.MapInterface;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;
@RunWith(Suite.class)
@Suite.SuiteClasses({MapEditorCommands.class, MapInterface.class, OrderExecutionCommands.class, StartupCommands.class})
public class CommandsTestSuite {
}