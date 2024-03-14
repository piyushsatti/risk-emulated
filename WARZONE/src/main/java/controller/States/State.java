package controller.States;

import controller.GameEngine;

public abstract class State {

    GameEngine ge;

    public State(GameEngine g){
        this.ge = g;
    }
    abstract public void displayMenu();
    abstract public void userInput();
    abstract public void next();
    abstract public void endGame();
    abstract  public void run();
}
