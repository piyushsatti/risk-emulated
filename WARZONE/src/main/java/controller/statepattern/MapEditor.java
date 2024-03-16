package controller.statepattern;

import controller.GameEngine;
import controller.MapInterface;
import controller.middleware.commands.MapEditorCommands;
import helpers.exceptions.InvalidMapException;

import java.io.FileNotFoundException;

public class MapEditor extends Phase {
    public MapEditor(GameEngine g) {
        super(g);
    }

    @Override
    public void displayMenu() {
    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }



    @Override
    public void run() {
        MapEditorCommands mec = new MapEditorCommands(d_ge.renderer.renderMapEditorCommands());
        mec.execute(this.d_ge);
    }

    @Override
    public void processInput(String input) {

    }


}



