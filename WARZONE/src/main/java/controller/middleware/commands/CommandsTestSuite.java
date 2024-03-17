package controller.middleware.commands;

import controller.MapInterface;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for testing  MapEditorCommands, MapInterface, IssueOrderCommands, and StartupCommands.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MapEditorCommands.class, MapInterface.class, IssueOrderCommands.class, StartupCommands.class})
public class CommandsTestSuite {
}