package models.Orders;

public class Advance implements Order{

    int d_playerOrderID;

    String d_playerOrderName;

    private final int d_fromCountryID;

    private final int d_toCountryID;

    private final int d_reinforcementsDeployed;
    public Advance(String p_playerOrderName, int p_playerOrderID,int p_fromCountryID, int p_toCountryID, int p_reinforcementsDeployed){

        this.d_playerOrderName = p_playerOrderName;

        this.d_playerOrderID = p_playerOrderID;

        this.d_fromCountryID = p_fromCountryID;

        this.d_toCountryID = p_toCountryID;

        this.d_reinforcementsDeployed = p_reinforcementsDeployed;

    }

    @Override
    public void execute() {

    }
}
