package models;

import controller.GameEngine;
import controller.middleware.commands.IssueOrderCommands;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.orders.*;
import view.TerminalRenderer;


import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

/**
 * The Player class represents a player in the game.
 */
public class Player {

    /**
     * The latest player ID.
     */
    private static int d_latest_playerID = 1;

    /**
     * The ID of the player.
     */
    private final int d_playerId;

    /**
     * The name of the player.
     */
    private String d_playerName;

    /**
     * Checks if the order was successfully executed.
     *
     * @return True if the order was successfully executed, false otherwise.
     */
    public boolean isOrderSuccess() {
        return d_orderSuccess;
    }
    /**
     * Sets the status of the order execution.
     *
     * @param orderSuccess True if the order was successfully executed, false otherwise.
     */
    public void setOrderSuccess(boolean orderSuccess) {
        this.d_orderSuccess = orderSuccess;
    }
    /**
     * Check if order has been successful issued.
     */
    private boolean d_orderSuccess;

    /**
     * The number of reinforcements available for the player.
     */
    private int d_reinforcements;

    /**
     * The list of countries assigned to the player.
     */
    private final ArrayList<Integer> d_assignedCountries;
    /**
     * The list of cards assigned to the player.
     */
    private final ArrayList<Card> d_listOfCards;
    private Order d_current_order;

    /**
     * The order list of the player.
     */
    private final Deque<Order> d_orderList = new ArrayDeque<>();

    /**
     * Adds an order to the list of orders.
     *
     * @param order The order to be added.
     */
    public void addOrderToList(Order order) {
        this.d_orderList.add(order);
    }

    /**
     * Indicates whether the issuing of orders has finished.
     */
    private boolean d_finishedIssueOrder;

    /**
     * The list of players with whom negotiation has been conducted.
     */
    private final ArrayList<Player> d_listOfNegotiatedPlayers;

    /**
     * Retrieves the terminal renderer.
     *
     * @return The terminal renderer.
     */
    public TerminalRenderer getRenderer() {
        return d_renderer;
    }

    /**
     * The terminal renderer.
     */
    TerminalRenderer d_renderer;

    /**
     * The game engine.
     */
    GameEngine d_gameEngine;

    /**
     * Sets the current order.
     *
     * @param order The current order.
     */
    public void addOrder(Order order) {
        this.d_current_order = order;
    }

    /**
     * Issues the current order.
     *
     * @throws InvalidCommandException If the command is invalid.
     */
    public void issue_order() throws InvalidCommandException {
        this.d_orderList.add(this.d_current_order);
    }

    /**
     * Retrieves the list of negotiated players.
     *
     * @return The list of negotiated players.
     */
    public ArrayList<Player> getListOfNegotiatedPlayers() {
        return d_listOfNegotiatedPlayers;
    }

    /**
     * Adds a player to the list of negotiated players.
     *
     * @param p_player The player to add
     */
    public void addToListOfNegotiatedPlayers(Player p_player) {
        d_listOfNegotiatedPlayers.add(p_player);
        return;
    }

    /**
     * Removes a player from the list of negotiated players.
     *
     * @param p_player The player to remove
     */
    public void removeFromListOfNegotiatedPlayers(Player p_player) {
        d_listOfNegotiatedPlayers.remove(p_player);
        return;
    }

    /**
     * Checks if the issue order phase has finished.
     *
     * @return True if the issue order phase has finished, false otherwise
     */
    public boolean isFinishedIssueOrder() {
        return d_finishedIssueOrder;
    }

    /**
     * Sets the status of the issue order phase.
     *
     * @param p_finishedIssueOrder The status of the issue order phase
     */
    public void setFinishedIssueOrder(boolean p_finishedIssueOrder) {
        this.d_finishedIssueOrder = p_finishedIssueOrder;
    }

