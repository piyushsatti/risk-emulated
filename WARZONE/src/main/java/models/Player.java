package models;

import controller.GameEngine;
import controller.commands.CommandValidator;
import helpers.exceptions.InvalidCommandException;
import models.orders.Deploy;
import models.orders.Order;
import views.TerminalRenderer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

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
     * The number of reinforcements available for the player.
     */
    private int d_reinforcements;

    /**
     * The list of countries assigned to the player.
     */
    private final ArrayList<Integer> d_assignedCountries;
    private final ArrayList<Card> d_listOfCards;

    /**
     * The order list of the player.
     */
    private final Deque<Order> d_orderList = new ArrayDeque<>();


    private final ArrayList<Player> d_listOfNegotiatedPlayers;


    public ArrayList<Player> getListOfNegotiatedPlayers() {
        return d_listOfNegotiatedPlayers;
    }

    public void addToListOfNegotiatedPlayers(Player p_player) {
        d_listOfNegotiatedPlayers.add(p_player);
        return;
    }

    public void removeFromListOfNegotiatedPlayers(Player p_player) {
        d_listOfNegotiatedPlayers.remove(p_player);
        return;
    }


    /**
     * Constructs a new Player object with the specified name.
     *
     * @param p_playerName The name of the player.
     */
    //Constructors
    public Player(String p_playerName) {

        this.d_playerId = d_latest_playerID;

        this.d_playerName = p_playerName;

        this.d_reinforcements = 0;

        this.d_assignedCountries = new ArrayList<>();

        this.d_listOfCards = new ArrayList<>();
        this.d_listOfNegotiatedPlayers = new ArrayList<>();

        d_latest_playerID++;

    }

    /**
     * Validates player's order by checking if the player has available reinforcements to place the order or not.
     *
     * @param p_numberTobeDeployed The number of troops to be deployed in the given order
     * @return whether the player has more reinforcements than the number of troops to be deployed.
     */
    public boolean deployment_validator(int p_numberTobeDeployed) {
        return p_numberTobeDeployed < this.getReinforcements();
    }

    /**
     * Issues an order for the player.
     *
     * @throws InvalidCommandException If the command issued by the player is invalid.
     */
    public void issue_order() throws InvalidCommandException {

        while (true) {

            TerminalRenderer.renderMessage("Player: " + this.d_playerName + " Reinforcements Available: " + this.getReinforcements());

            String command = TerminalRenderer.issueOrderView(this.getName());

            CommandValidator commandValidator = new CommandValidator();

            commandValidator.addCommand(command);

            String[] arr = command.split(" ");

            int l_countryID = GameEngine.CURRENT_MAP.getCountryID(arr[1]);

            int l_numberTobeDeployed = Integer.parseInt(arr[2]);


            if (l_countryID > 0 && l_numberTobeDeployed <= this.getReinforcements()) {

                if (this.d_assignedCountries.contains(l_countryID)) {

                    Order order = new Deploy(this.getName(), this.getPlayerId(), l_countryID, l_numberTobeDeployed);

                    TerminalRenderer.renderMessage("Order Created. Here are the Details: Deploy " + l_numberTobeDeployed + " on " + GameEngine.CURRENT_MAP.getCountry(l_countryID).getCountryName() + " by Player: " + this.d_playerName);

                    this.d_orderList.add(order);

                    this.setReinforcements(this.getReinforcements() - l_numberTobeDeployed);

                    TerminalRenderer.renderMessage("Player: " + this.d_playerName + " Reinforcements Available: " + this.getReinforcements());

                    return;

                } else {

                    TerminalRenderer.renderMessage("You (" + this.d_playerName + ") Cannot Deploy Troops here you don't own it.");
                    throw new InvalidCommandException("Invalid Command!!! You don't own the country");

                }

            } else {

                if (!deployment_validator(l_numberTobeDeployed)) {

                    TerminalRenderer.renderMessage("You (" + this.d_playerName + ") don't have enough troops for this deploy order");
                    throw new InvalidCommandException("Invalid Command!!! Not enough troops");

                } else {

                    throw new InvalidCommandException("Invalid Command");

                }

            }

        }

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

    public ArrayList<Card> getListOfCards() {
        return d_listOfCards;
    }
    public void addCard(){
        Card card = Card.createCard();
        this.d_listOfCards.add(card);

    }

}