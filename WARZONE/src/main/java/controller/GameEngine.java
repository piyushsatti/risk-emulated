package controller;

import controller.statepattern.End;
import controller.statepattern.Phase;
import controller.statepattern.Starting;
import controller.statepattern.gameplay.IssueOrder;
import controller.statepattern.gameplay.OrderExecution;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.Startup;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;

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
    public TerminalRenderer d_renderer;
    public WorldMap d_worldmap;
    private Phase d_current_phase;

    /**
     * Constructs a new GameEngine object.
     * Initializes the current phase to Starting phase, sets the maps folder,
     * initializes the renderer, world map, and player list.
     */
    public GameEngine()
    {
        this.d_current_phase = new Starting(this);
        d_maps_folder = "C:\\Users\\HP\\Documents\\MACS PROJECTS\\soen6441 updated mvc final\\risk-emulated\\WARZONE\\src\\main\\resources\\maps\\";
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
     * Sets the current phase of the game engine.
     *
     * @param p_phase The phase to set as the current phase.
     */
    public void setCurrentState(Phase p_phase) {
        this.d_current_phase = p_phase;
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
     * Runs a test of the game engine's gameplay.
     * It sets up and runs the phases of the game: Startup, IssueOrder, Reinforcement, and OrderExecution.
     *
     * @throws CountryDoesNotExistException If a country does not exist.
     * @throws InvalidCommandException      If an invalid command is encountered.
     */
    public void runGameplayTest() throws CountryDoesNotExistException, InvalidCommandException {
        this.setCurrentState(new Startup(this));
        this.d_current_phase.run();
        this.setCurrentState(new IssueOrder(this));
        this.d_current_phase.run();
        this.setCurrentState(new Reinforcement(this));
        this.d_current_phase.run();
        this.setCurrentState(new OrderExecution(this));
        this.d_current_phase.run();
       ;
    }

    /**
     * Resets the world map of the game engine.
     */
    public void resetMap(){
        d_worldmap = new WorldMap();
    }

}