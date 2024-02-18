package views;

import controller.GameEngine;
import models.Player;
import models.worldmap.Continent;
import models.worldmap.Country;
import models.worldmap.WorldMap;
import helpers.TerminalColors;

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

    /**
     * Renders the exit message and exits the application.
     */
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

    /**
     * Renders an error message.
     *
     * @param error_string The error message to be rendered.
     */
    public static void renderError(String error_string) {

        System.out.println(TerminalColors.ANSI_RED + error_string + TerminalColors.ANSI_RESET);

    }

    /**
     * Renders the request for a map filename.
     *
     * @return The filename entered by the user.
     */
    public static String renderMapEditorMenu() {

        System.out.println(TerminalColors.ANSI_BLUE + """
                Please Enter a valid .map filename from folder:\t
                """ + TerminalColors.ANSI_GREEN + GameEngine.MAPS_FOLDER + TerminalColors.ANSI_RESET);

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }
    /**
     * Renders the request for map editor commands.
     *
     * @return The command entered by the user.
     */
    public static String renderMapEditorCommands() {

        System.out.println(TerminalColors.ANSI_BLUE + """
                Please Enter a valid command:\t
                """ + TerminalColors.ANSI_GREEN + "Substitute an object to loop over valid commands" + TerminalColors.ANSI_RESET);

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }

    /**
     * Renders a message.
     *
     * @param message The message to be rendered.
     */
    public static void renderMessage(String message) {

        System.out.println(
                TerminalColors.ANSI_BLUE +
                        message +
                        TerminalColors.ANSI_RESET
        );

    }

    /**
     * Displays the map loaded in the game engine.
     *
     * @param p_enable_gameview Indicates whether to enable the game view (show player info).
     */
    public static void showMap(boolean p_enable_gameview) {
        WorldMap map = GameEngine.CURRENT_MAP;
        StringBuilder out = new StringBuilder();
        HashMap<Continent,List<Country>> continentCountriesMap = new HashMap<>();
        for (Country c : map.getCountries().values()) {
            List<Country> temp = continentCountriesMap.getOrDefault(c.getContinent(), new ArrayList<>());
            temp.add(c);
            continentCountriesMap.put(c.getContinent(), temp);
        }
        for(Continent continent : continentCountriesMap.keySet())
        {
            String continentName = continent.getContinentName();
            out.append(continentName);
            out.append("\n\t");
            for (Country country : continentCountriesMap.get(continent)) {
                out.append(country.getCountryName());
                if (p_enable_gameview) {
                    out.append(" Reinforcements Deployed: ").append(country.getReinforcements());
                    int l_ownerPlayerID = country.getCountryPlayerID();
                    if( Player.getPlayerFromList(GameEngine.PLAYER_LIST,l_ownerPlayerID)!=null){
                        out.append(" Player Name: ").append(Player.getPlayerFromList(GameEngine.PLAYER_LIST, l_ownerPlayerID).getName());
                    }

                }
                out.append("\n\t\t");
                for (Country borderCountries : country.getBorderCountries().values()) {
                    out.append(borderCountries.getCountryName()).append(" ");
                }
                out.append("\n\t");
            }
            out.append("\n");
        }
        System.out.println(out);
    }

    // this needs to be adjusted to reflect players
    /**
     * Displays the current game map.
     */
    public static void showCurrentGameMap() {
        WorldMap map = GameEngine.CURRENT_MAP;
        StringBuilder out = new StringBuilder();
        HashMap<Continent,List<Country>> continentCountriesMap = new HashMap<>();
        for (Country c : map.getCountries().values()) {
            List<Country> temp = continentCountriesMap.getOrDefault(c.getContinent(), new ArrayList<>());
            temp.add(c);
            continentCountriesMap.put(c.getContinent(), temp);
        }
        for(Continent continent : continentCountriesMap.keySet())
        {
            String continentName = continent.getContinentName();
            out.append(continentName);
            out.append("\n\t");
            for (Country country : continentCountriesMap.get(continent)) {
                out.append(country.getCountryName());
                out.append("\n\t\t");
                for (Country borderCountries : country.getBorderCountries().values()) {
                    out.append(borderCountries.getCountryName()).append(" ");
                }
                out.append("\n\t");
            }
            out.append("\n");
        }
        System.out.println(out);
    }
    /**
     * Renders the request for a map filename.
     *
     * @return The filename entered by the user.
     */
    public static String renderRequestMapFileName(){
        return "usa9.map";
    }
    /**
     * Renders the request to assign countries.
     *
     * @return The filename entered by the user.
     */
    public static String renderAssignCountries(){
        return "usa9.map";
    }

    /**
     * Renders the view for issuing orders by a player.
     *
     * @param p_player The name of the player issuing the order.
     * @return The order entered by the player.
     */
    public static String issueOrderView(String p_player) {

        TerminalRenderer.renderMessage(p_player + " Please Enter Your Order");

        Scanner in = new Scanner(System.in);

        return in.nextLine();

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