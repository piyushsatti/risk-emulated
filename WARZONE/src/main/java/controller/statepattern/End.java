package controller.statepattern;
import controller.GameEngine;

public class End extends Phase {
    GameEngine d_ge;

    public End(GameEngine p_ge) {
        super(p_ge);
        d_ge = p_ge;
        displayMenu();
    }

    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderExit();
    }

    @Override
    public void next() {
    }

    @Override
    public void endGame() {
    }

    @Override
    public void run() {
    }
}
