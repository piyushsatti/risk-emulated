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

public class IssueOrderCommands extends Commands{
    Player p;

    public boolean isFlag() {
        return flag;
    }

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
                "negotiate"
        });
        p = p_player;
    }
    @Override
    public boolean validateCommand()
    {
        Pattern pattern = Pattern.compile(
                "^deploy\\s+\\w+\\s+\\d+(\\s)*$|"+
                        "^advance\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$|"+
                        "^bomb\\s+\\w+\\s*(\\s)*$|"+
                        "^blockade\\s+\\w+\\s*(\\s)*$|"+
                        "^negotiate\\s+\\w+\\s*(\\s)*$|"+
                        "^airlift\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    @Override
    public void execute(GameEngine ge) throws  CountryDoesNotExistException, InvalidCommandException {

        if (!this.validateCommandName()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }
        else if(!this.validateCommand()){
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }
        String[] l_command = d_command.trim().split("\\s+");


        switch (l_command[0])
        {
            case "deploy":
                int l_countryID = ge.d_worldmap.getCountryID(l_command[1]);
                int l_numberTobeDeployed = Integer.parseInt(l_command[2]);
                Order order = new Deploy(p, p.getName(), p.getPlayerId(), l_countryID, l_numberTobeDeployed,ge);
                if(order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setReinforcements( p.getReinforcements() - l_numberTobeDeployed);
                    p.setOrderSuccess(true);
                    break;
                }

            case "advance":

                int l_fromCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                int l_toCountryID = ge.d_worldmap.getCountryID(l_command[2]);
                l_numberTobeDeployed = Integer.parseInt(l_command[3]);
                //Source player will call this so no need for that parameter
                order = new Advance(p,p.getName(),p.getPlayerId(), l_fromCountryID,l_toCountryID,l_numberTobeDeployed,ge);
                if(order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    break;
                }


            case "airlift":

                l_fromCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                l_toCountryID = ge.d_worldmap.getCountryID(l_command[2]);
                l_numberTobeDeployed = Integer.parseInt(l_command[3]);
                order = new Airlift(p,p.getName(),p.getPlayerId(), l_fromCountryID,l_toCountryID,l_numberTobeDeployed,ge);
                if(order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    break;
                }

            case "bomb":

                int l_bombCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                order = new Bomb(p,p.getPlayerId(),p.getName(), l_bombCountryID,ge);
                if(order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    break;
                }


            case "blockade":

                int l_blockadeCountryID = ge.d_worldmap.getCountryID(l_command[1]);
                order = new Blockade(p,p.getPlayerId(),p.getName(), l_blockadeCountryID,ge);
                if(order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    break;
                }

            case "negotiate":
                String l_targetPlayerID = l_command[1];
                Player targetPlayer = null;

                for(Player player: ge.d_players){
                    if(player.getName().equals(l_targetPlayerID)){
                        targetPlayer = player;
                    }
                }
                order = new Diplomacy(p, targetPlayer,p.getPlayerId(), p.getName());
                if(order.validateCommand()) {
                    p.addOrder(order);
                    p.issue_order();
                    p.setOrderSuccess(true);
                    break;
                }

        }



//
//        int l_countryID = ge.d_worldmap.getCountryID(l_command[1]);
//
//        int l_numberTobeDeployed = Integer.parseInt(l_command[2]);
//
//
//        if (l_countryID > 0 && l_numberTobeDeployed <= p.getReinforcements()) {
//
//            if (p.getAssignedCountries().contains(l_countryID)) {
//
//                Order order = new Deploy(p, p.getName(), p.getPlayerId(), l_countryID, l_numberTobeDeployed, ge);
//
//                ge.d_renderer.renderMessage("Order Created. Here are the Details: Deploy " + l_numberTobeDeployed + " on " + ge.d_worldmap.getCountry(l_countryID).getCountryName() + " by Player: " + p.getName());
//
//                p.addOrder(order);
//                p.issue_order();
//
//                p.setReinforcements(p.getReinforcements() - l_numberTobeDeployed);
//
//                ge.d_renderer.renderMessage("Player: " + p.getName() + " Reinforcements Available: " + p.getReinforcements());
//
//                return;
//
//            } else {
//
//                ge.d_renderer.renderMessage("You (" + p.getName() + ") Cannot Deploy Troops here you don't own it.");
//                throw new InvalidCommandException("Invalid Command!!! You don't own the country");
//
//            }

        }
    }



