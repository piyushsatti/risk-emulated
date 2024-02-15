package views;

import exceptions.InvalidCommandException;
import exceptions.InvalidPlayerToRemoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandValidator1 {
    private static final HashMap<String, List<String>> d_commandGamePhaseMap;
    private static final List<String> d_validCommandList;

    static {
        d_commandGamePhaseMap = new HashMap<>();
        List<String> l_commands = new ArrayList<>(
                Arrays.asList("editcontinent", "editcountry", "editneighbor", "savemap",
                        "editmap", "validatemap", "showmap"));
        d_commandGamePhaseMap.put("mapEditor", l_commands);
        l_commands.clear();
        l_commands.add("showmap");
        d_commandGamePhaseMap.put("gamePlay", l_commands);
        l_commands.clear();
        l_commands.add("loadmap");
        l_commands.add("gameplayer");
        l_commands.add("assigncountries");
        d_commandGamePhaseMap.put("startUp", l_commands);
        l_commands.clear();
        l_commands.add("deploy");
        d_commandGamePhaseMap.put("mainGameLoop", l_commands);

        d_validCommandList = new ArrayList<>(Arrays.asList("editcontinent", "editcountry", "editneighbor", "showmap",
                "savemap", "editmap", "validatemap", "loadmap", "gameplayer", "deploy", "assigncountries"));
    }
    // CommandValidator c2;
    // c2.addCommand("ehstrbrt")
    String[] d_command_arr = null;
    public void addCommand(String p_entered_command, String p_game_phase_string) throws InvalidCommandException, InvalidPlayerToRemoveException {
        this.d_command_arr = checkCommandValidity(p_entered_command, p_game_phase_string);
    }
    public String[] checkCommandValidity(String p_enteredCommand, String p_gamePhaseString)
            throws InvalidCommandException, NumberFormatException, InvalidPlayerToRemoveException {
        String[] l_command = new String[0];
        try {
            if (p_enteredCommand.isEmpty()) {
                throw new InvalidCommandException("empty command entered");
            }
            p_enteredCommand = p_enteredCommand.trim();
            l_command = p_enteredCommand.split(" ");
            String mainCommand = l_command[0];
            if (!d_validCommandList.contains(mainCommand)) {
                throw new InvalidCommandException("invalid command entered");
            } else if (!d_commandGamePhaseMap.get(p_gamePhaseString).contains(mainCommand)) {
                throw new InvalidCommandException("entered command invalid for this gamephase");
            } else if ((mainCommand.equals("savemap") || mainCommand.equals("editmap")
                    || mainCommand.equals("loadmap"))
                    && l_command.length != 2) {
                throw new InvalidCommandException("invalid command");
            } else if (mainCommand.equals("deploy") && l_command.length != 3) {
                throw new InvalidCommandException("invalid command");
            } else if ((mainCommand.equals("showmap") || mainCommand.equals("validatemap") ||
                    mainCommand.equals("assigncountries")) && l_command.length != 1) {
                throw new InvalidCommandException("invalid command");
            }
            if(mainCommand.equals("editcountry") || mainCommand.equals("editcontinent") || mainCommand.equals("editneighbor"))
            {
                this.checkEditCommandsValidity(l_command);
            }
            else if(mainCommand.equals("gameplayer"))
            {
                this.checkGamePlayerCommandValidity(l_command);
            }
            else if(mainCommand.equals("deploy"))
            {
                this.checkDeployCommandValidity(l_command);
            }
            this.processValidCommand(l_command, p_gamePhaseString);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
        return l_command;
    }

    private void checkDeployCommandValidity(String[] p_lCommand) throws NumberFormatException {
        try {
            int a = Integer.parseInt(p_lCommand[1]);
            int b = Integer.parseInt(p_lCommand[2]);
        }
        catch(NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }

    private void checkGamePlayerCommandValidity(String[] p_lCommand) throws InvalidCommandException {
        try {
            int l_countAddOption = 0;
            int l_countRemoveOption = 0;
            int len = p_lCommand.length;
            for (String s : p_lCommand) {
                if (s.equals("-add")) l_countAddOption++;
                else if (s.equals("-remove")) l_countRemoveOption++;
            }
            if (l_countAddOption == 0 && l_countRemoveOption == 0) {
                throw new InvalidCommandException("no options added");
            }
            int i=1;
            while(i<len)
            {
                String currOption = p_lCommand[i];
                if(!(currOption.equals("-add") || currOption.equals("-remove")))
                {
                    throw new InvalidCommandException("invalid command format");
                }
                else if(currOption.equals("-add") && i+1>=len)
                {
                    throw new InvalidCommandException("invalid command format");
                }
                else if(currOption.equals("-add") && (p_lCommand[i+1].equals("-add") || p_lCommand[i+1].equals("-remove")))
                {
                    throw new InvalidCommandException("invalid command format");
                }
                else if(currOption.equals("-remove") && (p_lCommand[i+1].equals("-add") || p_lCommand[i+1].equals("-remove")))
                {
                    throw new InvalidCommandException("invalid command format");
                }
                i+=2;
            }
        }
        catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    List<List<Integer>> addContinentIdList;
    private void processValidCommand(String[] p_lCommand, String p_gamePhaseString) {
        String mainCommand = p_lCommand[0];
        int len = p_lCommand.length;
        if (mainCommand.equals("showmap") && p_gamePhaseString.equals("mapEditor")) {
            //showmap(); method to show all continents, countries and their neighbors
        } else if (mainCommand.equals("showmap") && p_gamePhaseString.equals("gamePlay")) {
            //showmapGamePlay(); method to show all countries, continents, armies on each country, ownership, connecitvity
        }
        //map editor phase commands
        if (mainCommand.equals("editcountry")) {
            addContinentIdList = new ArrayList<>();
            int i=1;
            List<Integer> list = new ArrayList<>();
            while(i<len)
            {
                String currOption = p_lCommand[i];
                if(currOption.equals("-add"))
                {
                    list.add(Integer.valueOf(p_lCommand[i+1]));
                    list.add(Integer.valueOf(p_lCommand[i+2]));
                }
            }
        }


    }

    private void checkEditCommandsValidity(String[] p_lCommand) throws InvalidCommandException,NumberFormatException {

        try {
            int l_countAddOption = 0;
            int l_countRemoveOption = 0;
            int len = p_lCommand.length;
            for (String s : p_lCommand) {
                if (s.equals("-add")) l_countAddOption++;
                else if (s.equals("-remove")) l_countRemoveOption++;
            }
            if(l_countAddOption==0 && l_countRemoveOption==0)
            {
                throw new InvalidCommandException("no options added");
            }
            int i=1;
            String mainCommand = p_lCommand[0];
            while(i<len)
            {
                if(!(p_lCommand[i].equals("-add") || p_lCommand[i].equals("-remove")))
                {
                    throw new InvalidCommandException("invalid command format");
                }
                if(p_lCommand[i].equals("-add") && i+2>=len)
                {
                    throw new InvalidCommandException("invalid command format");
                }
                else if(p_lCommand[i].equals("-remove") && (mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && i+1>=len)
                {
                    throw new InvalidCommandException("invalid command format");
                }
                else if(p_lCommand[i].equals("-remove") && mainCommand.equals("editneighbor") && i+2>=len)
                {
                    throw new InvalidCommandException("invalid command format");
                }
                if(p_lCommand[i].equals("-add"))
                {
                    int p1 = Integer.parseInt(p_lCommand[i+1]);
                    int p2 = Integer.parseInt(p_lCommand[i+2]);
                    i+=3;
                }
                else if((mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && p_lCommand[i].equals("-remove"))
                {
                    int p1 = Integer.parseInt(p_lCommand[i+1]);
                    i+=2;
                }
                else if(mainCommand.equals("editneighbor") && p_lCommand[i].equals("-remove"))
                {
                    int p1 = Integer.parseInt(p_lCommand[i+1]);
                    int p2 = Integer.parseInt(p_lCommand[i+2]);
                    i+=3;
                }
            }
        } catch (InvalidCommandException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
