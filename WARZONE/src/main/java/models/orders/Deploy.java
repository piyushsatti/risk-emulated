package models.orders;
import controller.GameEngine;
import view.TerminalRenderer;
import views.TerminalRenderer;

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

    /**
     * Constructs an Deploy object with specified attributes.
     *
     * @param p_playerOrderName The name of the player issuing the order.
     * @param p_playerOrderID The ID of the player issuing the order.
     * @param p_fromCountryID The ID of the country from which reinforcements are deployed.
     * @param p_reinforcementsDeployed The number of reinforcements deployed.
     */
    public Deploy(String p_playerOrderName, int p_playerOrderID,int p_fromCountryID,int p_reinforcementsDeployed, GameEngine p_gameEngine){

        this.d_playerOrderName = p_playerOrderName;

        this.d_playerOrderID = p_playerOrderID;

        this.d_fromCountryID = p_fromCountryID;

        this.d_toCountryID = -1;

        this.d_reinforcementsDeployed = p_reinforcementsDeployed;

        this.d_gameEngine = p_gameEngine;

        this.d_terminalRenderer = new TerminalRenderer(this.d_gameEngine);

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


