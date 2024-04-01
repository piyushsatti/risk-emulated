package controller.Command;

import controller.Command.MapEditor.*;
import controller.GameEngine;

public class CommandCreator {

    public static Command createCommand(String p_input, GameEngine p_ge){

        String l_commandName = p_input.split("\\s+")[0];
        switch (l_commandName){
            case "editcountry":
                return new EditCountry(p_input,p_ge);
            case "editcontinent":
                return new EditContinent(p_input,p_ge);
            case "editmap":
                return new EditMap(p_input,p_ge);
            case "editneighbor":
                return new EditNeighbor(p_input,p_ge);
            case "savemap":
                return new SaveMap(p_input,p_ge);
            case "showmap":
                return new ShowMap(p_input,p_ge);
            case "validatemap":
                return new ValidateMap(p_input,p_ge);
            case "exit":
                return new Exit(p_input,p_ge);
        }

        return null;
    }

}
