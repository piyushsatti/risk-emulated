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
        boolean allPlayersFinished = true;
        for(Player p : d_ge.d_players){
            if(!p.isFinishedIssueOrder()){
                allPlayersFinished = false;
            }
        }
        while(!allPlayersFinished) {
            for (Player p : d_ge.d_players) {
                p.setOrderSuccess(false);
                while (!p.isOrderSuccess()) {
                    d_ge.d_renderer.renderMessage("Player: " + p.getName() + " Reinforcements Available: " + p.getReinforcements());
                    d_ge.d_renderer.renderMessage("Player: " + p.getName() + " Cards Available: " + p.displayCards());

                    System.out.println("Enter done if you have no more orders to give");
                    System.out.print(p.getName() + " enter order: ");
                    String command = scan.nextLine();
                    IssueOrderCommands ioc = new IssueOrderCommands(command, p);
                    try {
                        ioc.execute(d_ge);
                    } catch (CountryDoesNotExistException | InvalidCommandException e) {
                        d_ge.d_renderer.renderError("Following exception occured :" + e);

                    }
                }

            }
        }

        d_ge.setCurrentState(new OrderExecution(d_ge));

    }
}
