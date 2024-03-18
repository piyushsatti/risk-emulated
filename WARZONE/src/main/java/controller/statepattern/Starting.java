package controller.statepattern;

import controller.GameEngine;
import controller.statepattern.gameplay.Startup;
import helpers.TerminalColors;

public class Starting extends Phase {
    public Starting(GameEngine g) {
        super(g);
        d_phaseName = "Starting Phase";
        displayMenu();
    }

    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMessage("Current Phase : " +
                TerminalColors.ANSI_PURPLE + this.d_phaseName +
                TerminalColors.ANSI_RESET
        );
        String[] menu_options = {"mapeditor", "playgame", "exit"};
        d_ge.d_renderer.renderMenu("Starting Menu", menu_options);
    }

    @Override
    public void next() {
    }

    @Override
    public void endGame() {
    }

    @Override
    public void run() {

        String in = d_ge.d_renderer.renderUserInput(">>> ");

        switch (in) {
            case "mapeditor":
                d_ge.setCurrentState(new MapEditor(d_ge));
                break;
            case "playgame":
                d_ge.setCurrentState(new Startup(d_ge));
                break;
            case "exit":
                d_ge.setCurrentState(new End(d_ge));
                break;
            default:
                d_ge.d_renderer.renderError("Invalid selection!");
        }
    }
}
