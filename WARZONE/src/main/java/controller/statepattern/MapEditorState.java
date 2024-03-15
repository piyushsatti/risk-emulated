package controller.statepattern;

import controller.GameEngine;

public class MapEditorState extends State {
    public MapEditorState(GameEngine g) {
        super(g);
    }

    @Override
    public void displayMenu() {
        ge.renderer.renderMapEditorMenu();
        ge.renderer.renderMapEditorCommands();
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

