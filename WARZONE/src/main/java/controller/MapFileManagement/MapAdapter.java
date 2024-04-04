package controller.MapFileManagement;

import controller.GameEngine;
import models.worldmap.WorldMap;

public class MapAdapter extends MapInterface{

    ConquestMapInterface d_adaptee;

    public MapAdapter(ConquestMapInterface p_cmi){
        d_adaptee = p_cmi;
    }


    @Override
    public void saveMap(GameEngine p_gameEngine, String p_FileName){
        d_adaptee.saveConquestMap(p_gameEngine, p_FileName);
    }

    @Override
    public WorldMap loadMap(GameEngine p_gameEngine, MapFileLoader p_mfl){
        return d_adaptee.loadConquestMap(p_gameEngine, p_mfl);
    }

}
