# SOEN6441_Team24_Warzone

# How to Run.

## MapEditor Command

## GamePlay Commands

# Design and Project

## Naming conventions

- class names in CamelCase that starts with a capital letter
- data members start with d_
- method parameters start with p_
- local variables start with l_
- global variables in capital letters
- static members start with a capital letter, non-static members start with a lower case letter
  Code layout
- consistent layout throughout code (use an IDE auto-formatter)
  Commenting convention
- javadoc comments for every class and method
- long methods (more than 10 lines) are documented with comments for procedural steps
- no commented-out code
  Project structure
- one folder for every module in the high-level design
- tests are in a separate folder that has the exact same structure as the code folder
- 1-1 relationship between tested classes and test classes

## Design To-Do
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
- [ ] GameEngine
 - [ ] Implementation of a GameEngine class implementing and controlling the game phases according to the Warzone rules.
 - [ ] Game Play Command: -  showmap (show all countries and continents, armies on each country, ownership, and connectivity in a way that enables efficient game play)
 - [ ] Startup phase
   - [ ] Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph. Startup phase command: loadmap filename
   - [ ] User creates the players, then all countries are randomly assigned to players. Startup phase commands: gameplayer -add playername -remove playername
   - [ ] assigncountries
   - [ ] There must be a Player class that must hold (among other things) a list of Country objects that are owned by the Player and a list of Order objects that have been 
         created by the Player during the current turn, and has a method “issue_order()” (no parameters, no return value) whose function is to add an order to the list of 
         orders held by the player when the game engine calls it during the issue orders phase. The player class must also have a “next_order()” (no parameters) method that 
         is called by the GameEngine during the execute orders phase and returns the first order in the player’s list of orders, then removes it from the list
 - [ ] Main game loop
  - [ ] Loop over each player for the assign reinforcements, issue orders and execute orders main game loop phases
  - [ ] Main game loop: assign reinforcements phase: Assign to each player the correct number of reinforcement armies according to the Warzone rules.
  - [ ] Main game loop: issue orders phase: The GameEngine class calls the issue_order() method of the Player. This method will wait for the following command, then create a         deploy order object on the player’s list of orders, then reduce the number of armies in the player’s reinforcement pool. The game engine does this for all players in         round-robin fashion until all the players have placed all their reinforcement armies on the map. Issuing order command: deploy countryID num (until all       
        reinforcements have been placed)
  - [ ] Main game loop: execute orders phase: The GameEngine calls the next_order() method of the Player. Then the Order object’s execute() method is called, which will     
        enact the order. The effect of a deploy order is to place num armies on the country countryID.
- [ ] Programming Process
  - [ ] Architectural design—short document including an architectural design diagram. Short but complete and clear description of the design, which should break down the            system into cohesive modules. The architectural design should be reflected in the implementation of well-separated modules and/or folders.
  - [ ] Software versioning repository—well-populated history with dozens of commits, distributed evenly among team members, as well as evenly distributed over the time               allocated to the build. A tagged version should have been created for build 1. Use of a continuous integration solution that applies the following operation when             code is pushed onto the repository:
    - (1) project successfully compiles
    - (2) all unit tests successfully pass
    - (3) javadoc is compiled and reported as complete.
  - [ ] Javadoc API documentation—completed for all files, all classes and all methods
  - [ ] Unit testing framework—at least 10 relevant test cases testing the most important aspects of the code. Must include tests for:
    -  map validation – map is a connected graph;
    -  continent validation – continent is a connected subgraph;
    -  calculation of number of reinforcement armies;
    -  player cannot deploy more armies that there is in their reinforcement pool.
  - [ ] Coding standards—Consistent use of the coding conventions described below
 
