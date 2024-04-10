package models.worldmap;

import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * this class has test cases to check if the map is valid or not =>
 * 1. Map is a connected graph (isConnected()) + 2. all countries within each continent are connected (isContinentConnected()).
 */
public class WorldMapTest {
    /**
     * Tests the connectivity of the map by loading a map file.
     */
    @Test
    public void mapConnectivityTest1() {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = null;
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "usa9.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        assertTrue(l_ge.d_worldmap.isConnected() && l_ge.d_worldmap.isContinentConnected());
    }

    /**
     * Tests the connectivity of a disconnected map by loading a map file.
     */
    @Test
    public void mapConnectivityTest2() {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = null;
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "disconnected.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        assertFalse(l_ge.d_worldmap.isConnected() && l_ge.d_worldmap.isContinentConnected());
    }

}