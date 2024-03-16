package controller.statepattern;

import controller.GameEngine;

public class MainMenu extends Phase {


    String[] menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};

    public MainMenu(GameEngine ge) {
        super(ge);
    }

    @Override
    public void displayMenu() {
        d_ge.renderer.renderWelcome();
        d_ge.renderer.renderMenu(
                "Main Menu",
                menu_options
        );
    }


    @Override
    public void next() {
        this.d_ge.setCurrentState(new MapEditor(d_ge));
    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        displayMenu();

    }

    @Override
    public void processInput(String input) {

        if (input.strip().toLowerCase().startsWith("showmap")) {
            System.out.println("run showmap()");
        } else if (input.strip().toLowerCase().startsWith("loadmap")) {
            System.out.println("run loadmap()");
        } else if (input.strip().toLowerCase().startsWith("gameplayer")) {
            System.out.println("run gameplayer()");
        } else if (input.strip().toLowerCase().startsWith("assigncountries")) {
            System.out.println("run assigncountries()");
        } else if (input.strip().toLowerCase().startsWith("exit")) {
            d_ge.setCurrentState(new End(d_ge));
        } else {
            System.out.println("Invalid");
        }
    }


    public void showmap() {

    }
}


//Display Menu
//Get User Input
//Create Command
//Execute Command