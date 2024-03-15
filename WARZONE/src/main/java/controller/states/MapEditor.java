package controller.states;

import controller.GameEngine;
import views.TerminalRenderer;

public class MapEditor extends State{
    public MapEditor(GameEngine g) {
        super(g);
    }

    @Override
    public void displayMenu() {
        TerminalRenderer.renderMapEditorMenu();
        TerminalRenderer.renderMapEditorCommands();
    }

    @Override
    public String userInput() {
        return null;
    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        this.displayMenu();
        this.ge.setCurrentState(new End(ge));
        //issueOrder();
        //ExecuteOrder();

    }

    @Override
    public void processInput(String input) {

    }
}


//Command

    //MapEditCommand
        //AddCountry
        //RemovedNeighbor


    //GamePlayCommand
        //Deploy
        //Advance

