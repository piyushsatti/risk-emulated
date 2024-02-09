# SOEN6441_Team24_Warzone

# To-Do
- [ ] Map-Editor
  - [ ] User-driven creation/deletion of map elements: country, continent, and connectivity between countries. Map editor commands:
    - [ ] editcontinent -add continentID continentvalue -remove continentID 
    - [ ] editcountry -add countryID continentID -remove countryID
    - [ ] editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID
  - [ ] Display the map as text
    - [ ] showmap (show all continents and countries and their respective neighbors)
    - [ ] Save a map to a text file exactly as edited (using the “domination” game map format). Map editor command:
    savemap filename
    - [ ] Load a map from an existing “domination” map file, or create a new map from scratch if the file does not exist. Map editor command: editmap filename
    - [ ] The validatemap command can be triggered anytime during map editing. Map editor command: validatemap
      - [ ] map is a disconnected graph
      - [ ] map contains a continent that is a disconnected subgraph).  
      - [ ] <Pending>.