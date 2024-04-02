package controller.Command.MapEditor;
import controller.Command.Command;
import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.MapEditor;
import view.TerminalRenderer;
import java.io.IOException;
import java.util.regex.Pattern;

public class SaveMap extends Command {

    public SaveMap(String p_input, GameEngine p_ge) {
        super(p_input,p_ge);
        this.d_validCommandFormat = "savemap <map name>.map";
        this.d_commandPattern = Pattern.compile("^savemap\\s\\w+\\.map(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    @Override
    public void execute() {
        MapInterface mp = new MapInterface();
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        try {
            mp.saveMap(this.d_ge, this.d_splitCommand[1]);
        } catch (IOException e) {
            l_renderer.renderError(e.toString());
        }
    }

    @Override
    public boolean validateLogic() {
        return true;
    }
}
