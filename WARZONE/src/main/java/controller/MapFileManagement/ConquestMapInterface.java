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

public class ConquestMapInterface {

    public WorldMap loadConquestMap(GameEngine p_gameEngine, MapFileLoader mfl){

        WorldMap l_worldMap = new WorldMap();
        Scanner l_file_reader = null;

        for(int i = 0; i < 3; i++) {

            try {
                l_file_reader = new Scanner(mfl.d_mapFile);
            } catch (Exception e) {
                p_gameEngine.d_renderer.renderError("File does not exist!");
                return new WorldMap();
            }


            try {
                switch (i) {
                    case 0 -> loadContinents(l_file_reader, l_worldMap);
                    case 1 -> loadCountries(l_file_reader, l_worldMap);
                    case 2 -> loadBorders(l_file_reader, l_worldMap);
                }
            } catch (Exception e) {
                p_gameEngine.d_renderer.renderError("Invalid map!");
                return new WorldMap();
            }
        }

        return l_worldMap;
    }






    void loadContinents(Scanner p_scan, WorldMap p_map) throws ContinentAlreadyExistsException {

        while(p_scan.hasNextLine()){

            if(p_scan.nextLine().trim().equals("[Continents]")){

                while (p_scan.hasNextLine()){
                    String l_input = p_scan.nextLine();
                    if(l_input.isBlank()) return;
                    String[] l_split = l_input.split("=");
                    p_map.addContinent(replaceSpaces(l_split[0]), Integer.parseInt(l_split[1]));
                }

            }
        }
    }

    void loadCountries(Scanner p_scan, WorldMap p_map) throws ContinentDoesNotExistException, DuplicateCountryException {

        while(p_scan.hasNextLine()){

            if(p_scan.nextLine().trim().equals("[Territories]")){

                while (p_scan.hasNextLine()){
                    String l_input = p_scan.nextLine();
                    if(l_input.isBlank()) continue;
                    String[] l_split = l_input.split(",");
                    p_map.addCountry(replaceSpaces(l_split[0]),p_map.getContinentID(replaceSpaces(l_split[3])));
                }

            }
        }

    }


    void loadBorders(Scanner p_scan, WorldMap p_map) throws CountryDoesNotExistException {

        while(p_scan.hasNextLine()){

            if(p_scan.nextLine().trim().equals("[Territories]")){

                while (p_scan.hasNextLine()){
                    String l_input = p_scan.nextLine();
                    if(l_input.isBlank()) continue;
                    String[] l_split = l_input.split(",");

                    for(int i = 4; i < l_split.length; i++){
                        p_map.addBorder(p_map.getCountryID(replaceSpaces(l_split[0])), p_map.getCountryID(replaceSpaces(l_split[i])));
                    }

                }
            }
        }

    }


    public void saveConquestMap(GameEngine p_gameEngine, String p_FileName){
        WorldMap p_map = p_gameEngine.d_worldmap;
        File outputFile = new File(p_gameEngine.d_maps_folder + p_FileName);

        try {
            p_gameEngine.d_renderer.renderMessage("Was file created? " + outputFile.createNewFile());
        }catch (Exception e){
            p_gameEngine.d_renderer.renderError("Save map failed!");
            return;
        }

        String file_signature = "[Map]\n" +
                "author=Sean O'Connor\n" +
                "warn=yes image=Africa.bmp\n" +
                "wrap=no\n" +
                "scroll=horizontal\n";

        BufferedWriter writer = null;
        try {
             writer = new BufferedWriter(new FileWriter(outputFile));
        }catch (Exception e){
            p_gameEngine.d_renderer.renderError("Save map failed!");
            return;
        }

        StringBuilder added_line = new StringBuilder();
        added_line.append(file_signature);
        added_line.append("\n[Continents]\n");

        for (Continent continent_obj : p_map.getContinents().values()) {
            added_line.append(continent_obj.d_continentName)
                    .append("=").append(continent_obj.getBonus()).append("\n");
        }

        added_line.append("\n[Territories]\n");

        for(Continent continent_obj : p_map.getContinents().values()){
            HashMap<Integer, Country> l_hash = p_map.getContinentCountries(continent_obj);
            for(Country l_c:l_hash.values()){
                added_line.append(l_c.getCountryName());
                added_line.append(",0,0,");
                if(!l_c.getBorders().isEmpty()) added_line.append(l_c.getContinent().getContinentName()).append(",");
                boolean first = true;
                for(Border l_b: l_c.getBorders().values()){
                    if(!first) added_line.append(",");
                    added_line.append(l_b.getTarget().getCountryName());
                    first = false;
                }
                added_line.append("\n");
            }
            added_line.append("\n");
        }

        try {
            writer.write(added_line.toString());
            writer.close();
        }catch (Exception e){
            p_gameEngine.d_renderer.renderError("Save map failed!");
            return;
        }
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        Logger lw = new Logger(logEntryBuffer);
        logEntryBuffer.setString("saved map :" + p_FileName);
    }


    public String replaceSpaces(String input){
        return input.replace(' ', '_');
    }
}