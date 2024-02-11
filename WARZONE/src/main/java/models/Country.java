package models;
import java.util.*;

public class Country {
    private int d_countryID;
    private int d_continentID;
    private int d_deployedArmies;
    private List<Integer> d_neighborListID;
    private Player d_assignedPlayer;

    public Country(int d_countryID, int d_continentID, int d_deployedArmies, List<Integer> d_neighborListID, Player d_assignedPlayer) {
        this.d_countryID = d_countryID;
        this.d_continentID = d_continentID;
        this.d_deployedArmies = d_deployedArmies;
        this.d_neighborListID = d_neighborListID;
        this.d_assignedPlayer = d_assignedPlayer;
    }

}
