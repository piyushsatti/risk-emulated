package models.orders;

import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.Startup;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import org.junit.Test;
import strategy.AggressiveStrategy;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * The AirliftTest class contains unit tests for the Airlift class.
 */
public class AirliftTest {

    /**
     * Tests the validateCommand method of the Airlift class when the command is valid.
     */
    @Test
    public void validateCommandTests1() {
        // Test setup
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
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(l_ge);
        Reinforcement rf = new Reinforcement(l_ge);
        rf.run();

        // Validate command
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(1), 5, l_ge);
        assertTrue(l_al.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Airlift class when the command is invalid.
     */
    @Test
    public void validateCommandTests2() {
        // Test setup
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
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(l_ge);
        Reinforcement rf = new Reinforcement(l_ge);
        rf.run();

        // Validate command
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(1).getAssignedCountries().get(0), 5, l_ge);
        assertFalse(l_al.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Airlift class when the command is invalid because source and destination countries are the same.
     */
    @Test
    public void validateCommandTests3() {
        // Test setup
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
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(l_ge);
        Reinforcement rf = new Reinforcement(l_ge);
        rf.run();

        // Validate command
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        assertFalse(l_al.validateCommand());
    }

    /**
     * Tests the execute method of the Airlift class for successfully executing the order.
     */
    @Test
    public void orderValidationTest1() {
        // Test setup
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
        l_ge.setCurrentState(new Startup(l_ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(l_ge);
        Reinforcement rf = new Reinforcement(l_ge);
        rf.run();
        Deploy deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        deploy.execute();

        // Execute Airlift
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(1), 5, l_ge);
        l_al.execute();

        // Assertion
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, l_c.getReinforcements());
    }
}
