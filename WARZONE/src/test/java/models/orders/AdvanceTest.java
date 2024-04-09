package models.orders;

import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.IssueOrder;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.Startup;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import org.junit.Test;
import strategy.AggressiveStrategy;
import strategy.HumanStrategy;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * The AdvanceTest class contains unit tests for the Advance class.
 */
public class AdvanceTest {


    /**
     * Tests the validateCommand method of the Advance class when the command is valid.
     */
    @Test
    public void validateCommandTests1() {
        // Test setup
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = null;
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "test_map.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();

        // Validate command
        Advance l_av = new Advance(l_ge.d_players.get(0), l_ge.d_players.get(1), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(1).getAssignedCountries().get(0), 5, l_ge);
        assertTrue(l_av.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Advance class when the command is invalid.
     *
     * @throws InvalidCommandException when entered command is invalid
     */
    @Test
    public void validateCommandTests2() throws InvalidCommandException {
        // Test setup
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = null;
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "test_map.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));

        for (Player l_player : l_ge.d_players) {
            l_player.setPlayerStrategy(new AggressiveStrategy(l_player, l_ge));
        }
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();

        // Validate command
        Advance l_av = new Advance(l_ge.d_players.get(0), l_ge.d_players.get(1), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        l_ge.d_players.get(0).addOrder(l_av);
        l_ge.d_players.get(0).issue_order();
        assertFalse(l_av.validateCommand());
    }

    /**
     * Tests the execute method of the Advance class for successfully executing the order.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void orderValidationTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        MapInterface l_mp = new MapInterface();
        GameEngine l_ge = new GameEngine();
        l_ge.d_worldmap = l_mp.loadMap(l_ge, "usa9.map");
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();

        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        l_deploy.execute();

        // Execute Advance
        Advance l_av = new Advance(l_ge.d_players.get(0), l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(1), 5, l_ge);
        l_av.execute();

        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, l_c.getReinforcements());
    }

    /**
     * Tests the move result after executing Advance order.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void advanceMove() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        // Test setup
        MapInterface l_mp = new MapInterface();
        GameEngine l_ge = new GameEngine();
        l_ge.d_worldmap = l_mp.loadMap(l_ge, "usa9.map");
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        l_deploy.execute();

        // Execute Advance
        Advance l_av = new Advance(l_ge.d_players.get(0), l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(1), 5, l_ge);
        l_av.execute();

        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, l_c.getReinforcements());
    }

    /**
     * Tests the conquer result after executing Advance orer.
     */
    @Test
    public void advanceConquerCheck() {
        // Test setup
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = null;
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "order_test.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));
        l_ge.setCurrentState(new Startup(l_ge));

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        l_deploy.execute();

        // Execute Advance
        Advance l_av = new Advance(l_ge.d_players.get(0), l_ge.d_players.get(1), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(1).getAssignedCountries().get(0), 5, l_ge);
        l_av.execute();

        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, l_c.getReinforcements());
    }
}
