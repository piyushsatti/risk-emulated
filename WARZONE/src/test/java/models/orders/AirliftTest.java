package models.orders;

import controller.GameEngine;
import controller.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.Reinforcement;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.Country;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class AirliftTest {

    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Airlift al = new Airlift(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(0).getAssignedCountries().get(1), 5, ge);
        assertTrue(al.validateCommand());
    }

    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Airlift al = new Airlift(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(1).getAssignedCountries().get(0), 5, ge);
        assertFalse(al.validateCommand());

    }


    @Test
    public void validateCommandTests3() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Airlift al = new Airlift(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        assertFalse(al.validateCommand());

    }

    @Test
    public void orderValidationTest1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 5, ge);
        deploy.execute();

        Airlift al = new Airlift(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), ge.d_players.get(0).getAssignedCountries().get(1), 5, ge);
        al.execute();

        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(1));
        assertEquals(5, c.getReinforcements());
    }


}