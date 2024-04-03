package controller;

import controller.MapFileManagement.MapInterface;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.fail;

/**
 * This class contains JUnit tests for the MapInterface class.
 */
public class MapInterfaceTest {

    /**
     * Tests the behavior of the createFileObjectFromFileName method when provided with a non-existent file name.
     * Expects a FileNotFoundException to be thrown.
     *
     * @throws FileNotFoundException if the specified file does not exist.
     */
    @Test(expected = FileNotFoundException.class)
    public void readMapTest1() throws FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface mp = new MapInterface();
        mp.createFileObjectFromFileName(ge,"usa1.map");
    }
    /**
     * Tests the behavior of the createFileObjectFromFileName method when provided with an existing file name.
     * Expects the method to execute successfully without throwing any exceptions.
     *
     * @throws FileNotFoundException if the specified file does not exist.
     */
    @Test
    public void readMapTest2() throws FileNotFoundException {
        GameEngine ge = new GameEngine();
        try {
            MapInterface mp = new MapInterface();
            mp.createFileObjectFromFileName(ge,"usa9.map");
        } catch (Exception e) {
            fail("the map file exists, but there is an unexpected error in reading it");
        }
    }
}