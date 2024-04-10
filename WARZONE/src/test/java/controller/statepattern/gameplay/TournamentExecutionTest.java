package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.TournamentCommands;
import controller.statepattern.Tournament;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class contains test cases for the TournamentExecution class.
 */
public class TournamentExecutionTest {

    /**
     * Test case for the run method of TournamentExecution class.
     *
     * @throws CountryDoesNotExistException If the specified country does not exist.
     * @throws InvalidCommandException      If the command is invalid.
     */
    @Test
    public void run() throws CountryDoesNotExistException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        l_ge.setTournamentMode(true);
        l_ge.setCurrentState(new Tournament(l_ge));
        String l_cmd = "tournament -M usa9.map -P benevolent aggressive -G 2 -D 10";
        TournamentCommands l_tc = new TournamentCommands(l_cmd);
        l_tc.execute(l_ge);
        l_ge.setTournamentMode(true);
        l_ge.setCurrentState(new TournamentExecution(l_ge));
        l_ge.getCurrentState().run();
        l_ge.setCurrentState(new Reinforcement(l_ge));
            l_ge.getCurrentState().run();
            l_ge.setCurrentState(new IssueOrder(l_ge));
            l_ge.getCurrentState().run();
            l_ge.setCurrentState(new OrderExecution(l_ge));
            l_ge.getCurrentState().run();




    }
}