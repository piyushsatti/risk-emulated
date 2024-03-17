package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.IssueOrderCommands;
import controller.statepattern.Phase;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;

import java.util.Scanner;

public class IssueOrder extends Phase {
    public IssueOrder(GameEngine g) {
        super(g);
    }

    @Override
    public void displayMenu() {

    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }


    @Override
    public void run() throws CountryDoesNotExistException, InvalidCommandException {
        Scanner scan = new Scanner(System.in);
        for(Player p: d_ge.d_players){
            p.setOrderSuccess(false);
            while(!p.isOrderSuccess() && p.getReinforcements()>0){
            d_ge.d_renderer.renderMessage("Player: " + p.getName() + " Reinforcements Available: " + p.getReinforcements());
            System.out.print(p.getName() + " enter order: ");
            String command = scan.nextLine();
            IssueOrderCommands ioc = new IssueOrderCommands(command,p);
            try{
                 ioc.execute(d_ge);
                }catch(CountryDoesNotExistException  | InvalidCommandException e){
                    d_ge.d_renderer.renderError("Following exception occured :" + e);

                }
            }
            System.out.println("Command Issued!");
        }

        d_ge.setCurrentState(new OrderExecution(d_ge));

    }
}
