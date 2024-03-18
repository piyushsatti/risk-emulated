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

public class DiplomacyTest {

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


        Diplomacy dip = new Diplomacy(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName());
        assertTrue(dip.validateCommand());
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


        Diplomacy dip = new Diplomacy(ge.d_players.get(0), ge.d_players.get(0), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName());
        assertFalse(dip.validateCommand());
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

        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 4, ge);
        deploy.execute();

        Diplomacy dip = new Diplomacy(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName());
        dip.execute();

        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Bomb b = new Bomb(ge.d_players.get(1), ge.d_players.get(0), ge.d_players.get(1).getPlayerId(), ge.d_players.get(1).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        b.execute();

        assertEquals(4, c.getReinforcements());
    }

    @Test
    public void orderValidationTest2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException, InvalidCommandException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge, "usa9.map");
        ge.d_players.add(new Player("Priyanshu", ge));
        ge.d_players.add(new Player("Abc", ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        Deploy deploy = new Deploy(ge.d_players.get(0), ge.d_players.get(0).getName(), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getAssignedCountries().get(0), 10, ge);
        deploy.execute();

        Diplomacy dip = new Diplomacy(ge.d_players.get(0), ge.d_players.get(1), ge.d_players.get(0).getPlayerId(), ge.d_players.get(0).getName());
        dip.execute();

        Country c = ge.d_worldmap.getCountry(ge.d_players.get(0).getAssignedCountries().get(0));
        Bomb b = new Bomb(ge.d_players.get(1), ge.d_players.get(0), ge.d_players.get(1).getPlayerId(), ge.d_players.get(1).getName(), ge.d_players.get(0).getAssignedCountries().get(0), ge);
        b.execute();

        assertNotEquals(5, c.getReinforcements());
    }

}