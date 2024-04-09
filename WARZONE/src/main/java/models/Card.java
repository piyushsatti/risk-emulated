package models;

import java.util.Random;

/**
 * The Card class represents a card in the game.
 */
public class Card {
    /**
     * Gets the type of the card.
     *
     * @return The type of the card
     */
    public String getTypeOfCard() {
        return d_typeOfCard;
    }

    /**
     * Sets the type of card.
     *
     * @param p_typeOfCard The type of card to be set.
     */
    public void setD_typeOfCard(String p_typeOfCard) {
        this.d_typeOfCard = p_typeOfCard;
    }

    /**
     * Type of card.
     */
    private String d_typeOfCard;
    /**
     * Random number generator instance.
     */
    private static Random d_random = new Random();
    /**
     * Array containing types of cards.
     */
    private static String[] d_typeOfCards = {"airlift", "blockade", "bomb", "diplomacy"};

    /**
     * Constructs a Card object with the specified type.
     *
     * @param p_typeOfCard The type of card
     */
    public Card(String p_typeOfCard) {
        this.d_typeOfCard = p_typeOfCard;
    }

    /**
     * Creates a random card.
     *
     * @return A randomly generated card
     */
    public static Card createCard() {
        int l_randomCardNumber = d_random.nextInt(d_typeOfCards.length);
        Card l_card = new Card(d_typeOfCards[l_randomCardNumber]);
        return l_card;
    }
}
