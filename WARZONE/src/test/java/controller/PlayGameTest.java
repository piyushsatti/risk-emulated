package controller;


import models.Player;
import models.worldmap.WorldMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.FileNotFoundException;
import java.util.ArrayList;


public class PlayGameTest {
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
            if(l_player.getAssignedCountries().size()==l_min_count||l_player.getAssignedCountries().size()==l_min_count+1){
                flag = true;
            }
            else{
                flag=false;
            }
        }

        Assert.assertTrue(flag);


    }


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
            if(l_player.getReinforcements()==3||l_player.getReinforcements() == l_player.getAssignedCountries().size() / 3){
                flag = true;
            }
            else flag=false;
        }

        Assert.assertTrue(flag);

    }

    @Test
    public void checkDeployementvalidation(){

        int l_numberTobeDeployed = Integer.MAX_VALUE;

        for (Player l_player : GameEngine.PLAYER_LIST) {
            Assert.assertFalse(l_player.deployment_validator(l_numberTobeDeployed));
        }

    }
}
