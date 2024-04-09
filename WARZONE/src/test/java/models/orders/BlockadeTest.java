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
 * The BlockadeTest class contains unit tests for the Blockade class.
 */
public class BlockadeTest {

    /**
     * Tests the validateCommand method of the Blockade class when the command is valid.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        // Test setup
<<<<<<< Updated upstream
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
=======
        GameEngine ge = new GameEngine();
        MapInterface mp = null;
        MapFileLoader l_mfl = new MapFileLoader(ge, "usa9.map");

        if(l_mfl.isConquest()){
            mp = new MapAdapter(new ConquestMapInterface());
        }else{
            mp = new MapInterface();
        }
        ge.d_worldmap = mp.loadMap(ge, l_mfl);
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));
        ge.setCurrentState(new Startup(ge));
        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();
>>>>>>> Stashed changes

        // Validate command
        Blockade l_bl = new Blockade(l_ge.d_players.get(0), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getAssignedCountries().get(1), l_ge);
        assertTrue(l_bl.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Blockade class when the command is invalid.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
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

        // Validate command
        Blockade l_bl = new Blockade(l_ge.d_players.get(0), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName(), l_ge.d_players.get(1).getAssignedCountries().get(0), l_ge);
        assertFalse(l_bl.validateCommand());
    }

    /**
     * Tests the execute method of the Blockade class for successfully executing the order.
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

        // Execute Blockade
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        Blockade l_bl = new Blockade(l_ge.d_players.get(0), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge);
        l_bl.execute();

        // Assertion
        assertEquals(15, l_c.getReinforcements());
    }

    /**
     * Tests the execute method of the Blockade class for not executing the order when the conditions are not met.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void orderValidationTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
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
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 12, l_ge);
        l_deploy.execute();

        // Execute Blockade
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        Blockade l_bl = new Blockade(l_ge.d_players.get(0), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge);
        l_bl.execute();

        // Assertion
        assertEquals(36, l_c.getReinforcements());
    }

    /**
     * Tests the execute method of the Blockade class for not executing the order when the conditions are not met.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     * @throws InvalidCommandException         If the command is invalid.
     */
    @Test
    public void orderValidationTest3() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
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
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 4, l_ge);
        l_deploy.execute();

        // Execute Blockade
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(0));
        Blockade l_bl = new Blockade(l_ge.d_players.get(0), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge);
        l_bl.execute();

        // Assertion
        assertNotEquals(3, l_c.getReinforcements());
    }

}
