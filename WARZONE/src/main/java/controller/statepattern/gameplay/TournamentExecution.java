package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.MapFileManagement.MapInterface;
import controller.statepattern.End;
import controller.statepattern.Phase;
import models.LogEntryBuffer;
import models.Player;
import models.orders.Order;
import models.worldmap.Country;
import models.worldmap.WorldMap;
import view.Logger;

import java.sql.SQLOutput;
import java.util.*;

/**
 * Represents the phase of executing orders.
 */
public class TournamentExecution extends Phase {

    /**
     * The number of maps used in the tournament.
     */
    private static int MapNumber =0;

    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger lw = new Logger(logEntryBuffer);
    /**
     * Constructor for OrderExecution.
     *
     * @param p_gameEngine The GameEngine object.
     */
    public TournamentExecution(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * This method is intended to display the game menu.
     */
    @Override
    public void displayMenu() {

    }
    /**
     * This method is intended to advance the game to the next step or phase.
     */
    @Override
    public void next() {

    }
    /**
     * Retrieves the name of the current phase in the game.
     *
     * @param p_gameEngine The GameEngine object representing the current game state.
     * @return A String representing the name of the current phase.
     */
    public String getCurrentPhase(GameEngine p_gameEngine)
    {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        System.out.println( l_currClass.substring(l_index+1));
        return l_currClass.substring(l_index+1);
    }
    /**
     * This method is intended to end the game.
     */
    @Override
    public void endGame() {

    }

    /**
     * Executes the order execution phase.
     */
    @Override
    public void run() {
        String d_currPhase = getCurrentPhase(d_ge);
        System.out.println("GAME NUMBER !!!!!!! " + d_ge.getNumberGamesTournament());

        System.out.println("MAP NUMBER !!!!!!!! " + MapNumber);

        if(MapNumber == d_ge.getMapFilesTournament().size()){
            MapNumber =0;
            int numberOfGames = d_ge.getNumberGamesTournament();
            numberOfGames--;
            d_ge.setNumberGamesTournament(numberOfGames);
            System.out.println("Number of Games:" + d_ge.getNumberGamesTournament());
        }
        if(d_ge.getNumberGamesTournament() == 0 ){
            d_ge.setCurrentState(new End(d_ge));
        }
        MapInterface mp = new MapInterface();
        String d_map = d_ge.getMapFilesTournament().get(MapNumber);
        MapNumber++;
        d_ge.d_worldmap = null;
        try {
            logEntryBuffer.setString("Phase :"+ d_currPhase +"\n"+ " loading map for tournament");      // Log the command entry
            d_ge.d_worldmap = mp.loadMap(d_ge,d_map);
        }
        catch(Exception e){
            logEntryBuffer.setString("Phase :"+ d_currPhase +"\n"+ " Could Not Load Map. Moving to Next Map");     // Log if map loading fails
            System.out.println(e);
        }
        if(!d_ge.d_worldmap.validateMap()){
            d_ge.d_renderer.renderError("Invalid Map! Cannot load into game");
            d_ge.d_worldmap = new WorldMap();
            logEntryBuffer.setString("Phase :"+ d_currPhase +"\n"+ " Tournament Map is Invalid!");
        }
        logEntryBuffer.setString("Phase :"+ d_currPhase +"\n"+ " Tournament Map Loaded");

        if(assignCountries(d_ge,d_currPhase)){
            logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ "Countries assigned to Players");
        }
        System.out.println(d_ge);
        d_ge.setCurrentState(new Reinforcement(d_ge));

    }
    /**
     * Assigns countries to players in the tournament phase of the game.
     *
     * @param p_gameEngine The GameEngine object representing the current game state.
     * @param p_currPhase  The current phase of the game.
     * @return true if countries are successfully assigned to players, false otherwise.
     */
    private boolean assignCountries(GameEngine p_gameEngine,String p_currPhase) {
        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Assigning Countries to players in Tournament");
        for (Player l_player : p_gameEngine.d_players) {
            l_player.getAssignedCountries().clear();
        }
        if(p_gameEngine.d_players.size() == 0){
            p_gameEngine.d_renderer.renderError("Add atleast one player before assigning");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: assigncountries Not Executed  || Must add at least one player before assigning countries");
            return false;
        }
        if(p_gameEngine.d_worldmap.getCountries().size()==0){
            p_gameEngine.d_renderer.renderError(" Empty map Please load a Valid Map");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Executed Command: assigncountries Not Executed|| Load a valid map!");
            return false;
        }
        HashMap<Integer, Country> map = p_gameEngine.d_worldmap.getD_countries();
        Set<Integer> l_countryIDSet = map.keySet();
        ArrayList<Integer> l_countryIDList = new ArrayList<>(l_countryIDSet);

        int total_players = p_gameEngine.d_players.size();
        int playerNumber = 0;
        while (!l_countryIDList.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(l_countryIDList.size());
            int l_randomCountryID = l_countryIDList.get(randomIndex);
            l_countryIDList.remove(randomIndex);
            if ((playerNumber % total_players == 0) && playerNumber != 0) {
                playerNumber = 0;
            }
            Country country = map.get(l_randomCountryID);
            country.setCountryPlayerID(p_gameEngine.d_players.get(playerNumber).getPlayerId());
            p_gameEngine.d_players.get(playerNumber).setAssignedCountries(l_randomCountryID);
            playerNumber++;
        }

        System.out.println("Assigning of Countries Done");
        for (Player l_player : p_gameEngine.d_players) {
            System.out.println("Number of Countries: " + l_player.getAssignedCountries().size());
            System.out.println("List of Assigned Countries for Player: " + l_player.getName());
            ArrayList<Integer> l_listOfAssignedCountries = l_player.getAssignedCountries();
            for (Integer l_countryID : l_listOfAssignedCountries) {
                System.out.println(p_gameEngine.d_worldmap.getCountry(l_countryID).getCountryName());
            }
            System.out.println("-----------------------------------------------------------------");

        }
        System.out.println("ASSIGNMENT DONE HELLO FROM HERE");
        return true;

    }


    }


