package mvc.controller.middleware.commands;

import mvc.controller.GameEngine;
import mvc.controller.statepattern.Phase;
import mvc.controller.statepattern.tournament.Tournament;
import mvc.models.LogEntryBuffer;
import mvc.models.Player;
import mvc.view.Logger;
import strategy.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a set of commands related to tournament operations.
 */
public class TournamentCommands extends Commands {
    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * list of valid strategies
     */
    List<String> d_validStrategies = Arrays.asList("aggressive", "benevolent", "human", "random", "cheater");

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger d_lw = new Logger(d_logEntryBuffer);


    /**
     * list storing map files given by the user
     */
    List<String> d_mapFiles;
    /**
     * list storing input strategies given by the user
     */
    List<String> d_inputStrategies;

    /**
     * number of games
     */
    private int d_numberGames;

    /**
     * number of max turns
     */
    private int d_maxTurns;

    /**
     * method which returns the current gamephase
     *
     * @param p_gameEngine
     * @return current game phase
     */
    public String getCurrentPhase(GameEngine p_gameEngine) {
        Phase l_phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(l_phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        System.out.println(l_currClass.substring(l_index + 1));
        return l_currClass.substring(l_index + 1);
    }


    /**
     * Constructs a new instance of StartupCommands with the specified command and valid commands for tournament phase.
     *
     * @param p_command The command to be executed.
     */
    public TournamentCommands(String p_command) {
        super(p_command, new String[]{
                "tournament",
                "exit"
        });
    }

    /**
     * Validates the command based on the current state of the game engine.
     * The command is considered valid if it matches certain patterns and the current state is in the startup phase.
     *
     * @param p_gameEngine The game engine instance.
     * @return True if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand(GameEngine p_gameEngine) {
        Pattern l_pattern = Pattern.compile(
                "^tournament\\s+-M\\s+(?:\\w+\\.map\\s+){1,5}-P\\s+(?:\\w+\\s+){2,4}-G\\s+[1-5]\\s+-D\\s+(?:[1-4][0-9]|50)$|" +
                        "^exit$|"
        );
        Matcher l_matcher = l_pattern.matcher(d_command);
        return l_matcher.matches() && (p_gameEngine.getCurrentState().getClass() == Tournament.class);
    }

    /**
     * Executes the command based on the current state of the game engine.
     *
     * @param p_gameEngine The game engine instance.
     */
    @Override
    public void execute(GameEngine p_gameEngine) {
        String d_currPhase = getCurrentPhase(p_gameEngine);
        if (!this.validateCommandName()) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }
        if (!this.validateCommand(p_gameEngine)) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command format");
            return;
        }

        int l_index = 2;
        d_mapFiles = new ArrayList<>();
        while (!d_splitCommand[l_index].equals("-P")) {
            boolean l_flag = validateMapPresence(p_gameEngine, d_splitCommand[l_index]);
            if (!l_flag) {
                p_gameEngine.d_renderer.renderError("entered map file " + d_splitCommand[l_index] + " does not exist");
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "Command: tournament Not Executed, entered map file does not exist!");
                return;
            }
            if (d_mapFiles.contains(d_splitCommand[l_index])) {
                p_gameEngine.d_renderer.renderError("duplicate map file entered" + d_splitCommand[l_index]);
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "Command: tournament Not Executed, duplicate map file entered in command!");
                return;
            }
            d_mapFiles.add(d_splitCommand[l_index++]);
        }
        l_index++;
        d_inputStrategies = new ArrayList<>();
        while (!d_splitCommand[l_index].equals("-G")) {
            boolean l_flag = d_validStrategies.contains(d_splitCommand[l_index]);
            if (!l_flag) {
                p_gameEngine.d_renderer.renderError("entered strategy " + d_splitCommand[l_index] + " is invalid");
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "Command: tournament Not Executed, entered strategy does not exist!");
                return;
            }
            l_flag = d_inputStrategies.contains(d_splitCommand[l_index]);
            if (l_flag) {
                p_gameEngine.d_renderer.renderError("already entered strategy " + d_splitCommand[l_index]);
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "Command: tournament Not Executed, duplicate strategy entered in command!");
                return;
            }
            d_inputStrategies.add(d_splitCommand[l_index++]);
        }
        l_index++;
        d_numberGames = Integer.parseInt(d_splitCommand[l_index++]);
        l_index++;
        d_maxTurns = Integer.parseInt(d_splitCommand[l_index]);
        p_gameEngine.setInputStrategiesTournament(d_inputStrategies);
        p_gameEngine.setMapFilesTournament(d_mapFiles);
        p_gameEngine.setNumberOfTurns(d_maxTurns);
        p_gameEngine.setNumberGamesTournament(d_numberGames);
        createPlayers(p_gameEngine);
        p_gameEngine.setTournamentResults(new String[d_mapFiles.size()][d_numberGames]);
    }


    /**
     * method which checks if the map entered by the user in the tournament command exists or not.
     *
     * @param p_gameEngine
     * @param p_mapName    name of map file from tournament command
     * @return true: map exists or false: map does not exist
     */
    private boolean validateMapPresence(GameEngine p_gameEngine, String p_mapName) {
        File l_map_file_obj = new File(p_gameEngine.d_maps_folder + p_mapName);
        return l_map_file_obj.exists() && !l_map_file_obj.isDirectory();
    }

    /**
     * Creates players for the game based on the input strategies provided.
     * Each player is assigned a specific strategy based on the input.
     *
     * @param p_gameEngine The game engine object managing the game.
     */
    private void createPlayers(GameEngine p_gameEngine) {
        for (String l_strategy : d_inputStrategies) {
            Player l_newPlayer = new Player(l_strategy, p_gameEngine);
            switch (l_strategy) {
                case "aggressive":
                    l_newPlayer.setPlayerStrategy(new AggressiveStrategy(l_newPlayer, p_gameEngine));
                    break;
                case "benevolent":
                    l_newPlayer.setPlayerStrategy(new BenevolentStrategy(l_newPlayer, p_gameEngine));
                    break;
                case "cheater":
                    l_newPlayer.setPlayerStrategy(new CheaterStrategy(l_newPlayer, p_gameEngine));
                    break;
                case "random":
                    l_newPlayer.setPlayerStrategy(new RandomStrategy(l_newPlayer, p_gameEngine));
                    break;
                default:
                    l_newPlayer.setPlayerStrategy(new HumanStrategy(l_newPlayer, p_gameEngine));
                    break;
            }
            p_gameEngine.d_players.add(l_newPlayer);

        }
    }


}