package controller.phases;

import controller.GameEngine;

public abstract class Phase {

    GameEngine ge;
    String d_stateName;
    public Phase(GameEngine g){
        this.ge = g;
    }
    abstract public void displayMenu();
    abstract public void next();
    abstract public void endGame();
    abstract  public void run();
    abstract public void processInput();
}
