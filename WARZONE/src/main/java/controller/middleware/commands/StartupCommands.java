package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.InvalidMapException;
import models.Player;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.PlayGame.assignCountriesToPlayers;

public class StartupCommands extends Commands {

    public StartupCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap",
                "gameplayer",
                "assigncountries"
        });
    }

    @Override
    public boolean validateCommand()
    {
        Pattern pattern = Pattern.compile(
                "^loadmap\\s\\w+\\.map(\\s)*$|"+
                        "^assigncountries(\\s)*$|"+
                        "^gameplayer(?:(?:\\s+-add\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    @Override
    void execute(GameEngine ge) {
        if (!this.validateCommand()) {
            ge.renderer.renderError("InvalidCommandException : Invalid Command Format.");
        }
        String[] l_command = d_command.trim().split("//s+");
        switch (l_command[0]) {
            case "gameplayer":
                updatePlayerList(ge,l_command);
                break;
            case "assigncountries":
                assignCountriesToPlayers(ge);
                break;
            case "loadmap":
                try {
                    MapInterface.loadMap(ge, l_command[1]);
                } catch (FileNotFoundException e) {
                    ge.renderer.renderError("FileNotFoundException : File does not exist.");
                } catch (NumberFormatException e) {
                    ge.renderer.renderError("NumberFormatException : File has incorrect formatting.");
                } catch (InvalidMapException e) {
                    ge.renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
                }
        }
    }
    public void updatePlayerList(GameEngine ge,String[] p_command)
    {   int l_len = p_command.length;
        for(int i=1;i<l_len;i+=2)
        {
            if(p_command[i].equals("-add") && !ge.PLAYER_LIST.contains(p_command[i+1]))
            {
                ge.PLAYER_LIST.add(new Player(p_command[i+1],ge));
            }
            else if(p_command[i].equals("-remove"))
            {
                List<Player> playerList = ge.PLAYER_LIST;
                for(Player player : playerList)
                {
                    if(player.getName().equals(p_command[i+1]))
                    {
                        ge.PLAYER_LIST.remove(player);
                    }
                }
            }
        }
    }
}
