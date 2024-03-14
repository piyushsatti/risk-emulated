package controller.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * this class consists of methods which handle the valid commands along with the parameters mentioned for command options
 */
public class CommandInterface {
    /**
     * adds new continent with the continent name and its bonus army value in the current map
     *
     * @param p_continentName name of the continent
     * @param p_continentVal bonus army value for the continent
     * @throws ContinentAlreadyExistsException when continent already exists
     */
    public static void addContinentIdContinentVal(String p_continentName, String p_continentVal) throws ContinentAlreadyExistsException {
        int l_highestId = 0;
        for(int i : GameEngine.CURRENT_MAP.getContinents().keySet())
        {
            l_highestId = Math.max(i,l_highestId);
        }
        int l_bonus = Integer.parseInt(p_continentVal);
        int l_continentId = GameEngine.CURRENT_MAP.getContinentID(p_continentName);
        if(l_continentId!=-1)
        {
            throw new ContinentAlreadyExistsException(p_continentName);
        }
        GameEngine.CURRENT_MAP.addContinent(l_highestId+1,p_continentName,l_bonus);
    }

    /**
     *removes continent with the given continent name in the current map
     * @param p_continentName : name of continent
     * @throws ContinentDoesNotExistException : when continent with the given name does not exist
     */

    public static void removeContinentId(String p_continentName) throws ContinentDoesNotExistException, CountryDoesNotExistException {
        int l_continentId = GameEngine.CURRENT_MAP.getContinentID(p_continentName);
        if(l_continentId==-1) {
            throw new ContinentDoesNotExistException(p_continentName);
        }
        GameEngine.CURRENT_MAP.removeContinent(l_continentId);
    }

    /**
     *
     * adds country with the given country name to the continent with the given continent name in the current map
     * @param p_countryName : name of the country
     * @param p_continentName: name of the continent
     * @throws ContinentDoesNotExistException : when continent to which country is to be added does not exist
     */
    public static void addCountryIdContinentId(String p_countryName, String p_continentName) throws ContinentDoesNotExistException, DuplicateCountryException {
        int l_highestId = 0;
        for(int i : GameEngine.CURRENT_MAP.getCountries().keySet())
        {
            l_highestId = Math.max(i,l_highestId);
        }
        int l_continentId = GameEngine.CURRENT_MAP.getContinentID(p_continentName);
        if(l_continentId==-1) {
            throw new ContinentDoesNotExistException(p_continentName);
        }
        GameEngine.CURRENT_MAP.addCountry(l_highestId+1,l_continentId,p_countryName);
    }

    /**
     * removes country with the given country name from the current map
     * @param p_removeCountryName : name of the country to be removed
     * @throws CountryDoesNotExistException : when country to be removed does not already exist
     */
    public static void removeCountryId(String p_removeCountryName) throws CountryDoesNotExistException {
        int l_countryId = GameEngine.CURRENT_MAP.getCountryID(p_removeCountryName);
        if(l_countryId==-1)
        {
            throw new CountryDoesNotExistException(p_removeCountryName);
        }
        GameEngine.CURRENT_MAP.removeCountry(l_countryId);
    }

    /**adds borders between both the given country and the to be neighboring country
     * @param p_countryName : name of the country
     * @param p_neighbourCountryName : name of the neighboring country
     * @throws CountryDoesNotExistException : when country with the given doesn't exist. same in the case when neighboring country with given name doesn't exist
     */
    public static void addCountryIdNeighborCountryId(String p_countryName, String p_neighbourCountryName) throws CountryDoesNotExistException {
        int l_countryId = GameEngine.CURRENT_MAP.getCountryID(p_countryName);
        if(l_countryId==-1)
        {
            throw new CountryDoesNotExistException(p_countryName);
        }
        int l_neighborCountryId = GameEngine.CURRENT_MAP.getCountryID(p_neighbourCountryName);
        if(l_neighborCountryId==-1)
        {
            throw new CountryDoesNotExistException(l_neighborCountryId);
        }
        GameEngine.CURRENT_MAP.addBorder(l_countryId,l_neighborCountryId);
        GameEngine.CURRENT_MAP.addBorder(l_neighborCountryId,l_countryId);
    }

    /**removes borders between the countries
     * @param p_countryName : name of the country
     * @param p_neighborCountryName : name of the neighboring country
     * @throws CountryDoesNotExistException : when country does not already exist
     */
    public static void removeCountryIdNeighborCountryId(String p_countryName, String p_neighborCountryName) throws CountryDoesNotExistException {
        int l_countryId = GameEngine.CURRENT_MAP.getCountryID(p_countryName);
        int l_neighborCountryId = GameEngine.CURRENT_MAP.getCountryID(p_neighborCountryName);
        if(l_countryId==-1)
        {
            throw new CountryDoesNotExistException(p_countryName);
        }
        if(l_neighborCountryId==-1)
        {
            throw new CountryDoesNotExistException(l_neighborCountryId);
        }
        GameEngine.CURRENT_MAP.removeBorder(l_countryId,l_neighborCountryId);
        GameEngine.CURRENT_MAP.removeBorder(l_neighborCountryId,l_countryId);
    }

