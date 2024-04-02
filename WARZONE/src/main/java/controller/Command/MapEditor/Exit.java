package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.End;
import controller.statepattern.MapEditor;
import controller.statepattern.Starting;

import java.util.regex.Pattern;

public class Exit extends Command {
    protected Exit(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_commandPattern = Pattern.compile("^exit(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    @Override
    public void execute() {
        if(this.d_ge.getCurrentState().getClass() != Starting.class){
            this.d_ge.resetMap();
            this.d_ge.setCurrentState(new Starting(this.d_ge));
        }else {
            this.d_ge.setCurrentState(new End(this.d_ge));
        }
    }

    @Override
    public boolean validateLogic() {
        return true;
    }
}
