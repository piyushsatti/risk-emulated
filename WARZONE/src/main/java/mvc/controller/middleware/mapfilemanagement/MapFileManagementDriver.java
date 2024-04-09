package mvc.controller.middleware.mapfilemanagement;

import mvc.controller.GameEngine;

/**
 * MapFileManagementDriver is a driver class to demonstrate map file management functionalities.
 */
public class MapFileManagementDriver {

    /**
     * The main method to run the driver program.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        MapFileLoader mfl = new MapFileLoader(l_ge, "usa9.map");
        System.out.println(mfl.fileLoaded()); //checking if map file was loaded
        System.out.println(mfl.isConquest()); //checking if loaded map is in conquest format
        mfl = new MapFileLoader(l_ge, "Africa.map");
        ConquestMapInterface l_cmi = new ConquestMapInterface();
        l_ge.d_worldmap = l_cmi.loadConquestMap(l_ge, mfl); //load conquest map
        try {
            l_mp.saveMap(l_ge, "AfricaDomination.map");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        l_ge.d_renderer.showMap(false);
        mfl = new MapFileLoader(l_ge, "usa9.map");

        l_ge.d_worldmap = l_mp.loadMap(l_ge, mfl);
        l_cmi.saveConquestMap(l_ge, "usa9Conquest.map"); //saving conquest map


    }
}
