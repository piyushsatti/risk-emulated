package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AggressiveStrategy  implements Strategy {

    private Player d_player;
    private Random random = new Random();

    private GameEngine d_gameEngine;

    public AggressiveStrategy(Player p_player, GameEngine p_gameEngine) {
        this.d_player = p_player;
        this.d_gameEngine = p_gameEngine;
    }


    /**
     * this method returns the country id which has maximum number of armies
     * @return country id
     */
    @Override
    public int getSourceCountry() {
        int l_sourceCountryId = -1;
        List<Integer> l_listCountries = this.d_player.getAssignedCountries();
        int l_maxArmies = -1;
        for (int i = 0; i < l_listCountries.size(); i++) {
            int l_armies = this.d_gameEngine.d_worldmap.getCountry(i).getReinforcements();
            if (l_armies > l_maxArmies) {
                l_maxArmies = l_armies;
                l_sourceCountryId = this.d_gameEngine.d_worldmap.getCountry(i).getCountryID();
            }
        }
        return l_sourceCountryId;
    }

    /**
     * this method returns a random country id from the countries that the player owns except the source country
     *
     * @param p_sourceCountryId
     * @return country id
     */
    public int getOwnRandomCountry(int p_sourceCountryId) {
        int l_randomCountryId = p_sourceCountryId;
        ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
        do {
            l_randomCountryId = this.d_player.getAssignedCountries().get(random.nextInt(this.d_player.getAssignedCountries().size()));
        }
        while (l_randomCountryId != p_sourceCountryId);
        return l_randomCountryId;
    }

    /**
     * this method returns own neighboring countries to the source
     *
     * @param p_sourceCountryId
     * @return country id
     */
    public List<Integer> getOwnNeighboringCountriesToSource(int p_sourceCountryId) {
        ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
        for (Integer id : d_gameEngine.d_worldmap.getCountry(p_sourceCountryId).getAllBorderCountriesIDs()) {
            if (this.d_player.getAssignedCountries().contains(id)) {
                listOfAllBorderCountriesIDs.add(id);
            }
        }
        return listOfAllBorderCountriesIDs;
    }

    /**
     * this method returns the random enemy country id, aggressive player about which country is to be attacked
     *
     * @param p_sourceCountryId
     * @return random enemy country id
     */
    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
        for (Integer id : d_gameEngine.d_worldmap.getCountry(p_sourceCountryId).getAllBorderCountriesIDs()) {
            if (!this.d_player.getAssignedCountries().contains(id)) {
                listOfAllBorderCountriesIDs.add(id);
            }
        }
        int l_index = random.nextInt(listOfAllBorderCountriesIDs.size() - 1);
        return listOfAllBorderCountriesIDs.get(l_index);
    }

    /**
     * this method is used to create order for Aggressive player
     * @return Order object
     */
    public Order createOrder() {
        int randomNumber = random.nextInt(3) + 1;
        int l_strongestCountryId = getSourceCountry();
        int l_targetCountryID = getTargetCountry(l_strongestCountryId);
        Order order = null;
        if (randomNumber == 1) { //deploy
            order = new Deploy(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), getSourceCountry(), this.d_player.getReinforcements(), this.d_gameEngine);
        }
        else if (randomNumber == 2) { //advance - attack
            int l_maxArmies = this.d_gameEngine.d_worldmap.getCountry(l_strongestCountryId).getReinforcements();
            Player l_targetPlayer = null;
            for (Player player : this.d_gameEngine.d_players) {
                if (player.getAssignedCountries().contains(l_targetCountryID)) {
                    l_targetPlayer = player;
                }
            }
            order = new Advance(this.d_player, l_targetPlayer, this.d_player.getName(), this.d_player.getPlayerId(), l_strongestCountryId, l_targetCountryID, l_maxArmies, this.d_gameEngine);
        }
        else if (randomNumber == 3) //advance - move armies from neighbors(owned by player) to the source country(has max armies)
        {
            List<Integer> ownNeighboringCountriesToSource = getOwnNeighboringCountriesToSource(l_strongestCountryId);
            for (int l_country : ownNeighboringCountriesToSource) {
                int l_armiesToAdd = this.d_gameEngine.d_worldmap.getCountry(l_country).getReinforcements();
                this.d_gameEngine.d_worldmap.getCountry(l_country).setReinforcements(0);
                int l_currArmies = this.d_gameEngine.d_worldmap.getCountry(l_targetCountryID).getReinforcements();
                this.d_gameEngine.d_worldmap.getCountry(l_targetCountryID).setReinforcements(l_currArmies + l_armiesToAdd);
            }
        }
        else if (randomNumber == 4) {
            if (this.d_player.containsCard("airlift")) {
                int l_countrytoAirliftFrom = getOwnRandomCountry(l_strongestCountryId);
                int l_armiesToDeploy = this.d_gameEngine.d_worldmap.getCountry(l_countrytoAirliftFrom).getReinforcements();
                order = new Airlift(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), l_countrytoAirliftFrom, l_strongestCountryId, l_armiesToDeploy, this.d_gameEngine);
                this.d_player.removeCard("airlift");
            }
            else if (this.d_player.containsCard("bomb")) {
                int l_countryToBomb = getTargetCountry(l_strongestCountryId);
                Player l_targetPlayer = null;
                for (Player player : this.d_gameEngine.d_players) {
                    if (player.getAssignedCountries().contains(l_countryToBomb)) {
                        l_targetPlayer = player;
                    }
                }
                order = new Bomb(this.d_player, l_targetPlayer, this.d_player.getPlayerId(), this.d_player.getName(), l_countryToBomb, this.d_gameEngine);
                this.d_player.removeCard("bomb");
            }
        }
        return order;
    }
}

