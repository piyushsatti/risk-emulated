package controller;


import models.Player;
import models.worldmap.WorldMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The PlayGameTest class contains unit tests for the PlayGame class.
 * It includes test methods to validate the assignment of countries to players,
 * the assignment of reinforcements, and deployment validation.
 */
public class PlayGameTest {
    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a test world map and adds players to the game engine.
     */
    @Before
    public void setUp()  {

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


        GameEngine.CURRENT_MAP = l_testWorldMap;



        GameEngine.PLAYER_LIST.add(new Player("Player1"));
        GameEngine.PLAYER_LIST.add(new Player("Player2"));
    }

    /**
     * Tests the assignment of countries to players and verifies that each player
     * receives a comparable number of countries.
     *
     * @throws FileNotFoundException if the file for assigning countries is not found
     */
    @Test
    public void assignCountriesToPlayersTestCount() throws FileNotFoundException {


        PlayGame.assignCountriesToPlayers(GameEngine.PLAYER_LIST);

        ArrayList<Integer> l_countriesCount = new ArrayList<>();

        int l_min_count = Integer.MAX_VALUE;

        for (Player l_player : GameEngine.PLAYER_LIST) {
            l_countriesCount.add(l_player.getAssignedCountries().size());

            if(l_min_count>l_player.getAssignedCountries().size()){
                l_min_count =  l_player.getAssignedCountries().size();
            }
        }

        boolean flag = false;
        for (Player l_player : GameEngine.PLAYER_LIST) {
            flag = l_player.getAssignedCountries().size() == l_min_count || l_player.getAssignedCountries().size() == l_min_count + 1;
        }

        Assert.assertTrue(flag);


    }


    /**
     * Tests the assignment of reinforcements to players and verifies that each player
     * receives an appropriate number of reinforcements based on the number of countries they own.
     */
    @Test
    public void assignReinforcementsValidTest() {

        PlayGame.assignCountriesToPlayers(GameEngine.PLAYER_LIST);
        PlayGame.assignReinforcements(GameEngine.PLAYER_LIST);

        ArrayList<Integer> playerReinforcementCount = new ArrayList<>();

        for (Player l_player : GameEngine.PLAYER_LIST) {
            playerReinforcementCount.add(l_player.getReinforcements());
        }

        boolean flag=false;
        for (Player l_player : GameEngine.PLAYER_LIST) {
            flag = l_player.getReinforcements() == 3 || l_player.getReinforcements() == l_player.getAssignedCountries().size() / 3;
        }

        Assert.assertTrue(flag);

    }

    /**
     * Tests Validates if assigning can be done or not
     */
    @Test
    public void assignCountriesToPlayersValidTest(){
        Assert.assertTrue(GameEngine.assignCountriesValidator());
    }

    /**
     * Tests the deployment validation for each player, ensuring that a player cannot deploy
     * more troops than they possess.
     */
    @Test
    public void checkDeployementvalidation(){

        int l_numberTobeDeployed = Integer.MAX_VALUE;

        for (Player l_player : GameEngine.PLAYER_LIST) {
            Assert.assertFalse(l_player.deployment_validator(l_numberTobeDeployed));
        }

    }
}
