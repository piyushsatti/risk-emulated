package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.TournamentCommands;
import controller.statepattern.Tournament;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TournamentExecutionTest {

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
//        l_ge.getCurrentState().run();
//        l_ge.setCurrentState(new Reinforcement(l_ge));
//        l_ge.getCurrentState().run();
//        l_ge.setCurrentState(new IssueOrder(l_ge));
//        l_ge.getCurrentState().run();
//        l_ge.setCurrentState(new OrderExecution(l_ge));
//        l_ge.getCurrentState().run();


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