    /**
     *adds new player to the player list
     * @param p_playersToAdd : name of the player
     */
    public static void addPlayers(List<String> p_playersToAdd) {
        List<String> l_playersAdded = new ArrayList<>();
        List<String> l_existingPlayers = new ArrayList<>();
        for(Player l_player : GameEngine.PLAYER_LIST)
        {
            if(!l_existingPlayers.contains(l_player.getName())) l_existingPlayers.add(l_player.getName());
        }
        for(String l_playertoAdd : p_playersToAdd)
        {
            if(!l_existingPlayers.contains(l_playertoAdd))
            {
                GameEngine.PLAYER_LIST.add(new Player(l_playertoAdd));
                l_playersAdded.add(l_playertoAdd);
            }
        }
        if(!l_playersAdded.isEmpty()) System.out.println("added players sucessfully: "+ List.of(l_playersAdded));
    }

    /**
     * removes existing player from the player list
     * @param p_copyList Name of the player
     */
    public static void removePlayers(List<String> p_copyList) {
        List<String> l_playerNotExist = new ArrayList<>();
        List<String> l_playersRemoved = new ArrayList<>();
        for(String l_playerCheck : p_copyList)
        {   boolean found = false;
            Iterator<Player> it = GameEngine.PLAYER_LIST.iterator();
            while (it.hasNext()) {
                Player l_player = it.next();
                if (l_player.getName().equals(l_playerCheck)) {
                    found = true;
                    it.remove();
                    l_playersRemoved.add(l_playerCheck);
                }
            }
            if(!found) l_playerNotExist.add(l_playerCheck);
        }
        System.out.println("players removed successfully: "+List.of(l_playersRemoved));
        if(!l_playerNotExist.isEmpty())
        {
            System.out.println("could not remove players as they don't exist: "+List.of(l_playerNotExist));
        }
    }

    /**
     *loads the current map
     * @param p_filename : name of the map file
     * @throws FileNotFoundException : when map file is not found
     * @throws InvalidMapException : when map is invalid
     */
    public static void loadCurrentMap(String p_filename) throws FileNotFoundException, InvalidMapException {

        WorldMap l_map = MapInterface.loadMap(p_filename);
        if (MapInterface.validateMap(l_map)) {
            GameEngine.CURRENT_MAP = MapInterface.loadMap(p_filename);
        }
        else{
            GameEngine.CURRENT_MAP = null;
            TerminalRenderer.renderError("Invalid map, setting map to null.");
            throw new InvalidMapException(p_filename);
        }

    }

    /**
     * Validates the current map by checking if it is connected graph.
     * If the map is valid, it renders a message indicating that the map is valid.
     * If the map is not valid, it renders a message indicating that the map is not valid.
     */
    public static void validateMap() {
        if(MapInterface.validateMap(GameEngine.CURRENT_MAP)){
            TerminalRenderer.renderMessage("Map is Valid");
        }else{
            TerminalRenderer.renderMessage("Map is Not Valid");
        }
    }

    /**
     * saves map
     * @param p_filename : name of the map file
     * @throws IOException : If an I/O error occurs while writing to the file.
     *
     */
    public static void saveMap(String p_filename) throws IOException {
        MapInterface.saveMap(p_filename);
    }

    /**
     *this method checks if the map with the filename entered by the user exists in the maps folder or not
     * if yes, the map is loaded and current map is set to this map
     * if not, a new map with the filename given by the user is created, and it is set as the current map
     * @param p_filename : name of the map file entered by the user
     * @throws IOException : an IO exception has occurred
     */
    public static void editMap(String p_filename) throws IOException, CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, InvalidMapException {
        File l_map_file_obj = new File(GameEngine.MAPS_FOLDER + p_filename);
        //checking if the map with the filename entered by the user exists in the maps folder or not
        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory())
        {
            GameEngine.CURRENT_MAP = MapInterface.loadMap(p_filename); //loading the existing map and setting it as current map
        }
        else { //else case: map with filename entered by user does not exist
            if(!p_filename.contains(".map")) p_filename = p_filename + ".map"; //adding .map in the filename if the user didn't specify it
            File l_outputFile = new File(GameEngine.MAPS_FOLDER + p_filename);
            l_outputFile.createNewFile();
            //adding some boiler plate text for the newly created map
            String file_signature = """
                ; map: estonia.map
                ; map made with the map maker
                ; yura.net Risk 1.0.9.3
                                
                [files]
                pic estonia_pic.png
                map estonia_map.gif
                crd estonia.cards
                """;
            BufferedWriter writer = new BufferedWriter(new FileWriter(l_outputFile));
            StringBuilder l_added_line = new StringBuilder();
            l_added_line.append(file_signature);
            l_added_line.append("\n[continents]\n");
            l_added_line.append("\n[countries]\n");
            l_added_line.append("\n[borders]");
            writer.write(l_added_line.toString());
            writer.close();
            GameEngine.CURRENT_MAP = MapInterface.loadMap(p_filename); //setting this new map as the current map
            System.out.println("new map created: "+p_filename+" , please use edit commands to add countries,continents");
        }
    }

}
