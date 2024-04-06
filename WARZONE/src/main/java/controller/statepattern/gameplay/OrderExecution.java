package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.statepattern.End;
import controller.statepattern.Phase;
import models.Player;
import models.orders.Order;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the phase of executing orders.
 */
public class OrderExecution extends Phase {
    /**
     * Constructor for OrderExecution.
     *
     * @param p_gameEngine The GameEngine object.
     */
    public OrderExecution(GameEngine p_gameEngine) {
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
     * Checks if all players have executed their orders.
     *
     * @param p_Players The list of players to be checked.
     * @return {@code true} if all players have executed their orders, {@code false} otherwise.
     */
    public boolean allOrdersExecuted(ArrayList<Player> p_Players) {
        for (Player l_player : p_Players) {
            if (!l_player.getOrderList().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Executes the order execution phase.
     */
    @Override
    public void run() {

        int l_totalplayers = d_ge.d_players.size();
        int l_playerNumber = 0;
        System.out.println("In order execution phase --------------------------------------------");
        while (!allOrdersExecuted(d_ge.d_players)) {
            for(Player player: d_ge.d_players){
                if (!player.getOrderList().isEmpty()) {
                    Order order = player.next_order();
                    order.execute();
                }
            }
        }


        /**
         * Returns an iterator over the players in the game engine.
         *
         * @return An iterator that allows iteration over the players in the game engine.
         */
        Iterator<Player> iterator = d_ge.d_players.iterator();

        while (iterator.hasNext()) {
            Player p = iterator.next();
            System.out.println("Player " + p.getName() +" has country number: " + p.getAssignedCountries().size());
            if (p.getAssignedCountries().isEmpty()) {
                d_ge.d_renderer.renderMessage("Player " + p.getName() + " has lost all territories");
                iterator.remove(); // Remove the current player using the iterator
            }
        }

        if(isWinner()) {
            d_ge.setCurrentState(new End(d_ge));
        }
        d_ge.setCurrentState(new Reinforcement(d_ge));
    }

    /**
     * Checks if there is a winner in the game.
     *
     * @return True if there is a winner, false otherwise.
     */
    public boolean isWinner()
    {
        if(d_ge.d_players.size()==1 && (d_ge.d_players.get(0).getAssignedCountries().size() ==d_ge.d_worldmap.getCountries().size())){
            d_ge.d_renderer.renderMessage("Player "+d_ge.d_players.get(0).getName()+" has won the game");
            return true;
        }
        return false;
    }

}
