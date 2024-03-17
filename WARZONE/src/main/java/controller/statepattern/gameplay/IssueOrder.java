package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.statepattern.Phase;
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
    public void run() {
        Scanner scan = new Scanner(System.in);
        for(Player p: d_ge.d_players){
            System.out.print(p.getName() + " enter order: ");
            String command = scan.nextLine();
            //Player.issueOrder();

            System.out.println("Command Issued!");
        }

        d_ge.setCurrentState(new OrderExecution(d_ge));
    }
}
