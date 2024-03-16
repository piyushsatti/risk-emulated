//package models.worldmap;
//
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// * The WorldMapTest class contains unit tests for the WorldMap class.
// * It includes test methods to check if the world map is connected overall
// * and if continents within the map are connected.
// */
//public class WorldMapTest {
//
//    /**
//     * Tests whether the world map is connected overall.
//     * Verifies if all countries and continents in the map are connected.
//     */
//    @Test
//    public void isConnected() {
//
//        WorldMap l_testWorldMap = new WorldMap();
//
//        try {
//            l_testWorldMap.addContinent(1, "X", 0);
//            l_testWorldMap.addContinent(2, "Y", 0);
//            l_testWorldMap.addCountry(3,1,"A");
//            l_testWorldMap.addCountry(4,1,"B");
//            l_testWorldMap.addCountry(5,2,"C");
//            l_testWorldMap.addCountry(6,2,"D");
//            l_testWorldMap.addBorder(3,4);
//            l_testWorldMap.addBorder(4,5);
//            l_testWorldMap.addBorder(5,6);
//            l_testWorldMap.addBorder(6,3);
//            Assert.assertTrue(l_testWorldMap.isConnected());
//
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//        Assert.assertTrue(l_testWorldMap.isConnected());
//    }
//
//    /**
//     * Tests whether continents in the world map are connected internally.
//     * Verifies if all countries within each continent are connected.
//     */
//    @Test
//    public void isContinentConnected() {
//        WorldMap l_testWorldMap = new WorldMap();
//
//        try {
//            l_testWorldMap.addContinent(1, "X", 0);
//            l_testWorldMap.addContinent(2, "Y", 0);
//            l_testWorldMap.addCountry(3,1,"A");
//            l_testWorldMap.addCountry(4,1,"B");
//            l_testWorldMap.addCountry(5,2,"C");
//            l_testWorldMap.addCountry(6,2,"D");
//            l_testWorldMap.addBorder(3,4);
//            l_testWorldMap.addBorder(4,3);
//            l_testWorldMap.addBorder(5,6);
//            l_testWorldMap.addBorder(6,5);
//
//
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//        Assert.assertTrue(l_testWorldMap.isContinentConnected());
//    }
//}