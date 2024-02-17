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
import java.util.Scanner;

/**
 * The TerminalRenderer class provides methods for rendering various components in a terminal interface,
 * such as a welcome message, a menu, and displaying a map.
 */
public class TerminalRenderer {

    /**
     * Renders a welcome message for the terminal interface.
     */
    public static void renderWelcome() {
        System.out.println(
                TerminalColors.ANSI_CYAN +
                        """
                                                
                                 ___       ___     ____     ______      ______      ____        __      _    _____ \s
                                (  (       )  )   (    )   (   __ \\    (____  )    / __ \\      /  \\    / )  / ___/ \s
                                 \\  \\  _  /  /    / /\\ \\    ) (__) )       / /    / /  \\ \\    / /\\ \\  / /  ( (__   \s
                                  \\  \\/ \\/  /    ( (__) )  (    __/    ___/ /_   ( ()  () )   ) ) ) ) ) )   ) __)  \s
                                   )   _   (      )    (    ) \\ \\  _  /__  ___)  ( ()  () )  ( ( ( ( ( (   ( (     \s
                                   \\  ( )  /     /  /\\  \\  ( ( \\ \\_))   / /____   \\ \\__/ /   / /  \\ \\/ /    \\ \\___ \s
                                    \\_/ \\_/     /__(  )__\\  )_) \\__/   (_______)   \\____/   (_/    \\__/      \\____\\\s
                                \s
                                Welcome to WarZone. Built by Team24.
                                """ +
                        TerminalColors.ANSI_RESET
        );
    }

    public static void renderExit() {
        System.out.println(
                TerminalColors.ANSI_YELLOW +
                        """
                                                        
                                 ___       ___     ____     ______      ______      ____        __      _    _____ \s
                                (  (       )  )   (    )   (   __ \\    (____  )    / __ \\      /  \\    / )  / ___/ \s
                                 \\  \\  _  /  /    / /\\ \\    ) (__) )       / /    / /  \\ \\    / /\\ \\  / /  ( (__   \s
                                  \\  \\/ \\/  /    ( (__) )  (    __/    ___/ /_   ( ()  () )   ) ) ) ) ) )   ) __)  \s
                                   )   _   (      )    (    ) \\ \\  _  /__  ___)  ( ()  () )  ( ( ( ( ( (   ( (     \s
                                   \\  ( )  /     /  /\\  \\  ( ( \\ \\_))   / /____   \\ \\__/ /   / /  \\ \\/ /    \\ \\___ \s
                                    \\_/ \\_/     /__(  )__\\  )_) \\__/   (_______)   \\____/   (_/    \\__/      \\____\\\s
                                \s
                                Thank you for playing WarZone. Team24 will see you next build.
                                """ +
                        TerminalColors.ANSI_RESET
        );

        System.exit(0);

    }

    /**
     * Renders a menu with the specified type and options.
     *
     * @param menu_type The type of the menu.
     * @param options   The options to be displayed in the menu.
     */
    public static void renderMenu(String menu_type, String[] options) {

        StringBuilder out = new StringBuilder();

        out.append(
                TerminalColors.ANSI_BLUE +
                "============================\n" +
                TerminalColors.ANSI_GREEN)
                .append(String.format(
                """
                |  %s Options:\s
                """,
                menu_type)
                );

        for (int i = 0; i < options.length; i++) {

            out.append(String.format(
                """
                |     %d. %s\s
                """,
                i + 1,
                options[i])
            );

        }

        out.append(String.format(
            """
            |     %d. Exit\s
            """,
            options.length + 1)
        );

        System.out.println(
            out +
            TerminalColors.ANSI_BLUE +
            "============================" +
            TerminalColors.ANSI_RESET
        );

    }

    public static void renderError(String error_string) {

        System.out.println(TerminalColors.ANSI_RED + error_string + TerminalColors.ANSI_RESET);

    }

    public static String renderMapEditorMenu() {

        System.out.println(TerminalColors.ANSI_BLUE + """
                Please Enter a valid .map filename from folder:\t
                """ + TerminalColors.ANSI_GREEN + GameEngine.MAPS_FOLDER + TerminalColors.ANSI_RESET);

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }

    public static String renderMapEditorCommands() {

        System.out.println(TerminalColors.ANSI_BLUE + """
                Please Enter a valid command:\t
                """ + TerminalColors.ANSI_GREEN + "Substitute an object to loop over valid commands" + TerminalColors.ANSI_RESET);

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }

    public static void renderMessage(String message) {

        System.out.println(
                TerminalColors.ANSI_BLUE +
                        message +
                        TerminalColors.ANSI_RESET
        );

    }

    /**
     * Displays the map loaded in the game engine.
     */
    public static void showMap() {
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
        System.out.println(out);
    }

    // this needs to be adjusted to reflect players
    public static void showCurrentGameMap() {
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
        System.out.println(out);
    }

    /**
     * The main method demonstrates the usage of the TerminalRenderer class by rendering a welcome message,
     * rendering a sample menu, and displaying a map.
     *
     * @param args The command-line arguments (not used in this method).
     * @throws FileNotFoundException If the map file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {

        renderWelcome();

        String[] pop = {"Hi", "Bye", "Option"};

        renderMenu("Main Menu", pop);

    }

}