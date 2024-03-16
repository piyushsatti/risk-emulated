package controller;

import controller.statepattern.End;
import controller.statepattern.Phase;
import controller.statepattern.Starting;
import models.Player;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

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

    public GameEngine()
    {
        this.d_current_phase = new Starting(this);
        d_maps_folder = "WARZONE/src/main/resources/maps/";
        d_renderer = new TerminalRenderer(this);
        d_worldmap = new WorldMap();
        d_players = new ArrayList<>();
    }

    public static void main(String[] args) {
        GameEngine testEngine = new GameEngine();
        testEngine.runState();
    }

    public void setCurrentState(Phase p_p) {
        this.d_current_phase = p_p;
    }

    public void runState() {
        while (this.d_current_phase.getClass() != End.class) {
            this.d_current_phase.run();
        }
    }

}