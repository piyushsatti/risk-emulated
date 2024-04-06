    package controller.middleware.commands;

    import controller.GameEngine;
    import controller.MapFileManagement.MapInterface;
    import controller.statepattern.Phase;
    import controller.statepattern.Starting;
    import controller.statepattern.gameplay.Reinforcement;
    import controller.statepattern.gameplay.Startup;
    import models.LogEntryBuffer;
    import models.Player;
    import models.worldmap.Country;
    import models.worldmap.WorldMap;
    import strategy.*;
    import view.Logger;
    import view.TerminalRenderer;

    import java.util.*;

    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    /**
     * Represents a set of commands related to game startup operations.
     */
    public class StartupCommands extends Commands {
        /**
         * Represents a buffer for storing log entries.
         */
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        /**
         * list of valid strategies
         */
        List<String> d_validStrategies = Arrays.asList("aggressive","benevolent","human","random","cheater");
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
         * Retrieves the current phase of the game engine.
         *
         * @param p_gameEngine The game engine instance.
         * @return The name of the current phase.
         */
        public String getCurrentPhase(GameEngine p_gameEngine)
        {
            Phase phase = p_gameEngine.getCurrentState();
            String l_currClass = String.valueOf(phase.getClass());
            int l_index = l_currClass.lastIndexOf(".");
            return l_currClass.substring(l_index+1);
        }

        /**
         * Constructs a new instance of StartupCommands with the specified command and valid commands for startup phase.
         *
         * @param p_command The command to be executed.
         */
        public StartupCommands(String p_command) {
            super(p_command, new String[]{
                    "loadmap",
                    "gameplayer",
                    "assigncountries",
                    "showmap",
                    "exit",
                    "loadgame"
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
                    "^loadmap\\s\\w+\\.map(\\s)*$|" +
                            "^assigncountries(\\s)*$|" +
                            "gameplayer(?:\\s+-add\\s+\\w+\\s+\\w+)?(?:\\s+-remove\\s+\\w+)?|" +
                            "^loadgame\\s\\w+\\.map(\\s)*$|"
            );
            Matcher matcher = pattern.matcher(d_command) ;
            boolean flag1 = matcher.matches();
            boolean flag2 = p_gameEngine.getCurrentState().getClass() == Startup.class;
            return matcher.matches() && (p_gameEngine.getCurrentState().getClass() == Startup.class);
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

            String commandName = splitCommand[0];

            switch (commandName) {
                case "assigncountries":
                    if(assignCountries(p_gameEngine,d_currPhase)){
                        logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Executed Command: assigncountries || Countries assigned to Players");
                        p_gameEngine.setCurrentState(new Reinforcement(p_gameEngine));
                    }
                    break;
                case "showmap":
                    showmap(p_gameEngine,d_currPhase);
                    break;
                case "loadmap":
                    loadMap(p_gameEngine,d_currPhase);
                    break;
                case "gameplayer":
                    gameplayer(p_gameEngine, splitCommand,d_currPhase);
                    break;
                case "exit":
                    p_gameEngine.setCurrentState(new Starting(p_gameEngine));
                    logEntryBuffer.setString("Executed Exit Command");
                    break;

            }
        }

        /**
         * Assigns countries to players in the game engine.
         *
         * @param p_gameEngine          The game engine instance.
         * @param p_currPhase The current phase of the game engine.
         * @return True if the countries are successfully assigned to players, false otherwise.
         */
        private boolean assignCountries(GameEngine p_gameEngine,String p_currPhase) {
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: assigncountries");
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

        /**
         * Assigns countries to players during the startup phase of the game.
         *
         * @param p_gameEngine          The game engine instance.
         * @param p_currPhase The current phase of the game.
         *
         */
        private void showmap(GameEngine p_gameEngine,String p_currPhase){
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: showmap");
            if(p_gameEngine.d_worldmap == null){
                p_gameEngine.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: showmap Not Executed || No map loaded into game!");
            }else{
                p_gameEngine.d_renderer.showMap(false);
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command:" + d_command+ " Executed");
            }
        }

        /**
         * Loads a map into the game engine and validates it.
         * If the map is invalid, an error message is rendered.
         *
         * @param p_gameEngine         The GameEngine instance managing the game state.
         * @param p_currPhase The current phase of the game.
         */

        private void loadMap(GameEngine p_gameEngine,String p_currPhase){
            if(this.splitCommand.length < 2){
                // Render error if the command format is invalid
                p_gameEngine.d_renderer.renderError("Invalid command! Correct format is loadmap <mapname>");
            }else{

                try {
                    logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: loadmap" + d_command);      // Log the command entry
                    p_gameEngine.d_worldmap = mp.loadMap(p_gameEngine, splitCommand[1]);
                }
                catch(Exception e){

                    logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: loadmap Not Executed as Map Could Not be Loaded");     // Log if map loading fails
                    System.out.println(e);
                }

                if(!p_gameEngine.d_worldmap.validateMap()){
                    p_gameEngine.d_renderer.renderError("Invalid Map! Cannot load into game");
                    p_gameEngine.d_worldmap = new WorldMap();
                    logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: loadmap Not Executed as Map is Invalid!");
                }
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: loadmap  Executed");
            }
        }

        /**
         * Adds players to the game engine if they are not already existing.
         * The method logs the players successfully added to the game engine.
         *
         * @param p_gameEngine              The GameEngine instance managing the game state.
         * @param p_playersToAdd  The list of player names to add to the game engine.
         * @param p_currPhase     The current phase of the game.
         */public void addPlayers(GameEngine p_gameEngine,List<String[]> p_playersToAdd,String p_currPhase)
        {
            List<String[]> l_playersAdded = new ArrayList<>();
            List<String> l_existingPlayers = new ArrayList<>();
            for(Player l_player : p_gameEngine.d_players)
            {
                if(!l_existingPlayers.contains(l_player.getName())) l_existingPlayers.add(l_player.getName());
            }
            for(String[] l_playertoAdd : p_playersToAdd)
            {
                if(!l_existingPlayers.contains(l_playertoAdd[0]))
                {
                    Player l_newPlayer = new Player(l_playertoAdd[0],p_gameEngine);
                    p_gameEngine.d_players.add(l_newPlayer);
                    switch(l_playertoAdd[1]){
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
                    l_playersAdded.add(l_playertoAdd);
                }
            }
            if(!l_playersAdded.isEmpty())
            {
                System.out.println("added players sucessfully: "+ List.of(l_playersAdded));
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "  Command: " + d_command + "Added Players "+ List.of(l_playersAdded)+ "successfully");
            }
        }

        /**
         * Removes players from the game engine if they exist in the list of players.
         * Players are removed based on the list of player names provided.
         *
         * @param p_gameEngine            The GameEngine instance managing the game state.
         * @param p_copyList    The list of player names to remove from the game engine.
         * @param p_currPhase   The current phase of the game.
         */
        public void removePlayers(GameEngine p_gameEngine,List<String> p_copyList,String p_currPhase)
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
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "  Command: " + d_command + "Removed Players "+ List.of(l_playersRemoved)+ "successfully");
            if(!l_playerNotExist.isEmpty())
            {
                System.out.println("could not remove players as they don't exist: "+List.of(l_playerNotExist));
            }
        }
        /**
         * Modifies the list of players in the game engine based on the provided command.
         * It logs the execution of the command and the players added or removed.
         *
         * @param p_gameEngine            The GameEngine instance managing the game state.
         * @param splitCommand  The array containing the command and its arguments.
         * @param p_currPhase   The current phase of the game.
         */
        public void gameplayer(GameEngine p_gameEngine, String[] splitCommand,String p_currPhase){
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: gameplayer=>" + d_command );
            int l_len = splitCommand.length;
            List<String[]> l_addPlayers = new ArrayList<>();
            List<String> l_removePlayers = new ArrayList<>();
            int l_index = 1;
            while(l_index<l_len)
            {
                if(splitCommand[l_index].equals("-add"))
                {   if(!d_validStrategies.contains(splitCommand[l_index+2].toLowerCase()))
                    {
                        p_gameEngine.d_renderer.renderError("invalid strategy entered. valid strategies: \"aggressive\",\"benevolent\",\"human\",\"random\",\"cheater\"");
                        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: gameplayer Not Executed  || invalid strategy entered");
                        return;
                    }
                    if(!l_addPlayers.contains(splitCommand[l_index+1]))
                    {
                        String[] l_addPlayer = new String[2]; //0th index:player name 1st index: strategy
                        l_addPlayer[0] = splitCommand[l_index+1];
                        l_addPlayer[1] = splitCommand[l_index+2];
                        l_addPlayers.add(l_addPlayer);
                    }
                    l_index += 3;            }
                else if(splitCommand[l_index].equals("-remove") && !l_removePlayers.contains(splitCommand[l_index+1])) {
                    l_removePlayers.add(splitCommand[l_index+1]);
                    l_index += 2;
                }
            }
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Executed Command: gameplayer=>" + d_command  );
            addPlayers(p_gameEngine,l_addPlayers,p_currPhase);
            removePlayers(p_gameEngine,l_removePlayers,p_currPhase);
        }

    }
