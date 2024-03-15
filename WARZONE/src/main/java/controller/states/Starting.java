package controller.states;

import controller.GameEngine;
import views.TerminalRenderer;

import java.util.Scanner;

public class Starting extends State {


    public Starting(GameEngine g) {
        super(g);
        this.d_stateName = "Starting Phase";
    }

    @Override
    public void displayMenu() {

        TerminalRenderer.renderMessage("current game phase: " + this.d_stateName);

        TerminalRenderer.renderWelcome();

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

        String user_in;

        user_in = in.nextLine();

        if (user_in.strip().replace(" ", "").equalsIgnoreCase("mapeditor")) {

            ge.setCurrentState(new MapEditor(ge));


        } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("playgame")) {

            ge.setCurrentState(new Setup(ge));


        } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

            ge.setCurrentState(new End(ge));

        } else {

            TerminalRenderer.renderMessage("Not an option. Try again.");

        }
    }

}