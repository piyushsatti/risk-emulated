package controller.Command.MapEditor;
import controller.Command.Command;
import controller.GameEngine;
import controller.MapInterface;
import view.TerminalRenderer;
import java.io.IOException;
import java.util.regex.Pattern;

public class SaveMap extends Command {

    public SaveMap(String p_input, GameEngine p_ge) {
        super(p_input);
        this.d_validCommandFormat = "savemap <map name>.map";
        this.d_commandPattern = Pattern.compile("^savemap\\s\\w+\\.map(\\s)*$");
        this.d_ge = p_ge;
    }

    @Override
    public void execute() {
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        try {
            MapInterface.saveMap(this.d_ge, this.d_splitCommand[1]);
        } catch (IOException e) {
            l_renderer.renderError(e.toString());
        }
    }

    @Override
    public boolean validateLogic() {
        return true;
    }
}
