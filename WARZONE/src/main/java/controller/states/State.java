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
    public String userInput(){
        Scanner in = new Scanner(System.in);
        String user_in;
        user_in = in.nextLine();
        in.close();
        return user_in;
    }
    abstract public void next();
    abstract public void endGame();
    abstract  public void run();
    abstract public void processInput();
}
