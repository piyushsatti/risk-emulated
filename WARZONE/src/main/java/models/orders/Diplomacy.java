package models.orders;

import models.Player;

public class Diplomacy implements Order{

    Player d_sourcePlayer;

    Player d_targetPlayer;


    int d_playerOrderID;

    String d_playerOrderName;

    public Diplomacy(Player p_sourcePlayer, Player p_targetPlayer, int p_playerOrderID, String p_playerOrderName ) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_targetPlayer = p_targetPlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName = p_playerOrderName;
    }

    @Override
    public boolean validateCommand(){

        return true;
    }

    @Override
    public void execute(){
        d_sourcePlayer.addToListOfNegotiatedPlayers(d_targetPlayer);
        d_targetPlayer.addToListOfNegotiatedPlayers(d_sourcePlayer);
        return;
    }



}
