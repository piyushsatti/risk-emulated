//package models;
//
//import models.map.Country;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Player{
//
//    private String d_playerName;
//    private int d_reinforcements;
//    private ArrayList<Country> d_assignedCountries;
//
//    private static List<Player> d_Players = new ArrayList<>();
//
//    //Getters
//    public String getName() {
//        return d_playerName;
//    }
//    public int getReinforcements() {
//        return d_reinforcements;
//    }
//    public ArrayList<Country> getassignedCountries() {
//        return d_assignedCountries;
//    }
//
//    //Setters
//    public void setName(String p_name) {
//        this.d_playerName= p_name;
//    }
//    public void setReinforcements(int p_reinforcements) {
//        this.d_reinforcements= p_reinforcements;
//    }
//    public void setassignedCountries(Country d_assignedCountries) {
//        this.d_assignedCountries.add(d_assignedCountries);
//    }
//
//    //Constructors
//    public Player(String p_playerName){
//     this.d_playerName= p_playerName;
//     this.d_reinforcements= 0;
//     this.d_assignedCountries= new ArrayList<>();
//     d_Players.add(this);
//    }
//
//
////    public Player(String d_playerName,int d_reinforcements,ArrayList<Country> d_assignedCountries){
////        this.d_playerName= "";
////        this.d_reinforcements= 0;
////        this.d_assignedCountries= d_assignedCountries;
////       }
//
//    //issue order
//
//    //addPlayer assuming a list of players to be added is given as parameter
//    public static void addPlayer(ArrayList<String> p_names){
//        for(String l_name: p_names){
//            System.out.println("Name of the Player to be added: "+  l_name);
//            System.out.println("Total list of Player: "+  d_Players.size());
//            boolean l_found = false;
//            for (Player l_player : d_Players) {
//                if (l_player.getName().equals(l_name)) {
//
//                    d_Players.remove(l_player);
//                    System.out.println("Hi" + d_Players);
//                    l_found = true;
//                    break;
//                }
//            }
//            d_Players.add(new Player(l_name));
//        }
//    }
//    //removePlayer assuming a list of players to be added is given as parameter
//    public static void removePlayer(ArrayList<String> p_names){
//        for(String l_name: p_names){
//            for (Player l_player : d_Players) {
//                if (l_player.getName().equals(l_name)) {
//                    d_Players.remove(l_player);
//                }
//            }
//
//        }
//    }
//    public void printPlayerDetails(){
//        System.out.print("Name: "+this.d_playerName +" ");
//        System.out.print("Reinforcements: "+this.d_reinforcements+" ");
//        for(Country c: this.d_assignedCountries){
//            System.out.print("Country ID: " +c.getD_countryID() +" Country Name: "+ c.getD_countryName() +" ");
//        }
//    }
//   public static void displayPlayers(){
//       System.out.print("Number of Players: " + d_Players.size());
//        for(Player l_player: d_Players){
//            l_player.printPlayerDetails();
//        }
//   }
//
//
//}