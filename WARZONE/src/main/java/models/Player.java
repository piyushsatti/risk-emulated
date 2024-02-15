package models;

import controller.MapInterfaceV1;
import models.map.Country;
import models.map.MapCustom;

import java.util.*;

public class Player{

    private String d_playerName;
    private int d_reinforcements;
    private Map<Integer,Country> d_assignedCountries;

    public static MapCustom getMap() {
        return map;
    }

    private static MapCustom map;

    public Queue<Order> getD_orderList() {
        return d_orderList;
    }

    private  Queue<Order> d_orderList;
    public static List<Player> getD_Players() {
        return d_Players;
    }

    private static List<Player> d_Players = new ArrayList<>();

    //Getters
    public String getName() {
        return d_playerName;
    }
    public int getReinforcements() {
        return d_reinforcements;
    }
    public Map<Integer,Country> getassignedCountries() {
        return this.d_assignedCountries;
    }

    //Setters
    public void setName(String p_name) {
        this.d_playerName= p_name;
    }
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements= p_reinforcements;
    }
    public void setassignedCountries(Integer p_countryID, Country p_assignedCountry) {
        this.d_assignedCountries.put(p_countryID, p_assignedCountry);
    }

    //Constructors
    public Player(String p_playerName){
        this.d_playerName= p_playerName;
        this.d_reinforcements= 0;
        this.d_assignedCountries= new HashMap<>();
        this.d_orderList = new LinkedList<>();
        //d_Players.add(this);
    }


//    public Player(String d_playerName,int d_reinforcements,ArrayList<Country> d_assignedCountries){
//        this.d_playerName= "";
//        this.d_reinforcements= 0;
//        this.d_assignedCountries= d_assignedCountries;
//       }

    //issue order

    //addPlayer assuming a list of players to be added is given as parameter
    public static void addPlayer(ArrayList<String> p_names){
        for(String l_name: p_names){
            System.out.println("Name of the Player to be added: "+  l_name);
            System.out.println("Total list of Player: "+  d_Players.size());
            boolean l_found = false;
            for (Player l_player : d_Players) {
                if (l_player.getName().equals(l_name)) {

                    d_Players.remove(l_player);
                    System.out.println("Hi" + d_Players);
                    l_found = true;
                    break;
                }
            }
            d_Players.add(new Player(l_name));
        }
    }
    //removePlayer assuming a list of players to be added is given as parameter
    public static void removePlayer(ArrayList<String> p_names){
        for(String l_name: p_names){
            for (Player l_player : d_Players) {
                if (l_player.getName().equals(l_name)) {
                    d_Players.remove(l_player);
                }
            }

        }
    }
    public void printPlayerDetails(){
        System.out.print("Name: "+this.d_playerName +" ");
        System.out.print("Reinforcements: "+this.d_reinforcements+" ");
        for(Map.Entry<Integer, Country> entry : this.d_assignedCountries.entrySet()){
            System.out.print("Country ID: " +entry.getKey() +" Country Details: "+ entry.getValue() +" ");
        }
    }
    public static void displayPlayers(){
        System.out.print("Number of Players: " + d_Players.size());
        for(Player l_player: d_Players){
            l_player.printPlayerDetails();
        }
    }
//        public static void addPlayer(ArrayList<String> p_names){
//            for(String l_name: p_names){
//                System.out.println("Name of the Player to be added: "+  l_name);
//                System.out.println("Total list of Player: "+  d_Players.size());
//                boolean l_found = false;
//                for (Player l_player : d_Players) {
//                    if (l_player.getName().equals(l_name)) {
//
//                        d_Players.remove(l_player);
//                        System.out.println("Hi" + d_Players);
//                        l_found = true;
//                        break;
//                    }
//                }
//                d_Players.add(new Player(l_name));
//            }
//        }
//// Create new player objects for each name in the p_names list
//        for (String name : p_names) {
//            if (!addedNames.contains(name)) { // Check if name has already been added
//                Player player = new Player(name);
//                d_Players.add(player);
//                addedNames.add(name); // Add name to the set of added names
//            }
//        }
//
//        for(Player py : d_Players){
//            System.out.println("Current Object Name: "+py.getName());
//        }
//
//    }
    //removePlayer assuming a list of players to be added is given as parameter


    public static void assignCountriesToPlayers(){
        map = MapInterfaceV1.loader("usa8");
        Map<Integer, Country> listOfCountries = map.getD_countries();
        int totalplayers = d_Players.size();
        int playerNumber =0;
        for(Map.Entry<Integer, Country> entry : listOfCountries.entrySet()) {
            if((playerNumber%totalplayers ==0) && playerNumber!=0){
                playerNumber =0;
            }
            Integer key = entry.getKey();
            Country value = entry.getValue();
            Player p = d_Players.get(playerNumber);
            p.setassignedCountries(key,value);

            playerNumber++;
        }

        for(Player l_player: d_Players){
            l_player.printPlayerDetails();
            System.out.println(" ");
        }
        for(Player l_player: d_Players){
            System.out.println("Number of Countries: " + l_player.getassignedCountries().size());
            System.out.println(" ");
        }

    }
    public void issue_order(int l_numberTobeDeployed, int l_countryID) {
//         int l_numberTobeDeployed = 1; //To be acquired from command prompt
//         int l_countryID = 4; //To be acquired from command prompt
        if(this.d_assignedCountries.containsKey(l_countryID)){
            System.out.println("You own the Country: "+this.d_playerName);
            System.out.println("The country is: "+ this.d_assignedCountries.get(l_countryID).getD_countryName());
        }else{
            System.out.println("Cannot Deploy Troops here you dont own it."+this.d_playerName);
            return;
        }
        if(this.d_reinforcements<l_numberTobeDeployed){
            System.out.println("Not enough Troops! for "+this.d_playerName);
            return;
        }else{
         Order order = new Order(this.getName(),"deploy",l_countryID,l_numberTobeDeployed);
         System.out.println("Order Created for Player: "+this.d_playerName);
         this.d_orderList.add(order);
         this.setReinforcements(this.getReinforcements()-l_numberTobeDeployed);
            System.out.println("Player "+this.d_playerName+" Updated Reinforcements: "+this.getReinforcements());

        }
        System.out.println("The order list is:");
        for(Order order: d_orderList){
            order.printOrder();
        }


    }
    public static boolean allTroopsPlaced(List<Player> p_Players){
        for(Player l_player : p_Players){
            if(l_player.getReinforcements()!=0){
                return false;
            }
        }
        return true;
    }
    public static boolean allOrdersExecuted(List<Player> p_Players){
        for(Player l_player : p_Players){
            if(!l_player.d_orderList.isEmpty()){
                return false;
            }
        }
        return true;
    }
    public Order next_order(){
        return this.d_orderList.poll();
    }




}