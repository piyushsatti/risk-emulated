package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.End;
import controller.statepattern.MapEditor;
import controller.statepattern.Phase;

public class Startup extends Phase {
    String[] menu_options = {"showmap", "loadmap", "gameplayer", "assigncountries", "exit"};

    public Startup(GameEngine ge) {
        super(ge);
        displayMenu();
    }

    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMenu(
                "Game Menu",
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
        String in = this.d_ge.d_renderer.renderUserInput(">>> ");

        if (in.equals("exit")) {
            d_ge.setCurrentState(new End(d_ge));
            return;
        }

        StartupCommands suc = new StartupCommands(in);
        suc.execute(this.d_ge);
    }
}