package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.*;
import models.LogEntryBuffer;
import view.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapEditorCommands extends Commands{
    public MapEditorCommands(String p_command) {
        super(p_command, new String[]{
            "editcontinent",
                    "editcountry",
                    "editneighbor",
                    "showmap",
                    "savemap",
                    "editmap",
                    "validatemap"
        });
    }
    @Override
    public boolean validateCommand()
    {
        Pattern pattern = Pattern.compile("^editcontinent(?:(?:\\s+-add\\s+\\w+\\s+\\d+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|"+
                "^editcountry(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|"+
                "^editneighbor(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*)*(\\s)*$|" +
                "^showmap(\\s)*$|"+
                "^validatemap(\\s)*$|"+
                "^savemap\\s\\w+\\.map(\\s)*$|"+
                "^editmap\\s\\w+\\.map(\\s)*$");
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    @Override
    public void execute(GameEngine ge) {

        if (!this.validateCommand()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format.");
        }

        String[] l_command = d_command.trim().split("//s+");

        switch (l_command[0]) {
            case "showmap":
                ge.d_renderer.showMap(false);
                break;
            case "validatemap":
                MapInterface.validateMap(ge);
                break;
            case "savemap":
                try {
                    MapInterface.saveMap(ge, l_command[1]);
                } catch (IOException e) {
                    ge.d_renderer.renderError("IOException : Encountered File I/O Error");
                }
            case "editmap":
                try {
                    MapInterface.loadMap(ge, l_command[1]);
                } catch (FileNotFoundException e) {
                    ge.d_renderer.renderError("FileNotFoundException : File does not exist.");
                    ge.d_renderer.renderMessage("Creating file by the name : " + l_command[1]);
                    MapEditorCommands me = new MapEditorCommands("savemap " + l_command[1]);
                    me.execute(ge);
                    me = new MapEditorCommands("loadmap " + l_command[1]);
                    me.execute(ge);
                } catch (NumberFormatException e) {
                    ge.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
                } catch (InvalidMapException e) {
                    ge.d_renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
                }
                break;
            case "editcontinent":
                editContinent(ge, l_command, 1);
                break;
            case "editcountry":
                editCountry(ge, l_command, 1);
                break;
            case "editneighbor":
                editNeighbor(ge, l_command, 1);
                break;
        }
    }

    public void editContinent(GameEngine ge, String[] p_command, int i) {
        if (p_command[i].equals("-add")) {
            try {
                ge.d_worldmap.addContinent(
                        p_command[i + 1],
                        Integer.parseInt(p_command[i + 2])
                );
            } catch (ContinentAlreadyExistsException e) {
                ge.d_renderer.renderError("ContinentAlreadyExists : " + e.getMessage());
            }
            editContinent(ge, p_command, i += 3);
        } else {
            try {
                ge.d_worldmap.removeContinent(
                        ge.d_worldmap.getContinentID(p_command[i + 1])
                );
            } catch (ContinentDoesNotExistException e) {
                ge.d_renderer.renderError("ContinentDoesNotExist : " + e.getMessage());
            }

            editContinent(ge, p_command, i += 2);
        }
    }

    public void editCountry(GameEngine ge, String[] p_command, int i) {
        if (p_command[i].equals("-add")) {
            try {
                ge.d_worldmap.addCountry(
                        p_command[i + 1],
                        ge.d_worldmap.getContinentID(p_command[i + 2])
                );
            } catch (ContinentDoesNotExistException e) {
                ge.d_renderer.renderError("ContinentDoesNotExistException : " + e.getMessage());
            } catch (DuplicateCountryException e) {
                ge.d_renderer.renderError("DuplicateCountryException : " + e.getMessage());
            }
            editCountry(ge, p_command, i += 3);
        } else {
            try {
                ge.d_worldmap.removeCountry(
                        ge.d_worldmap.getCountryID(p_command[i + 1])
                );
            } catch (CountryDoesNotExistException e) {
                ge.d_renderer.renderError("CountryDoesNotExist : " + e.getMessage());
            }
            editCountry(ge, p_command, i += 2);
        }
    }

    public void editNeighbor(GameEngine ge, String[] p_command, int i) {
        if (p_command[i].equals("-add")) {
            try {
                ge.d_worldmap.addBorder(
                        ge.d_worldmap.getCountryID(p_command[i + 1]),
                        ge.d_worldmap.getCountryID(p_command[i + 2])
                );
            } catch (CountryDoesNotExistException e) {
                ge.d_renderer.renderError("CountryDoesNotExistException : " + e.getMessage());

            }
            editNeighbor(ge, p_command, i += 3);
        } else {
            try {
                ge.d_worldmap.removeBorder(
                        ge.d_worldmap.getCountryID(p_command[i + 1]),
                        ge.d_worldmap.getCountryID(p_command[i + 2])
                );
            } catch (CountryDoesNotExistException e) {
                ge.d_renderer.renderError("CountryDoesNotExistException : " + e.getMessage());
            }

            editNeighbor(ge, p_command, i += 3);
        }
    }

}