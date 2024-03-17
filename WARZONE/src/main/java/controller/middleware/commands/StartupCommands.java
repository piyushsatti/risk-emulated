package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.End;
import controller.statepattern.Starting;
import controller.statepattern.gameplay.IssueOrder;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.Startup;
import models.Player;
import models.worldmap.Country;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents commands executed during game startup.
 */
public class StartupCommands extends Commands {

    /**
     * Constructor for StartupCommands.
     *
     * @param p_command The command string.
     */
    public StartupCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap",
                "gameplayer",
                "assigncountries",
                "showmap",
                "exit"
        });
    }

    /**
     * Validates the command format for startup commands.
     *
     * @return True if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Pattern pattern = Pattern.compile(
                "^loadmap\\s\\w+\\.map(\\s)*$|" +
                        "^assigncountries(\\s)*$|" +
                        "^gameplayer(?:(?:\\s+-add\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    /**
     * Executes the startup commands.
     *
     * @param p_gameEngine The GameEngine object used to execute the command.
     */
    @Override
    public void execute(GameEngine p_gameEngine) {

        if (!this.validateCommandName() ) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }

        String l_commandName = splitCommand[0];

        switch (l_commandName) {
            case "assigncountries":
                if(assignCountries(p_gameEngine)){
                    p_gameEngine.setCurrentState(new Reinforcement(p_gameEngine));
                }
                break;
            case "showmap":
                showmap(p_gameEngine);
                break;
            case "loadmap":
                loadMap(p_gameEngine);
                break;
            case "gameplayer":
                gameplayer(p_gameEngine, splitCommand);
                break;
            case "exit":
                p_gameEngine.setCurrentState(new Starting(p_gameEngine));
                break;

        }
    }


    /**
     * Assigns countries to players during game setup.
     *
     * @param p_gameEngine The GameEngine object.
     * @return True if countries are assigned successfully, false otherwise.
     */
    private boolean assignCountries(GameEngine p_gameEngine) {
        if(p_gameEngine.d_players.size() == 0){
            p_gameEngine.d_renderer.renderError("Add atleast one player before assigning");
            return false;
        }
        if(p_gameEngine.d_worldmap.getCountries().size()==0){
            p_gameEngine.d_renderer.renderError(" Empty map Please load a Valid Map");
            return false;
        }
        HashMap<Integer, Country> l_map = p_gameEngine.d_worldmap.getD_countries();
        Set<Integer> l_countryIDSet = l_map.keySet();
        ArrayList<Integer> l_countryIDList = new ArrayList<>(l_countryIDSet);

        int l_total_players = p_gameEngine.d_players.size();
        int l_playerNumber = 0;
        while (!l_countryIDList.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(l_countryIDList.size());
            int l_randomCountryID = l_countryIDList.get(randomIndex);
            l_countryIDList.remove(randomIndex);
            if ((l_playerNumber % l_total_players == 0) && l_playerNumber != 0) {
                l_playerNumber = 0;
            }
            Country country = l_map.get(l_randomCountryID);
            country.setCountryPlayerID(p_gameEngine.d_players.get(l_playerNumber).getPlayerId());
            p_gameEngine.d_players.get(l_playerNumber).setAssignedCountries(l_randomCountryID);
            l_playerNumber++;
        }

        System.out.println("Assigning of Countries Done");
        for (Player l_player : p_gameEngine.d_players) {
            System.out.println("Number of Countries: " + l_player.getAssignedCountries().size());
            System.out.println("List of Assigned Countries for Player: " + l_player.getName());
            ArrayList<Integer> l_listOfAssignedCountries = l_player.getAssignedCountries();
            for (Integer l_countryID : l_listOfAssignedCountries) {
                System.out.println(p_gameEngine.d_worldmap.getCountry(l_countryID).getCountryName());
            }
            System.out.println("-----------------------------------------------------------------");

        }
        return true;

    }
    /**
     * Displays the map.
     *
     * @param p_gameEngine The GameEngine object.
     */
    private void showmap(GameEngine p_gameEngine){

        if(p_gameEngine.d_worldmap == null){
            p_gameEngine.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
        }else{
            p_gameEngine.d_renderer.showMap(false);
        }
    }


    /**
     * Loads the map.
     *
     * @param p_gameEngine The GameEngine object.
     */
    private void loadMap(GameEngine p_gameEngine){
        if(this.splitCommand.length < 2){
            p_gameEngine.d_renderer.renderError("Invalid command! Correct format is loadmap <mapname>");
        }else{

            try {
                MapInterface.loadMap2(p_gameEngine, splitCommand[1]);
            }
            catch(Exception e){
                System.out.println(e);
            }

            if(!MapInterface.validateMap(p_gameEngine)){
                p_gameEngine.d_renderer.renderError("Invalid Map! Cannot load into game");
            }
        }
    }

    /**
     * Adds players to the game.
     *
     * @param p_gameEngine The GameEngine object.
     * @param p_playersToAdd The list of players to add.
     */
    public void addPlayers(GameEngine p_gameEngine,List<String> p_playersToAdd)
    {
        List<String> l_playersAdded = new ArrayList<>();
        List<String> l_existingPlayers = new ArrayList<>();
        for(Player l_player : p_gameEngine.d_players)
        {
            if(!l_existingPlayers.contains(l_player.getName())) l_existingPlayers.add(l_player.getName());
        }
        for(String l_playertoAdd : p_playersToAdd)
        {
            if(!l_existingPlayers.contains(l_playertoAdd))
            {
                p_gameEngine.d_players.add(new Player(l_playertoAdd,p_gameEngine));
                l_playersAdded.add(l_playertoAdd);
            }
        }
        if(!l_playersAdded.isEmpty()) System.out.println("added players sucessfully: "+ List.of(l_playersAdded));
    }

    /**
     * Removes players from the game.
     *
     * @param p_gameEngine The GameEngine object.
     * @param p_copyList The list of players to remove.
     */
    public void removePlayers(GameEngine p_gameEngine,List<String> p_copyList)
    {   System.out.println("players to remove:"+List.of(p_copyList));
        List<String> l_playerNotExist = new ArrayList<>();
        List<String> l_playersRemoved = new ArrayList<>();
        for(String l_playerCheck : p_copyList)
        {   boolean found = false;
            Iterator<Player> it = p_gameEngine.d_players.iterator();
            while (it.hasNext()) {
                Player l_player = it.next();
                if (l_player.getName().equals(l_playerCheck)) {
                    found = true;
                    it.remove();
                    l_playersRemoved.add(l_playerCheck);
                }
            }
            if(!found) l_playerNotExist.add(l_playerCheck);
        }
        System.out.println("players removed successfully: "+List.of(l_playersRemoved));
        if(!l_playerNotExist.isEmpty())
        {
            System.out.println("could not remove players as they don't exist: "+List.of(l_playerNotExist));
        }
    }
    /**
     * Modifies the list of players in the game.
     *
     * @param p_gameEngine The GameEngine object.
     * @param p_splitCommand The array containing command arguments.
     */
    public void gameplayer(GameEngine p_gameEngine, String[] p_splitCommand){
        int l_len = p_splitCommand.length;
        List<String> l_addPlayers = new ArrayList<>();
        List<String> l_removePlayers = new ArrayList<>();
        for(int i=1;i<l_len;i+=2)
        {
            if(p_splitCommand[i].equals("-add") && !l_addPlayers.contains(p_splitCommand[i+1]))
            {
                l_addPlayers.add(p_splitCommand[i+1]);
            }
            else if(p_splitCommand[i].equals("-remove") && !l_removePlayers.contains(p_splitCommand[i+1])) {
                l_removePlayers.add(p_splitCommand[i+1]);
            }
        }
        addPlayers(p_gameEngine,l_addPlayers);
        removePlayers(p_gameEngine,l_removePlayers);

    }

}
