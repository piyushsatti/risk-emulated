package main.java.controller;

import main.java.models.worldmap.Continent;
import main.java.models.worldmap.Country;
import main.java.models.worldmap.WorldMap;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class MapInterface {

    private static File createFileObjectFromFileName(String p_map_name) throws FileNotFoundException {

        File map_file_obj = new File(GameEngine.MAPS_FOLDER + p_map_name);

        if (map_file_obj.exists() && !map_file_obj.isDirectory()) {

            System.out.println("Successfully Loaded Map: " + map_file_obj.getName());

            return map_file_obj;

        } else {

            throw new FileNotFoundException("File does not exist.");

        }

    }

    public static WorldMap loadMap(String p_map_name) throws FileNotFoundException, NumberFormatException {

        File map_file_obj;

        Scanner file_reader = null;

        WorldMap map = new WorldMap();

        map_file_obj = createFileObjectFromFileName(p_map_name);

        file_reader = new Scanner(map_file_obj);

        String data;

        String[] split_data;

        Boolean[] state = {false, false, false};

        int i = 1;

        while (file_reader.hasNextLine()) {

            data = file_reader.nextLine();

            if (data.equals("[continents]")) {

                state[0] = true;

                state[1] = false;

                state[2] = false;

                continue;

            } else if (data.equals("[countries]")) {

                state[0] = false;

                state[1] = true;

                state[2] = false;

                continue;

            } else if (data.equals("[borders]")) {

                state[0] = false;

                state[1] = false;

                state[2] = true;

                continue;

            } else if (data.isEmpty()) {

                state[0] = false;

                state[1] = false;

                state[2] = false;

            }

            split_data = data.split(" ");

            if (state[0]) {

                map.addContinent(i, split_data[0], Integer.parseInt(split_data[1]));

                i++;

                continue;

            } else if (state[1]) {

                map.addCountry(

                        Integer.parseInt(split_data[0]),

                        Integer.parseInt(split_data[2]),

                        split_data[1]

                );

                continue;

            } else if (state[2]) {

                for (int j = 1; j < split_data.length; j++) {

                    map.addBorder(
                            Integer.parseInt(split_data[0]),
                            Integer.parseInt(split_data[j])
                    );

                }

                continue;

            }

            state[0] = false;

            state[1] = false;

            state[2] = false;

        }

        return map;

    }

    public static void saveMap(String p_file_name, WorldMap p_map) throws IOException {

        File outputFile = new File(GameEngine.MAPS_FOLDER + p_file_name);

        if (!outputFile.exists()) {

            outputFile.createNewFile();

        }

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

            added_line.append(country_obj.getD_countryID())
                    .append(" ").append(country_obj.getD_countryName())
                    .append(" ").append(country_obj.getD_continent().getD_continentID())
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

    public static boolean validateMap(WorldMap map) {

        return (map.isConnected()) && (map.isContinentConnected());

    }

    public static void main(String[] args) throws IOException {

        // WorldMap map = MapInterface.loadMap("samplemaps/usa8regions.map");

        WorldMap map = MapInterface.loadMap("usa9.map");

        System.out.println(MapInterface.validateMap(map));

        MapInterface.saveMap("test_out.map", map);

    }

}