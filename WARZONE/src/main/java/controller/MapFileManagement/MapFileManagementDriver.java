package controller.MapFileManagement;

import controller.GameEngine;

public class MapFileManagementDriver {

    public static void main(String[] args){
        GameEngine ge = new GameEngine();
        MapFileLoader mfl = new MapFileLoader(ge, "usa9.map");
        System.out.println(mfl.fileLoaded());
        System.out.println(mfl.isConquest());
        mfl = new MapFileLoader(ge, "Africa.map");
        System.out.println(mfl.fileLoaded());
        System.out.println(mfl.isConquest());

    }
}
