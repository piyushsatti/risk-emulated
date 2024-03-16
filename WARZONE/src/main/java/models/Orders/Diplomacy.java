package models.Orders;

import models.Player;

public class Diplomacy implements Order{

    Player d_sourcePlayer;

    Player d_targetPlayer;

    public Diplomacy(Player p_sourcePlayer, Player p_targetPlayer) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_targetPlayer = p_targetPlayer;
    }


    @Override
    public void execute(){
        d_sourcePlayer.addToListOfNegotiatedPlayers(d_targetPlayer);
        d_targetPlayer.addToListOfNegotiatedPlayers(d_sourcePlayer);
        return;
    }



}
