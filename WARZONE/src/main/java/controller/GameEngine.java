package controller;
import controller.phases.*;
import models.Player;
import models.worldmap.WorldMap;
import views.TerminalRenderer;

import java.util.ArrayList;

/**
 * The GameEngine class manages the main logic of the game, including handling game phases, user input, and game loops.
 * It coordinates between the map editor, gameplay, and other settings.
 */
public class GameEngine {





    /**
     * The folder where map files are stored.
     */
    public  String d_mapsFolder = "WARZONE/src/main/resources/maps/";

    private Phase d_currentPhase;

    /**
     * The currently loaded map.
     */
    public  WorldMap d_map;

    /**
     * List of players in the game.
     */
    public  ArrayList<Player> d_playerList;

    public TerminalRenderer renderer;

    public GameEngine(){
        d_currentPhase = new StartingMenu(this);
        d_playerList = new ArrayList<Player>();
        renderer = new TerminalRenderer(this);
    }

    public void setCurrentPhase(Phase p){
        this.d_currentPhase = p;
    }

    public void runState(){
        while(d_currentPhase.getClass() != End.class) {
            d_currentPhase.run();
        }
    }




}