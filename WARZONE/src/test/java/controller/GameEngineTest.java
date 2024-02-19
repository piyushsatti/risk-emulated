package controller;

import models.Player;
import models.worldmap.WorldMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The GameEngineTest class contains unit tests for the GameEngine class.
 * It includes test methods to validate various functionalities of the game engine.
 */
public class GameEngineTest {

    /**
     * Sets up the test environment before executing each test method.
     * Initializes a test world map and adds players to the player list.
     */
    @Before
    public void setUp() {

        WorldMap l_testWorldMap = new WorldMap();

        try {
            l_testWorldMap.addContinent(1, "X", 0);
            l_testWorldMap.addContinent(2, "Y", 0);
            l_testWorldMap.addCountry(3, 1, "A");
            l_testWorldMap.addCountry(4, 1, "B");
            l_testWorldMap.addCountry(5, 2, "C");
            l_testWorldMap.addCountry(6, 2, "D");
            l_testWorldMap.addBorder(3, 4);
            l_testWorldMap.addBorder(4, 3);
            l_testWorldMap.addBorder(5, 6);
            l_testWorldMap.addBorder(6, 5);


        } catch (Exception e) {
            System.out.println(e);
        }

        GameEngine.CURRENT_MAP = l_testWorldMap;

        GameEngine.PLAYER_LIST.add(new Player("Player1"));
        GameEngine.PLAYER_LIST.add(new Player("Player2"));
    }

    /**
     * Tests the functionality to assign countries to players.
     * Verifies if countries are assigned to players correctly.
     */
    @Test
    public void assignCountriesToPlayersValidTest(){
        PlayGame.assignCountriesToPlayers(GameEngine.PLAYER_LIST);
        Assert.assertTrue(GameEngine.assignCountriesValidator());
    }

}