package controller.states;

import controller.GameEngine;

public class StateDriver {

    public static void main(String[] args){
        GameEngine testEngine = new GameEngine();
        testEngine.runState();
    }
}