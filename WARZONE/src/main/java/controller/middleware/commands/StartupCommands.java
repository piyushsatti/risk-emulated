package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.Player;
import models.worldmap.Country;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartupCommands extends Commands {

    public StartupCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap",
                "gameplayer",
                "assigncountries",
                "showmap"
        });
    }

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

    @Override
    public void execute(GameEngine ge) {

        if (!this.validateCommandName() ) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format.");
            return;
        }
        else if(!this.validateCommand()){
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }

        String[] l_command = d_command.split(" ");

        switch (l_command[0]) {
            case "assigncountries":
                assignCountries(ge);
                break;
            case "showmap":
                showmap(ge);
                break;
            case "loadmap":
                loadMap(ge);
                break;
            case "gameplayer":
                break;
            case "exit":

        }
    }





    private void assignCountries(GameEngine ge) {

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

    }

    private void showmap(GameEngine ge){

        if(ge.d_worldmap == null){
            ge.d_renderer.renderError("No map loaded into game! Please use 'loadmap' command");
        }else{
            ge.d_renderer.showMap(false);
        }
    }

    private void loadMap(GameEngine ge){
        if(this.splitCommand.length < 2){
            ge.d_renderer.renderError("Invalid command! Correct format is loadmap <mapname>");
        }else{

            try {
                MapInterface.loadMap2(ge, splitCommand[1]);
            }
            catch(Exception e){
                System.out.println(e);
            }

            if(!MapInterface.validateMap(ge)){
                ge.d_renderer.renderError("Invalid Map! Cannot load into game");
            }
        }
    }

}
