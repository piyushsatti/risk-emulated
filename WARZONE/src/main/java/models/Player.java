
package main.java.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Player{
   private static int d_latest_playerID =1;
    private int d_playerId;
    private String d_playerName;
    private int d_reinforcements;
    private ArrayList<Integer> d_assignedCountries;
    private final Deque<Order> d_orderList;


    //Constructors
    public Player(String p_playerName){
        this.d_playerId=d_latest_playerID;
        this.d_playerName= p_playerName;
        this.d_reinforcements= 0;
        this.d_assignedCountries= new ArrayList<Integer>();
        this.d_orderList = new ArrayDeque<Order>();
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


    public Deque<Order> getOrderList() {
        return d_orderList;
    }

    public int getPlayerId() {
        return d_playerId;
    }




    //Setters
    public void setPlayerId(int d_playerId) {
        this.d_playerId = d_playerId;
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


    //addPlayer assuming a list of players to be added is given as parameter
//    public static void addPlayer(ArrayList<String> p_names){
//        for(String l_name: p_names){
//            System.out.println("Name of the Player to be added: "+  l_name);
//            System.out.println("Total list of Player: "+  d_Players.size());
//            for (Player l_player : d_Players) {
//                if (l_player.getName().equals(l_name)) {
//
//                    d_Players.remove(l_player);
//                    System.out.println("Hi" + d_Players);
//                    break;
//                }
//            }
//            d_Players.add(new Player(l_name));
//        }
//    }

    //removePlayer assuming a list of players to be added is given as parameter
//    public static void removePlayer(ArrayList<String> p_names){
//        for(String l_name: p_names){
//            d_Players.removeIf(l_player -> l_player.getName().equals(l_name));
//        }
//    }

    //Printing Details of  all players
//    public void printPlayerDetails(){
//        System.out.print("Name: "+this.d_playerName +" ");
//        System.out.print("Reinforcements: "+this.d_reinforcements+" ");
//        for(HashMap.Entry<Integer, Country> entry : this.d_assignedCountries.entrySet()){
//            System.out.print("Country ID: " +entry.getKey() +" Country Details: "+ entry.getValue() +" ");
//        }
//    }


    //Printing Details of  Single Player
//    public static void displayPlayers(){
//        System.out.print("Number of Players: " + d_Players.size());
//        for(Player l_player: d_Players){
//            l_player.printPlayerDetails();
//        }
//    }

    //Assigning Countries to each player
//    public static void assignCountriesToPlayers() throws FileNotFoundException {
//        map = MapInterface.loadMap("usa8");
//        HashMap<Integer, Country> listOfCountries = map.getD_countries();
//        int total_players = d_Players.size();
//        int playerNumber =0;
//        for(HashMap.Entry<Integer, Country> entry : listOfCountries.entrySet()) {
//            if ((playerNumber % total_players == 0) && playerNumber != 0) {
//                playerNumber =0;
//            }
//            Integer key = entry.getKey();
//            Country value = entry.getValue();
//            Player p = d_Players.get(playerNumber);
//            p.setAssignedCountries(key, value);
//
//            playerNumber++;
//        }
//
//        for(Player l_player: d_Players){
//            l_player.printPlayerDetails();
//            System.out.println(" ");
//        }
//        for(Player l_player: d_Players){
//            System.out.println("Number of Countries: " + l_player.getAssignedCountries().size());
//            System.out.println(" ");
//        }
//
//    }

    //Issue order function to issue orders in round robin manner.
    //NOTE TO PIYUSH: need to get the value of l_numberTobeDeployed and l_countryID inside issue_order from user
    public void issue_order(int l_numberTobeDeployed, int l_countryID) {

        //issue order view gives l_numberTobeDeployed,l_country
       // l_country is a string we need l_countryID
        if(this.d_assignedCountries.contains(l_countryID)){
            System.out.println("You own the Country: "+this.d_playerName);
           // System.out.println("The country is: "+ this.d_assignedCountries.get(l_countryID).getD_countryName());
        }else{
            System.out.println("Cannot Deploy Troops here you don't own it." + this.d_playerName);
            return;
        }
        if(this.d_reinforcements<l_numberTobeDeployed){
            System.out.println("Not enough Troops! for "+this.d_playerName);
            return;
        }else{
            Order order = new Order(this.getPlayerId(),l_countryID,l_numberTobeDeployed);
            System.out.println("Order Created for Player: "+this.d_playerName);
            this.d_orderList.add(order);
            this.setReinforcements(this.getReinforcements()-l_numberTobeDeployed);
            System.out.println("Player "+this.d_playerName+" Updated Reinforcements: "+this.getReinforcements());

        }
        System.out.println("The order list is:");
        for(Order order: d_orderList){
            //order.printOrder();
        }


    }



    //return next order in the order list.
    public Order next_order(){
        return this.d_orderList.poll();
    }



}
