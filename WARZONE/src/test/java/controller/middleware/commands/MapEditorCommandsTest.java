package controller.middleware.commands;

import controller.GameEngine;
import controller.middleware.commands.MapEditorCommands;
import controller.statepattern.MapEditor;
import controller.statepattern.gameplay.IssueOrder;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Javadoc for testing MapEditorCommands class.
 */
public class MapEditorCommandsTest {
    /**
     * Represents the game engine associated with this instance.
     */
    GameEngine d_gameEngine;

    /**
     * This method is executed before each test method to set up the testing environment.
     */
    @Before
    public void beforeTest() {
        d_gameEngine = new GameEngine();
        d_gameEngine.setCurrentState(new MapEditor(d_gameEngine));
    }


    /**
     * Test case for validating the command with only one country name.
     */
    @Test
    public void testShowMap1() {
        String l_cmd = "showmap India";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the command with multiple country names.
     */
    @Test
    public void testShowMap2() {
        String l_cmd = "showmap India Nepal";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the command with no parameters.
     */
    @Test
    public void testShowMap3() {
        String l_cmd = "showmap ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the command with multiple country names.
     */
    @Test
    public void testValidateMap1() {
        String l_cmd = "validatemap India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the command with multiple country names.
     */
    @Test
    public void testValidateMap2() {
        String l_cmd = "validatemap India Bangladesh Nepal";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for validating the command with no parameters.
     */
    @Test
    public void testValidateMap3() {
        String l_cmd = "validatemap ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for savemap with no parameters.
     */
    @Test
    public void testSaveMap1() {
        String l_cmd = "savemap ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for savemap with no incorrect name.
     */
    @Test
    public void testSaveMap2() {
        String l_cmd = "savemap piyush";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for savemap with correct map name.
     */
    @Test
    public void testSaveMap3() {
        String l_cmd = "savemap piyush.map";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for savemap with no incorrect name.
     */
    @Test
    public void testSaveMap4() {
        String l_cmd = "savemap piyush satti.map";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editmap with no parameters.
     */
    @Test
    public void testEditMap1() {
        String l_cmd = "editmap ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editmap with no incorrect name.
     */
    @Test
    public void testEditMap2() {
        String l_cmd = "editmap piyush";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editmap with no correct name.
     */
    @Test
    public void testEditMap3() {
        String l_cmd = "editmap piyush.map";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editmap with no incorrect name.
     */
    @Test
    public void testEditMap4() {
        String l_cmd = "editmap piyush satti.map";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent without parameters.
     */
    @Test
    public void testEditContinent1() {
        String l_cmd = "editcontinent ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect name.
     */
    @Test
    public void testEditContinent2() {
        String l_cmd = "editcontinent Asia Africa";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent3() {
        String l_cmd = "editcontinent -add Asia -remove Africa";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent4() {
        String l_cmd = "editcontinent -add Asia Africa -remove Australia";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent5() {
        String l_cmd = "editcontinent -add -remove Australia";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent6() {
        String l_cmd = "editcontinent -remove ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent7() {
        String l_cmd = "editcontinent -remove -add Asia";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent8() {
        String l_cmd = "editcontinent -remove -add ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with the correct command.
     */
    @Test
    public void testEditContinent9() {
        String l_cmd = "editcontinent -add Asia 100";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with the correct command.
     */
    @Test
    public void testEditContinent10() {
        String l_cmd = "editcontinent -add Asia 100 -remove Africa";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editcontinent with incorrect command.
     */
    @Test
    public void testEditContinent11() {
        String l_cmd = "editcontinent -shashi Asia 100 -remove Africa";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor.
     */
    @Test
    public void testEditNeighbor1() {
        String l_cmd = "editneighbor ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor2() {
        String l_cmd = "editneighbor India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor3() {
        String l_cmd = "editneighbor -add India -remove India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with the correct command.
     */
    @Test
    public void testEditNeighbor4() {
        String l_cmd = "editneighbor -add India Bangladesh -remove India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor5() {
        String l_cmd = "editneighbor -add -remove India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor6() {
        String l_cmd = "editneighbor -remove ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor7() {
        String l_cmd = "editneighbor -remove -add India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor8() {
        String l_cmd = "editneighbor -remove -add ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor9() {
        String l_cmd = "editneighbor -add India Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor10() {
        String l_cmd = "editneighbor -add -remove";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor11() {
        String l_cmd = "editneighbor -remove India ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editNeighbor with incorrect command.
     */
    @Test
    public void testEditNeighbor12() {
        String l_cmd = "editneighbor -remove India -shashi Australia ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the correct command.
     */
    @Test
    public void testEditCountry1() {
        String l_cmd = "editcountry ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry2() {
        String l_cmd = "editcountry India Asia";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry3() {
        String l_cmd = "editcountry -add India -remove India";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry4() {
        String l_cmd = "editcountry -add India Asia -remove Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry5() {
        String l_cmd = "editcountry -add -remove Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry6() {
        String l_cmd = "editcountry -remove ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry7() {
        String l_cmd = "editcountry -remove -add India";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry8() {
        String l_cmd = "editcountry -remove -add ";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the correct command.
     */
    @Test
    public void testEditCountry9() {
        String l_cmd = "editcountry -add India Asia";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the correct command.
     */
    @Test
    public void testEditCountry10() {
        String l_cmd = "editcountry -add India Asia -remove Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertTrue(l_obj.validateCommand(d_gameEngine));
    }

    /**
     * Test case for editCountry with the incorrect command.
     */
    @Test
    public void testEditCountry11() {
        String l_cmd = "editcountry -add India Asia -shashi Bangladesh";
        MapEditorCommands l_obj = new MapEditorCommands(l_cmd);
        assertFalse(l_obj.validateCommand(d_gameEngine));
    }
}