package models.orders;

import controller.GameEngine;
import controller.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.OrderExecution;
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
public class DeployTest {

    /**
     * Test case to validate the execution of the deployment command.
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"order_test.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getAssignedCountries().get(0),1,ge);
        assertTrue(deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deployment command when the player does not own the country.
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"order_test.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),ge.d_players.get(1).getAssignedCountries().get(0),1,ge);
        assertFalse(deploy.validateCommand());
    }


    /**
     * Test case to validate the execution of the deployment command when the number of troops to deploy is negative.
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests3() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"order_test.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getAssignedCountries().get(0),-10,ge);
        assertFalse(deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deployment command when the number of troops to deploy exceeds the available troops.
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests4() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"order_test.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getAssignedCountries().get(0),100,ge);
        assertFalse(deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deployment command when the target country ID is invalid (negative).
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void validateCommandTests5() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"order_test.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),-11,1,ge);
        assertFalse(deploy.validateCommand());
    }

    /**
     * Test case to validate the execution of the deploy order when deploying troops to a country.
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void orderValidationTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"usa9.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getAssignedCountries().get(0),5,ge);
        deploy.execute();
         Country c =ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0)) ;
        assertEquals(5,c.getReinforcements());
    }

    /**
     * Test case to validate the execution of the deploy order when deploying troops to a country with a different number of reinforcements.
     * @throws CountryDoesNotExistException if the country being deployed to does not exist.
     * @throws ContinentAlreadyExistsException if the continent being added already exists.
     * @throws ContinentDoesNotExistException if the continent being referred to does not exist.
     * @throws DuplicateCountryException if the country being added already exists.
     * @throws FileNotFoundException if the specified file is not found.
     * @throws InvalidCommandException if the command executed is invalid.
     */
    @Test
    public void orderValidationTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"usa9.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Deploy deploy = new Deploy(ge.d_players.get(0),ge.d_players.get(0).getName(),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getAssignedCountries().get(0),10,ge);
        deploy.execute();
        Country c =ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0)) ;
        assertNotEquals(5,c.getReinforcements());
    }


}