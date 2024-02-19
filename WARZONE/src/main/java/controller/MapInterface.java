package controller;

import helpers.exceptions.*;
import models.worldmap.Continent;
import models.worldmap.Country;
import models.worldmap.WorldMap;
import views.TerminalRenderer;

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
     * @param p_map_name The name of the map file.
     * @return The File object representing the map file.
     * @throws FileNotFoundException If the specified file does not exist.
     */
    private static File createFileObjectFromFileName(String p_map_name) throws FileNotFoundException {

        File l_map_file_obj = new File(GameEngine.MAPS_FOLDER + p_map_name);

        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory()) {

            System.out.println("Successfully Loaded Map: " + l_map_file_obj.getName());

            return l_map_file_obj;

        } else {

            throw new FileNotFoundException("File does not exist.");

        }

    }

    /**
     * Loads a world map from the specified file.
     *
     * @param p_map_name The name of the map file to load.
     * @return The loaded WorldMap object.
     * @throws FileNotFoundException    If the specified file does not exist.
     * @throws NumberFormatException    If there is an error in parsing numeric data.
     */
    public static WorldMap loadMap(String p_map_name) throws FileNotFoundException, NumberFormatException, InvalidMapException {

        File l_map_file_obj;

        Scanner l_file_reader;

        WorldMap map = new WorldMap();

        l_map_file_obj = createFileObjectFromFileName(p_map_name);

        l_file_reader = new Scanner(l_map_file_obj);

        String l_data;

        String[] l_split_data;

        Boolean[] l_state = {false, false, false};

        int i = 1;

        while (l_file_reader.hasNextLine()) {

            l_data = l_file_reader.nextLine();

            if (l_data.equals("[continents]")) {

                l_state[0] = true;

                l_state[1] = false;

                l_state[2] = false;

                continue;

            } else if (l_data.equals("[countries]")) {

                l_state[0] = false;

                l_state[1] = true;

                l_state[2] = false;

                continue;

            } else if (l_data.equals("[borders]")) {

                l_state[0] = false;

                l_state[1] = false;

                l_state[2] = true;

                continue;

            } else if (l_data.isEmpty()) {

                l_state[0] = false;

                l_state[1] = false;

                l_state[2] = false;

            }

            l_split_data = l_data.split(" ");

            try {

                if (l_state[0]) {

                    map.addContinent(i, l_split_data[0], Integer.parseInt(l_split_data[1]));

                    i++;

                    continue;

                } else if (l_state[1]) {

                    map.addCountry(

                            Integer.parseInt(l_split_data[0]),

                            Integer.parseInt(l_split_data[2]),

                            l_split_data[1]

                    );

                    continue;

                } else if (l_state[2]) {

                    for (int j = 1; j < l_split_data.length; j++) {

                        map.addBorder(
                                Integer.parseInt(l_split_data[0]),
                                Integer.parseInt(l_split_data[j])
                        );

                    }

                    continue;

                }

            } catch (ContinentAlreadyExistsException | ContinentDoesNotExistException | DuplicateCountryException |
                     CountryDoesNotExistException e) {

                throw new InvalidMapException("WorldMap Features Invalid: " + e);

            }

            l_state[0] = false;

            l_state[1] = false;

            l_state[2] = false;

        }

        return map;


    }

    /**
     * Saves the provided map to a file with the given file name.
     *
     * @param p_file_name The name of the file to save the map to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveMap(String p_file_name) throws IOException {

        WorldMap p_map = GameEngine.CURRENT_MAP;

        File outputFile = new File(GameEngine.MAPS_FOLDER + p_file_name);

        TerminalRenderer.renderMessage("Was file created? " + outputFile.createNewFile());

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

    }

    /**
     * Validates the integrity of the provided map.
     *
     * @param map The WorldMap object to validate.
     * @return True if the map is valid, false otherwise.
     */
    public static boolean validateMap(WorldMap map) {

        return (map.isConnected()) && (map.isContinentConnected());

    }

    /**
     * The main method demonstrates loading a map, validating it, and then saving it to a file.
     *
     * @param args The command-line arguments (not used in this method).
     * @throws IOException If an I/O error occurs while loading or saving the map.
     */
    public static void main(String[] args) throws IOException, CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, InvalidMapException {

        WorldMap map = MapInterface.loadMap("usa9.map");

        System.out.println(MapInterface.validateMap(map));

        MapInterface.saveMap("test_out.map");

    }

}