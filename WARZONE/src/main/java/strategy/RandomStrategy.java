package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.Advance;

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

    @Override
    public int getTargetCountry() {
        ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
        for(int countryIDs: this.d_player.getAssignedCountries()){
            for(Integer id : d_gameEngine.d_worldmap.getCountry(countryIDs).getAllBorderCountriesIDs()){
                if(!this.d_player.getAssignedCountries().contains(id)){
                    listOfAllBorderCountriesIDs.add(id);
                }
            }
        }
        int l_index = random.nextInt(listOfAllBorderCountriesIDs.size()-1);
        return listOfAllBorderCountriesIDs.get(l_index);

    }

    /**
     * method which returns the country id of the neighbor from which armies need to be moved from
     * @return neighboring country id
     */
    public int getNeigbouringCountry() {
        ArrayList<Integer> listOfAllBorderCountriesIDs = new ArrayList<>();
        for(int countryIDs: this.d_player.getAssignedCountries()){
            for(Integer id : d_gameEngine.d_worldmap.getCountry(countryIDs).getAllBorderCountriesIDs()){
                if(this.d_player.getAssignedCountries().contains(id)){
                    listOfAllBorderCountriesIDs.add(id);
                }
            }
        }
        int l_index = random.nextInt(listOfAllBorderCountriesIDs.size()-1);
        return listOfAllBorderCountriesIDs.get(l_index);
    }

    /**
     * method which returns a random number of armies from 1 to number of armies in the source country
     * @return random number of armies
     */
    public int getRandomNumberArmies()
    {
        int l_numArmies = this.d_gameEngine.d_worldmap.getCountry(this.getSourceCountry()).getReinforcements();
        return random.nextInt(l_numArmies)+1;
    }


    public void createOrder(){

        int randomNumber = random.nextInt(4) + 1; // Generates random number between 0 (inclusive) and 4 (exclusive), adding 1 to make it between 1 and 4

        // Trigger actions based on the generated random number
        if (randomNumber == 1) {

            // Perform action A
        } else if (randomNumber == 2) {
            System.out.println("Random number is 2. Performing action B.");
            // Perform action B
        } else if (randomNumber == 3) {
            System.out.println("Random number is 3. Performing action C.");
            // Perform action C
        } else {
            System.out.println("Random number is 4. Performing action D.");
            // Perform action D
        }
    }


}
