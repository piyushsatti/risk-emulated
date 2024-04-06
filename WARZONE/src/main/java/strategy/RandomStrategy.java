        package strategy;

        import controller.GameEngine;
        import models.Player;
        import models.orders.*;

        import java.util.ArrayList;
        import java.util.Random;

        /**
         * Implements the Strategy interface for a player following a random strategy.
         */
        public class RandomStrategy implements Strategy{
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

            public String getStrategyName() {
                return d_strategyName;
            }

            public void setStrategyName(String d_strategyName) {
                this.d_strategyName = d_strategyName;
            }

            private String d_strategyName;

            /**
             * Constructor for RandomStrategy class.
             * @param p_player The player for which the strategy is applied.
             * @param p_gameEngine The game engine managing the game.
             */
            public RandomStrategy(Player p_player,GameEngine p_gameEngine) {
                this.d_player = p_player;
                this.d_gameEngine = p_gameEngine;
                this.d_strategyName = "Random";
            }

            /**
             * method which returns the random source country id
             * @return random source country id
             */
            @Override
            public int getSourceCountry() {
                int l_size = this.d_player.getAssignedCountries().size();
                if(l_size==0){
                    return -1;
                }
                int l_index = random.nextInt(l_size);
                return this.d_player.getAssignedCountries().get(l_index);

            }

            /**
             * this method return the random enemy country id
             * @param p_sourceCountryId
             * @return random enemy country id
             */
            @Override
            public int getTargetCountry(int p_sourceCountryId) {
                ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
                    for(Integer id : d_gameEngine.d_worldmap.getCountry(p_sourceCountryId).getAllBorderCountriesIDs()){
                        if(!this.d_player.getAssignedCountries().contains(id))
                        {
                            listOfAllBorderCountriesIDs.add(id);
                        }
                    }
                if(listOfAllBorderCountriesIDs.size() == 0) return -1;
                int l_index = random.nextInt(listOfAllBorderCountriesIDs.size());
                return listOfAllBorderCountriesIDs.get(l_index);
            }

            /**
             * method which returns the country id of the neighbor from which armies need to be moved from
             * @return neighboring country id
             */
            public int getRandomNeigbouringCountry(int sourceCountryID) {
                ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
                for(Integer id : d_gameEngine.d_worldmap.getCountry(sourceCountryID).getAllBorderCountriesIDs()){
                    listOfAllBorderCountriesIDs.add(id);

                }
               if(listOfAllBorderCountriesIDs.size() == 0){
                   return -1;
               }
                int l_index = random.nextInt(listOfAllBorderCountriesIDs.size());
                return listOfAllBorderCountriesIDs.get(l_index);
            }

            /**
             * this method returns a random country id from the countries that the player owns except the source country
             * @param p_sourceCountryId
             * @return country id
             */
            public int getOwnRandomCountry(int p_sourceCountryId)
            {
                int l_randomCountryId = p_sourceCountryId;

                // Get the list of own neighboring countries
                ArrayList<Integer> ownNeighboringCountries = new ArrayList<>();
                for (Integer id : d_gameEngine.d_worldmap.getCountry(p_sourceCountryId).getAllBorderCountriesIDs()) {
                    if (this.d_player.getAssignedCountries().contains(id)) {
                        ownNeighboringCountries.add(id);
                    }
                }

                // If there are no own neighboring countries, return -1
                if (ownNeighboringCountries.isEmpty()) {
                    return -1;
                }

                // Choose a random own neighboring country
                do {
                    l_randomCountryId = ownNeighboringCountries.get(random.nextInt(ownNeighboringCountries.size()));
                } while (l_randomCountryId == p_sourceCountryId);

                return l_randomCountryId;
            }

            /**
             * method which returns a random number of armies from 1 to number of armies from the army pool of player
             * @return random number of armies
             */
            public int getRandomNumberArmiesFromPool()
            {
                int l_numArmies = this.d_player.getReinforcements();
                if(l_numArmies ==0 ){
                    return 0;
                }
                return random.nextInt(l_numArmies)+1;
            }

            /**
             * method which returns a random number of armies from 1 to number of armies from the source country
             * @return random number of armies
             */
            public int getRandomNumberArmiesFromSourceCountry(int p_sourceCountryId)
            {
                int l_numArmies = this.d_gameEngine.d_worldmap.getCountry(p_sourceCountryId).getReinforcements();
                if(l_numArmies ==0){
                    return 0;
                }
                return random.nextInt(l_numArmies)+1;
            }
            /**
             * Creates an order based on a random strategy.
             * @return The order created based on the random strategy.
             */
            public Order createOrder(){
                int randomNumber = random.nextInt(4) + 1;
                Order order = null;
                if (randomNumber == 1) {
                    int l_sourceCountryId = getSourceCountry();
                    if(l_sourceCountryId == -1 || getRandomNumberArmiesFromPool() ==0){
                        return null;
                    }
                    order = new Deploy(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), getSourceCountry(), getRandomNumberArmiesFromPool(), this.d_gameEngine);
                    return order;
                } else if (randomNumber == 2) { //advance
                    int l_sourceCountryId = getSourceCountry();
                    if(l_sourceCountryId == -1){
                        return null;
                    }
                    int l_targetCountryID = getRandomNeigbouringCountry(l_sourceCountryId);
                    if(l_targetCountryID == -1){
                        return null;
                    }
                    Player l_targetPlayer = null;
                    for(Player player : this.d_gameEngine.d_players){
                        if(player.getAssignedCountries().contains(l_targetCountryID)){
                            l_targetPlayer = player;
                        }
                    }
                    int l_armiesFromSourceCountry = getRandomNumberArmiesFromSourceCountry(l_sourceCountryId);
                    if(l_armiesFromSourceCountry !=0){
                    order  = new Advance(this.d_player, l_targetPlayer,this.d_player.getName(),this.d_player.getPlayerId(), l_sourceCountryId, l_targetCountryID, l_armiesFromSourceCountry, this.d_gameEngine);
                    return order;
                    }
                    return null;
                } else if (randomNumber == 3) {
                    if (this.d_player.containsCard("airlift")) {
                        int l_fromCountryID = getSourceCountry();
                        int l_toCountryID = getOwnRandomCountry(l_fromCountryID);
                        if(l_toCountryID == -1 || l_fromCountryID == -1) {
                            return null;
                        }  if(this.d_gameEngine.d_worldmap.getCountry(l_fromCountryID).getReinforcements() == 0){
                            return null;
                        }
                            int l_randomArmiesToDeploy = random.nextInt(this.d_gameEngine.d_worldmap.getCountry(l_fromCountryID).getReinforcements()) + 1;
                            order = new Airlift(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), l_fromCountryID, l_toCountryID, l_randomArmiesToDeploy, this.d_gameEngine);
                            this.d_player.removeCard("airlift");
                            return order;

                    }
                    else if (this.d_player.containsCard("bomb")) {
                        int l_sourceCountryId = getSourceCountry();
                        if(l_sourceCountryId == -1){
                            return null;
                        }
                        int l_targetNeighborId = getTargetCountry(l_sourceCountryId);
                        if(l_targetNeighborId == -1){
                            return null;
                        }
                        Player l_targetPlayer = null;
                        for(Player player : this.d_gameEngine.d_players){
                            if(player.getAssignedCountries().contains(l_targetNeighborId)){
                                l_targetPlayer = player;
                            }
                        }
                        order = new Bomb(this.d_player, l_targetPlayer, this.d_player.getPlayerId(), this.d_player.getName(), l_targetNeighborId, this.d_gameEngine);
                        this.d_player.removeCard("bomb");
                        return order;
                    }
                    else if(this.d_player.containsCard("diplomacy"))
                    {   int l_randomTargetPlayer;
                        if (this.d_gameEngine.d_players.size() == 1) {
                            l_randomTargetPlayer = this.d_player.getPlayerId();
                        } else {
                            do {
                                l_randomTargetPlayer = this.d_gameEngine.d_players.get(random.nextInt(this.d_gameEngine.d_players.size())).getPlayerId();
                            } while (l_randomTargetPlayer == this.d_player.getPlayerId());
                        }

                        order = new Diplomacy(this.d_player, this.d_gameEngine.d_players.get(l_randomTargetPlayer), this.d_player.getPlayerId(), this.d_player.getName());
                        this.d_player.removeCard("diplomacy");
                        return order;
                    }
                    else if(this.d_player.containsCard("blockade"))
                    {  int l_sourceCountryId = getSourceCountry();
                        if(l_sourceCountryId == -1){
                            return null;
                        }
                        order = new Blockade(this.d_player, this.d_player.getPlayerId(), this.d_player.getName(), getSourceCountry(), d_gameEngine);
                        this.d_player.removeCard("blockade");
                        return order;
                    }
                    else {
                        int l_sourceCountryId = getSourceCountry();
                        if(l_sourceCountryId == -1 || getRandomNumberArmiesFromPool() ==0){
                            return null;
                        }
                        order = new Deploy(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), getSourceCountry(), getRandomNumberArmiesFromPool(), this.d_gameEngine);
                       return order;
                    }
                }
                return order;
            }


        }
