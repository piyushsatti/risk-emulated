package models;
import java.util.Random;

/**
 * The Card class represents a card in the game.
 */
public class Card {
    /**
     * Constructs a Card object with the specified type.
     *
     * @param p_typeOfCard The type of card
     */
    public Card(String p_typeOfCard){
        this.d_typeOfCard = p_typeOfCard;
    }

    /**
     * Creates a random card.
     *
     * @return A randomly generated card
     */
    public static Card createCard(){
        int randomCardNumber = random.nextInt(d_typeOfCards.length);
        Card card = new Card(d_typeOfCards[randomCardNumber]);
        return card;
    }
    private String d_typeOfCard;
    private static  Random random = new Random();
    private static String[] d_typeOfCards = {"airlift","blockade","bomb","diplomacy"};

    /**
     * Gets the type of the card.
     *
     * @return The type of the card
     */
    public String getTypeOfCard() {
        return d_typeOfCard;
    }

    /**
     * Sets the type of the card.
     *
     * @param d_typeOfCard The type of the card to set
     */
    public void setD_typeOfCard(String d_typeOfCard) {
        this.d_typeOfCard = d_typeOfCard;
    }
}
