package models.orders;

import controller.GameEngine;
import controller.MapInterface;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.gameplay.OrderExecution;
import controller.statepattern.gameplay.Reinforcement;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.Player;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class BombTest {

    @Test
    public void validateCommandTests1() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"order_test.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        OrderExecution oe = new OrderExecution(ge);
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();

        Bomb b = new Bomb(ge.d_players.get(0),ge.d_players.get(1),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getName(), ge.d_players.get(1).getAssignedCountries().get(0),ge);
        assertTrue(b.validateCommand());
    }

    @Test
    public void validateCommandTests2() throws CountryDoesNotExistException, ContinentAlreadyExistsException, ContinentDoesNotExistException, DuplicateCountryException, FileNotFoundException {
        GameEngine ge = new GameEngine();
        MapInterface.loadMap2(ge,"usa9.map");
        ge.d_players.add(new Player("Priyanshu",ge));
        ge.d_players.add(new Player("Abc",ge));

        StartupCommands cmd = new StartupCommands("assigncountries");
        OrderExecution oe = new OrderExecution(ge);
        cmd.execute(ge);
        Reinforcement rf = new Reinforcement(ge);
        rf.run();


        Bomb b = new Bomb(ge.d_players.get(0),ge.d_players.get(1),ge.d_players.get(0).getPlayerId(),ge.d_players.get(0).getName(), ge.d_players.get(0).getAssignedCountries().get(1),ge);
        assertFalse(b.validateCommand());
    }



    public void validateCommandTests3(){
        //assertTrue(validateCommand());
    }

}