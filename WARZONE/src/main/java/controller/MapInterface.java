package main.java.controller;

import java.io.File;
import java.io.IOException;

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

    public static void loader(String p_map_name) {
        try {
            createFileObjectFromFileName(p_map_name);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        MapInterface.loader("usa8");
    }
}
