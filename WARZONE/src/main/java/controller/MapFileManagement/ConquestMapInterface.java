package controller.MapFileManagement;
import controller.GameEngine;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.worldmap.WorldMap;

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


    public void saveConquestMap(GameEngine p_gameEngine){

    }


    public String replaceSpaces(String input){
        return input.replace(' ', '_');
    }
}
