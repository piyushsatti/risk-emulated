package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.statepattern.End;
import controller.statepattern.Phase;
import models.Player;
import models.orders.Order;

import java.util.ArrayList;

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

    @Override
    public void displayMenu() {

    }
    @Override
    public void next() {

    }
    @Override
    public void endGame() {

    }

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
        System.out.println("In order execution phase");
        while (!allOrdersExecuted(d_ge.d_players)) {
            for(Player player: d_ge.d_players){
                System.out.println("Player  " + player.getName());
                if (!player.getOrderList().isEmpty()) {
                    Order order = player.next_order();
                    order.execute();
                }
            }
        }

        for(Player p: d_ge.d_players){
            if(p.getAssignedCountries().isEmpty()){
                d_ge.d_renderer.renderMessage("Player "+p.getName()+" has lost all territories");
                d_ge.d_players.remove(p);
            }
        }
        if(isWinner())
        {
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
        if(d_ge.d_players.size()==1){
            d_ge.d_renderer.renderMessage("Player "+d_ge.d_players.get(0).getName()+" has won the game");
            return true;
        }
        return false;
    }

}
