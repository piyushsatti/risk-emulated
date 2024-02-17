package main.java.views;

import main.java.controller.GameEngine;
import main.java.models.worldmap.Continent;
import main.java.models.worldmap.Country;
import main.java.models.worldmap.WorldMap;
import main.java.utils.TerminalColors;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TerminalRenderer {
    public static String renderWelcome() {
        return TerminalColors.ANSI_CYAN + """
                
                 ___       ___     ____     ______      ______      ____        __      _    _____ \s
                (  (       )  )   (    )   (   __ \\    (____  )    / __ \\      /  \\    / )  / ___/ \s
                 \\  \\  _  /  /    / /\\ \\    ) (__) )       / /    / /  \\ \\    / /\\ \\  / /  ( (__   \s
                  \\  \\/ \\/  /    ( (__) )  (    __/    ___/ /_   ( ()  () )   ) ) ) ) ) )   ) __)  \s
                   )   _   (      )    (    ) \\ \\  _  /__  ___)  ( ()  () )  ( ( ( ( ( (   ( (     \s
                   \\  ( )  /     /  /\\  \\  ( ( \\ \\_))   / /____   \\ \\__/ /   / /  \\ \\/ /    \\ \\___ \s
                    \\_/ \\_/     /__(  )__\\  )_) \\__/   (_______)   \\____/   (_/    \\__/      \\____\\\s
                \s
                Welcome to WarZone. Built by Team24.
                """ + TerminalColors.ANSI_RESET;
    }

    public static String renderMenu(String menu_type, String[] options) {
        // Display menu graphics
        StringBuilder out = new StringBuilder();
        out.append(TerminalColors.ANSI_BLUE + "============================\n" + TerminalColors.ANSI_GREEN).append(String.format("""
                |  %s Options:\s
                """, menu_type));
        for (int i = 0; i < options.length; i++) {
            out.append(String.format("""
                    |     %d. %s\s
                    """, i + 1, options[i]));
        }
        out.append(String.format("""
                |     %d. Exit\s
                """, options.length + 1));
        return out + TerminalColors.ANSI_BLUE + "============================" + TerminalColors.ANSI_RESET;
    }

    public static String showMap() throws FileNotFoundException
    {
        WorldMap map = GameEngine.CURRENT_MAP;
        StringBuilder out = new StringBuilder();
        HashMap<Continent,List<Country>> continentCountriesMap = new HashMap<>();
        for (Country c : map.getCountries().values()) {
            List<Country> temp= continentCountriesMap.getOrDefault(c.getD_continent(),new ArrayList<>());
            temp.add(c);
            continentCountriesMap.put(c.getD_continent(),temp);
        }
        for(Continent continent : continentCountriesMap.keySet())
        {
            String continentName = continent.getContinentName();
            out.append(continentName);
            out.append("\n\t");
            for (Country country : continentCountriesMap.get(continent)) {
                out.append(country.getD_countryName());
                out.append("\n\t\t");
                for (Country borderCountries : country.getBorderCountries().values()) {
                    out.append(borderCountries.getD_countryName()).append(" ");
                }
                out.append("\n\t");
            }
            out.append("\n");
        }
        System.out.println(out.toString());
        return out.toString();
    }
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println(renderWelcome());
        String[] poop = {"Hi", "Bye", "Opti"};
        System.out.println(renderMenu("Main Menu", poop));
        //showMap(MapInterface.loadMap("usa9.map"));
    }
}
