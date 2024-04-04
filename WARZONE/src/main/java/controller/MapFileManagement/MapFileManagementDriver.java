package controller.MapFileManagement;

import controller.GameEngine;
import models.worldmap.WorldMap;

public class MapFileManagementDriver {

    public static void main(String[] args){
        GameEngine ge = new GameEngine();
        MapInterface mp = new MapInterface();
        MapFileLoader mfl = new MapFileLoader(ge, "usa9.map");
        System.out.println(mfl.fileLoaded());
        System.out.println(mfl.isConquest());
        mfl = new MapFileLoader(ge, "Africa.map");
        ConquestMapInterface cmi = new ConquestMapInterface();
        ge.d_worldmap = cmi.loadConquestMap(ge,mfl);
        try {
            mp.saveMap(ge, "AfricaDomination.map");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        ge.d_renderer.showMap(false);
        mfl = new MapFileLoader(ge, "usa9.map");

        ge.d_worldmap = mp.loadMap(ge, mfl);
        cmi.saveConquestMap(ge,"usa9Conquest.map");


    }
}
