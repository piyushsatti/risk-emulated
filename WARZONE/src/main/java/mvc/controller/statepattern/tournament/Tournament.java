package mvc.controller.statepattern.tournament;

import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import mvc.controller.GameEngine;
import mvc.controller.middleware.commands.TournamentCommands;
import mvc.controller.statepattern.Phase;

/**
 * Represents the tournament phase of the game.
 * This phase is responsible for managing the tournament gameplay.
 */
public class Tournament extends Phase {
    /**
     * Constructs a Phase with the specified GameEngine.
     *
     * @param gameEngine The GameEngine object managing the game.
     */
    public Tournament(GameEngine gameEngine) {
        super(gameEngine);
        d_phaseName = "Starting Phase";
    }


    /**
     * Displays the tournament menu, showing the current game phase and available options.
     */
    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMessage("current game phase: " + this.d_phaseName);
        String[] l_menu_options = {"Enter valid tournament command to start tournament OR Enter exit to go back to main menu"};
        d_ge.d_renderer.renderMenu("Tournament Menu", l_menu_options);
    }

    /**
     * Moves to the next step in the tournament phase.
     */
    @Override
    public void next() {

    }

    /**
     * Ends the current game.
     */
    @Override
    public void endGame() {

    }

    /**
     * Runs the tournament phase, displaying the menu, executing user commands, and transitioning to the next phase.
     * This method is responsible for executing the tournament phase logic.
     *
     * @throws CountryDoesNotExistException If a country referenced in the game does not exist.
     * @throws InvalidCommandException      If an invalid command is entered by the user.
     */
    @Override
    public void run() throws CountryDoesNotExistException, InvalidCommandException {
        displayMenu();
        TournamentCommands l_tuc = new TournamentCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        l_tuc.execute(this.d_ge);
        d_ge.setTournamentMode(true);
        d_ge.setCurrentState(new TournamentExecution(d_ge));
    }
}
