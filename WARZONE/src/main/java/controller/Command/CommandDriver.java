package controller.Command;

import controller.Command.MapEditor.EditContinent;
import controller.Command.MapEditor.EditCountry;
import controller.GameEngine;

public class CommandDriver {

    public static void main(String[] args){

        GameEngine testEngine = new GameEngine();
        EditContinent continent = new EditContinent("editcontinent -add America 1 -add Asia 5", testEngine);
        continent.validate();
        continent.execute();
        EditCountry country = new EditCountry("editcountry -add Canada America -add China Asia", testEngine);
        country.validate();
        country.execute();

    }
}
