package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy implements Strategy{

    private Player d_player;
    private Random random = new Random();

    private GameEngine d_gameEngine;

    public BenevolentStrategy(Player p_player, GameEngine p_gameEngine) {
        this.d_player = p_player;
        this.d_gameEngine = p_gameEngine;
    }

    /**
     * this method returns the weakest country ( country with minimum number of armies)
     * @return country id
     */
    @Override
    public int getSourceCountry() {
        int l_sourceCountryId = Integer.MAX_VALUE;
        List<Integer> l_listCountries = this.d_player.getAssignedCountries();
        int l_minArmies = -1;
        for (int i = 0; i < l_listCountries.size(); i++) {
            int l_armies = this.d_gameEngine.d_worldmap.getCountry(i).getReinforcements();
            if (l_armies < l_minArmies) {
                l_minArmies = l_armies;
                l_sourceCountryId = this.d_gameEngine.d_worldmap.getCountry(i).getCountryID();
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
        for (Integer id : d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).getAllBorderCountriesIDs()) {
            if (this.d_player.getAssignedCountries().contains(id) && (this.d_gameEngine.d_worldmap.getCountry(id).getReinforcements() > this.d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).getReinforcements()))
            {
                listOfAllBorderCountriesIDs.add(id);
            }
        }
        return random.nextInt(listOfAllBorderCountriesIDs.size());
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
            int l_numArmiesStrongerCountry = this.d_gameEngine.d_worldmap.getCountry(getStrongerNeighborId).getReinforcements();
            int l_numArmiesToDeploy = random.nextInt(l_numArmiesStrongerCountry)+1;
            int l_currArmiesWeakestCountry = this.d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).getReinforcements();
            this.d_gameEngine.d_worldmap.getCountry(p_weakestCountryId).setReinforcements(l_currArmiesWeakestCountry + l_numArmiesToDeploy);
            this.d_gameEngine.d_worldmap.getCountry(getStrongerNeighborId).setReinforcements(l_numArmiesStrongerCountry- l_numArmiesToDeploy);
        }
        else if (randomNumber == 3) {
            if (this.d_player.containsCard("airlift")) {
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
