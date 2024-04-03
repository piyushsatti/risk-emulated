package controller.MapFileManagement;

import controller.GameEngine;
import models.worldmap.WorldMap;

public class MapFileManagementDriver {

    public static void main(String[] args){
        GameEngine ge = new GameEngine();
        MapFileLoader mfl = new MapFileLoader(ge, "usa9.map");
        System.out.println(mfl.fileLoaded());
        System.out.println(mfl.isConquest());
        mfl = new MapFileLoader(ge, "Africa.map");
        ConquestMapInterface cmi = new ConquestMapInterface();
        ge.d_worldmap = cmi.loadConquestMap(ge,mfl);
        ge.d_renderer.showMap(false);


    }
}
