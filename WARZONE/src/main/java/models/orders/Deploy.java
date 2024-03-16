package models.orders;
import controller.GameEngine;
import helpers.exceptions.InvalidCommandException;
import view.TerminalRenderer;
import models.Player;

/**
 * The Deploy class implements Order inteface.
 * It is used to create a deploy order.
 */
public class Deploy implements Order {

    int d_playerOrderID;

    String d_playerOrderName;

    private final int d_fromCountryID;

    private final int d_toCountryID;

    private final int d_reinforcementsDeployed;

    GameEngine d_gameEngine;

    TerminalRenderer d_terminalRenderer;

    Player d_sourcePlayer;

    /**
     * Constructs an Deploy object with specified attributes.
     *
     * @param p_playerOrderName The name of the player issuing the order.
     * @param p_playerOrderID The ID of the player issuing the order.
     * @param p_fromCountryID The ID of the country from which reinforcements are deployed.
     * @param p_reinforcementsDeployed The number of reinforcements deployed.
     */
    public Deploy(Player p_sourcePlayer, String p_playerOrderName, int p_playerOrderID,int p_fromCountryID,int p_reinforcementsDeployed, GameEngine p_gameEngine){
        this.d_sourcePlayer = p_sourcePlayer;

        this.d_playerOrderName = p_playerOrderName;

        this.d_playerOrderID = p_playerOrderID;

        this.d_fromCountryID = p_fromCountryID;

        this.d_toCountryID = -1;

        this.d_reinforcementsDeployed = p_reinforcementsDeployed;

        this.d_gameEngine = p_gameEngine;

        this.d_terminalRenderer = new TerminalRenderer(this.d_gameEngine);

    }

    public boolean deployment_validator(int p_numberTobeDeployed) {
        return p_numberTobeDeployed < this.d_sourcePlayer.getReinforcements();
    }

    @Override
    public boolean validateCommand() throws InvalidCommandException {

        if (!this.deployment_validator(this.d_reinforcementsDeployed)) {

            this.d_terminalRenderer.renderMessage("You (" + this.d_sourcePlayer.getName() + ") don't have enough troops for this deploy order");
            return false;

        }

        if (d_fromCountryID <=  0){
            this.d_terminalRenderer.renderMessage(" (" + this.d_sourcePlayer.getName() + ") Negative countries not possible");
            return false;
        }


        return true;
    }

    /**
     * Executes the order by deploying reinforcements to the specified country.
     */
    public void execute(){
        int l_currentReinforcements = this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getReinforcements();

       this.d_gameEngine.CURRENT_MAP
               .getCountry(this.d_fromCountryID)
               .setReinforcements(this.d_reinforcementsDeployed + l_currentReinforcements);

        this.d_terminalRenderer.renderMessage(
              "Order Executed: " +
                      this.d_reinforcementsDeployed +
                       " troops deployed on " +
                      this.d_gameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getCountryName() +
                       " by " +
                       this.d_playerOrderName
       );

   }

}


