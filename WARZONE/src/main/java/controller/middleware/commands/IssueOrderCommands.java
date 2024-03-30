package controller.middleware.commands;

import controller.GameEngine;
import controller.statepattern.Phase;
import controller.statepattern.gameplay.IssueOrder;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.LogEntryBuffer;
import models.Player;
import models.orders.*;
import view.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The IssueOrderCommands class represents commands related to issuing orders in a game.
 * It extends the Commands class and provides functionality for various order types.
 */
public class IssueOrderCommands extends Commands{
    /** The player associated with these commands. */
    Player p;

    /** The current phase of game. */
    String l_currPhase;

    /**
     * Checks if a certain condition is met.
     *
     * @return True if the condition is met, otherwise false.
     */
    public boolean isFlag() {
        return flag;
    }
    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger lw = new Logger(logEntryBuffer);

    /**
     * Sets the flag indicating a condition.
     *
     * @param flag The flag value to set.
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * set flag to false by default.
     */
    boolean flag = false;
    /**
     * Constructs a new IssueOrderCommands object with the given command and player.
     *
     * @param p_command The command string.
     * @param p_player The player associated with these commands.
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
                "showmap"
        });
        p = p_player;
    }
    /**
     * Validates the command based on the current game state.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return True if the command is valid according to the game rules and current state, otherwise false.
     */
    @Override
    public boolean validateCommand(GameEngine p_gameEngine)
    {

        Pattern pattern = Pattern.compile(
                "^deploy\\s+\\w+\\s+\\d+(\\s)*$|"+
                        "^advance\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$|"+
                        "^bomb\\s+\\w+\\s*(\\s)*$|"+
                        "^blockade\\s+\\w+\\s*(\\s)*$|"+
                        "^negotiate\\s+\\w+\\s*(\\s)*$|"+
                        "^done(\\s)*$|" +
                        "^showmap(\\s)*$|" +
                        "^airlift\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$"

        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches() && (p_gameEngine.getCurrentState().getClass() == IssueOrder.class);
    }
    /**
     * Displays the map during the Issue Order phase of the game.
     *
     * @param ge The game engine containing the game state and resources.
     */
    private void showmapIssueOrder(GameEngine ge){

        if(ge.d_worldmap == null){
            ge.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
            logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" ShowMap Order Not executed as " +
                    "there is no map loaded "+p.getName()+" needs to load a map first");
        }else{
            ge.d_renderer.showMap(true);
            logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" ShowMap Order " +d_command);
        }
    }

    /**
     * Retrieves the name of the current phase of the game.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return The name of the current game phase.
     */
    public String getCurrentPhase(GameEngine p_gameEngine)
    {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index+1);
    }

    /**
     * Executes the command issued by the player in the game.
     *
     * @param p_gameEngine The game engine managing the game state and resources.
     * @throws CountryDoesNotExistException If the specified country does not exist.
     * @throws InvalidCommandException If the command is invalid or not recognized.
     */
    @Override
    public void execute(GameEngine p_gameEngine) throws  CountryDoesNotExistException, InvalidCommandException {

        // Validate command name
        if (!this.validateCommandName()) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        } else if (!this.validateCommand(p_gameEngine)) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");      // Split the command into parts

        l_currPhase = getCurrentPhase(p_gameEngine);

        switch (l_command[0]) {
            case "deploy":
                int l_countryID = p_gameEngine.d_worldmap.getCountryID(l_command[1]);
                int l_numberTobeDeployed = Integer.parseInt(l_command[2]);
                Order order = new Deploy(p, p.getName(), p.getPlayerId(), l_countryID, l_numberTobeDeployed, p_gameEngine);
                if (order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setReinforcements(p.getReinforcements() - l_numberTobeDeployed);
                    p.setOrderSuccess(true);
                    p_gameEngine.d_renderer.renderMessage("Command Issued!");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued Deploy Order:"+d_command);
                }
                break;

            case "advance":
                int l_fromCountryID = p_gameEngine.d_worldmap.getCountryID(l_command[1]);
                int l_toCountryID = p_gameEngine.d_worldmap.getCountryID(l_command[2]);
                l_numberTobeDeployed = Integer.parseInt(l_command[3]);
                Player l_targetPlayer = null;
                for(Player player : p_gameEngine.d_players){
                    if(player.getAssignedCountries().contains(l_toCountryID)){
                        l_targetPlayer = player;
                    }
                }
                order = new Advance(p, l_targetPlayer,p.getName(),p.getPlayerId(), l_fromCountryID, l_toCountryID, l_numberTobeDeployed, p_gameEngine);
                if (order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    p_gameEngine.d_renderer.renderMessage("Command Issued!");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued Advance Order:"+d_command);

                }
                break;

            case "airlift":
                if (p.containsCard("airlift")) {
                    l_fromCountryID = p_gameEngine.d_worldmap.getCountryID(l_command[1]);
                    l_toCountryID = p_gameEngine.d_worldmap.getCountryID(l_command[2]);
                    l_numberTobeDeployed = Integer.parseInt(l_command[3]);
                    order = new Airlift(p, p.getName(), p.getPlayerId(), l_fromCountryID, l_toCountryID, l_numberTobeDeployed, p_gameEngine);
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("airlift");
                        p.setOrderSuccess(true);
                        p_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued Airlift Order:"+d_command);

                    }
                } else {
                    p_gameEngine.d_renderer.renderMessage("You don't own airlift card");
                    logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Airlift Order: Order Not executed as "+
                            p.getName()+" does not own an airlift card");
                }
                break;

            case "bomb":
                if (p.containsCard("bomb")) {
                    int l_bombCountryID = p_gameEngine.d_worldmap.getCountryID(l_command[1]);
                    l_targetPlayer = null;
                    for(Player player : p_gameEngine.d_players){
                        if(player.getAssignedCountries().contains(l_bombCountryID)){
                            l_targetPlayer = player;
                        }
                    }
                    order = new Bomb(p, l_targetPlayer, p.getPlayerId(), p.getName(), l_bombCountryID, p_gameEngine);
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("bomb");
                        p_gameEngine.d_renderer.renderMessage("Command Issued!");
                        p.setOrderSuccess(true);
                        logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Issued Bomb Order:"+d_command);

                    }
                } else {
                    p_gameEngine.d_renderer.renderMessage("You don't own bomb card");
                    logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Bomb Order Not executed as "+
                            p.getName()+" does not own a Bomb card");
                }
                break;


            case "blockade":
                if (p.containsCard("blockade")) {
                    int l_blockadeCountryID = p_gameEngine.d_worldmap.getCountryID(l_command[1]);
                    order = new Blockade(p, p.getPlayerId(), p.getName(), l_blockadeCountryID, p_gameEngine);
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("blockade");
                        p.setOrderSuccess(true);
                        p_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued  Blockade Order:"+d_command);

                    }
                } else {
                    p_gameEngine.d_renderer.renderMessage("You don't own blockade card");
                    logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Blockade Order Not executed as "+
                            p.getName()+" does not own a Blockade card");
                }
                break;

            case "negotiate":
                if (p.containsCard("diplomacy")) {
                    String l_targetPlayerID = l_command[1];
                    Player targetPlayer = null;

                    for (Player player : p_gameEngine.d_players) {
                        if (player.getName().equals(l_targetPlayerID)) {
                            targetPlayer = player;
                        }
                    }
                    order = new Diplomacy(p, targetPlayer, p.getPlayerId(), p.getName());
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("diplomacy");
                        p.setOrderSuccess(true);
                        p_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued  Negotiate Order:"+d_command);

                    }
                } else {
                    p_gameEngine.d_renderer.renderMessage("You don't own negotiate card");
                    logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Negotiate Order Not executed as "+
                            p.getName()+" does not own a Negotiate card");
                }
                break;
            case "done":
                logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Issued Order:"+d_command);
                if (p.getReinforcements() !=0) {
                    p_gameEngine.d_renderer.renderMessage("Need to deploy all troops!");
                    logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" Done Order Not executed as "
                            +p.getName()+" needs to deploy all troops");

                }else {
                    p.setFinishedIssueOrder(true);
                    logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Player has finished issuing all orders:"+d_command);

                    p.setOrderSuccess(true);
                }
                break;
            case "showmap":
                logEntryBuffer.setString("Phase :"+l_currPhase+"\n"+" Player Name:"+p.getName()+" || Issued Showmap Order:"+d_command);
                showmapIssueOrder(p_gameEngine);
                break;


        }
    }
}



