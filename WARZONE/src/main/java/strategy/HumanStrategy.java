package strategy;

import controller.GameEngine;
import helpers.exceptions.InvalidCommandException;
import models.LogEntryBuffer;
import models.Player;
import models.orders.*;
import models.worldmap.Country;

/**
 * Represents a strategy where a human player manually selects actions.
 */
public class HumanStrategy implements Strategy{
    /**
     * The player associated with this strategy.
     */
    private Player d_player;
    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    /**
     * The game engine associated with this strategy.
     */
    GameEngine d_gameEngine;
    /**
     * Gets the source country for the human player's action.
     * Since this is a human strategy, the source country is not determined programmatically.
     * @return Always returns 0 since the source country is not determined programmatically.
     */
    @Override
    public int getSourceCountry() {
        return 0;
    }

    public String getStrategyName() {
        return d_strategyName;
    }

    public void setStrategyName(String d_strategyName) {
        this.d_strategyName = d_strategyName;
    }

    private String d_strategyName;

    /**
     * Gets the target country for the human player's action.
     * Since this is a human strategy, the target country is not determined programmatically.
     * @param p_sourceCountryId The ID of the source country.
     * @return Always returns 0 since the target country is not determined programmatically.
     */
    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return 0;
    }

    /**
     * Creates an order based on the human player's actions.
     * Since this is a human strategy, the order is created manually by the player.
     * @return Always returns null since the order is created manually by the player.
     */
    @Override
    public Order createOrder() throws InvalidCommandException {
        /**
         *      now, based on the main command name like deploy,advance,etc.
         *      corresponding methods to process the execution of those commands are called.
         */

        String l_command = d_player.getCommands();
        String[] l_command_array = l_command.trim().split("\\s+");
        switch (l_command_array[0]) {
            case "deploy":
                int l_countryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[1]);
                int l_numberTobeDeployed = Integer.parseInt(l_command_array[2]);
                Order order = new Deploy(d_player, d_player.getName(), d_player.getPlayerId(), l_countryID, l_numberTobeDeployed, d_gameEngine);
                if (order.validateCommand()) { //if the order can be executed based on the command execution scenario/logic, call execution methods
                    d_player.addOrder(order);
                    d_player.setReinforcements(d_player.getReinforcements() - l_numberTobeDeployed);
                    d_player.setOrderSuccess(true);
                    d_gameEngine.d_renderer.renderMessage("Command Issued!");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" || Issued Deploy Order:"+l_command_array); //setting log message
                    return order;
                }
                break;

            case "advance":
                int l_fromCountryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[1]);
                int l_toCountryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[2]);
                l_numberTobeDeployed = Integer.parseInt(l_command_array[3]);
                Player l_targetPlayer = null;
                //checking if the player given in the command exists in the player list and then setting target player
                for(Player player : d_gameEngine.d_players){
                    if(player.getAssignedCountries().contains(l_toCountryID)){
                        l_targetPlayer = player;
                    }
                }
                order = new Advance(d_player, l_targetPlayer,d_player.getName(),d_player.getPlayerId(), l_fromCountryID, l_toCountryID, l_numberTobeDeployed, d_gameEngine);
                if (order.validateCommand()) { //if the order can be executed based on the command execution scenario/logic, call execution methods
                    d_player.addOrder(order);
                    d_player.setOrderSuccess(true);
                    d_gameEngine.d_renderer.renderMessage("Command Issued!");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" || Issued Advance Order:"+l_command); //setting log message
                    return order;
                }
                break;

            case "airlift":
                if (d_player.containsCard("airlift")) {
                    l_fromCountryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[1]);
                    l_toCountryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[2]);
                    l_numberTobeDeployed = Integer.parseInt(l_command_array[3]);
                    order = new Airlift(d_player, d_player.getName(), d_player.getPlayerId(), l_fromCountryID, l_toCountryID, l_numberTobeDeployed, d_gameEngine);
                    if (order.validateCommand()) { //if the order can be executed based on the command execution scenario/logic, call execution methods
                        d_player.addOrder(order);
                        d_player.removeCard("airlift");
                        d_player.setOrderSuccess(true);
                        d_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" || Issued Airlift Order:"+l_command); //setting log message
                        return order;
                    }
                } else {
                    d_gameEngine.d_renderer.renderMessage("You don't own airlift card");
                    logEntryBuffer.setString("Phase :"+ d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Airlift Order: Order Not executed as "+
                            d_player.getName()+" does not own an airlift card");
                }
                break;

            case "bomb":
                if (d_player.containsCard("bomb")) {
                    int l_bombCountryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[1]);
                    l_targetPlayer = null;
                    //checking if the player given in the command exists in the player list and then setting target player
                    for(Player player : d_gameEngine.d_players){
                        if(player.getAssignedCountries().contains(l_bombCountryID)){
                            l_targetPlayer = player;
                        }
                    }
                    order = new Bomb(d_player, l_targetPlayer, d_player.getPlayerId(), d_player.getName(), l_bombCountryID, d_gameEngine);
                    if (order.validateCommand()) { //if the order can be executed based on the command execution scenario/logic, call execution methods
                        d_player.addOrder(order);
                        d_player.removeCard("bomb");
                        d_gameEngine.d_renderer.renderMessage("Command Issued!");
                        d_player.setOrderSuccess(true);
                        logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Issued Bomb Order:"+l_command); //setting log message
                        return order;
                    }
                } else {
                    d_gameEngine.d_renderer.renderMessage("You don't own bomb card");
                    logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Bomb Order Not executed as "+
                            d_player.getName()+" does not own a Bomb card");
                }
                break;


            case "blockade":
                if (d_player.containsCard("blockade")) {
                    int l_blockadeCountryID = d_gameEngine.d_worldmap.getCountryID(l_command_array[1]);
                    order = new Blockade(d_player, d_player.getPlayerId(), d_player.getName(), l_blockadeCountryID, d_gameEngine);
                    if (order.validateCommand()) { //if the order can be executed based on the command execution scenario/logic, call execution methods
                        d_player.addOrder(order);
                        d_player.removeCard("blockade");
                        d_player.setOrderSuccess(true);
                        d_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" || Issued  Blockade Order:"+l_command); //setting log message
                        return order;
                    }
                } else {
                    d_gameEngine.d_renderer.renderMessage("You don't own blockade card");
                    logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Blockade Order Not executed as "+
                            d_player.getName()+" does not own a Blockade card"); //setting log message
                }
                break;

            case "negotiate":
                if (d_player.containsCard("diplomacy")) {
                    String l_targetPlayerID = l_command_array[1];
                    Player targetPlayer = null;
                    //checking if the player given in the command exists in the player list and then setting target player
                    for (Player player : d_gameEngine.d_players) {
                        if (player.getName().equals(l_targetPlayerID)) {
                            targetPlayer = player;
                        }
                    }
                    order = new Diplomacy(d_player, targetPlayer, d_player.getPlayerId(), d_player.getName());
                    if (order.validateCommand()) { //if the order can be executed based on the command execution scenario/logic, call execution methods
                        d_player.addOrder(order);
                        d_player.removeCard("diplomacy");
                        d_player.setOrderSuccess(true);
                        d_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" || Issued  Negotiate Order:"+l_command); //setting log message
                        return order;
                    }
                } else {
                    d_gameEngine.d_renderer.renderMessage("You don't own negotiate card");
                    logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Negotiate Order Not executed as "+
                            d_player.getName()+" does not own a Negotiate card"); //setting log message
                }
                break;
            case "done":
                logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Issued Order:"+l_command);//setting log message
                if (d_player.getReinforcements() !=0) {
                    d_gameEngine.d_renderer.renderMessage("Need to deploy all troops!");
                    logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" Done Order Not executed as "
                            +d_player.getName()+" needs to deploy all troops");

                }else {
                    d_player.setFinishedIssueOrder(true);
                    logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Player has finished issuing all orders:"+l_command); //setting log message

                    d_player.setOrderSuccess(true);
                }
                break;
            case "showmap":
                logEntryBuffer.setString("Phase :"+d_player.getCurrentPhase()+"\n"+" Player Name:"+d_player.getName()+" || Issued Showmap Order:"+l_command); //setting log message
                if(this.d_gameEngine.d_worldmap == null){
                    this.d_gameEngine.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" ShowMap Order Not executed as " +
                            "there is no map loaded "+d_player.getName()+" needs to load a map first");
                }else{
                    this.d_gameEngine.d_renderer.showMap(true);
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+d_player.getName()+" ShowMap Order " +l_command);
                }
                return null;


        }
        return null;
    }

    /**
     * Constructs a HumanStrategy object with the given player and game engine.
     * @param p_player The player associated with this strategy.
     * @param p_gameEngine The game engine associated with this strategy.
     */
    public HumanStrategy(Player p_player, GameEngine p_gameEngine)
    {
         d_player = p_player;
        d_gameEngine = p_gameEngine;
        d_strategyName = "Human";
    }
}
