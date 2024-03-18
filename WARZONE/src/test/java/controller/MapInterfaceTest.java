package controller;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.fail;

public class MapInterfaceTest {

    @Test(expected = FileNotFoundException.class)
    public void readMapTest1() throws FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.createFileObjectFromFileName(ge, "usa1.map");
    }

    @Test
    public void readMapTest2() throws FileNotFoundException {
        GameEngine ge = new GameEngine();
        try {
            MapInterface.createFileObjectFromFileName(ge, "usa9.map");
        } catch (Exception e) {
            fail("the map file exists, but there is an unexpected error in reading it");
        }
    }
}