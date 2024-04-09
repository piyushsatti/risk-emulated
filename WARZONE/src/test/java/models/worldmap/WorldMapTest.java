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
     *
     * @throws CountryDoesNotExistException    if a country does not exist
     * @throws ContinentAlreadyExistsException if a continent already exists
     * @throws ContinentDoesNotExistException  if a continent does not exist
     * @throws DuplicateCountryException       if a country is duplicated
     * @throws FileNotFoundException           if the map file is not found
     */
    @Test
    public void mapConnectivityTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
<<<<<<< Updated upstream:WARZONE/src/test/java/models/worldmap/WorldMapTest.java
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "usa9.map");
        assertTrue(l_ge.d_worldmap.isConnected() && l_ge.d_worldmap.isContinentConnected());
=======
        GameEngine ge = new GameEngine();
        MapInterface mp = null;
        MapFileLoader l_mfl = new MapFileLoader(ge, "usa9.map");

        if(l_mfl.isConquest()){
            mp = new MapAdapter(new ConquestMapInterface());
        }else{
            mp = new MapInterface();
        }
        ge.d_worldmap = mp.loadMap(ge, l_mfl);
        assertTrue(ge.d_worldmap.isConnected() && ge.d_worldmap.isContinentConnected());
>>>>>>> Stashed changes:WARZONE/src/main/java/models/worldmap/WorldMapTest.java
    }

    /**
     * Tests the connectivity of a disconnected map by loading a map file.
     *
     * @throws CountryDoesNotExistException    if a country does not exist
     * @throws ContinentAlreadyExistsException if a continent already exists
     * @throws ContinentDoesNotExistException  if a continent does not exist
     * @throws DuplicateCountryException       if a country is duplicated
     * @throws FileNotFoundException           if the map file is not found
     */
    @Test
    public void mapConnectivityTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
<<<<<<< Updated upstream:WARZONE/src/test/java/models/worldmap/WorldMapTest.java
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "disconnected.map");
        assertFalse(l_ge.d_worldmap.isConnected() && l_ge.d_worldmap.isContinentConnected());
=======
        GameEngine ge = new GameEngine();
        MapInterface mp = null;
        MapFileLoader l_mfl = new MapFileLoader(ge, "usa9.map");

        if(l_mfl.isConquest()){
            mp = new MapAdapter(new ConquestMapInterface());
        }else{
            mp = new MapInterface();
        }
        ge.d_worldmap = mp.loadMap(ge, l_mfl);
        assertFalse(ge.d_worldmap.isConnected() && ge.d_worldmap.isContinentConnected());
>>>>>>> Stashed changes:WARZONE/src/main/java/models/worldmap/WorldMapTest.java
    }

}