package controller.states;

import controller.GameEngine;

import java.util.Scanner;

public abstract class State {

    GameEngine ge;
    String d_stateName;
    public State(GameEngine g){
        this.ge = g;
    }
    abstract public void displayMenu();
    abstract public void next();
    abstract public void endGame();
    abstract  public void run();
    abstract public void processInput();
}
