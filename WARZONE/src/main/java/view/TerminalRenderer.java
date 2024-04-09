package view;

import controller.GameEngine;
import helpers.TerminalColors;
import models.LogEntryBuffer;
import models.worldmap.Continent;
import models.worldmap.Country;
import models.worldmap.WorldMap;

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
     * Log entry buffer for logging messages.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Logger for logging messages.
     */
    Logger d_lw = new Logger(d_logEntryBuffer);

    /**
     * Game engine associated with the renderer.
     */
    GameEngine d_ge;

    /**
     * Scanner object for reading user input from the terminal.
     */
    Scanner d_in;

    /**
     * Constructs a new TerminalRenderer object.
     *
     * @param p_gameEngine The game engine associated with the renderer
     */
    public TerminalRenderer(GameEngine p_gameEngine) {
        d_ge = p_gameEngine;
        d_in = new Scanner(System.in);
    }

    /**
     * Renders a welcome message for the terminal interface.
     */
    public void renderWelcome() {
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
                                Welcome to WarZone. Built by Team 20.
                                """ +
                        TerminalColors.ANSI_RESET
        );
    }

    /**
     * Renders the exit message and exits the application.
     */
    public void renderExit() {
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
                                Thank you for playing WarZone. Team 20 will see you next build.
                                """ +
                        TerminalColors.ANSI_RESET
        );

        System.exit(0);

    }

    /**
     * Renders a menu with the specified type and options.
     *
     * @param p_menu_type The type of the menu.
     * @param p_options   The options to be displayed in the menu.
     */
    public void renderMenu(String p_menu_type, String[] p_options) {

        StringBuilder l_out = new StringBuilder();

        l_out.append(
                        TerminalColors.ANSI_BLUE +
                                "============================\n" +
                                TerminalColors.ANSI_GREEN)
                .append(String.format(
                        """
                                |  %s Options:\s
                                """,
                        p_menu_type)
                );

        for (int l_i = 0; l_i < p_options.length; l_i++) {

            l_out.append(String.format(
                    """
                            |     %d. %s\s
                            """,
                    l_i + 1,
                    p_options[l_i])
            );

        }

        l_out.append(String.format(
                """
                        |     %d. Exit\s
                        """,
                p_options.length + 1)
        );

        System.out.println(
                l_out +
                        TerminalColors.ANSI_BLUE +
                        "============================" +
                        TerminalColors.ANSI_RESET
        );

    }

    /**
     * Renders an error message.
     *
     * @param p_error_string The error message to be rendered.
     */
    public void renderError(String p_error_string) {

        System.out.println(TerminalColors.ANSI_RED + p_error_string + TerminalColors.ANSI_RESET);

    }

    /**
     * Renders the request for a map filename.
     *
     * @return The filename entered by the user.
     */
    public String renderMapEditorMenu() {

        this.renderMessage(TerminalColors.ANSI_BLUE + """
                Please Enter a valid .map filename from folder:\t
                """ + TerminalColors.ANSI_GREEN + d_ge.d_maps_folder + TerminalColors.ANSI_RESET);

        Scanner l_in = new Scanner(System.in);

        return l_in.nextLine().strip();

    }

    /**
     * Renders the request for map editor commands.
     */
    public void renderMapEditorCommands() {
        this.renderMessage(
                TerminalColors.ANSI_BLUE + """
                        Please Enter a valid command:\t
                        """ + TerminalColors.ANSI_GREEN +
                        "Super Commands: savemap, editmap, showmap, validatemap\n" +
                        "Map Edit Commands: editcountry, editneighbor, editcontinent" +
                        TerminalColors.ANSI_RESET + "\n" +
                        "Type 'exit' to quit map editing."
        );
    }

    /**
     * Renders a message to the user and waits for user input.
     *
     * @param p_message The message to be displayed to the user.
     * @return The user's input as a String.
     */
    public String renderUserInput(String p_message) {
        renderMessage(p_message);
        return this.d_in.nextLine();
    }

    /**
     * Renders a message.
     *
     * @param p_message The message to be rendered.
     */
    public void renderMessage(String p_message) {

        System.out.println(
                TerminalColors.ANSI_BLUE +
                        p_message +
                        TerminalColors.ANSI_RESET
        );

    }

    /**
     * Displays the map loaded in the game engine.
     *
     * @param p_enable_gameview Indicates whether to enable the game view (show player info).
     */
    public void showMap(boolean p_enable_gameview) {

        WorldMap l_map = d_ge.d_worldmap;
        StringBuilder l_out = new StringBuilder();
        HashMap<Continent, List<Country>> l_continentCountriesMap = new HashMap<>();

        for (Country l_c : l_map.getCountries().values()) {
            List<Country> l_temp = l_continentCountriesMap.getOrDefault(l_c.getContinent(), new ArrayList<>());
            l_temp.add(l_c);
            l_continentCountriesMap.put(l_c.getContinent(), l_temp);
        }
        for (Continent l_c : l_map.getContinents().values()) {
            l_continentCountriesMap.computeIfAbsent(l_c, k -> new ArrayList<>());
        }
        for (Continent l_continent : l_continentCountriesMap.keySet()) {
            String l_continentName = l_continent.getContinentName();
            l_out.append(l_continentName);
            l_out.append("\n\t");
            for (Country l_country : l_continentCountriesMap.get(l_continent)) {
                l_out.append(l_country.getCountryName());
                if (p_enable_gameview) {
                    l_out.append(" Reinforcements Deployed: ").append(l_country.getReinforcements());
                    int l_ownerPlayerID = l_country.getCountryPlayerID();

                }
                l_out.append("\n\t\t");
                for (Country l_borderCountries : l_country.getBorderCountries().values()) {
                    l_out.append(l_borderCountries.getCountryName()).append(" ");
                }
                l_out.append("\n\t");
            }
            l_out.append("\n");
        }
        System.out.println(l_out);
    }

    /**
     * Displays the current game map.
     */
    public void showCurrentGameMap() {
        WorldMap l_map = d_ge.d_worldmap;
        StringBuilder l_out = new StringBuilder();
        HashMap<Continent, List<Country>> l_continentCountriesMap = new HashMap<>();
        for (Country l_c : l_map.getCountries().values()) {
            List<Country> l_temp = l_continentCountriesMap.getOrDefault(l_c.getContinent(), new ArrayList<>());
            l_temp.add(l_c);
            l_continentCountriesMap.put(l_c.getContinent(), l_temp);
        }
        for (Continent l_continent : l_continentCountriesMap.keySet()) {
            String l_continentName = l_continent.getContinentName();
            l_out.append(l_continentName);
            l_out.append("\n\t");
            for (Country l_country : l_continentCountriesMap.get(l_continent)) {
                l_out.append(l_country.getCountryName());
                l_out.append("\n\t\t");
                for (Country l_borderCountries : l_country.getBorderCountries().values()) {
                    l_out.append(l_borderCountries.getCountryName()).append(" ");
                }
                l_out.append("\n\t");
            }
            l_out.append("\n");
        }
        System.out.println(l_out);
    }

    /**
     * Renders the request for a map filename.
     *
     * @return The filename entered by the user.
     */
    public String renderRequestMapFileName() {
        return "usa9.map";
    }

    /**
     * Renders the request to assign countries.
     *
     * @return The filename entered by the user.
     */
    public String renderAssignCountries() {
        return "usa9.map";
    }

    /**
     * Renders the view for issuing orders by a player.
     *
     * @param p_player The name of the player issuing the order.
     * @return The order entered by the player.
     */
    public String issueOrderView(String p_player) {
        this.renderMessage(p_player + " Please Enter Your Order");
        Scanner l_in = new Scanner(System.in);
        return l_in.nextLine();
    }

}