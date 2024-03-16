package controller.phases;

import controller.GameEngine;
import views.TerminalRenderer;

import java.util.Scanner;

public class StartingMenu extends Phase {

    boolean first = true;

    public StartingMenu(GameEngine g) {
        super(g);
        this.d_stateName = "Starting Phase";
    }

    @Override
    public void displayMenu() {

        TerminalRenderer.renderMessage("current game phase: " + this.d_stateName);

        if(first) {
            TerminalRenderer.renderWelcome();
        }
        first = false;

        String[] menu_options = {"Map Editor", "Play Game"};

        TerminalRenderer.renderMenu(
                "Starting Menu",
                menu_options
        );

    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        displayMenu();
        processInput();
    }

    @Override
    public void processInput() {

        Scanner in = new Scanner(System.in);

        int user_selection;

        user_selection = in.nextInt();

        if (user_selection == 1) {

            ge.setCurrentPhase(new MapEditor(ge));

        } else if (user_selection == 2) {

            ge.setCurrentPhase(new Startup(ge));

        } else if (user_selection == 3) {

            ge.setCurrentPhase(new End(ge));

        } else {

            TerminalRenderer.renderMessage("Not an option. Try again.");

        }
    }

}