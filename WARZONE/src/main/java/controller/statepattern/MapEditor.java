package controller.statepattern;

import controller.GameEngine;
import controller.middleware.commands.MapEditorCommands;

public class MapEditor extends Phase {
    public MapEditor(GameEngine g) {
        super(g);
    }

    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMapEditorCommands();
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
        MapEditorCommands mec = new MapEditorCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        mec.execute(this.d_ge);
    }
}



