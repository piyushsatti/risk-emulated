package views;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
public class SampleViews {
    public static void main(String[] args) {
        String mapFile = "C:\\Users\\HP\\Documents\\MACS PROJECTS\\SOEN6441\\WARZONE\\src\\main\\resources\\maps\\usa8\\usa8regions.map";
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFile))) {
            Map<String, Integer> continentToID = new HashMap<>();
            Map<String, List<String>> continentToCountries = new HashMap<>();
            Map<Integer, List<String>> continentIDToCountries = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null && !line.equals("[continents]")) {
                // Skip lines until the [continents] section is reached
            }
            // Read the number of countries for each continent
            int continentID = 1; // Initialize continent ID
            // Read the number of countries for each continent
            while ((line = reader.readLine()) != null && !line.equals("[countries]")) {
                if (!line.isEmpty() && line.contains(" ")) {
                    String[] parts = line.split(" ");
                    String continent = parts[0];
                    // Add continent only if it's not already present
                    if (!continentToID.containsKey(continent)) {
                        continentToID.put(continent, continentID);
                        continentID++; // Increment continent ID for the next continent
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : continentToID.entrySet()) {
                System.out.println("Continent: " + entry.getKey() + ", ID: " + entry.getValue());
            }
            while ((line = reader.readLine()) != null && !line.equals("[borders]")) {
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    continentID = Integer.parseInt(parts[2]);
                    String country = parts[1];
                    continentIDToCountries.computeIfAbsent(continentID, k -> new ArrayList<>()).add(country);
                }
            }

            // Print the name of each country along with its continent ID
            for (Map.Entry<Integer, List<String>> entry : continentIDToCountries.entrySet()) {
                continentID = entry.getKey();
                List<String> countries = entry.getValue();
                System.out.println("Continent ID: " + continentID);
                System.out.println("Countries: ");
                for (String country : countries) {
                    System.out.println(country);
                }
                System.out.println();
                System.out.println("test line");
            }

//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



