package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.IssueOrderCommands;
import controller.statepattern.Phase;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;

import java.util.Scanner;

/**
 * Represents the phase where players issue orders.
 */
public class IssueOrder extends Phase {
    /**
     * Constructor for IssueOrder.
     *
     * @param p_gameEngine The GameEngine object.
     */
    public IssueOrder(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * This method is intended to display the game menu.
     */
    @Override
    public void displayMenu() {

    }
    /**
     * This method is intended to advance the game to the next step or phase.
     */
    @Override
    public void next() {

    }

    /**
     * This method is intended to end the game.
     */
    @Override
    public void endGame() {

    }
    /**
     * Checks if all players have finished issuing orders.
     *
     * @return True if all players have finished, false otherwise.
     */
    public boolean allPlayersFinished(){
        boolean l_playersFinished = true;
     for(Player l_p : d_ge.d_players){
         if(!l_p.isFinishedIssueOrder()){
             l_playersFinished = false;
             return l_playersFinished;
         }
     }
     return l_playersFinished;
 }

    /**
     * Executes the phase of issuing orders.
     *
     * @throws CountryDoesNotExistException If a country does not exist.
     * @throws InvalidCommandException     If the command is invalid.
     */
    @Override
    public void run() throws CountryDoesNotExistException, InvalidCommandException {
        Scanner scan = new Scanner(System.in);
        this.d_ge.d_renderer.renderMessage("In Issue order Phase");
        int l_playerNumber = 0;
        while(!allPlayersFinished()) {
            Player p = d_ge.d_players.get(l_playerNumber);
                p.setOrderSuccess(false);
                while (!p.isOrderSuccess()) {          // Render player's available reinforcements and cards
                    d_ge.d_renderer.renderMessage("Player: " + p.getName() + " Reinforcements Available: " + p.getReinforcements());
                    d_ge.d_renderer.renderMessage("Player: " + p.getName() + " Cards Available: " + p.displayCards());

                    this.d_ge.d_renderer.renderMessage("Enter done if you have no more orders to give");
                    this.d_ge.d_renderer.renderMessage(p.getName() + " enter order: ");
                    String command = scan.nextLine();
                    IssueOrderCommands ioc = new IssueOrderCommands(command, p);
                    try {
                        ioc.execute(d_ge);
                    } catch (CountryDoesNotExistException | InvalidCommandException e) {
                        d_ge.d_renderer.renderError("Following exception occured :" + e);


                    }
                }
                l_playerNumber++;
                if(l_playerNumber == d_ge.d_players.size()){         // Reset player number to 0 if it reaches the end of the player list
                    l_playerNumber = 0;
                }

            }

        d_ge.setCurrentState(new OrderExecution(d_ge));

    }
}
