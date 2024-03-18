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
 public boolean allPlayersFinished(){
        boolean playersFinished = true;
     for(Player p : d_ge.d_players){
         if(!p.isFinishedIssueOrder()){
             playersFinished = false;
             return playersFinished;
         }
     }
     return playersFinished;
 }

    @Override
    public void run() {
        try {
            Scanner scan = new Scanner(System.in);
            int l_playerNumber = 0;

            while (!allPlayersFinished()) {
                Player p = d_ge.d_players.get(l_playerNumber);
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

                l_playerNumber++;

                if (l_playerNumber == d_ge.d_players.size()) {
                    l_playerNumber = 0;
                }
            }

            d_ge.setCurrentState(new OrderExecution(d_ge));
        } catch (Exception e) {
            d_ge.d_renderer.renderError("Devdutt please use the functions created.");
        }
    }
}
