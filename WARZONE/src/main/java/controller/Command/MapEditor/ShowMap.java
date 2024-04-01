package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.gameplay.IssueOrder;

import java.util.regex.Pattern;

public class ShowMap extends Command {


    public ShowMap(String p_input, GameEngine p_ge) {
        super(p_input);
        this.d_validCommandFormat = "showmap";
        this.d_commandPattern = Pattern.compile("^showmap(\\s)*$");
        this.d_ge = p_ge;
    }

    @Override
    public void execute() {
        boolean p_enable_gameview = this.d_ge.getCurrentState().getClass() == IssueOrder.class;
        this.d_ge.d_renderer.showMap(p_enable_gameview);
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
