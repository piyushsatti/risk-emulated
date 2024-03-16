package controller;

import controller.middleware.commands.MapEditorCommands;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderExecutionCommandsTest {

    @Test
    public void testBomb1() {
        String cmd = "bomb ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testBomb2() {
        String cmd = "bomb piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testBomb3() {
        String cmd = "bomb piyush satti";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testBlockade1() {
        String cmd = "blockade ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testBlockade2() {
        String cmd = "blockade piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testBlockade3() {
        String cmd = "blockade piyush satti";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testNegotiate1() {
        String cmd = "negotiate ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testNegotiate2() {
        String cmd = "negotiate piyush";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testNegotiate3() {
        String cmd = "negotiate piyush satti";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testDeploy1() {
        String cmd = "deploy India Shashi";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testDeploy2() {
        String cmd = "deploy India ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testDeploy3() {
        String cmd = "deploy India 300";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testDeploy4() {
        String cmd = "deploy ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAdvance1() {
        String cmd = "advance ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAdvance2() {
        String cmd = "advance India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAdvance3() {
        String cmd = "advance India Bangladesh 300";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testAdvance4() {
        String cmd = "advance India 300 ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAdvance5() {
        String cmd = "advance 300 ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAdvance6() {
        String cmd = "advance India Bangladesh Nepal";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAirlift1() {
        String cmd = "airlift ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAirlift2() {
        String cmd = "airlift India Bangladesh";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAirlift3() {
        String cmd = "airlift India Bangladesh 300";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertTrue(obj.validateCommand());
    }

    @Test
    public void testAirlift4() {
        String cmd = "airlift India 300 ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAirlift5() {
        String cmd = "airlift 300 ";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }

    @Test
    public void testAirlift6() {
        String cmd = "airlift India Bangladesh Nepal";
        MapEditorCommands obj = new MapEditorCommands(cmd);
        assertFalse(obj.validateCommand());
    }
}