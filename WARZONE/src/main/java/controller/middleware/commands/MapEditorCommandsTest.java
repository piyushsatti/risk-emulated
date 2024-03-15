package controller.middleware.commands;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapEditorCommandsTest {
    @Test
    public void testShowMap1() {
        String cmd = "showmap India";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testShowMap2() {
        String cmd = "showmap India Nepal";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testShowMap3() {
        String cmd = "showmap ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testValidateMap1() {
        String cmd = "validatemap India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testValidateMap2() {
        String cmd = "validatemap India Bangladesh Nepal";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testValidateMap3() {
        String cmd = "validatemap ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testSaveMap1() {
        String cmd = "savemap ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testSaveMap2() {
        String cmd = "savemap piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testSaveMap3() {
        String cmd = "savemap piyush.map";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testSaveMap4() {
        String cmd = "savemap piyush satti.map";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditMap1() {
        String cmd = "editmap ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditMap2() {
        String cmd = "editmap piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditMap3() {
        String cmd = "editmap piyush.map";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditMap4() {
        String cmd = "editmap piyush satti.map";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent1() {
        String cmd = "editcontinent ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditContinent2() {
        String cmd = "editcontinent Asia Africa";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent3() {
        String cmd = "editcontinent -add Asia -remove Africa";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent4() {
        String cmd = "editcontinent -add Asia Africa -remove Australia";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent5() {
        String cmd = "editcontinent -add -remove Australia";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent6() {
        String cmd = "editcontinent -remove ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent7() {
        String cmd = "editcontinent -remove -add Asia";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent8() {
        String cmd = "editcontinent -remove -add ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditContinent9() {
        String cmd = "editcontinent -add Asia 100";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditContinent10() {
        String cmd = "editcontinent -add Asia 100 -remove Africa";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditContinent11() {
        String cmd = "editcontinent -shashi Asia 100 -remove Africa";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor1() {
        String cmd = "editneighbor ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor2() {
        String cmd = "editneighbor India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor3() {
        String cmd = "editneighbor -add India -remove India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor4() {
        String cmd = "editneighbor -add India Bangladesh -remove India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor5() {
        String cmd = "editneighbor -add -remove India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor6() {
        String cmd = "editneighbor -remove ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor7() {
        String cmd = "editneighbor -remove -add India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor8() {
        String cmd = "editneighbor -remove -add ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor9() {
        String cmd = "editneighbor -add India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor10() {
        String cmd = "editneighbor -add -remove";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor11() {
        String cmd = "editneighbor -remove India ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditNeighbor12() {
        String cmd = "editneighbor -remove India -shashi Australia ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }


    @Test
    public void testEditCountry1() {
        String cmd = "editcountry ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditCountry2() {
        String cmd = "editcountry India Asia";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditCountry3() {
        String cmd = "editcountry -add India -remove India";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditCountry4() {
        String cmd = "editcountry -add India Asia -remove Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditCountry5() {
        String cmd = "editcountry -add -remove Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditCountry6() {
        String cmd = "editcountry -remove ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditCountry7() {
        String cmd = "editcountry -remove -add India";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditCountry8() {
        String cmd = "editcountry -remove -add ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testEditCountry9() {
        String cmd = "editcountry -add India Asia";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditCountry10() {
        String cmd = "editcountry -add India Asia -remove Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testEditCountry11() {
        String cmd = "editcountry -add India Asia -shashi Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
}