package controller.MapFileManagement;

import controller.GameEngine;
import helpers.exceptions.*;
import models.LogEntryBuffer;
import models.worldmap.Continent;
import models.worldmap.Country;
import models.worldmap.WorldMap;
import view.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The MapInterface class provides functionality for loading, saving, and validating world maps.
 * It includes methods to load a map from a file, save a map to a file, and validate the integrity
 * of a map.
 */
public class MapInterface {

    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger d_lw = new Logger(d_logEntryBuffer);

    /**
     * Creates a File object from the specified file name within the game engine's maps folder.
     *
     * @param p_gameEngine The game engine instance.
     * @param p_mapName    The name of the map file to create the File object for.
     * @return The File object corresponding to the specified map file name.
     * @throws FileNotFoundException If the specified file does not exist or is a directory.
     */
    public File createFileObjectFromFileName(GameEngine p_gameEngine, String p_mapName) throws FileNotFoundException {

        File l_map_file_obj = new File(p_gameEngine.d_maps_folder + p_mapName);

        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory()) {
            return l_map_file_obj;
        } else {
            p_gameEngine.d_renderer.renderMessage("File does not exist");
            d_logEntryBuffer.setString("File Not Found. it does not exist");
            throw new FileNotFoundException("File does not exist.");
        }
    }

    /**
     * Saves the provided map to a file with the given file name.
     *
     * @param p_gameEngine the game engine associated with the map.
     * @param p_FileName   The name of the file to save the map to.
     */
    public void saveMap(GameEngine p_gameEngine, String p_FileName) {
        WorldMap p_map = p_gameEngine.d_worldmap;
        p_map.normalizeContinents();
        File l_outputFile = new File(p_gameEngine.d_maps_folder + p_FileName);
        //creating a new output file
        try {
            p_gameEngine.d_renderer.renderMessage("Was file created? " + l_outputFile.createNewFile());
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("Save map failed!");
            p_gameEngine.d_renderer.renderError("IOException : Encountered File I/O Error");
            d_logEntryBuffer.setString("\nPhase: MapEditor \n  Command: savemap Not Executed due to File I/O Error");
            return;
        }
        //the file signature for the output file
        String l_file_signature = """
                ; map: estonia.map
                ; map made with the map maker
                ; yura.net Risk 1.0.9.3
                                
                [files]
                pic estonia_pic.png
                map estonia_map.gif
                crd estonia.cards
                """;
        BufferedWriter l_writer = null;

        try {
            l_writer = new BufferedWriter(new FileWriter(l_outputFile));
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("Save map failed!");
            d_logEntryBuffer.setString("\nSaveMap failed!");
            return;
        }
        //from here, we are adding the map contents to the output file.
        StringBuilder l_added_line = new StringBuilder();
        l_added_line.append(l_file_signature);
        l_added_line.append("\n[continents]\n");
        //adding continents
        for (Continent l_continent_obj : p_map.getContinents().values()) {
            l_added_line.append(l_continent_obj.d_continentName)
                    .append(" ").append(l_continent_obj.getBonus())
                    .append(" ").append("xxx").append("\n");
        }

        l_added_line.append("\n[countries]\n");
        //adding countries
        for (Country l_country_obj : p_map.getCountries().values()) {
            l_added_line.append(l_country_obj.getCountryID())
                    .append(" ").append(l_country_obj.getCountryName())
                    .append(" ").append(l_country_obj.getContinent().getContinentID())
                    .append(" 0 0").append("\n");
        }
        l_added_line.append("\n[borders]");

        for (Integer l_country_id : p_map.getCountries().keySet()) {
            HashMap<Integer, Country> l_border_countries = p_map.getCountry(l_country_id).getBorderCountries();
            l_added_line.append("\n");
            l_added_line.append(l_country_id);

            for (Integer l_border_country_id : l_border_countries.keySet()) {
                l_added_line.append(" ").append(l_border_country_id);
            }
        }
        try {
            l_writer.write(l_added_line.toString());
            l_writer.close();
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("Save map failed!");
            d_logEntryBuffer.setString("\nSaveMap failed!");
            return;
        }
        d_logEntryBuffer.setString(" \nsaved map :" + p_FileName);
    }

    /**
     * Loads a map into the game engine.
     * @param p_gameEngine The GameEngine object.
     * @param p_mapName The name of the map to load.
     * @return WorldMap object
     * @throws FileNotFoundException If the specified file is not found.
     * @throws ContinentAlreadyExistsException If a continent already exists while loading.
     * @throws ContinentDoesNotExistException If a continent does not exist while loading.
     * @throws DuplicateCountryException If a country already exists while loading.
     * @throws CountryDoesNotExistException If a country does not exist while loading.
     */
    public WorldMap loadMap(GameEngine p_gameEngine, String p_mapName) throws FileNotFoundException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, CountryDoesNotExistException {
        WorldMap l_worldMap = new WorldMap();
        File l_map_file_obj;
        Scanner l_file_reader;
        //creating a map file object
        l_map_file_obj = createFileObjectFromFileName(p_gameEngine, p_mapName);
        l_file_reader = new Scanner(l_map_file_obj);
        String[] l_split_data;
        while (l_file_reader.hasNextLine()) {

            l_split_data = l_file_reader.nextLine().split(" ");
            //loading the continents,countries and borders
            switch (l_split_data[0]) {
                case "[continents]" -> loadContinents(l_file_reader, l_worldMap);
                case "[countries]" -> loadCountries(l_file_reader, l_worldMap);
                case "[borders]" -> loadBorders(l_file_reader, l_worldMap);
            }
        }
        return l_worldMap;
    }


    /**
     * Loads a map into the game engine from a file using the provided map file loader.
     *
     * @param p_gameEngine The game engine to load the map into.
     * @param p_mfl        The map file loader containing the map file.
     * @return The loaded world map.
     */
    public WorldMap loadMap(GameEngine p_gameEngine, MapFileLoader p_mfl) {

        WorldMap l_worldMap = new WorldMap();
        Scanner l_file_reader = null;
        try {
            l_file_reader = new Scanner(p_mfl.d_mapFile);
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("File does not exist!");
            d_logEntryBuffer.setString("\nfile does not exist!");
            return new WorldMap();
        }
        String[] l_split_data;
        while (l_file_reader.hasNextLine()) {

            l_split_data = l_file_reader.nextLine().split(" ");
            //loading borders, continents and countries
            try {
                switch (l_split_data[0]) {

                    case "[continents]" -> loadContinents(l_file_reader, l_worldMap);
                    case "[countries]" -> loadCountries(l_file_reader, l_worldMap);
                    case "[borders]" -> loadBorders(l_file_reader, l_worldMap);
                }
            } catch (Exception e) { //mapfile is invalid
                p_gameEngine.d_renderer.renderError("Invalid map file!");
                d_logEntryBuffer.setString("\nfile does not exist!");
                new WorldMap();
            }

        }
        return l_worldMap;
    }

    /**
     * Loads continents data from a Scanner object into a WorldMap instance.
     *
     * @param p_fileReader The Scanner object used for reading data from the input file.
     * @param p_worldMap   The WorldMap instance where the continents will be loaded.
     * @throws ContinentAlreadyExistsException If attempting to add a continent that already exists in the WorldMap.
     */
    public void loadContinents(Scanner p_fileReader, WorldMap p_worldMap) throws ContinentAlreadyExistsException {

        String[] l_inputData;
        while (p_fileReader.hasNextLine()) {
            l_inputData = p_fileReader.nextLine().split(" ");

            if (l_inputData[0].isBlank()) return;
            else {
                String l_continentName = l_inputData[0];
                int l_bonus = Integer.parseInt(l_inputData[1]);
                p_worldMap.addContinent(l_continentName, l_bonus);
            }
        }
    }

    /**
     * Loads continents from a file into the world map.
     *
     * @param p_fileReader The scanner object used to read the file.
     * @param p_worldMap   The WorldMap object to which continents will be added.
     * @throws ContinentDoesNotExistException if a continent does not exist on the world map.
     * @throws DuplicateCountryException      if a country already exists on the map.
     */
    public void loadCountries(Scanner p_fileReader, WorldMap p_worldMap) throws ContinentDoesNotExistException, DuplicateCountryException {
        String[] l_inputData;
        while (p_fileReader.hasNextLine()) {
            l_inputData = p_fileReader.nextLine().split(" ");
            if (l_inputData[0].isBlank()) return;
            else {
                int l_countryID = Integer.parseInt(l_inputData[0]);
                String l_countryName = l_inputData[1];
                int l_continentID = Integer.parseInt(l_inputData[2]);
                p_worldMap.addCountry(l_countryName, l_continentID, l_countryID);
            }
        }
    }

    /**
     * Loads borders from a file into the world map.
     *
     * @param p_fileReader The scanner object used to read the file.
     * @param p_worldMap   The WorldMap object to which borders will be added.
     * @throws CountryDoesNotExistException If a country mentioned in the border data does not exist in the world map.
     */
    public void loadBorders(Scanner p_fileReader, WorldMap p_worldMap) throws CountryDoesNotExistException {
        String[] l_inputData;
        while (p_fileReader.hasNextLine()) {
            l_inputData = p_fileReader.nextLine().split(" ");
            if (l_inputData[0].isBlank()) return;
            else {
                int l_sourceCountry = Integer.parseInt(l_inputData[0]);
                for (String l_s : l_inputData) {
                    int targetCountry = Integer.parseInt(l_s);
                    if (l_sourceCountry != targetCountry) {
                        p_worldMap.addBorder(l_sourceCountry, targetCountry);
                    }
                }
            }
        }
    }

}