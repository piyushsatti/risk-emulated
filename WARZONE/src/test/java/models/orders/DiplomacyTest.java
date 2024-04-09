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

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * The DiplomacyTest class contains unit tests for the Diplomacy class.
 */
public class DiplomacyTest {

    /**
     * Tests the validateCommand method of the Diplomacy class when the command is valid.
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
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();
        // Validate command
        Diplomacy l_dip = new Diplomacy(l_ge.d_players.get(0), l_ge.d_players.get(1), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName());
        assertTrue(l_dip.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Diplomacy class when the command is invalid.
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
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();

        // Validate command
        Diplomacy l_dip = new Diplomacy(l_ge.d_players.get(0), l_ge.d_players.get(0), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName());
        assertFalse(l_dip.validateCommand());
    }

    /**
     * Tests the execute method of the Diplomacy class for successfully executing the order.
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
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 4, l_ge);
        l_deploy.execute();

        // Execute Diplomacy
        Diplomacy l_dip = new Diplomacy(l_ge.d_players.get(0), l_ge.d_players.get(1), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName());
        l_dip.execute();

        // Execute Bomb
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        Bomb l_b = new Bomb(l_ge.d_players.get(1), l_ge.d_players.get(0), l_ge.d_players.get(1).getPlayerId(), l_ge.d_players.get(1).getName(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge);
        l_b.execute();

        // Assertion
        assertEquals(4, l_c.getReinforcements());
    }

    /**
     * Tests the execute method of the Diplomacy class for not executing the order when the conditions are not met.
     */
    @Test
    public void orderValidationTest2() {
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
        StartupCommands l_cmd = new StartupCommands("assigncountries");
        l_cmd.execute(l_ge);
        Reinforcement l_rf = new Reinforcement(l_ge);
        l_rf.run();
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 10, l_ge);
        l_deploy.execute();

        // Execute Diplomacy
        Diplomacy l_dip = new Diplomacy(l_ge.d_players.get(0), l_ge.d_players.get(1), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName());
        l_dip.execute();

        // Execute Bomb
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        Bomb l_b = new Bomb(l_ge.d_players.get(1), l_ge.d_players.get(0), l_ge.d_players.get(1).getPlayerId(), l_ge.d_players.get(1).getName(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge);
        l_b.execute();

        // Assertion
        assertNotEquals(5, l_c.getReinforcements());
    }
}
