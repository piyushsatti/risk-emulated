package controller.middleware.commands;

import controller.GameEngine;
import controller.statepattern.Phase;
import controller.statepattern.gameplay.IssueOrder;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.LogEntryBuffer;
import models.Player;
import view.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The IssueOrderCommands class represents commands related to issuing orders in a game.
 * It extends the Commands class and provides functionality for various order types.
 */
public class IssueOrderCommands extends Commands {
    /**
     * The player associated with these commands.
     */
    Player d_player;

    /**
     * The current phase of game.
     */
    String d_currPhase;

    /**
     * Checks if a certain condition is met.
     *
     * @return True if the condition is met, otherwise false.
     */
    public boolean isD_flag() {
        return d_flag;
    }

    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger d_lw = new Logger(d_logEntryBuffer);

    /**
     * Sets the flag indicating a condition.
     *
     * @param p_flag The flag value to set.
     */
    public void setD_flag(boolean p_flag) {
        this.d_flag = p_flag;
    }

    /**
     * set flag to false by default.
     */
    boolean d_flag = false;


    /**
     * Constructs a new IssueOrderCommands object with the given command and player.
     *
     * @param p_command The command string.
     * @param p_player  The player associated with these commands.
     */
    public IssueOrderCommands(String p_command, Player p_player) {
        super(p_command, new String[]{
                "deploy",
                "advance",
                "bomb",
                "blockade",
                "airlift",
                "negotiate",
                "done",
                "showmap",
                "savegame",
                "loadgame"
        });
        d_player = p_player;
    }

    /**
     * Validates the command based on the current game state.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return True if the command is valid according to the game rules and current state, otherwise false.
     */
    @Override
    public boolean validateCommand(GameEngine p_gameEngine) {

        Pattern pattern = Pattern.compile(
                "^deploy\\s+\\w+\\s+\\d+(\\s)*$|" +
                        "^advance\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$|" +
                        "^bomb\\s+\\w+\\s*(\\s)*$|" +
                        "^blockade\\s+\\w+\\s*(\\s)*$|" +
                        "^negotiate\\s+\\w+\\s*(\\s)*$|" +
                        "^done(\\s)*$|" +
                        "^showmap(\\s)*$|" +
                        "^airlift\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$|" +
                        "^savegame\\s+\\w+\\s*$|" +
                        "^loadgame\\s+\\w+\\s*$|"
        );
        Matcher matcher = pattern.matcher(d_command);
        //returning true of pattern matches and the current state is Issue Order
        return matcher.matches() && (p_gameEngine.getCurrentState().getClass() == IssueOrder.class);
    }

    /**
     * Displays the map during the Issue Order phase of the game.
     *
     * @param ge The game engine containing the game state and resources.
     */
    private void showmapIssueOrder(GameEngine ge) {

        if (ge.d_worldmap == null) {
            ge.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
            d_logEntryBuffer.setString("Issue Order Phase: \n" + " Player Name:" + d_player.getName() + " ShowMap Order Not executed as " +
                    "there is no map loaded " + d_player.getName() + " needs to load a map first");
        } else {
            ge.d_renderer.showMap(true);
            d_logEntryBuffer.setString("Issue Order Phase: \n" + " Player Name:" + d_player.getName() + " ShowMap Order " + d_command);
        }
    }

    /**
     * Retrieves the name of the current phase of the game.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return The name of the current game phase.
     */
    public String getCurrentPhase(GameEngine p_gameEngine) {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index + 1);
    }

    /**
     * Executes the command issued by the player in the game. first it is checked if the command belongs to the
     * same current phase, format is checked and then based on the main command name like deploy,advance,etc.
     * corresponding methods to process the execution of those commands are called.
     *
     * @param p_gameEngine The game engine managing the game state and resources.
     * @throws CountryDoesNotExistException If the specified country does not exist.
     * @throws InvalidCommandException      If the command is invalid or not recognized.
     */
    @Override
    public void execute(GameEngine p_gameEngine) throws CountryDoesNotExistException, InvalidCommandException {
        if (d_player.getStrategy().getStrategyName() == "Human") { //if this is a human player
            //validation of command, whether it is valid for the same phase or not, whether the format is correct or not
            if (!this.validateCommandName()) {
                p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command for this phase");
                return;
            } else if (!this.validateCommand(p_gameEngine)) {
                p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
                return;
            }

            String[] l_command = d_command.trim().split("\\s+");// Split the command into parts

            d_currPhase = getCurrentPhase(p_gameEngine);
            d_player.setCurrentPhase(d_currPhase);
            d_player.setCommands(d_command);
            //issue the order
            d_player.issue_order();

        } else {
            d_currPhase = getCurrentPhase(p_gameEngine);
            d_player.setCurrentPhase(d_currPhase);
            d_player.issue_order();
            d_player.setFinishedIssueOrder(true);
        }

    }
}


