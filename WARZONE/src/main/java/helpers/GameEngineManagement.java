package helpers;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import controller.GameEngine;
import models.Player;
import models.worldmap.WorldMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameEngineManagement {
    private final GameEngine d_ge;
    ObjectMapper objectMapper;

    public GameEngineManagement(GameEngine p_ge) {
        this.d_ge = p_ge;
        objectMapper = new ObjectMapper();
    }

    public void saveGame(String p_save_file_name) throws IOException {
        // Create a File object
        File player_file = new File(p_save_file_name + ".player");
        File map_file = new File(p_save_file_name + ".map");

        // Check if the file exists
        if (!player_file.exists()) {
            try {
                // If the file doesn't exist, create a new file
                player_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!map_file.exists()) {
            try {
                // If the file doesn't exist, create a new file
                map_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        objectMapper.configure(SerializationFeature. FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.writeValue(player_file, d_ge.d_players);
        objectMapper.writeValue(map_file, d_ge.d_worldmap);
    }

    public void loadGame(String p_save_file_name) throws IOException {
        File player_file = new File(p_save_file_name + ".player");
        File map_file = new File(p_save_file_name + ".map");
        d_ge.d_players = (ArrayList<Player>) objectMapper.readValue(player_file, ArrayList.class);
        d_ge.d_worldmap = objectMapper.readValue(map_file, WorldMap.class);
    }
}