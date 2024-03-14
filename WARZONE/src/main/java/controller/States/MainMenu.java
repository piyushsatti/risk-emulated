package controller.States;
import controller.GameEngine;
import views.TerminalRenderer;

import java.util.Scanner;

public class MainMenu extends State{


    public MainMenu(GameEngine ge){
        super(ge);
    }

    String[] menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};
    @Override
    public void displayMenu() {
        TerminalRenderer.renderWelcome();
        TerminalRenderer.renderMenu(
                "Main Menu",
                menu_options
        );
    }

    @Override
    public void userInput() {

    }

    @Override
    public void next() {
        this.ge.setCurrentState(new MapEditor(ge));
    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        this.displayMenu();
        this.next();
    }
}


//Display Menu
//Get User Input
//Create Command
//Execute Command