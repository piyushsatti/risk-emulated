package controller.statepattern;

import controller.GameEngine;

import java.util.Scanner;

public class Starting extends Phase {

    boolean first = true;
    public Starting(GameEngine g) {
        super(g);
        d_phaseName = "Starting Phase";
    }

    @Override
    public void displayMenu() {
        if (first) d_ge.d_renderer.renderWelcome();
        first = false;
        d_ge.d_renderer.renderMessage("current game phase: " + this.d_phaseName);
        String[] menu_options = {"Map Editor", "Play Game"};
        d_ge.d_renderer.renderMenu("Starting Menu", menu_options);
        System.out.print("Enter Selection: ");
    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        displayMenu();
        Scanner s = new Scanner(System.in);
        int l_userSelection = 0;

        try {
            l_userSelection = s.nextInt();
        }catch (Exception e){
            System.out.println("Invalid selection!");
            return;
        }

        if (l_userSelection == 1){
            d_ge.setCurrentState(new MapEditor(d_ge));
        }
        else if(l_userSelection == 2){
            d_ge.setCurrentState(new MapEditor(d_ge));
        }
        else if (l_userSelection == 3) {
            d_ge.setCurrentState(new End(d_ge));
        }else{
            System.out.println("Invalid selection!");
        }
    }
}
