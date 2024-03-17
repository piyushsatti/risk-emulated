package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.MapEditorCommands;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.MapEditor;
import controller.statepattern.Phase;

public class Startup extends Phase {


    String[] menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};

    public Startup(GameEngine ge) {
        super(ge);
    }

    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderWelcome();
        d_ge.d_renderer.renderMenu(
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
        StartupCommands suc = new StartupCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        suc.execute(this.d_ge);
    }
}