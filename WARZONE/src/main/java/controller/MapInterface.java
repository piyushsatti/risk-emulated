package main.java.controller;

import main.java.models.map.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MapInterface {
    private static File d_map_file_obj;
    private static void createFileObjectFromFileName(String p_map_name) {
        try {
            d_map_file_obj = new File(
                    "WARZONE/src/main/resources/maps/" +
                            p_map_name + "/" +
                            p_map_name + "regions.map"
            );
            if (d_map_file_obj.exists() && !d_map_file_obj.isDirectory()) {
                System.out.println(
                        "Successfully Loaded Map: " +
                                d_map_file_obj.getName()
                );
            } else {
                throw new Exception("File Does not Exist.");
            }
        } catch (IOException e) {

            System.out.println(
                    "IOException: \n" + e
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map loader(String p_map_name) {
        Scanner file_reader = null;
        Map map = new Map();
        try {
            createFileObjectFromFileName(p_map_name);
            file_reader = new Scanner(d_map_file_obj);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }
        String data;
        String[] split_data;
        Boolean[] state = {false, false, false};
        int i = 1;
        while (true) {
            assert file_reader != null;
            if (!file_reader.hasNextLine()) break;
            data = file_reader.nextLine();
            /*
              Manages the state of the file reading program
             */
            switch (data) {
                case "[continents]" -> {
                    state[0] = true;
                    state[1] = false;
                    state[2] = false;
                    continue;
                }
                case "[countries]" -> {
                    state[0] = false;
                    state[1] = true;
                    state[2] = false;
                    continue;
                }
                case "[borders]" -> {
                    state[0] = false;
                    state[1] = false;
                    state[2] = true;
                    continue;
                }
                case "" -> {
                    state[0] = false;
                    state[1] = false;
                    state[2] = false;
                }
            }
            /*
              Executes object initialization based on current state
             */
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

    public static void main(String[] args) {
        Map map = MapInterface.loader("usa8");
        System.out.println(map.d_continents.values());
        System.out.println(map.d_countries.values());
    }
}
