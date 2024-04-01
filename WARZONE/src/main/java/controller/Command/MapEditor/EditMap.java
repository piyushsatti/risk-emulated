package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.MapInterface;
import view.TerminalRenderer;
import java.util.regex.Pattern;

public class EditMap extends Command {


    public EditMap(String p_input, GameEngine p_ge) {
        super(p_input);
        this.d_validCommandFormat = "editmap <map name>.map";
        this.d_commandPattern = Pattern.compile("editmap\\s\\w+\\.map(\\s)*$");
        this.d_ge = p_ge;
    }

    @Override
    public void execute() {

        MapInterface mp = new MapInterface();
        String mapName = "";
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        mapName = this.d_splitCommand[1];

        try {
            mp.loadMap(this.d_ge, mapName);
        } catch (Exception e) {
            l_renderer.renderError("FileNotFoundException : File does not exist.");
            l_renderer.renderMessage("Creating file by the name : " + mapName);
            try {
                mp.saveMap(this.d_ge, mapName);
                mp.loadMap(this.d_ge, mapName);
            } catch (Exception ex) {
                l_renderer.renderError(ex.toString());
            }
        }
    }

    @Override
    public boolean validateLogic() {
        return true;
    }
}
