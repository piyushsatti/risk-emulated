package main.java.controller;

import main.java.models.worldmap.Continent;
import main.java.models.worldmap.Country;
import main.java.models.worldmap.WorldMap;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class MapInterface {

    private static File createFileObjectFromFileName(String p_map_name) throws FileNotFoundException {

        File map_file_obj = new File(
                "WARZONE/src/main/resources/maps/" +
                        p_map_name + "regions.map"
        );

        if (map_file_obj.exists() && !map_file_obj.isDirectory()) {

            System.out.println(
                    "Successfully Loaded Map: " +
                            map_file_obj.getName()
            );

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

        while (true) {

            if (!file_reader.hasNextLine()) break;

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

    public static void saveMap(String p_file_name, WorldMap p_map) throws FileNotFoundException, IOException {

        File inputFile = new File(
                "WARZONE/src/main/resources/maps/" +
                        p_file_name + "regions.map"
        );

        File tempFile = new File("WARZONE/src/main/resources/maps/temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String current_line;

        StringBuilder added_line = new StringBuilder();

        Boolean[] state = {false, false, false};

        int i = 1;

        boolean flag = true;

        while (true) {

            current_line = reader.readLine();

            if (current_line == null) break;

            if (flag) added_line.append(current_line).append("\n");

            if (current_line.equals("[continents]")) {

                for (Continent continent_obj : p_map.getContinents().values()) {

                    added_line.append(continent_obj.d_continentName).append(" ").append(continent_obj.getBonus()).append(" ").append("gray").append("\n");

                }

                added_line.append("\n");

                flag = false;

            } else if (current_line.equals("[countries]")) {

                for (Country country_obj : p_map.getCountries().values()) {

                    added_line.append(country_obj.getD_countryID()).append(" ").append(country_obj.getD_countryName()).append(" ").append(country_obj.getD_continent().getD_continentID()).append(" ").append(69).append(" ").append(69).append("\n");

                }

                added_line.append("\n");

                flag = false;

            } else if (current_line.equals("[borders]")) {

                for (Integer country_id : p_map.getCountries().keySet()) {

                    HashMap<Integer, Country> border_countries = p_map.getCountry(country_id).getBorderCountries();

                    added_line.append(country_id);

                    for (Integer border_country_id : border_countries.keySet()) {

                        added_line.append(" ").append(border_country_id);

                    }

                    added_line.append("\n");

                }

                added_line.append("\n");

                flag = false;

            } else if (current_line.isEmpty()) {

                flag = true;

            }

        }

        writer.write(added_line.append("\n").toString());

        writer.close();

        reader.close();

//        if (!inputFile.delete()) {
//            System.out.println("Could not delete original file.");
//            return;
//        }

//        if (!tempFile.renameTo(new File("WARZONE/SRC/resources/mapsinputFile.map"))) {
//            System.out.println("Could not rename temporary file.");
//        }
    }

    public static boolean validateMap(WorldMap map) {
        return (map.isConnected()) && (map.isContinentConnected());
    }

    public static void main(String[] args) throws IOException {

        WorldMap map = MapInterface.loadMap("usa8");

        System.out.println(MapInterface.validateMap(map));

        MapInterface.saveMap("usa8", map);

    }

}