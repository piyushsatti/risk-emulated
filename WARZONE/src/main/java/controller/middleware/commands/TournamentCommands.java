package controller.middleware.commands;

import controller.GameEngine;
import controller.MapFileManagement.MapInterface;
import controller.statepattern.Phase;
import controller.statepattern.Tournament;
import controller.statepattern.gameplay.Startup;
import models.LogEntryBuffer;
import view.Logger;

import java.io.File;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a set of commands related to game startup operations.
 */
public class TournamentCommands extends Commands {
    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    /**
     * list of valid strategies
     */
    List<String> d_validStrategies = Arrays.asList("aggressive","benevolent","human","random","cheater");
    /**
     * Represents map interface.
     */
    MapInterface mp = new MapInterface();
    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger lw = new Logger(logEntryBuffer);

    /**
     * Represents the current phase in the game.
     */
    String p_currPhase;

    /**
     * list storing map files given by the user
     */
    List<String> d_mapFiles;
    /**
     * list storing input strategies given by the user
     */
    List<String> d_inputStrategies;
    /**
     * number of games
     */
    private int d_numberGames;
    /**
     * number of max turns
     */
    private int d_maxTurns;



    /**
     * method which returns the current gamephase
     * @param p_gameEngine
     * @return current game phase
     */
    public String getCurrentPhase(GameEngine p_gameEngine)
    {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index+1);
    }

    /**
     * Constructs a new instance of StartupCommands with the specified command and valid commands for tournament phase.
     *
     * @param p_command The command to be executed.
     */
    public TournamentCommands(String p_command) {
        super(p_command, new String[]{
                "tournament",
                "exit"
        });
    }

    /**
     * Validates the command based on the current state of the game engine.
     * The command is considered valid if it matches certain patterns and the current state is in the startup phase.
     *
     * @param p_gameEngine The game engine instance.
     * @return True if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand(GameEngine p_gameEngine) {
        Pattern pattern = Pattern.compile(
                        "^tournament\\s+-M\\s+(?:\\w+\\.map\\s+){1,5}-P\\s+(?:\\w+\\s+){2,4}-G\\s+[1-5]\\s+-D\\s+(?:[1-4][0-9]|50)$|"+
                                "^exit$|"
        );
        Matcher matcher = pattern.matcher(d_command) ;
        return matcher.matches() && (p_gameEngine.getCurrentState().getClass() == Tournament.class);
    }

    /**
     * Executes the command based on the current state of the game engine.
     *
     * @param p_gameEngine The game engine instance.
     */
    @Override
    public void execute(GameEngine p_gameEngine) {
        String d_currPhase = getCurrentPhase(p_gameEngine);
        if (!this.validateCommandName() ) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }
        if (!this.validateCommand(p_gameEngine) ) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command format");
            return;
        }

        int l_index = 2;
        d_mapFiles = new ArrayList<>();
        while(splitCommand[l_index].equals("-P"))
        {
            boolean flag = validateMapPresence(p_gameEngine,splitCommand[l_index]);
            if(!flag)
            {
                p_gameEngine.d_renderer.renderError("entered map file "+ splitCommand[l_index]+" does not exist");
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: tournament Not Executed, entered map file does not exist!");
                return;
            }
            if(d_mapFiles.contains(splitCommand[l_index]))
            {
                p_gameEngine.d_renderer.renderError("duplicate map file entered"+ splitCommand[l_index]);
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: tournament Not Executed, duplicate map file entered in command!");
                return;
            }
            d_mapFiles.add(splitCommand[l_index++]);
        }
        l_index++;
        d_inputStrategies = new ArrayList<>();
        while(!splitCommand[l_index].equals("-G"))
        {
            boolean flag = d_validStrategies.contains(splitCommand[l_index]);
            if(!flag)
            {
                p_gameEngine.d_renderer.renderError("entered strategy "+ splitCommand[l_index]+" is invalid");
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: tournament Not Executed, entered strategy does not exist!");
                return;
            }
            flag = d_inputStrategies.contains(splitCommand[l_index]);
            if(flag)
            {
                p_gameEngine.d_renderer.renderError("already entered strategy "+ splitCommand[l_index]);
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: tournament Not Executed, duplicate strategy entered in command!");
                return;
            }
            d_inputStrategies.add(splitCommand[l_index++]);
        }
        l_index++;
        d_numberGames = Integer.parseInt(splitCommand[l_index++]);
        d_maxTurns = Integer.parseInt(splitCommand[l_index]);
    }

    /**
     * method which checks if the map entered by the user in the tournament command exists or not.
     * @param p_gameEngine
     * @param p_mapName name of map file from tournament command
     * @return true: map exists or false: map does not exist
     */
    private boolean validateMapPresence(GameEngine p_gameEngine, String p_mapName) {
        File l_map_file_obj = new File(p_gameEngine.d_maps_folder + p_mapName);
        return l_map_file_obj.exists() && !l_map_file_obj.isDirectory();
    }
    private void startTournament(GameEngine p_gameEngine, List<String> d_mapFiles,List<String> d_inputStrategies,int d_numberGames, int d_maxTurns)  {
        MapInterface mp = new MapInterface();
        for(int i=0;i<d_numberGames;i++){
            for(String d_map : d_mapFiles){
                try {
                    logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " loading map for tournament");      // Log the command entry
                    p_gameEngine.d_worldmap = mp.loadMap(p_gameEngine,d_map);
                }
                catch(Exception e){
                    logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Could Not Load Map. Moving to Next Map");     // Log if map loading fails
                    System.out.println(e);
                }
                if(!p_gameEngine.d_worldmap.validateMap()){
                    p_gameEngine.d_renderer.renderError("Invalid Map! Cannot load into game");
                    p_gameEngine.d_worldmap = new WorldMap();
                    logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Tournament Map is Invalid!");
                }
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Tournament Map Loaded");
                for(String l_strategy : d_inputStrategies){
                    Player l_newPlayer = new Player(l_strategy,p_gameEngine);
                    switch(l_strategy){
                        case "aggressive":
                            l_newPlayer.setPlayerStrategy(new AggressiveStrategy(l_newPlayer,p_gameEngine));
                            break;
                        case "benevolent":
                            l_newPlayer.setPlayerStrategy(new BenevolentStrategy(l_newPlayer,p_gameEngine));
                            break;
                        case "cheater":
                            l_newPlayer.setPlayerStrategy(new CheaterStrategy(l_newPlayer,p_gameEngine));
                            break;
                        case "random":
                            l_newPlayer.setPlayerStrategy(new RandomStrategy(l_newPlayer,p_gameEngine));
                            break;
                        default:
                            l_newPlayer.setPlayerStrategy(new HumanStrategy(l_newPlayer,p_gameEngine));
                            break;
                    }
                    p_gameEngine.d_players.add(l_newPlayer);

                }
                if(assignCountries(p_gameEngine,p_currPhase)){
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ "Countries assigned to Players");
                }
                for( i=0;i<d_maxTurns;i++){
                    p_gameEngine.setCurrentState(new Reinforcement(p_gameEngine));
                }


            }
        }
    }
    private boolean assignCountries(GameEngine p_gameEngine,String p_currPhase) {
        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Assigning Countries to players in Tournament");
        if(p_gameEngine.d_players.size() == 0){
            p_gameEngine.d_renderer.renderError("Add atleast one player before assigning");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: assigncountries Not Executed  || Must add at least one player before assigning countries");
            return false;
        }
        if(p_gameEngine.d_worldmap.getCountries().size()==0){
            p_gameEngine.d_renderer.renderError(" Empty map Please load a Valid Map");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Executed Command: assigncountries Not Executed|| Load a valid map!");
            return false;
        }
        HashMap<Integer, Country> map = p_gameEngine.d_worldmap.getD_countries();
        Set<Integer> l_countryIDSet = map.keySet();
        ArrayList<Integer> l_countryIDList = new ArrayList<>(l_countryIDSet);

        int total_players = p_gameEngine.d_players.size();
        int playerNumber = 0;
        while (!l_countryIDList.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(l_countryIDList.size());
            int l_randomCountryID = l_countryIDList.get(randomIndex);
            l_countryIDList.remove(randomIndex);
            if ((playerNumber % total_players == 0) && playerNumber != 0) {
                playerNumber = 0;
            }
            Country country = map.get(l_randomCountryID);
            country.setCountryPlayerID(p_gameEngine.d_players.get(playerNumber).getPlayerId());
            p_gameEngine.d_players.get(playerNumber).setAssignedCountries(l_randomCountryID);
            playerNumber++;
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

}