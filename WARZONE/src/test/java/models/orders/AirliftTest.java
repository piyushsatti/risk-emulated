package models.orders;

import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import mvc.controller.GameEngine;
import mvc.controller.middleware.commands.StartupCommands;
import mvc.controller.middleware.mapfilemanagement.ConquestMapInterface;
import mvc.controller.middleware.mapfilemanagement.MapAdapter;
import mvc.controller.middleware.mapfilemanagement.MapFileLoader;
import mvc.controller.middleware.mapfilemanagement.MapInterface;
import mvc.controller.statepattern.gameplay.Reinforcement;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

;

/**
 * The AirliftTest class contains unit tests for the Airlift class.
 */
public class AirliftTest {

    /**
     * Tests the validateCommand method of the Airlift class when the command is valid.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
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
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(1), 5, l_ge);
        assertTrue(l_al.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Airlift class when the command is invalid.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
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
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(1).getAssignedCountries().get(0), 5, l_ge);
        assertFalse(l_al.validateCommand());
    }

    /**
     * Tests the validateCommand method of the Airlift class when the command is invalid because source and destination countries are the same.
     *
     * @throws CountryDoesNotExistException    If a country does not exist.
     * @throws ContinentAlreadyExistsException If a continent already exists.
     * @throws ContinentDoesNotExistException  If a continent does not exist.
     * @throws DuplicateCountryException       If a country is duplicated.
     * @throws FileNotFoundException           If a file is not found.
     */
    @Test
    public void validateCommandTests3() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
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
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        assertFalse(l_al.validateCommand());
    }

    /**
     * Tests the execute method of the Airlift class for successfully executing the order.
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
        Deploy l_deploy = new Deploy(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), 5, l_ge);
        l_deploy.execute();
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
        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        deploy.execute();
>>>>>>> Stashed changes

        // Execute Airlift
        Airlift l_al = new Airlift(l_ge.d_players.get(0), l_ge.d_players.get(0).getName(), l_ge.d_players.get(0).getPlayerId(), l_ge.d_players.get(0).getAssignedCountries().get(0), l_ge.d_players.get(0).getAssignedCountries().get(1), 5, l_ge);
        l_al.execute();

        // Assertion
        Country l_c = l_ge.d_worldmap.getCountry(l_ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, l_c.getReinforcements());
    }
}
