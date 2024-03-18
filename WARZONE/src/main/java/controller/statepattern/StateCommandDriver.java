package controller.statepattern;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;

/**
 * The StateCommandDriver class is responsible for testing the game engine's functionality
 * by simulating gameplay with different commands and states.
 */
public class StateCommandDriver {

    /**
     * The main method serves as the entry point for the StateCommandDriver program.
     * It tests the game engine's functionality by simulating gameplay with different commands and states.
     *
     * @param args Command-line arguments
     * @throws CountryDoesNotExistException If a country does not exist.
     * @throws InvalidCommandException      If an invalid command is encountered.
     */
    public static void main(String[] args) throws CountryDoesNotExistException, InvalidCommandException {

        //loadmap
        GameEngine testEngine = new GameEngine();
        try {
            MapInterface.loadMap2(testEngine, "usa9.map");
        } catch (Exception e) {
            System.out.println(e);
        }

        //add players
        testEngine.d_players.add(new Player("abc", testEngine));
        testEngine.d_players.add(new Player("def", testEngine));
        testEngine.d_players.add(new Player("ghi", testEngine));

        testEngine.runGameplayTest();
    }
}
