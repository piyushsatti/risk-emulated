package controller.middleware.commands;

import controller.GameEngine;
import controller.statepattern.Phase;
import controller.statepattern.gameplay.IssueOrder;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.LogEntryBuffer;
import models.Player;
import models.orders.*;
import view.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssueOrderCommands extends Commands{
    Player p;

    public boolean isFlag() {
        return flag;
    }
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    Logger lw = new Logger(logEntryBuffer);

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    boolean flag = false;
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
        return matcher.matches() && (p_gameEngine.getCurerentState().getClass() == IssueOrder.class);
    }
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

    @Override
    public void execute(GameEngine p_gameEngine) throws  CountryDoesNotExistException, InvalidCommandException {

        if (!this.validateCommandName()) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        } else if (!this.validateCommand(p_gameEngine)) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }
        String[] l_command = d_command.trim().split("\\s+");


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
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Airlift Order: Order Not executed as "+
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
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued Bomb Order:"+d_command);

                    }
                } else {
                    p_gameEngine.d_renderer.renderMessage("You don't own bomb card");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Bomb Order Not executed as "+
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
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Blockade Order Not executed as "+
                            p.getName()+" does not own a Blockade card");
                }
                break;

            case "negotiate":
                if (p.containsCard("negotiate")) {
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
                        p.removeCard("negotiate");
                        p.setOrderSuccess(true);
                        p_gameEngine.d_renderer.renderMessage("Command Issued!");
                        logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued  Negotiate Order:"+d_command);

                    }
                } else {
                    p_gameEngine.d_renderer.renderMessage("You don't own negotiate card");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Negotiate Order Not executed as "+
                            p.getName()+" does not own a Negotiate card");
                }
                break;
            case "done":
                logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued Order:"+d_command);
                if (p.getReinforcements() !=0) {
                    p_gameEngine.d_renderer.renderMessage("Need to deploy all troops!");
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" Done Order Not executed as "
                            +p.getName()+" needs to deploy all troops");

                }else {
                    p.setFinishedIssueOrder(true);
                    logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Player has finished issuing all orders:"+d_command);

                    p.setOrderSuccess(true);
                }
                break;
            case "showmap":
                logEntryBuffer.setString("Issue Order Phase: \n"+" Player Name:"+p.getName()+" || Issued Showmap Order:"+d_command);
                showmapIssueOrder(p_gameEngine);
                break;


        }
    }
}



