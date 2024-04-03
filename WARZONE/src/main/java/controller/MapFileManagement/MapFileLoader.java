package controller.MapFileManagement;
import controller.GameEngine;
import java.io.File;
import java.util.Scanner;

public class MapFileLoader {

    File d_mapFile;

    public MapFileLoader(GameEngine p_ge, String p_mapName) {

        File l_map_file_obj = new File(p_ge.d_maps_folder + p_mapName);

        if (l_map_file_obj.exists() && !l_map_file_obj.isDirectory()) {
            d_mapFile = l_map_file_obj;
        } else {
            p_ge.d_renderer.renderMessage("File does not exist");
        }
    }

    public boolean fileLoaded(){
        return this.d_mapFile != null;
    }

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
