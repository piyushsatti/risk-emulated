package controller;

import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
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
     * Creates a File object from the given map name.
     *
     * @param p_mapName The name of the map file.
     * @return The File object representing the map file.
     * @throws FileNotFoundException If the specified file does not exist.
     */
    protected static File createFileObjectFromFileName(GameEngine p_gameEngine, String p_mapName) throws FileNotFoundException {

        File l_map_file_obj = new File(p_gameEngine.d_maps_folder + p_mapName);
        p_gameEngine.d_renderer.renderMessage(p_gameEngine.d_maps_folder + p_mapName);
        p_gameEngine.d_renderer.renderMessage("Map object: " + l_map_file_obj);

        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory()) {
            return l_map_file_obj;
        } else {
            p_gameEngine.d_renderer.renderMessage("File does not exist");
            throw new FileNotFoundException("File does not exist.");
        }
    }

    /**
     * Saves the provided map to a file with the given file name.
     *
     * @param p_file_name The name of the file to save the map to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveMap(GameEngine ge, String p_file_name) throws IOException {
        WorldMap p_map = ge.d_worldmap;
        File outputFile = new File(ge.d_maps_folder + p_file_name);
        ge.d_renderer.renderMessage("Was file created? " + outputFile.createNewFile());

        String file_signature = """
                ; map: estonia.map
                ; map made with the map maker
                ; yura.net Risk 1.0.9.3
                                
                [files]
                pic estonia_pic.png
                map estonia_map.gif
                crd estonia.cards
                """;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        StringBuilder added_line = new StringBuilder();
        added_line.append(file_signature);
        added_line.append("\n[continents]\n");

        for (Continent continent_obj : p_map.getContinents().values()) {
            added_line.append(continent_obj.d_continentName)
                    .append(" ").append(continent_obj.getBonus())
                    .append(" ").append("xxx").append("\n");
        }

        added_line.append("\n[countries]\n");

        for (Country country_obj : p_map.getCountries().values()) {
            added_line.append(country_obj.getCountryID())
                    .append(" ").append(country_obj.getCountryName())
                    .append(" ").append(country_obj.getContinent().getContinentID())
                    .append(" 0 0").append("\n");
        }
        added_line.append("\n[borders]");

        for (Integer country_id : p_map.getCountries().keySet()) {
            HashMap<Integer, Country> border_countries = p_map.getCountry(country_id).getBorderCountries();
            added_line.append("\n");
            added_line.append(country_id);

            for (Integer border_country_id : border_countries.keySet()) {
                added_line.append(" ").append(border_country_id);
            }
        }
        writer.write(added_line.toString());
        writer.close();
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        Logger lw = new Logger(logEntryBuffer);
        logEntryBuffer.setString("saved map :" + p_file_name);
    }

    /**
     * Validates the integrity of the provided map.
     *
     * @return True if the map is valid, false otherwise.
     */
    public static boolean validateMap(GameEngine p_gameEngine) {
        return (p_gameEngine.d_worldmap.isConnected()) && (p_gameEngine.d_worldmap.isContinentConnected());
    }

    /**
     * Loads a map into the game engine.
     *
     * @param p_gameEngine The GameEngine object.
     * @param p_map_name   The name of the map to load.
     * @throws FileNotFoundException           If the specified file is not found.
     * @throws ContinentAlreadyExistsException If a continent already exists while loading.
     * @throws ContinentDoesNotExistException  If a continent does not exist while loading.
     * @throws DuplicateCountryException       If a country already exists while loading.
     * @throws CountryDoesNotExistException    If a country does not exist while loading.
     */
    public static void loadMap(GameEngine p_gameEngine, String p_map_name)
            throws FileNotFoundException, ContinentAlreadyExistsException, ContinentDoesNotExistException,
            DuplicateCountryException, CountryDoesNotExistException {
        Scanner l_file_reader = new Scanner(
                createFileObjectFromFileName(p_gameEngine, p_map_name)
        );
        String[] l_split_data;

        while (l_file_reader.hasNextLine()) {
            l_split_data = l_file_reader.nextLine().split(" ");

            switch (l_split_data[0]) {
                case "[continents]" -> loadContinents(l_file_reader, p_gameEngine.d_worldmap);
                case "[countries]" -> loadCountries(l_file_reader, p_gameEngine.d_worldmap);
                case "[borders]" -> loadBorders(l_file_reader, p_gameEngine.d_worldmap);
            }
        }
    }

    /**
     * Loads continents data from a Scanner object into a WorldMap instance.
     *
     * @param fileReader The Scanner object used for reading data from the input file.
     * @param p_worldMap The WorldMap instance where the continents will be loaded.
     * @throws ContinentAlreadyExistsException If attempting to add a continent that already exists in the WorldMap.
     */
    public static void loadContinents(Scanner fileReader, WorldMap p_worldMap) throws ContinentAlreadyExistsException {

        String[] inputData;
        int bonus;
        while (fileReader.hasNextLine()) {
            inputData = fileReader.nextLine().split(" ");

            if (inputData[0].isBlank()) return;
            else {
                String continentName = inputData[0];
                bonus = Integer.parseInt(inputData[1]);
                p_worldMap.addContinent(continentName, bonus);
            }
        }
    }

    /**
     * Loads continents from a file into the world map.
     *
     * @param fileReader The scanner object used to read the file.
     * @param p_worldMap The WorldMap object to which continents will be added.
     * @throws ContinentAlreadyExistsException If a continent with the same name already exists in the world map.
     */
    public static void loadCountries(Scanner fileReader, WorldMap p_worldMap) throws ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException {
        String[] inputData;
        int countryID;
        int continentID;
        while (fileReader.hasNextLine()) {
            inputData = fileReader.nextLine().split(" ");

            if (inputData[0].isBlank()) return;
            else {
                countryID = Integer.parseInt(inputData[0]);
                String countryName = inputData[1];
                continentID = Integer.parseInt(inputData[2]);
                p_worldMap.addCountry(countryName, continentID, countryID);
            }
        }
    }

    /**
     * Loads borders from a file into the world map.
     *
     * @param fileReader The scanner object used to read the file.
     * @param p_worldMap         The WorldMap object to which borders will be added.
     * @throws CountryDoesNotExistException If a country mentioned in the border data does not exist in the world map.
     */
    public static void loadBorders(Scanner fileReader, WorldMap p_worldMap)
            throws CountryDoesNotExistException {
        String[] inputData;
        int sourceCountry;
        int targetCountry;
        while (fileReader.hasNextLine()) {
            inputData = fileReader.nextLine().split(" ");
            if (inputData[0].isBlank()) return;
            else {
                sourceCountry = Integer.parseInt(inputData[0]);
                for (String s : inputData) {
                    targetCountry = Integer.parseInt(s);
                    if (sourceCountry != targetCountry) {
                        p_worldMap.addBorder(sourceCountry, targetCountry);
                    }
                }
            }
        }
    }
}