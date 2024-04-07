package controller;

import controller.statepattern.End;
import controller.statepattern.Phase;
import controller.statepattern.Starting;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEngine class manages the main logic of the game, including handling game phases, user input, and game loops.
 * It coordinates between the map editor, gameplay, and other settings.
 */
public class GameEngine {

    /**
     * The folder where map files are stored.
     */
    public String d_maps_folder;
    /**
     * List of players in the game.
     */
    public ArrayList<Player> d_players;
    /**
     * Represents the terminal renderer for displaying game information.
     */
    public TerminalRenderer d_renderer;

    /**
     * Represents the world map used in the game.
     */
    public WorldMap d_worldmap;

    /**
     * Represents the current phase of the game.
     */
    private Phase d_current_phase;

    /**
     * The number of turns played in the game.
     */
    private int d_numberOfTurns = 0;

    /**
     * Indicates whether the game is in tournament mode.
     */
    private boolean d_tournamentMode = false;

    /**
     * Indicates whether a tournament winner has been found.
     */
    private boolean d_tournamentWinnerFound = false;

    /**
     * List of input strategies used in the tournament mode.
     */
    private List<String> d_inputStrategiesTournament = new ArrayList<>();

    /**
     * List of map files used in the tournament mode.
     */
    private List<String> d_mapFilesTournament = new ArrayList<>();

    /**
     * Number of games to be played in the tournament mode.
     */
    private int d_numberGamesTournament;

    /**
     * Maximum number of turns allowed per game in the tournament mode.
     */
    private int d_maxTurns;

    /**
     * Retrieves the list of input strategies used in the tournament mode.
     *
     * @return The list of input strategies.
     */
    public List<String> getInputStrategiesTournament() {
        return d_inputStrategiesTournament;
    }

    /**
     * Sets the list of input strategies used in the tournament mode.
     *
     * @param d_inputStrategiesTournament The list of input strategies to be set.
     */
    public void setInputStrategiesTournament(List<String> d_inputStrategiesTournament) {
        this.d_inputStrategiesTournament = d_inputStrategiesTournament;
    }

    /**
     * Retrieves the list of map files used in the tournament mode.
     *
     * @return The list of map files.
     */
    public List<String> getMapFilesTournament() {
        return d_mapFilesTournament;
    }

    /**
     * Sets the list of map files used in the tournament mode.
     *
     * @param d_mapFilesTournament The list of map files to be set.
     */
    public void setMapFilesTournament(List<String> d_mapFilesTournament) {
        this.d_mapFilesTournament = d_mapFilesTournament;
    }

    /**
     * Retrieves the number of games to be played in the tournament mode.
     *
     * @return The number of games.
     */
    public int getNumberGamesTournament() {
        return d_numberGamesTournament;
    }

    /**
     * Sets the number of games to be played in the tournament mode.
     *
     * @param d_numberGamesTournament The number of games to be set.
     */
    public void setNumberGamesTournament(int d_numberGamesTournament) {
        this.d_numberGamesTournament = d_numberGamesTournament;
    }

    /**
     * Retrieves the maximum number of turns allowed per game in the tournament mode.
     *
     * @return The maximum number of turns.
     */
    public int getMaxTurns() {
        return d_maxTurns;
    }

    /**
     * Sets the maximum number of turns allowed per game in the tournament mode.
     *
     * @param d_maxTurns The maximum number of turns to be set.
     */
    public void setMaxTurns(int d_maxTurns) {
        this.d_maxTurns = d_maxTurns;
    }

    /**
     * Constructs a new GameEngine object.
     * Initializes the current phase to Starting phase, sets the maps folder,
     * initializes the renderer, world map, and player list.
     */
    public GameEngine()
    {
        this.d_current_phase = new Starting(this);
        d_maps_folder ="risk-emulated/WARZONE/src/main/resources/maps/";
        d_renderer = new TerminalRenderer(this);
        d_worldmap = new WorldMap();
        d_players = new ArrayList<>();
    }

    /**
     * The main method serves as the entry point for the GameEngine program.
     * It creates a new GameEngine object, runs the state of the game engine.
     *
     * @param args Command-line arguments
     * @throws CountryDoesNotExistException If a country does not exist.
     * @throws InvalidCommandException      If an invalid command is encountered.
     */
    public static void main(String[] args) throws CountryDoesNotExistException, InvalidCommandException {
        GameEngine testEngine = new GameEngine();
        testEngine.runState();
    }
    /**
     * Retrieves the number of turns played in the game.
     *
     * @return The number of turns played.
     */
    public int getNumberOfTurns() {
        return d_numberOfTurns;
    }

    /**
     * Sets the number of turns played in the game.
     *
     * @param d_numberOfTurns The number of turns to set.
     */
    public void setNumberOfTurns(int d_numberOfTurns) {
        this.d_numberOfTurns = d_numberOfTurns;
    }

    /**
     * Sets the current phase of the game engine.
     *
     * @param p_phase The phase to set as the current phase.
     */
    public void setCurrentState(Phase p_phase) {
        this.d_current_phase = p_phase;
    }

    /**
     * Get the current phase of the game engine.
     * @return the current phase of the game engine.
     */
    public Phase getCurrentState() {
        return this.d_current_phase;
    }

    /**
     * Checks if a tournament winner has been found.
     *
     * @return true if a tournament winner has been found, false otherwise.
     */
    public boolean isTournamentWinnerFound() {
        return d_tournamentWinnerFound;
    }

    /**
     * Sets the status indicating if a tournament winner has been found.
     *
     * @param tournamentWinnerFound The status indicating if a tournament winner has been found.
     */
    public void setTournamentWinnerFound(boolean tournamentWinnerFound) {
        d_tournamentWinnerFound = tournamentWinnerFound;
    }
    /**
     * Checks if the game is in tournament mode.
     *
     * @return true if the game is in tournament mode, false otherwise.
     */
    public boolean isTournamentMode() {
        return d_tournamentMode;
    }

    /**
     * Sets the status indicating if the game is in tournament mode.
     *
     * @param tournamentMode The status indicating if the game is in tournament mode.
     */
    public void setTournamentMode(boolean tournamentMode) {
        d_tournamentMode = tournamentMode;
    }

    /**
     * Runs the current state of the game engine until the End phase is reached.
     *
     * @throws CountryDoesNotExistException If a country does not exist.
     * @throws InvalidCommandException      If an invalid command is encountered.
     */
    public void runState() throws CountryDoesNotExistException, InvalidCommandException {
        while (this.d_current_phase.getClass() != End.class) {
            this.d_current_phase.run();
        }
    }

    /**
     * Resets the world map of the game engine.
     */
    public void resetMap(){
        d_worldmap = new WorldMap();
    }

}
