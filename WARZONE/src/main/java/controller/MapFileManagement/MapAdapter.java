package controller.MapFileManagement;

import controller.GameEngine;
import models.worldmap.WorldMap;

/**
 * MapAdapter is a class that adapts ConquestMapInterface to MapInterface.
 */
public class MapAdapter extends MapInterface {
    /**
     * adaptee conquestmap interface
     */
    ConquestMapInterface d_adaptee;

    /**
     * Constructs a MapAdapter object with the given ConquestMapInterface.
     *
     * @param p_cmi The ConquestMapInterface object to be adapted.
     */
    public MapAdapter(ConquestMapInterface p_cmi) {
        d_adaptee = p_cmi;
    }

    /**
     * Saves the game map using the adapted ConquestMapInterface.
     *
     * @param p_gameEngine The game engine containing the map to save.
     * @param p_FileName   The name of the file to save.
     */
    @Override
    public void saveMap(GameEngine p_gameEngine, String p_FileName) {
        d_adaptee.saveConquestMap(p_gameEngine, p_FileName);
    }

    /**
     * Loads the game map using the adapted ConquestMapInterface.
     *
     * @param p_gameEngine The game engine to load the map into.
     * @param p_mfl        The map file loader containing the map file.
     * @return The loaded world map.
     */
    @Override
    public WorldMap loadMap(GameEngine p_gameEngine, MapFileLoader p_mfl) {
        return d_adaptee.loadConquestMap(p_gameEngine, p_mfl);
    }

}
