package main.java.controller;

import main.java.models.worldmap.Continent;
import main.java.models.worldmap.WorldMap;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class MapInterface {

    private static File createFileObjectFromFileName(String p_map_name) throws FileNotFoundException {
        File map_file_obj = new File(
                "WARZONE/src/main/resources/maps/" +
                        p_map_name + "/" +
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
                map.addContinent(i, split_data[0]);
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

        File inputFile = new File(p_file_name);
        File tempFile = new File("WARZONE/SRC/resources/maps/temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String current_line;
        StringBuilder added_line = new StringBuilder();
        Boolean[] state = {false, false, false};
        int i = 1;

        while (true) {

            current_line = reader.readLine();
            if (current_line == null) break;

            if (!state[0] && !state[1] && !state[2]) {
                added_line.append(current_line);
            }

            if (current_line.equals("[continents]")) {
                state[0] = true;
                state[1] = false;
                state[2] = false;
            } else if (current_line.equals("[countries]")) {
                state[0] = false;
                state[1] = true;
                state[2] = false;
            } else if (current_line.equals("[borders]")) {
                state[0] = false;
                state[1] = false;
                state[2] = true;
            } else if (current_line.isEmpty()) {
                state[0] = false;
                state[1] = false;
                state[2] = false;
            } else {
                if (state[0]) {
                    HashMap<Integer, Continent> continents_all = p_map.getContinents();
                    for (Continent continent_obj : continents_all.values()) {
                        added_line.append(continent_obj.d_continentName).append(" ").append(continent_obj.getBonus()).append("\n");
                    }
                } else if (state[1]) {

                } else if (state[2]) {

                } else {
                    state[0] = false;
                    state[1] = false;
                    state[2] = false;
                }
            }
            writer.write(added_line.toString());
        }

        writer.close();
        reader.close();

        if (!inputFile.delete()) {
            System.out.println("Could not delete original file.");
            return;
        }

        if (!tempFile.renameTo(new File("WARZONE/SRC/resources/mapsinputFile.map"))) {
            System.out.println("Could not rename temporary file.");
        }
    }

    public static boolean validateMap(WorldMap map) {
        return (map.isConnected()) && (map.isContinentConnected());
    }

    public static void main(String[] args) throws FileNotFoundException {
        WorldMap map = MapInterface.loadMap("usa8");
        System.out.println(MapInterface.validateMap(map));
    }
}