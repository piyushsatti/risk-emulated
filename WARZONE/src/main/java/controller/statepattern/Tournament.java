package controller.statepattern;

import controller.GameEngine;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;

public class Tournament extends Phase{
    /**
     * Constructs a Phase with the specified GameEngine.
     *
     * @param gameEngine The GameEngine object managing the game.
     */
    public Tournament(GameEngine gameEngine) {

        super(gameEngine);
        d_phaseName = "Tournament Phase";
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
    public void run() throws CountryDoesNotExistException, InvalidCommandException {

    }
}
