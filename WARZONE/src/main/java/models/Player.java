package models;
import java.util.*;
public class Player {

    private String d_name;
    private int d_reinforcements;
    private List<Country> d_assignedCountries;

    public String getName() {
        return d_name;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }
    public int getReinforcements() {
        return d_reinforcements;
    }

    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements = p_reinforcements;
    }


}
