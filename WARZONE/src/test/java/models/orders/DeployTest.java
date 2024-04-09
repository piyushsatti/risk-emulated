package models.orders;

import controller.GameEngine;
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
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "test_map.map");
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
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "test_map.map");
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
     * Test case to validate the execution of the deployment command when the number of troops to deploy is negative.
     *
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void validateCommandTests3() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "test_map.map");
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
     * Test case to validate the execution of the deployment command when the number of troops to deploy exceeds the available troops.
     *
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void validateCommandTests4() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "test_map.map");
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
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void validateCommandTests5() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "test_map.map");
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
     * Test case to validate the execution of the deploy order when deploying troops to a country.
     *
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void orderValidationTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "usa9.map");
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
     * Test case to validate the execution of the deploy order when deploying troops to a country with a different number of reinforcements.
     *
     * @throws CountryDoesNotExistException    if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException  if the continent being referred to does not exist.
     * @throws DuplicateCountryException       if the country being added already exists.
     * @throws FileNotFoundException           if the specified file is not found.
     * @throws InvalidCommandException         if the command executed is invalid.
     */
    @Test
    public void orderValidationTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine l_ge = new GameEngine();
        MapInterface l_mp = new MapInterface();
        l_mp.loadMap(l_ge, "usa9.map");
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