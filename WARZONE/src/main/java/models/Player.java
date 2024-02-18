
package main.java.models;

import main.java.controller.GameEngine;
import main.java.controller.commands.CommandValidator;
import main.java.utils.exceptions.InvalidCommandException;
import main.java.views.TerminalRenderer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Player{
   private static int d_latest_playerID =1;
    private final int d_playerId;
    private String d_playerName;
    private int d_reinforcements;
    private final ArrayList<Integer> d_assignedCountries;
    private final Deque<Order> d_orderList = new ArrayDeque<>();


    //Constructors
    public Player(String p_playerName){
        this.d_playerId=d_latest_playerID;
        this.d_playerName= p_playerName;
        this.d_reinforcements= 0;
        this.d_assignedCountries = new ArrayList<>();
        d_latest_playerID++;
    }


    //Getters

    public String getName() {
        return d_playerName;
    }
    public int getReinforcements() {
        return d_reinforcements;
    }

    public ArrayList<Integer> getAssignedCountries() {
        return this.d_assignedCountries;
    }
    public static Player getPlayerFromList(ArrayList<Player> p_listOfPlayers, int p_playerID){
        for (Player l_player : p_listOfPlayers) {
            if (l_player.getPlayerId() == p_playerID) {
                return l_player;
            }
        }
        return null;
    }

    public Deque<Order> getOrderList() {
        return this.d_orderList;
    }

    public int getPlayerId() {
        return this.d_playerId;
    }

    public void setName(String p_name) {
        this.d_playerName= p_name;
    }
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements= p_reinforcements;
    }

    public void setAssignedCountries(Integer p_countryID) {
        this.d_assignedCountries.add(p_countryID);
    }


    public void issue_order() throws InvalidCommandException {

        while(true) {
            System.out.println("Player: " + this.d_playerName + " Reinforcements Available: " + this.getReinforcements());
            String command = TerminalRenderer.issueOrderview(this.getName());
            CommandValidator commandValidator = new CommandValidator();
            commandValidator.addCommand(command);

            String[] arr = command.split(" ");

            int l_countryID = GameEngine.CURRENT_MAP.getCountryID(arr[1]);
            int l_numberTobeDeployed = Integer.parseInt(arr[2]);

            if (l_countryID > 0 && l_numberTobeDeployed <= this.getReinforcements()) {

                if (this.d_assignedCountries.contains(l_countryID)) {
                    Order order = new Order(this.getName(),this.getPlayerId(), l_countryID, l_numberTobeDeployed);
                    System.out.println("Order Created. Here are the Details: Deploy " +l_numberTobeDeployed+ " on "+ GameEngine.CURRENT_MAP.getCountry(l_countryID).getD_countryName() + " by Player: " + this.d_playerName);
                    this.d_orderList.add(order);
                    this.setReinforcements(this.getReinforcements() - l_numberTobeDeployed);
                    System.out.println("Player: " + this.d_playerName + " Reinforcements Available: " + this.getReinforcements());
                    return;


                } else {
                    System.out.println("You (" + this.d_playerName + ") Cannot Deploy Troops here you don't own it.");
                }
            } else {
                if( l_numberTobeDeployed > this.getReinforcements()){
                    System.out.println("You (" +this.d_playerName+") don't have enough troops for this deploy order");
            }else{
                    System.out.println("Invalid Command! The command should be in following format: deploy countryID num");
                }
            }
        }
    }



    public Order next_order(){
        return this.d_orderList.poll();
    }



}
