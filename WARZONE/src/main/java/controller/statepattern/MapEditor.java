package controller.statepattern;

import controller.GameEngine;
import controller.middleware.commands.MapEditorCommands;

public class MapEditor extends Phase {
    public MapEditor(GameEngine g) {
        super(g);
        displayMenu();
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
        String in = this.d_ge.d_renderer.renderUserInput(">>> ");

        if (in.equals("exit")) {
            d_ge.setCurrentState(new End(d_ge));
            return;
        }

        MapEditorCommands mec = new MapEditorCommands(in);
        mec.execute(this.d_ge);
    }
}