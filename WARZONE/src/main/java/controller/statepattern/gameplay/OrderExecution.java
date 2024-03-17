package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.statepattern.Phase;

public class OrderExecution extends Phase {
    public OrderExecution(GameEngine g) {
        super(g);
    }

    @Override
    public void displayMenu() {

    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        System.out.println("In order execution phase");


    }
}
