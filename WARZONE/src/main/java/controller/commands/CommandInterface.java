package main.java.controller.commands;

import main.java.controller.GameEngine;

public class CommandInterface {

    public static void addContinentIdContinentVal(Integer integer, Integer integer1) {

        // GameEngine.CURRENT_MAP.addContinent();

    }

    public static void removeContinent(int continentId) {

        GameEngine.CURRENT_MAP.removeContinent(continentId);

    }

    public static void addCountry(int country_id, int continent_id, String country_name) {

        GameEngine.CURRENT_MAP.addCountry(country_id, continent_id, country_name);

    }

    public static void removeCountry(int Country_id) {

        GameEngine.CURRENT_MAP.removeCountry(Country_id);

    }

    public static void addNeighboursToCountry(int country_id, int neighbour_id) {

        GameEngine.CURRENT_MAP.addBorder(country_id, neighbour_id);

    }

    public static void removeNeighboursToCountry(int country_id, int neighbour_id) {

        // GameEngine.CURRENT_MAP.getCountry(country_id).removeBorder(neighbour_id);

    }

    public static void addPlayers(String s) {
    }

    public static void removePlayers(String s) {
    }

    public static void loadCurrentMap() {
    }

    public static void validateMap() {
    }

    public static void saveMap() {
    }

    public static void editMap() {
    }
}
