package controller.Command;

import controller.GameEngine;
import controller.statepattern.Starting;

import java.util.regex.Pattern;

public class Exit extends Command{
    protected Exit(String p_input, GameEngine p_ge) {
        super(p_input);
        this.d_ge = p_ge;
        this.d_commandPattern = Pattern.compile("^exit(\\s)*$");
    }

    @Override
    public void execute() {
        if(this.d_ge.getCurrentState().getClass() != Starting.class){
            this.d_ge.setCurrentState(new Starting(this.d_ge));
        }
    }

    @Override
    public boolean validateLogic() {
        return true;
    }
}
