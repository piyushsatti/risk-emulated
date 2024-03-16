package controller.statepattern;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.gameplay.IssueOrder;
import controller.statepattern.gameplay.OrderExecution;
import models.Player;

public class StateCommandDriver {


    public static void main(String[] args){

        //loadmap
        GameEngine testEngine = new GameEngine();
        try {
            MapInterface.loadMap2(testEngine, "usa9.map");
        }catch (Exception e){
            System.out.println(e);
        }

        //add players
        testEngine.d_players.add(new Player("abc"));
        testEngine.d_players.add(new Player("def"));
        testEngine.d_players.add(new Player("ghi"));

        testEngine.runGameplayTest();



    }


}
