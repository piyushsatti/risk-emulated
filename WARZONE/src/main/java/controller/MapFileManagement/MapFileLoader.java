package controller.MapFileManagement;
import controller.GameEngine;
import java.io.File;
import java.util.Scanner;

/**
 * MapFileLoader is a class responsible for loading map files.
 */
public class MapFileLoader {
    File d_mapFile;

    /**
     * Constructs a MapFileLoader object with the specified game engine and map name.
     * @param p_ge The game engine.
     * @param p_mapName The name of the map file to load.
     */
    public MapFileLoader(GameEngine p_ge, String p_mapName) {

        File l_map_file_obj = new File(p_ge.d_maps_folder + p_mapName);

        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory()) {
            d_mapFile = l_map_file_obj;
        }
    }

    /**
     * Checks if the file has been successfully loaded.
     * @return True if the file has been loaded, false otherwise.
     */
    public boolean fileLoaded(){
        return this.d_mapFile != null;
    }

    /**
     * Checks if the loaded map file is in the Conquest format.
     * @return True if the map is in the Conquest format, false otherwise.
     */
    public boolean isConquest() {

        Scanner l_file_reader = null;
        try {
            l_file_reader = new Scanner(this.d_mapFile);
        }
        catch(Exception ignored){}

        String l_firstToken = l_file_reader.nextLine().trim();
        return l_firstToken.equals("[Map]");

    }

}
