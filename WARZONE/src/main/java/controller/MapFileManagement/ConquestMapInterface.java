package controller.MapFileManagement;

import controller.GameEngine;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.LogEntryBuffer;
import models.worldmap.Border;
import models.worldmap.Continent;
import models.worldmap.Country;
import models.worldmap.WorldMap;
import view.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * ConquestMapInterface is a class responsible for loading Conquest maps into the game engine.
 */
public class ConquestMapInterface {

    /**
     * Loads a Conquest map into the game engine.
     *
     * @param p_gameEngine The game engine to load the map into.
     * @param p_mfl        The map file loader containing the map file.
     * @return The loaded world map.
     */
    public WorldMap loadConquestMap(GameEngine p_gameEngine, MapFileLoader p_mfl) {
        WorldMap l_worldMap = new WorldMap();
        Scanner l_file_reader = null;
        for (int l_i = 0; l_i < 3; l_i++) {
            try {
                l_file_reader = new Scanner(p_mfl.d_mapFile);
            } catch (Exception e) { //map file does not exist
                p_gameEngine.d_renderer.renderError("File does not exist!");
                return new WorldMap();
            }
            try {
                switch (l_i) {
                    case 0 -> loadContinents(l_file_reader, l_worldMap); //load continents from the map file.
                    case 1 -> loadCountries(l_file_reader, l_worldMap); // load countries from the map file
                    case 2 -> loadBorders(l_file_reader, l_worldMap); // load borders from the map file
                }
            } catch (Exception e) { //map file is invalid
                p_gameEngine.d_renderer.renderError("Invalid map file!");
                return new WorldMap();
            }
        }
        return l_worldMap;
    }

    /**
     * Loads continents from the map file.
     *
     * @param p_scan The scanner object to read the file.
     * @param p_map  The world map to load continents into.
     * @throws ContinentAlreadyExistsException If a continent with the same name already exists.
     */
    void loadContinents(Scanner p_scan, WorldMap p_map) throws ContinentAlreadyExistsException {
        while (p_scan.hasNextLine()) {
            if (p_scan.nextLine().trim().equals("[Continents]")) {
                while (p_scan.hasNextLine()) {
                    String l_input = p_scan.nextLine();
                    if (l_input.isBlank()) return;
                    String[] l_split = l_input.split("=");
                    p_map.addContinent(replaceSpaces(l_split[0]), Integer.parseInt(l_split[1]));
                }
            }
        }
    }

    /**
     * Loads countries from the map file.
     *
     * @param p_scan The scanner object to read the file.
     * @param p_map  The world map to load countries into.
     * @throws ContinentDoesNotExistException If the continent specified for a country does not exist.
     * @throws DuplicateCountryException      If a country with the same name already exists.
     */
    void loadCountries(Scanner p_scan, WorldMap p_map) throws ContinentDoesNotExistException, DuplicateCountryException {

        while (p_scan.hasNextLine()) {
            if (p_scan.nextLine().trim().equals("[Territories]")) {
                while (p_scan.hasNextLine()) {
                    String l_input = p_scan.nextLine();
                    if (l_input.isBlank()) continue;
                    String[] l_split = l_input.split(",");
                    p_map.addCountry(replaceSpaces(l_split[0]), p_map.getContinentID(replaceSpaces(l_split[3])));
                }
            }
        }

    }

    /**
     * Loads borders from the map file.
     *
     * @param p_scan The scanner object to read the file.
     * @param p_map  The world map to load borders into.
     * @throws CountryDoesNotExistException If a country specified as a neighbor does not exist.
     */
    void loadBorders(Scanner p_scan, WorldMap p_map) throws CountryDoesNotExistException {

        while (p_scan.hasNextLine()) {
            if (p_scan.nextLine().trim().equals("[Territories]")) {
                while (p_scan.hasNextLine()) {
                    String l_input = p_scan.nextLine();
                    if (l_input.isBlank()) continue;
                    String[] l_split = l_input.split(",");
                    for (int l_i = 4; l_i < l_split.length; l_i++) {
                        p_map.addBorder(p_map.getCountryID(replaceSpaces(l_split[0])), p_map.getCountryID(replaceSpaces(l_split[l_i])));
                    }
                }
            }
        }

    }

    /**
     * Saves the current game map to a Conquest map file.
     *
     * @param p_gameEngine The game engine containing the map to save.
     * @param p_FileName   The name of the file to save.
     */
    public void saveConquestMap(GameEngine p_gameEngine, String p_FileName) {
        WorldMap p_map = p_gameEngine.d_worldmap;
        File l_outputFile = new File(p_gameEngine.d_maps_folder + p_FileName);
        //creating a new output file
        try {
            p_gameEngine.d_renderer.renderMessage("Was file created? " + l_outputFile.createNewFile());
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("Save map failed!");
            return;
        }
        //the file signature for the output file
        String l_file_signature = "[Map]\n" +
                "author=Sean O'Connor\n" +
                "warn=yes image=Africa.bmp\n" +
                "wrap=no\n" +
                "scroll=horizontal\n";
        BufferedWriter l_writer = null;
        try {
            l_writer = new BufferedWriter(new FileWriter(l_outputFile));
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("Save map failed!");
            return;
        }
        //from here, we are adding the map contents to the output file.
        StringBuilder l_added_line = new StringBuilder();
        l_added_line.append(l_file_signature);
        l_added_line.append("\n[Continents]\n");
        //adding continents
        for (Continent l_continent_obj : p_map.getContinents().values()) {
            l_added_line.append(l_continent_obj.d_continentName)
                    .append("=").append(l_continent_obj.getBonus()).append("\n");
        }
        //adding countries
        l_added_line.append("\n[Territories]\n");

        for (Continent l_continent_obj : p_map.getContinents().values()) {
            HashMap<Integer, Country> l_hash = p_map.getContinentCountries(l_continent_obj);
            for (Country l_c : l_hash.values()) {
                l_added_line.append(l_c.getCountryName());
                l_added_line.append(",0,0,");
                if (!l_c.getBorders().isEmpty()) l_added_line.append(l_c.getContinent().getContinentName()).append(",");
                boolean l_first = true;
                for (Border l_b : l_c.getBorders().values()) {
                    if (!l_first) l_added_line.append(",");
                    l_added_line.append(l_b.getTarget().getCountryName());
                    l_first = false;
                }
                l_added_line.append("\n");
            }
            l_added_line.append("\n");
        }

        try {
            l_writer.write(l_added_line.toString());
            l_writer.close();
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("Save map failed!");
            return;
        }
        //logging the result
        LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();
        Logger l_lw = new Logger(l_logEntryBuffer);
        l_logEntryBuffer.setString("saved map :" + p_FileName);
    }

    /**
     * Replaces spaces in a string with underscores.
     *
     * @param p_input The input string.
     * @return The modified string with spaces replaced by underscores.
     */
    public String replaceSpaces(String p_input) {
        return p_input.replace(' ', '_');
    }
}
