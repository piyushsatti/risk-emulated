package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BenevolentStrategy class implements the Strategy interface for the benevolent strategy.
 */
public class BenevolentStrategy implements Strategy{

    /**
     * The player associated with this strategy.
     */
    private Player d_player;

    /**
     * Random object for generating random numbers.
     */
    private Random random = new Random();

    /**
     * The game engine associated with this strategy.
     */
    private GameEngine d_gameEngine;

    private String d_strategyName;

    /**
     * Constructor for BenevolentStrategy.
     * @param p_player The player using this strategy.
     * @param p_gameEngine The game engine in which the player is playing.
     */
    public BenevolentStrategy(Player p_player, GameEngine p_gameEngine) {
        this.d_player = p_player;
        this.d_gameEngine = p_gameEngine;
        this.d_strategyName = "Benevolent";
    }

    /**
     * Retrieves the name of the strategy.
     *
     * @return The name of the strategy.
     */
    public String getStrategyName() {
        return d_strategyName;
    }

    /**
     * Sets the name of the strategy.
     *
     * @param d_strategyName The name of the strategy to be set.
     */
    public void setStrategyName(String d_strategyName) {
        this.d_strategyName = d_strategyName;
    }

    /**
     * this method returns the weakest country ( country with minimum number of armies)
     * @return country id
     */
    @Override
    public int getSourceCountry() {
        int l_sourceCountryId = Integer.MAX_VALUE;
        List<Integer> l_listCountries = this.d_player.getAssignedCountries();
        int l_minArmies = Integer.MAX_VALUE;
        for (int i = 0; i < l_listCountries.size(); i++) {
            int l_armies = this.d_gameEngine.d_worldmap.getCountry(l_listCountries.get(i)).getReinforcements();
            if (l_armies < l_minArmies) {
                l_minArmies = l_armies;
                l_sourceCountryId = this.d_gameEngine.d_worldmap.getCountry(l_listCountries.get(i)).getCountryID();
            }
        }
        return l_sourceCountryId;
    }
    /**
     * this method returns the player's own random country's id which has more number of armies
     * and is neighboring to the weakest country
     * @return country id
     */
    public int getStrongerNeighborId(int p_weakestCountryId)
    {
        ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
        if(d_gameEngine.d_worldmap.getCountry(p_weakestCountryId) != null) {
            for (Integer id : d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).getAllBorderCountriesIDs()) {
                if (this.d_player.getAssignedCountries().contains(id) && (this.d_gameEngine.d_worldmap.getCountry(id).getReinforcements() > this.d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).getReinforcements())) {
                    listOfAllBorderCountriesIDs.add(id);
                }
            }
        }
        return listOfAllBorderCountriesIDs.size()==0 ? 0 : random.nextInt(listOfAllBorderCountriesIDs.size());
    }

    /**
     * this method returns the target enemy country
     * @param p_sourceCountryId
     * @return country id
     */
    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return -1;
    }

    /**
     * this method is used to create order for Aggressive player
     * @return Order object
     */
    public Order createOrder() {
        int randomNumber = random.nextInt(3) + 1;
        int p_weakestCountryId = getSourceCountry();
        int getStrongerNeighborId = getStrongerNeighborId(p_weakestCountryId);
        Order order = null;
        if (randomNumber == 1) { //deploy
            order = new Deploy(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), p_weakestCountryId, this.d_player.getReinforcements(), this.d_gameEngine);
        }
        else if (randomNumber == 2) //advance - move random armies from a random strong neighbor to the weakest country(has min armies)
        {
            if(getStrongerNeighborId==0) return null;
            int l_numArmiesStrongerCountry = this.d_gameEngine.d_worldmap.getCountry(getStrongerNeighborId).getReinforcements();
            System.out.println("l_numArmiesStrongerCountries"+ l_numArmiesStrongerCountry);
            if(l_numArmiesStrongerCountry==0) return null;
            int l_numArmiesToDeploy = random.nextInt(l_numArmiesStrongerCountry)+1;
            int l_currArmiesWeakestCountry = this.d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).getReinforcements();
            this.d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).setReinforcements(l_currArmiesWeakestCountry + l_numArmiesToDeploy);
            this.d_gameEngine.d_worldmap.getCountry(getStrongerNeighborId).setReinforcements(l_numArmiesStrongerCountry- l_numArmiesToDeploy);
        }
        else if (randomNumber == 3) {
            if (this.d_player.containsCard("airlift")) {
                if(getStrongerNeighborId==0) return null;
                int l_countrytoAirliftFrom = getStrongerNeighborId;
                int l_numArmiesToDeploy = random.nextInt(l_countrytoAirliftFrom)+1;
                order = new Airlift(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), l_countrytoAirliftFrom, p_weakestCountryId, l_numArmiesToDeploy, this.d_gameEngine);
                this.d_player.removeCard("airlift");
            }
            else if (this.d_player.containsCard("diplomacy")) {
                int l_randomTargetPlayer = this.d_player.getPlayerId();
                do {
                    l_randomTargetPlayer = this.d_gameEngine.d_players.get(random.nextInt(this.d_gameEngine.d_players.size())).getPlayerId();
                }
                while(l_randomTargetPlayer!=this.d_player.getPlayerId());
                order = new Diplomacy(this.d_player, this.d_gameEngine.d_players.get(l_randomTargetPlayer), this.d_player.getPlayerId(), this.d_player.getName());
                this.d_player.removeCard("diplomacy");
            }
        }
        return order;
    }


}
