package controller.middlewarecommands;

import controller.MapInterface;
import controller.middleware.commands.IssueOrderCommands;
import controller.middleware.commands.MapEditorCommands;
import controller.middleware.commands.StartupCommands;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MapEditorCommandsTest.class, StartupCommandsTest.class,IssueOrderCommandsTest.class})
public class CommandsTestSuite {
}