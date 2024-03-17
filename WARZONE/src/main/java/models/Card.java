package models;
import java.util.Random;


public class Card {
    public String getTypeOfCard() {
        return d_typeOfCard;
    }

    public void setD_typeOfCard(String d_typeOfCard) {
        this.d_typeOfCard = d_typeOfCard;
    }

    private String d_typeOfCard;
    private static  Random random = new Random();
    private static String[] d_typeOfCards = {"airlift","blockade","bomb","diplomacy"};

    public Card(String p_typeOfCard){
        this.d_typeOfCard = p_typeOfCard;
    }
    public static Card createCard(){
        int randomCardNumber = random.nextInt(d_typeOfCards.length);
        Card card = new Card(d_typeOfCards[randomCardNumber]);
        return card;

    }




}
