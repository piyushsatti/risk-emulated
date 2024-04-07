package controller.statepattern;

import controller.GameEngine;
import controller.middleware.commands.StartupCommands;
import controller.middleware.commands.TournamentCommands;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.TournamentExecution;
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
        d_phaseName = "Starting Phase";
    }


    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMessage("current game phase: " + this.d_phaseName);
        String[] menu_options = {"Enter valid tournament command to start tournament OR Enter exit to go back to main menu"};
        d_ge.d_renderer.renderMenu("Tournament Menu", menu_options);
    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() throws CountryDoesNotExistException, InvalidCommandException {
        displayMenu();
        TournamentCommands tuc = new TournamentCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        tuc.execute(this.d_ge);
        d_ge.setCurrentState(new TournamentExecution(d_ge));
    }
}
