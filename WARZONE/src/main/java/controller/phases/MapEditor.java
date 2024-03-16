package controller.phases;

import controller.GameEngine;
import views.TerminalRenderer;

public class MapEditor extends Phase {
    public MapEditor(GameEngine g) {
        super(g);
        this.d_stateName = "Map Editor";
    }

    @Override
    public void displayMenu() {
        TerminalRenderer.renderMessage("current game phase: " + this.d_stateName);
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

    }

    public void promptUserForMap() {


    }


    public void promptUserForEditorCommand() {


    }


}







