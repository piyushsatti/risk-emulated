package models.orders;

import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.Reinforcement;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * JUnit test class to validate the deployment command execution in the game.
 */
public class
DeployTest {

    /**
     * Test case to validate the execution of the deployment command.
     *
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests1() throws InvalidCommandException {
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

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 1, l_ge);
        assertTrue(l_deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deployment command when the player does not own the country.
     *
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests2() throws InvalidCommandException {
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

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(1).getAssignedCountries().get(0), 1, l_ge);
        assertFalse(l_deploy.validateCommand());
    }


    /**
     * Test case to validate the execution of the deployment command when the number of troops to l_deploy is negative.
     *
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests3() throws InvalidCommandException {
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

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), -10, l_ge);
        assertFalse(l_deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deployment command when the number of troops to l_deploy exceeds the available troops.
     *
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests4() throws InvalidCommandException {
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

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 100, l_ge);
        assertFalse(l_deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deployment command when the target country ID is invalid (negative).
     *
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests5() throws InvalidCommandException {
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

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), -11, 1, l_ge);
        assertFalse(l_deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the l_deploy order when deploying troops to a country.
     */
    @Test
    public void orderValidationTest1() {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "usa9.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        l_deploy.execute();
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        assertEquals(5, l_c.getReinforcements());
    }

    /**
     * Test case to validate the execution of the l_deploy order when deploying troops to a country with a different number of reinforcements.
     */
    @Test
    public void orderValidationTest2() {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = null;
        MapFileLoader l_mfl = new MapFileLoader(l_ge, "usa9.map");

        if (l_mfl.isConquest()) {
            l_mp = new MapAdapter(new ConquestMapInterface());
        } else {
            l_mp = new MapInterface();
        }
        l_ge.d_worldmap = l_mp.loadMap(l_ge, l_mfl);
        l_ge.d_players.add(new Player("Priyanshu", l_ge));
        l_ge.d_players.add(new Player("Abc", l_ge));

        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();


        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 10, l_ge);
        l_deploy.execute();
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        assertNotEquals(5, l_c.getReinforcements());
    }


}