package controller.middleware.commands;

import controller.MapInterface;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MapEditorCommands.class, MapInterface.class, IssueOrderCommands.class, StartupCommands.class})
public class CommandsTestSuite {
}