package controller.middleware.commands;

import controller.GameEngine;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;
import models.orders.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The IssueOrderCommands class represents commands related to issuing orders in the game.
 * It extends the abstract class Commands.
 */
public class IssueOrderCommands extends Commands{
    Player p;

    /**
     * Gets the current value of the flag.
     *
     * @return True if the flag is set, false otherwise.
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag.
     *
     * @param flag The value to set the flag.
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    boolean flag = false;
    /**
     * Constructs an IssueOrderCommands object with the given command string and player.
     *
     * @param p_command  The command string.
     * @param p_player   The player associated with the commands.
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
     * Validates the command format against predefined patterns.
     *
     * @return True if the command format is valid, false otherwise.
     */
    @Override
    public boolean validateCommand()
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
        return matcher.matches();
    }
    /**
     * Displays the game map if it's loaded, otherwise, renders an error message.
     *
     * @param ge The GameEngine object containing the game state.
     */
    private void showmapIssueOrder(GameEngine ge){

        if(ge.d_worldmap == null){
            ge.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
        }else{
            ge.d_renderer.showMap(true);
        }
    }

    /**
     * Executes the command using the provided GameEngine.
     *
     * @param ge The GameEngine object used to execute the command.
     * @throws CountryDoesNotExistException If a country referenced in the command does not exist.
     * @throws InvalidCommandException     If the command is invalid.
     */
    @Override
    public void execute(GameEngine ge) throws  CountryDoesNotExistException, InvalidCommandException {

        if (!this.validateCommandName()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        } else if (!this.validateCommand()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }
        String[] l_command = d_command.trim().split("\\s+");


        switch (l_command[0]) {
            case "deploy":
                int l_countryID = ge.d_worldmap.getCountryID(l_command[1]);
                int l_numberTobeDeployed = Integer.parseInt(l_command[2]);
                Order order = new Deploy(p, p.getName(), p.getPlayerId(), l_countryID, l_numberTobeDeployed, ge);
                if (order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setReinforcements(p.getReinforcements() - l_numberTobeDeployed);
                    p.setOrderSuccess(true);
                    System.out.println("Command Issued!");

                }
                break;

            case "advance":
                int l_fromCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                int l_toCountryID = ge.d_worldmap.getCountryID(l_command[2]);
                l_numberTobeDeployed = Integer.parseInt(l_command[3]);
                Player l_targetPlayer = null;
                for(Player player : ge.d_players){
                    if(player.getAssignedCountries().contains(l_toCountryID)){
                        l_targetPlayer = player;
                    }
                }
                order = new Advance(p, l_targetPlayer,p.getName(),p.getPlayerId(), l_fromCountryID, l_toCountryID, l_numberTobeDeployed, ge);
                if (order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    System.out.println("Command Issued!");

                }
                break;

            case "airlift":
                if (p.containsCard("airlift")) {
                    l_fromCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                    l_toCountryID = ge.d_worldmap.getCountryID(l_command[2]);
                    l_numberTobeDeployed = Integer.parseInt(l_command[3]);
                    order = new Airlift(p, p.getName(), p.getPlayerId(), l_fromCountryID, l_toCountryID, l_numberTobeDeployed, ge);
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("airlift");
                        p.setOrderSuccess(true);
                        System.out.println("Command Issued!");

                    }
                } else {
                    ge.d_renderer.renderMessage("You don't own airlift card");
                }
                break;

            case "bomb":
                if (p.containsCard("bomb")) {
                    int l_bombCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                    l_targetPlayer = null;
                    for(Player player : ge.d_players){
                        if(player.getAssignedCountries().contains(l_bombCountryID)){
                            l_targetPlayer = player;
                        }
                    }
                    order = new Bomb(p, l_targetPlayer, p.getPlayerId(), p.getName(), l_bombCountryID, ge);
                    if (order.validateCommand()) {
                        System.out.println("Order Successful");
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("bomb");
                        System.out.println("Command Issued!");
                        p.setOrderSuccess(true);

                    }
                } else {
                    ge.d_renderer.renderMessage("You don't own bomb card");
                }
                break;


            case "blockade":
                if (p.containsCard("blockade")) {
                    int l_blockadeCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                    order = new Blockade(p, p.getPlayerId(), p.getName(), l_blockadeCountryID, ge);
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("blockade");
                        p.setOrderSuccess(true);
                        System.out.println("Command Issued!");

                    }
                } else {
                    ge.d_renderer.renderMessage("You don't own blockade card");
                }
                break;

            case "negotiate":
                if (p.containsCard("negotiate")) {
                    String l_targetPlayerID = l_command[1];
                    Player targetPlayer = null;

                    for (Player player : ge.d_players) {
                        if (player.getName().equals(l_targetPlayerID)) {
                            targetPlayer = player;
                        }
                    }
                    order = new Diplomacy(p, targetPlayer, p.getPlayerId(), p.getName());
                    if (order.validateCommand()) {
                        p.addOrder(order);
                        p.issue_order();
                        p.removeCard("negotiate");
                        p.setOrderSuccess(true);
                        System.out.println("Command Issued!");

                    }
                } else {
                    ge.d_renderer.renderMessage("You don't own negotiate card");
                }
                break;
            case "done":
                if (p.getReinforcements() !=0) {
                    ge.d_renderer.renderMessage("Need to deploy all troops!");

                }else {
                 p.setFinishedIssueOrder(true);
                 p.setOrderSuccess(true);
                }
                break;
            case "showmap":
                showmapIssueOrder(ge);
                break;

        }
    }
}



