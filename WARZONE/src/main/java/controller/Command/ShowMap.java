package controller.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import controller.statepattern.gameplay.IssueOrder;
import controller.statepattern.gameplay.Startup;
import java.util.regex.Pattern;

public class ShowMap extends Command {


    public ShowMap(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "showmap";
        this.d_commandPattern = Pattern.compile("^showmap(\\s)*$");
        this.d_validPhases = new Class[3];
        this.d_validPhases[0] = MapEditor.class;
        this.d_validPhases[1] = Startup.class;
        this.d_validPhases[2] = IssueOrder.class;
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
