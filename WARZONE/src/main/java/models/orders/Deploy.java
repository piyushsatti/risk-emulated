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

    /**
     * The ID representing the order of the player in the game.
     */
    int d_playerOrderID;

    /**
     * The name of the player in the player order.
     */
    String d_playerOrderName;

    /**
     * The ID of the country from which reinforcements are deployed.
     */
    private final int d_fromCountryID;

    /**
     * The ID of the country to which reinforcements are deployed.
     */
    private final int d_toCountryID;

    /**
     * The number of reinforcements deployed.
     */
    private final int d_reinforcementsDeployed;

    /**
     * The game engine responsible for processing this command.
     */
    GameEngine d_gameEngine;

    /**
     * The terminal renderer used for displaying game information.
     */
    TerminalRenderer d_terminalRenderer;

    /**
     * The player who initiated this reinforcement command.
     */
    Player d_sourcePlayer;

    /**
     * Constructs a Deploy object with the specified attributes.
     *
     * @param p_sourcePlayer           The player who initiated the reinforcement deployment.
     * @param p_playerOrderName        The name of the player issuing the deployment order.
     * @param p_playerOrderID          The ID of the player issuing the deployment order.
     * @param p_fromCountryID          The ID of the country from which reinforcements are deployed.
     * @param p_reinforcementsDeployed The number of reinforcements deployed.
     * @param p_gameEngine             The game engine responsible for processing this deployment.
     */
    public Deploy(Player p_sourcePlayer, String p_playerOrderName, int p_playerOrderID, int p_fromCountryID, int p_reinforcementsDeployed, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_playerOrderName = p_playerOrderName;
        this.d_playerOrderID = p_playerOrderID;
        this.d_fromCountryID = p_fromCountryID;
        this.d_toCountryID = -1;
        this.d_reinforcementsDeployed = p_reinforcementsDeployed;
        this.d_gameEngine = p_gameEngine;
        this.d_terminalRenderer = new TerminalRenderer(this.d_gameEngine);

    }

    /**
     * Validates the deployment command to ensure the player has sufficient reinforcements and owns the specified country.
     *
     * @param p_numberTobeDeployed The number of troops to be deployed
     * @return True if the deployment is valid, false otherwise
     */
    public boolean deployment_validator(int p_numberTobeDeployed) {
        return p_numberTobeDeployed <= this.d_sourcePlayer.getReinforcements();
    }

    /**
     * Validates the deploy command by checking if the player has enough troops, if the country ID is valid,
     * and if the player owns the specified country.
     *
     * @return True if the deploy command is valid, false otherwise
     * @throws InvalidCommandException if the command is invalid
     */
    @Override
    public boolean validateCommand() throws InvalidCommandException {

        if (!this.deployment_validator(this.d_reinforcementsDeployed)) {

            this.d_terminalRenderer.renderMessage("You (" + this.d_sourcePlayer.getName() + ") don't have enough troops for this deploy order");
            return false;
        }
        if (d_fromCountryID <= 0) {
            this.d_terminalRenderer.renderMessage(" (" + this.d_sourcePlayer.getName() + ") Negative countries not possible");
            return false;
        }
        if (!this.d_sourcePlayer.getAssignedCountries().contains(d_fromCountryID)) {
            this.d_terminalRenderer.renderMessage("You don't own the country");
            return false;
        }

        if (this.d_reinforcementsDeployed <= 0) {
            this.d_terminalRenderer.renderMessage("Cannot use negative or zero armies");
            return false;
        }

        return true;
    }

    /**
     * Executes the order by deploying reinforcements to the specified country.
     */
    public void execute() {
        if (this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID) == null) {
            return;
        }
        int l_currentReinforcements = this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getReinforcements();

        this.d_gameEngine.d_worldmap
                .getCountry(this.d_fromCountryID)
                .setReinforcements(this.d_reinforcementsDeployed + l_currentReinforcements);

        this.d_terminalRenderer.renderMessage(
                "Deploy Successful " +
                        this.d_reinforcementsDeployed +
                        " troops deployed on " +
                        this.d_gameEngine.d_worldmap.getCountry(this.d_fromCountryID).getCountryName() +
                        " by " +
                        this.d_playerOrderName
        );

    }

}


