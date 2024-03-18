package controller.statepattern;

import controller.GameEngine;

public abstract class Phase {

    public GameEngine d_ge;
    public String d_phaseName;

    public Phase(GameEngine g) {
        this.d_ge = g;
    }

    abstract public void displayMenu();

    abstract public void next();

    abstract public void endGame();

    abstract public void run();

}