    /**
     * Constructs a new Player object with the specified name.
     *
     * @param p_playerName The name of the player.
     */
    //Constructors
    public Player(String p_playerName, GameEngine p_gameEngine) {

        this.d_playerId = d_latest_playerID;
        this.d_playerName = p_playerName;
        this.d_reinforcements = 0;
        this.d_assignedCountries = new ArrayList<>();
        this.d_listOfCards = new ArrayList<>();
        this.d_listOfNegotiatedPlayers = new ArrayList<>();
        this.d_gameEngine = p_gameEngine;
        this.d_renderer = new TerminalRenderer(this.d_gameEngine);
        this.d_orderSuccess = false;
        this.d_finishedIssueOrder = false;
        d_latest_playerID++;
    }

    /**
     * Gets the next order from the player's order list.
     *
     * @return The next order from the player's order list.
     */
    public Order next_order() {
        return this.d_orderList.poll();
    }


    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return d_playerName;
    }

    /**
     * Gets the reinforcements available for the player.
     *
     * @return The reinforcements available for the player.
     */
    public int getReinforcements() {
        return d_reinforcements;
    }

    /**
     * Gets the list of countries assigned to the player.
     *
     * @return The list of countries assigned to the player.
     */
    public ArrayList<Integer> getAssignedCountries() {
        return this.d_assignedCountries;
    }

    /**
     * Gets a player from the list of players based on the player ID.
     *
     * @param p_listOfPlayers The list of players.
     * @param p_playerID      The ID of the player to retrieve.
     * @return The player with the specified ID, or null if not found.
     */
    public static Player getPlayerFromList(ArrayList<Player> p_listOfPlayers, int p_playerID) {
        for (Player l_player : p_listOfPlayers) {
            if (l_player.getPlayerId() == p_playerID) {
                return l_player;
            }
        }
        return null;
    }

    /**
     * Retrieves the order list of the player.
     *
     * @return The order list of the player.
     */
    public Deque<Order> getOrderList() {
        return this.d_orderList;
    }

    /**
     * Retrieves the ID of the player.
     *
     * @return The ID of the player.
     */
    public int getPlayerId() {
        return this.d_playerId;
    }

    /**
     * Sets the name of the player.
     *
     * @param p_name The name to set for the player.
     */
    public void setName(String p_name) {
        this.d_playerName = p_name;
    }

    /**
     * Sets the reinforcements of the player.
     *
     * @param p_reinforcements The reinforcements to set for the player.
     */
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements = p_reinforcements;
    }

    /**
     * Sets the assigned countries of the player.
     *
     * @param p_countryID The ID of the country to add to the player's assigned countries.
     */
    public void setAssignedCountries(Integer p_countryID) {
        this.d_assignedCountries.add(p_countryID);
    }

    /**
     * Remove a country from the list of owned countries for the player.
     *
     * @param p_countryID The ID of the country to add to the player's assigned countries.
     */
    public void removeAssignedCountries(Integer p_countryID) {
        this.d_assignedCountries.remove(p_countryID);
    }

    /**
     * Retrieves the list of cards.
     *
     * @return The list of cards.
     */
    public ArrayList<Card> getListOfCards() {
        return d_listOfCards;
    }

    /**
     * Adds a card to the list of cards.
     */
    public void addCard() {
        Card l_card = Card.createCard();
        this.d_listOfCards.add(l_card);
    }
    /**
     * Removes a card of the specified type from the hand.
     *
     * @param p_cardType The type of card to remove
     */
    public void removeCard(String p_cardType){
        Iterator<Card> iterator = this.d_listOfCards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getTypeOfCard().equals(p_cardType)) {
                iterator.remove(); // Remove the current card using the iterator
            }
        }
    }
    /**
     * Checks if the hand contains a card with the specified name.
     *
     * @param cardName The name of the card to check for
     * @return True if the hand contains the card, false otherwise
     */
    public boolean containsCard(String cardName) {
        for (Card l_card : this.d_listOfCards) {
            if (l_card.getTypeOfCard().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Displays the types of cards in the hand.
     *
     * @return A string representation of the cards in the hand
     */
    public String displayCards(){
        String l_s = "";
        for(Card l_card: this.d_listOfCards){
            l_s+=" "+l_card.getTypeOfCard();
        }
        return l_s;
    }

}