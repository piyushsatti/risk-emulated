package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
import controller.statepattern.End;
import controller.statepattern.Phase;
import models.LogEntryBuffer;
import models.Player;
import models.worldmap.Country;
import models.worldmap.WorldMap;
import view.Logger;

import java.util.*;

/**
 * Represents the phase of executing orders.
 */
public class TournamentExecution extends Phase {

    /**
     * The number of maps used in the tournament.
     */
    private static int MapNumber = 0;

    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Represents the map interface for the tournament.
     */
    MapInterface d_mp = null;

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger d_lw = new Logger(d_logEntryBuffer);

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
    public String getCurrentPhase(GameEngine p_gameEngine) {
        Phase l_phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(l_phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index + 1);
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

        if (MapNumber == d_ge.getMapFilesTournament().size()) {
            MapNumber = 0;
            d_ge.setNumberOfGames(d_ge.getNumberOfGames() + 1);
            int l_numberOfGames = d_ge.getNumberGamesTournament();
            //System.out.println("Number of Games"+ (d_ge.getNumberOfGames()-1));
            l_numberOfGames--;
            d_ge.setNumberGamesTournament(l_numberOfGames);

            //d_ge.setGameResult(d_ge.getNumberOfGames()-1,MapNumber,"in progress");
            d_ge.setD_currentMapIndex(MapNumber);
            //System.out.println("Number of Games:" + d_ge.getNumberGamesTournament());
        } else {
            d_ge.setD_currentMapIndex(MapNumber);
            //System.out.println("Number of Games"+ (d_ge.getNumberOfGames()-1));
            d_ge.setGameResult(d_ge.getNumberOfGames() - 1, MapNumber, "in progress");
        }
        if (d_ge.getNumberGamesTournament() == 0) {
            d_ge.setCurrentState(new End(d_ge));
        }
        String d_map = d_ge.getMapFilesTournament().get(MapNumber);
        MapNumber++;
        d_ge.d_worldmap = null;
        try {
            d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " loading map for tournament");      // Log the command entry
            MapFileLoader l_mfl = new MapFileLoader(d_ge, d_map);

            if(l_mfl.isConquest()){
                d_mp = new MapAdapter(new ConquestMapInterface());
            }else{
                d_mp = new MapInterface();
            }
            d_ge.d_worldmap = d_mp.loadMap(d_ge, l_mfl);
        } catch (Exception e) {
            d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Could Not Load Map. Moving to Next Map");     // Log if map loading fails
            System.out.println(e);
        }
        if (!d_ge.d_worldmap.validateMap()) {
            d_ge.d_renderer.renderError("Invalid Map! Cannot load into game");
            d_ge.d_worldmap = new WorldMap();
            d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Tournament Map is Invalid!");
        }
        d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Tournament Map Loaded");

        if (assignCountries(d_ge, d_currPhase)) {
            d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "Countries assigned to Players");
        }
        d_ge.setCurrentState(new Reinforcement(d_ge));

    }

    /**
     * Assigns countries to players in the tournament phase of the game.
     *
     * @param p_gameEngine The GameEngine object representing the current game state.
     * @param p_currPhase  The current phase of the game.
     * @return true if countries are successfully assigned to players, false otherwise.
     */
    private boolean assignCountries(GameEngine p_gameEngine, String p_currPhase) {
        d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " Assigning Countries to players in Tournament");
        for (Player l_player : p_gameEngine.d_players) {
            l_player.getAssignedCountries().clear();
        }
        if (p_gameEngine.d_players.isEmpty()) {
            p_gameEngine.d_renderer.renderError("Add atleast one player before assigning");
            d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "Command: assigncountries Not Executed  || Must add at least one player before assigning countries");
            return false;
        }
        if (p_gameEngine.d_worldmap.getCountries().isEmpty()) {
            p_gameEngine.d_renderer.renderError(" Empty map Please load a Valid Map");
            d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " Executed Command: assigncountries Not Executed|| Load a valid map!");
            return false;
        }
        HashMap<Integer, Country> l_map = p_gameEngine.d_worldmap.getD_countries();
        Set<Integer> l_countryIDSet = l_map.keySet();
        ArrayList<Integer> l_countryIDList = new ArrayList<>(l_countryIDSet);

        int l_total_players = p_gameEngine.d_players.size();
        int l_playerNumber = 0;
        while (!l_countryIDList.isEmpty()) {
            Random l_rand = new Random();
            int l_randomIndex = l_rand.nextInt(l_countryIDList.size());
            int l_randomCountryID = l_countryIDList.get(l_randomIndex);
            l_countryIDList.remove(l_randomIndex);
            if ((l_playerNumber % l_total_players == 0) && l_playerNumber != 0) {
                l_playerNumber = 0;
            }
            Country l_country = l_map.get(l_randomCountryID);
            l_country.setCountryPlayerID(p_gameEngine.d_players.get(l_playerNumber).getPlayerId());
            p_gameEngine.d_players.get(l_playerNumber).setAssignedCountries(l_randomCountryID);
            l_playerNumber++;
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
        return true;

    }


}


