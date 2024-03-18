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

    /** Log entry buffer for logging messages. */
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

    /** Logger for logging messages. */
    Logger lw = new Logger(logEntryBuffer);

    /** Game engine associated with the renderer. */
    GameEngine d_ge;

    /** Scanner object for reading user input from the terminal. */
    Scanner in;

    /**
     * Constructs a new TerminalRenderer object.
     *
     * @param p_gameEngine The game engine associated with the renderer
     */
    public TerminalRenderer(GameEngine p_gameEngine) {
        d_ge = p_gameEngine;
        in = new Scanner(System.in);
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
     * @param menu_type The type of the menu.
     * @param options   The options to be displayed in the menu.
     */
    public void renderMenu(String menu_type, String[] options) {

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
    public void renderError(String error_string) {

        System.out.println(TerminalColors.ANSI_RED + error_string + TerminalColors.ANSI_RESET);

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

        Scanner in = new Scanner(System.in);

        return in.nextLine().strip();

    }
    /**
     * Renders the request for map editor commands.
     *
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
     * @param message The message to be displayed to the user.
     * @return The user's input as a String.
     */
    public String renderUserInput(String message) {
        renderMessage(message);
        return this.in.nextLine();
    }

    /**
     * Renders a message.
     *
     * @param message The message to be rendered.
     */
    public void renderMessage(String message) {

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
    public void showMap(boolean p_enable_gameview) {

        WorldMap map = d_ge.d_worldmap;
        StringBuilder out = new StringBuilder();
        HashMap<Continent,List<Country>> continentCountriesMap = new HashMap<>();

        for (Country c : map.getCountries().values()) {
            List<Country> temp = continentCountriesMap.getOrDefault(c.getContinent(), new ArrayList<>());
            temp.add(c);
            continentCountriesMap.put(c.getContinent(), temp);
        }
        for (Continent c : map.getContinents().values()){
            continentCountriesMap.computeIfAbsent(c, k -> new ArrayList<>());
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

                }
                out.append("\n\t\t");
                for (Country borderCountries : country.getBorderCountries().values())
                {
                    out.append(borderCountries.getCountryName()).append(" ");
                }
                out.append("\n\t");
            }
            out.append("\n");
        }
        System.out.println(out);
    }

    /**
     * Displays the current game map.
     */
    public void showCurrentGameMap() {
        WorldMap map = d_ge.d_worldmap;
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
    public String renderRequestMapFileName(){
        return "usa9.map";
    }
    /**
     * Renders the request to assign countries.
     *
     * @return The filename entered by the user.
     */
    public String renderAssignCountries(){
        return "usa9.map";
    }

    /**
     * Renders the view for issuing orders by a player.
     *
     * @param p_player The name of the player issuing the order.
     * @return The order entered by the player.
     */
    public String issueOrderView(String p_player)
    {
        this.renderMessage(p_player + " Please Enter Your Order");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

}