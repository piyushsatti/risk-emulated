package models.worldmap;

import controller.GameEngine;
import controller.MapInterface;
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
    @Test
    public void mapConnectivityTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"usa9.map");
        assertTrue(ge.d_worldmap.isConnected() && ge.d_worldmap.isContinentConnected());
    }
    @Test
    public void mapConnectivityTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"disconnected.map");
        assertFalse(ge.d_worldmap.isConnected() && ge.d_worldmap.isContinentConnected());
    }

}