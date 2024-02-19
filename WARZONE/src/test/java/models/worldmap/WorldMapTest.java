package models.worldmap;

import models.worldmap.WorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

    @Test
    void isConnected() {

        WorldMap l_testWorldMap = new WorldMap();

        try {
            l_testWorldMap.addContinent(1, "X", 0);
            l_testWorldMap.addContinent(2, "Y", 0);
            l_testWorldMap.addCountry(3,1,"A");
            l_testWorldMap.addCountry(4,1,"B");
            l_testWorldMap.addCountry(5,2,"C");
            l_testWorldMap.addCountry(6,2,"D");
            l_testWorldMap.addBorder(3,4);
            l_testWorldMap.addBorder(4,5);
            l_testWorldMap.addBorder(5,6);
            l_testWorldMap.addBorder(6,3);
            assertTrue(l_testWorldMap.isConnected());

        }catch (Exception e){
            System.out.println(e);
        }

        assertTrue(l_testWorldMap.isConnected());
    }

    @Test
    void isContinentConnected() {
        WorldMap l_testWorldMap = new WorldMap();

        try {
            l_testWorldMap.addContinent(1, "X", 0);
            l_testWorldMap.addContinent(2, "Y", 0);
            l_testWorldMap.addCountry(3,1,"A");
            l_testWorldMap.addCountry(4,1,"B");
            l_testWorldMap.addCountry(5,2,"C");
            l_testWorldMap.addCountry(6,2,"D");
            l_testWorldMap.addBorder(3,4);
            l_testWorldMap.addBorder(4,3);
            l_testWorldMap.addBorder(5,6);
            l_testWorldMap.addBorder(6,5);


        }catch (Exception e){
            System.out.println(e);
        }

        assertTrue(l_testWorldMap.isContinentConnected());
    }
}