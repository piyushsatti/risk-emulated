package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.MapInterface;
import view.TerminalRenderer;

import java.util.regex.Pattern;

public class ValidateMap extends Command {


    public ValidateMap(String p_input, GameEngine p_ge) {
        super(p_input);
        this.d_validCommandFormat = "validatemap";
        this.d_commandPattern = Pattern.compile("^validatemap(\\s)*$");
        this.d_ge = p_ge;
    }

    @Override
    public void execute() {
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        if (MapInterface.validateMap(this.d_ge))
        {
            l_renderer.renderMessage("Map is valid");
        }
        else
        {
            l_renderer.renderMessage("Map is not valid");
        }
    }

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
