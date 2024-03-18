package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.Phase;
import controller.statepattern.Starting;
import controller.statepattern.gameplay.Reinforcement;
import controller.statepattern.gameplay.Startup;
import models.LogEntryBuffer;
import models.Player;
import models.worldmap.Country;
import view.Logger;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StartupCommands extends Commands {
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    Logger lw = new Logger(logEntryBuffer);

    String p_currPhase;

    public String getCurrentPhase(GameEngine p_gameEngine)
    {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index+1);
    }

    public StartupCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap",
                "gameplayer",
                "assigncountries",
                "showmap",
                "exit"
        });
    }

    @Override
    public boolean validateCommand(GameEngine p_gameEngine) {
        Pattern pattern = Pattern.compile(
                "^loadmap\\s\\w+\\.map(\\s)*$|" +
                        "^assigncountries(\\s)*$|" +
                        "^gameplayer(?:(?:\\s+-add\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command) ;
        return matcher.matches() && (p_gameEngine.getCurrentState().getClass() == Startup.class);
    }

    @Override
    public void execute(GameEngine p_ge) {
        String d_currPhase = getCurrentPhase(p_ge);
        if (!this.validateCommandName() ) {
            p_ge.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }

        String commandName = splitCommand[0];

        switch (commandName) {
            case "assigncountries":
                if(assignCountries(p_ge,d_currPhase)){
                    logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Executed Command: assigncountries || Countries assigned to Players");
                    p_ge.setCurrentState(new Reinforcement(p_ge));
                }
                break;
            case "showmap":
                showmap(p_ge,d_currPhase);
                break;
            case "loadmap":
                loadMap(p_ge,d_currPhase);
                break;
            case "gameplayer":
                gameplayer(p_ge, splitCommand,d_currPhase);
                break;
            case "exit":
                p_ge.setCurrentState(new Starting(p_ge));
                logEntryBuffer.setString("Executed Exit Command");
                break;

        }
    }





    private boolean assignCountries(GameEngine ge,String p_currPhase) {
        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: assigncountries");
        if(ge.d_players.size() == 0){
            ge.d_renderer.renderError("Add atleast one player before assigning");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "Command: assigncountries Not Executed  || Must add at least one player before assigning countries");
            return false;
        }
        if(ge.d_worldmap.getCountries().size()==0){
            ge.d_renderer.renderError(" Empty map Please load a Valid Map");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Executed Command: assigncountries Not Executed|| Load a valid map!");
            return false;
        }
        HashMap<Integer, Country> map = ge.d_worldmap.getD_countries();
        Set<Integer> l_countryIDSet = map.keySet();
        ArrayList<Integer> l_countryIDList = new ArrayList<>(l_countryIDSet);

        int total_players = ge.d_players.size();
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
            country.setCountryPlayerID(ge.d_players.get(playerNumber).getPlayerId());
            ge.d_players.get(playerNumber).setAssignedCountries(l_randomCountryID);
            playerNumber++;
        }

        System.out.println("Assigning of Countries Done");
        for (Player l_player : ge.d_players) {
            System.out.println("Number of Countries: " + l_player.getAssignedCountries().size());
            System.out.println("List of Assigned Countries for Player: " + l_player.getName());
            ArrayList<Integer> l_listOfAssignedCountries = l_player.getAssignedCountries();
            for (Integer l_countryID : l_listOfAssignedCountries) {
                System.out.println(ge.d_worldmap.getCountry(l_countryID).getCountryName());
            }
            System.out.println("-----------------------------------------------------------------");

        }
        return true;

    }

    private void showmap(GameEngine ge,String p_currPhase){
        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: showmap");
        if(ge.d_worldmap == null){
            ge.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: showmap Not Executed || No map loaded into game!");
        }else{
            ge.d_renderer.showMap(false);
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command:" + d_command+ " Executed");
        }
    }



    private void loadMap(GameEngine ge,String p_currPhase){
        if(this.splitCommand.length < 2){
            ge.d_renderer.renderError("Invalid command! Correct format is loadmap <mapname>");
        }else{

            try {
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: loadmap" + d_command);
                MapInterface.loadMap2(ge, splitCommand[1]);
            }
            catch(Exception e){
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: loadmap Not Executed as Map Could Not be Loaded");
                System.out.println(e);
            }

            if(!MapInterface.validateMap(ge)){
                ge.d_renderer.renderError("Invalid Map! Cannot load into game");
                logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: loadmap Not Executed as Map is Invalid!");
            }
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Command: loadmap  Executed");
        }
    }

    public void addPlayers(GameEngine ge,List<String> p_playersToAdd,String p_currPhase)
    {
        List<String> l_playersAdded = new ArrayList<>();
        List<String> l_existingPlayers = new ArrayList<>();
        for(Player l_player : ge.d_players)
        {
            if(!l_existingPlayers.contains(l_player.getName())) l_existingPlayers.add(l_player.getName());
        }
        for(String l_playertoAdd : p_playersToAdd)
        {
            if(!l_existingPlayers.contains(l_playertoAdd))
            {
                ge.d_players.add(new Player(l_playertoAdd,ge));
                l_playersAdded.add(l_playertoAdd);
            }
        }
        if(!l_playersAdded.isEmpty())
        {
            System.out.println("added players sucessfully: "+ List.of(l_playersAdded));
            logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ "  Command: " + d_command + "Added Players "+ List.of(l_playersAdded)+ "successfully");
        }
    }

    public void removePlayers(GameEngine ge,List<String> p_copyList,String p_currPhase)
    {   System.out.println("players to remove:"+List.of(p_copyList));
        List<String> l_playerNotExist = new ArrayList<>();
        List<String> l_playersRemoved = new ArrayList<>();
        for(String l_playerCheck : p_copyList)
        {   boolean found = false;
            Iterator<Player> it = ge.d_players.iterator();
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
    public void gameplayer(GameEngine ge, String[] splitCommand,String p_currPhase){
        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Entered Command: gameplayer=>" + d_command );
        int l_len = splitCommand.length;
        List<String> addPlayers = new ArrayList<>();
        List<String> removePlayers = new ArrayList<>();
        for(int i=1;i<l_len;i+=2)
        {
            if(splitCommand[i].equals("-add") && !addPlayers.contains(splitCommand[i+1]))
            {
                addPlayers.add(splitCommand[i+1]);
            }
            else if(splitCommand[i].equals("-remove") && !removePlayers.contains(splitCommand[i+1])) {
                removePlayers.add(splitCommand[i+1]);
            }
        }
        logEntryBuffer.setString("Phase :"+ p_currPhase +"\n"+ " Executed Command: gameplayer=>" + d_command  );
        addPlayers(ge,addPlayers,p_currPhase);
        removePlayers(ge,removePlayers,p_currPhase);
    }

}
