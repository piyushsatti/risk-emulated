package controller.statepattern;

import controller.GameEngine;

import java.util.Scanner;

public abstract class Phase {

    GameEngine d_ge;
    String d_phaseName;

    public Phase(GameEngine g) {
        this.d_ge = g;
    }

    abstract public void displayMenu();

    abstract public void next();

    abstract public void endGame();

    abstract public void run();

    abstract public void processInput(String input);
}
