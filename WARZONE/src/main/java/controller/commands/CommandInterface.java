package main.java.controller.commands;

import main.java.controller.GameEngine;
import main.java.utils.exceptions.CountryDoesNotExistException;

public class CommandInterface {

    public static void addContinentIdContinentVal(Integer integer, Integer integer1) {

        // GameEngine.CURRENT_MAP.addContinent();

    }

    public static void removeContinent(int continentId) {

        GameEngine.CURRENT_MAP.removeContinent(continentId);

    }

    public static void addCountry(int country_id, int continent_id, String country_name) {

        GameEngine.CURRENT_MAP.addCountry(country_id, continent_id, country_name);

    }

    public static void removeCountry(int Country_id) {

        GameEngine.CURRENT_MAP.removeCountry(Country_id);

    }

    public static void addNeighboursToCountry(int country_id, int neighbour_id) {

        GameEngine.CURRENT_MAP.addBorder(country_id, neighbour_id);

    }

    public static void removeNeighboursToCountry(int country_id, int neighbour_id) {

        // GameEngine.CURRENT_MAP.getCountry(country_id).removeBorder(neighbour_id);

=======
import main.java.controller.MapInterface;
import main.java.models.Player;
import main.java.utils.exceptions.ContinentDoesNotExistException;
import main.java.utils.exceptions.CountryDoesNotExistException;
import main.java.utils.exceptions.PlayerDoesNotExistException;

import java.io.FileNotFoundException;

public class CommandInterface {

    public static void addContinentIdContinentVal(String p_continentName, String p_continentVal) {
        int highestId = 0;
        for(int i : GameEngine.CURRENT_MAP.getContinents().keySet())
        {
            highestId = Math.max(i,highestId);
        }
        int bonus = Integer.parseInt(p_continentVal);
        GameEngine.CURRENT_MAP.addContinent(highestId+1,p_continentName,bonus);
    }

    public static void removeContinentId(String p_continentName) throws ContinentDoesNotExistException {
        int l_continentId = GameEngine.CURRENT_MAP.getContinentID(p_continentName);
        if(l_continentId==0) {
            throw new ContinentDoesNotExistException(p_continentName);
        }
        GameEngine.CURRENT_MAP.removeContinent(l_continentId);
    }

    public static void addCountryIdContinentId(String p_countryName, String p_continentName) throws ContinentDoesNotExistException {
        int highestId = 0;
        for(int i : GameEngine.CURRENT_MAP.getCountries().keySet())
        {
            highestId = Math.max(i,highestId);
        }
        int l_continentId = GameEngine.CURRENT_MAP.getContinentID(p_continentName);
        if(l_continentId==0) {
            throw new ContinentDoesNotExistException(p_continentName);
        }
        GameEngine.CURRENT_MAP.addCountry(highestId+1,l_continentId,p_countryName);
    }

    public static void removeCountryId(String p_removeCountryName) throws CountryDoesNotExistException {
        int l_countryId = GameEngine.CURRENT_MAP.getCountryID(p_removeCountryName);
        if(l_countryId==0)
        {
            throw new CountryDoesNotExistException(p_removeCountryName);
        }
        GameEngine.CURRENT_MAP.removeCountry(l_countryId);
    }

    public static void addCountryIdNeighborCountryId(String p_countryName, String p_neighbourCountryName) throws CountryDoesNotExistException {
        int l_countryId = GameEngine.CURRENT_MAP.getCountryID(p_countryName);
        if(l_countryId==0)
        {
            throw new CountryDoesNotExistException(p_countryName);
        }
        int l_neighborCountryId = GameEngine.CURRENT_MAP.getCountryID(p_neighbourCountryName);
        if(l_neighborCountryId==0)
        {
            throw new CountryDoesNotExistException(l_neighborCountryId);
        }
        GameEngine.CURRENT_MAP.addBorder(l_countryId,l_neighborCountryId);
        GameEngine.CURRENT_MAP.addBorder(l_neighborCountryId,l_countryId);
    }

    public static void removeCountryIdNeighborCountryId(String p_countryName, String p_neighborCountryName) throws CountryDoesNotExistException {
        int l_countryId = GameEngine.CURRENT_MAP.getCountryID(p_countryName);
        int l_neighborCountryId = GameEngine.CURRENT_MAP.getCountryID(p_neighborCountryName);
        if(l_countryId==0)
        {
            throw new CountryDoesNotExistException(p_countryName);
        }
        if(l_neighborCountryId==0)
        {
            throw new CountryDoesNotExistException(l_neighborCountryId);
        }
        GameEngine.CURRENT_MAP.removeBorder(l_countryId,l_neighborCountryId);
        GameEngine.CURRENT_MAP.removeBorder(l_neighborCountryId,l_countryId);
>>>>>>> Stashed changes
    }

    public static void addPlayers(String p_playerName) {
        Player newPlayer = new Player(p_playerName);
        GameEngine.PLAYER_LIST.add(newPlayer);
    }

    public static void removePlayers(String p_playerName) throws PlayerDoesNotExistException {
        boolean playerExists = false;
        for(Player p : GameEngine.PLAYER_LIST)
        {
            if(p.getName().equals(p_playerName))
            {
                playerExists = true;
                GameEngine.PLAYER_LIST.remove(p);
            }
        }
        if(!playerExists) {
            throw new PlayerDoesNotExistException("p_playerName");
        }
    }

    public static void loadCurrentMap(String p_filename) throws FileNotFoundException {
        MapInterface.loadMap(p_filename);
    }

    public static void validateMap() {
        MapInterface.validateMap(GameEngine.CURRENT_MAP); //check if this is correct/required
    }

    public static void saveMap(String p_filename) {
        saveMap(p_filename);
    }

    public static void editMap() {

    }
}
