        package strategy;

        import controller.GameEngine;
        import models.Player;
        import models.orders.*;

        import java.util.ArrayList;
        import java.util.Random;

        public class RandomStrategy implements Strategy{
            private Player d_player;
            private Random random = new Random();

            private GameEngine d_gameEngine;
            public RandomStrategy(Player p_player,GameEngine p_gameEngine) {
                this.d_player = p_player;
                this.d_gameEngine = p_gameEngine;
            }

            /**
             * method which returns the random source country id
             * @return random source country id
             */
            @Override
            public int getSourceCountry() {
                int l_index = random.nextInt(  this.d_player.getAssignedCountries().size()-1);
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
                int l_index = random.nextInt(listOfAllBorderCountriesIDs.size()-1);
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

                int l_index = random.nextInt(listOfAllBorderCountriesIDs.size()-1);
                return listOfAllBorderCountriesIDs.get(l_index);
            }

            public int getOwnRandomCountry(int p_sourceCountryId)
            {
                int l_randomCountryId = p_sourceCountryId;
                ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
                do {
                    l_randomCountryId = this.d_player.getAssignedCountries().get(random.nextInt(this.d_player.getAssignedCountries().size()));
                }
                while(l_randomCountryId!=p_sourceCountryId);
                return l_randomCountryId;
            }

            /**
             * method which returns a random number of armies from 1 to number of armies from the army pool of player
             * @return random number of armies
             */
            public int getRandomNumberArmiesFromPool()
            {
                int l_numArmies = this.d_player.getReinforcements();
                return random.nextInt(l_numArmies)+1;
            }

            /**
             * method which returns a random number of armies from 1 to number of armies from the source country
             * @return random number of armies
             */
            public int getRandomNumberArmiesFromSourceCountry(int p_sourceCountryId)
            {
                int l_numArmies = this.d_gameEngine.d_worldmap.getCountry(p_sourceCountryId).getReinforcements();
                return random.nextInt(l_numArmies)+1;
            }
            public Order createOrder(){
                int randomNumber = random.nextInt(4) + 1;
                Order order = null;
                if (randomNumber == 1) {
                    order = new Deploy(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), getSourceCountry(), getRandomNumberArmiesFromPool(), this.d_gameEngine);
                    return order;
                } else if (randomNumber == 2) { //advance
                    int l_sourceCountryId = getSourceCountry();
                    int l_targetCountryID = getRandomNeigbouringCountry(l_sourceCountryId);
                    Player l_targetPlayer = null;
                    for(Player player : this.d_gameEngine.d_players){
                        if(player.getAssignedCountries().contains(l_targetCountryID)){
                            l_targetPlayer = player;
                        }
                    }
                    int l_armiesFromSourceCountry = getRandomNumberArmiesFromSourceCountry(l_sourceCountryId);
                    order  = new Advance(this.d_player, l_targetPlayer,this.d_player.getName(),this.d_player.getPlayerId(), l_sourceCountryId, l_targetCountryID, l_armiesFromSourceCountry, this.d_gameEngine);
                } else if (randomNumber == 3) {
                    if (this.d_player.containsCard("airlift")) {
                        int l_fromCountryID = getSourceCountry();
                        int l_toCountryID = getOwnRandomCountry(l_fromCountryID);
                        int l_randomArmiesToDeploy = random.nextInt(this.d_gameEngine.d_worldmap.getCountry(l_fromCountryID).getReinforcements())+1;
                        order = new Airlift(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), l_fromCountryID, l_toCountryID, l_randomArmiesToDeploy, this.d_gameEngine);
                    }
                    else if (this.d_player.containsCard("bomb")) {
                        int l_sourceCountryId = getSourceCountry();
                        int l_targetNeighborId = getTargetCountry(l_sourceCountryId);
                        Player l_targetPlayer = null;
                        for(Player player : this.d_gameEngine.d_players){
                            if(player.getAssignedCountries().contains(l_targetNeighborId)){
                                l_targetPlayer = player;
                            }
                        }
                        order = new Bomb(this.d_player, l_targetPlayer, this.d_player.getPlayerId(), this.d_player.getName(), l_targetNeighborId, this.d_gameEngine);
                    }
                    else if(this.d_player.containsCard("diplomacy"))
                    {   int l_randomTargetPlayer = this.d_player.getPlayerId();
                        do {
                            l_randomTargetPlayer = this.d_gameEngine.d_players.get(random.nextInt(this.d_gameEngine.d_players.size())).getPlayerId();
                        }
                        while(l_randomTargetPlayer!=this.d_player.getPlayerId());

                        order = new Diplomacy(this.d_player, this.d_gameEngine.d_players.get(l_randomTargetPlayer), this.d_player.getPlayerId(), this.d_player.getName());
                    }
                    else if(this.d_player.containsCard("blockade"))
                    {
                        order = new Blockade(this.d_player, this.d_player.getPlayerId(), this.d_player.getName(), getSourceCountry(), d_gameEngine);
                        this.d_player.removeCard("blockade");
                    }
                    else {
                        order = new Deploy(this.d_player, this.d_player.getName(), this.d_player.getPlayerId(), getSourceCountry(), getRandomNumberArmiesFromPool(), this.d_gameEngine);
                    }
                }
                return order;
            }


        }
