package controller;

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
     * Creates a File object from the specified file name within the game engine's maps folder.
     *
     * @param p_gameEngine The game engine instance.
     * @param p_mapName    The name of the map file to create the File object for.
     * @return The File object corresponding to the specified map file name.
     * @throws FileNotFoundException If the specified file does not exist or is a directory.
     */
    protected  File createFileObjectFromFileName(GameEngine p_gameEngine, String p_mapName) throws FileNotFoundException {

        File l_map_file_obj = new File(p_gameEngine.d_maps_folder + p_mapName);
        //p_gameEngine.d_renderer.renderMessage(p_gameEngine.d_maps_folder + p_mapName);
        //p_gameEngine.d_renderer.renderMessage("Map object: " + l_map_file_obj);

        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory()) {
            return l_map_file_obj;
        } else {
            p_gameEngine.d_renderer.renderMessage("File does not exist");
            throw new FileNotFoundException("File does not exist.");
        }
    }



    /**
     * Saves the provided map to a file with the given file name.
     * @param p_gameEnginee the game engine associated with the map.
     * @param p_FileName The name of the file to save the map to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public  void saveMap(GameEngine p_gameEnginee, String p_FileName) throws IOException {
        WorldMap p_map = p_gameEnginee.d_worldmap;
        File outputFile = new File(p_gameEnginee.d_maps_folder + p_FileName);
        p_gameEnginee.d_renderer.renderMessage("Was file created? " + outputFile.createNewFile());

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
        logEntryBuffer.setString("saved map :" + p_FileName);
    }


    /**
     * Loads a map into the game engine.
     *
     * @param p_gameEngine      The GameEngine object.
     * @param mapName The name of the map to load.
     * @throws FileNotFoundException         If the specified file is not found.
     * @throws ContinentAlreadyExistsException If a continent already exists while loading.
     * @throws ContinentDoesNotExistException If a continent does not exist while loading.
     * @throws DuplicateCountryException      If a country already exists while loading.
     * @throws CountryDoesNotExistException  If a country does not exist while loading.
     */
    public WorldMap loadMap(GameEngine p_gameEngine, String mapName) throws FileNotFoundException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, CountryDoesNotExistException {
        WorldMap l_worldMap = new WorldMap();
        File l_map_file_obj;
        Scanner l_file_reader;
        l_map_file_obj = createFileObjectFromFileName(p_gameEngine, mapName);
        l_file_reader = new Scanner(l_map_file_obj);
        String[] l_split_data;
        while (l_file_reader.hasNextLine()) {

            l_split_data = l_file_reader.nextLine().split(" ");

            switch (l_split_data[0]) {
                case "[continents]" -> loadContinents(l_file_reader, l_worldMap);
                case "[countries]" -> loadCountries(l_file_reader, l_worldMap);
                case "[borders]" -> loadBorders(l_file_reader, l_worldMap);
            }
        }
        return l_worldMap;
    }

    /**
     * Loads continents data from a Scanner object into a WorldMap instance.
     *
     * @param fileReader The Scanner object used for reading data from the input file.
     * @param p_worldMap The WorldMap instance where the continents will be loaded.
     * @throws ContinentAlreadyExistsException If attempting to add a continent that already exists in the WorldMap.
     */
    public void loadContinents(Scanner fileReader, WorldMap p_worldMap) throws ContinentAlreadyExistsException {

        String[] inputData;
        while (fileReader.hasNextLine()) {
            inputData = fileReader.nextLine().split(" ");

            if (inputData[0].isBlank()) return;
            else {
                String continentName = inputData[0];
                int bonus = Integer.parseInt(inputData[1]);
                p_worldMap.addContinent(continentName, bonus);
            }
        }
    }

    /**
     * Loads continents from a file into the world map.
     *
     * @param fileReader The scanner object used to read the file.
     * @param p_worldMap         The WorldMap object to which continents will be added.
     * @throws ContinentDoesNotExistException if a continent does not exist on the world map.
     * @throws DuplicateCountryException if a country already exists on the map.
     */
    public void loadCountries(Scanner fileReader, WorldMap p_worldMap) throws ContinentDoesNotExistException, DuplicateCountryException {
        String[] inputData;
        while (fileReader.hasNextLine()) {
            inputData = fileReader.nextLine().split(" ");

            if (inputData[0].isBlank()) return;
            else {
                int countryID = Integer.parseInt(inputData[0]);
                String countryName = inputData[1];
                int continentID = Integer.parseInt(inputData[2]);
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
    public void loadBorders(Scanner fileReader, WorldMap p_worldMap) throws CountryDoesNotExistException {
        String[] inputData;
        while (fileReader.hasNextLine()) {

            inputData = fileReader.nextLine().split(" ");
            if (inputData[0].isBlank()) return;
            else {
                int sourceCountry = Integer.parseInt(inputData[0]);
                for (String s : inputData) {
                    int targetCountry = Integer.parseInt(s);
                    if (sourceCountry != targetCountry) {
                        p_worldMap.addBorder(sourceCountry, targetCountry);
                    }
                }
            }
        }
    }

}