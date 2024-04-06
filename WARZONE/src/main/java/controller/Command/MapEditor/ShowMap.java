package controller.Command.MapEditor;
import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import controller.statepattern.gameplay.IssueOrder;
import controller.statepattern.gameplay.Startup;
import java.util.regex.Pattern;

/**
 * ShowMap represents a command to display the current map.
 */
public class ShowMap extends Command {
    /**
     * Constructs a ShowMap command with specified input and game engine.
     * @param p_input The input string for the command.
     * @param p_ge The game engine to operate on.
     */
    public ShowMap(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "showmap";
        this.d_commandPattern = Pattern.compile("^showmap(\\s)*$");
        this.d_validPhases = new Class[3];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the ShowMap command, displaying the current map.
     */
    @Override
    public void execute() {
        boolean p_enable_gameview = this.d_ge.getCurrentState().getClass() == IssueOrder.class;
        this.d_ge.d_renderer.showMap(p_enable_gameview);
    }
    /**
     * Validates the logic of the ShowMap command.
     * This method checks if the map is empty or not.
     * @return true if the map is not empty, false otherwise.
     */
    @Override
    public boolean validateLogic() {
        if (this.d_ge.d_worldmap.getContinents().isEmpty()) {
            this.d_ge.d_renderer.renderError("Map is empty!");
            return false;
        } else {
            return true;
        }
    }
}
