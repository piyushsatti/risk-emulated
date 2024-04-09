package controller.statepattern.gameplay;

import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import mvc.controller.GameEngine;
import mvc.controller.middleware.commands.TournamentCommands;
import mvc.controller.statepattern.tournament.Tournament;
import mvc.controller.statepattern.tournament.TournamentExecution;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
        String l_cmd = "tournament -M usa9.map test_map.map -P benevolent aggressive -G 2 -D 10";
        TournamentCommands l_tc = new TournamentCommands(l_cmd);
        l_tc.execute(l_ge);
        l_ge.setTournamentMode(true);
        l_ge.setCurrentState(new TournamentExecution(l_ge));
        l_ge.runState();

        String [][] l_tournament_res = l_ge.getTournamentResults();
        int l_len = l_tournament_res.length;
        int l_col = l_tournament_res[0].length;
        for(int l_i=0;l_i<l_len;l_i++)
        {
            for(int l_j=0;l_j<l_col;l_j++)
            {
                assertNotNull(l_tournament_res[l_i][l_j]);
            }
        }
    }
}