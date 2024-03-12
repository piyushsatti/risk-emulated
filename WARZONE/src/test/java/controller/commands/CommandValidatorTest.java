package controller.commands;

import helpers.exceptions.InvalidCommandException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * this test class has test methods to test the validity of 'deploy','gameplayer','editcontinent','editcountry' and 'editneighbor' commands
 *
 */
public class CommandValidatorTest {
    /**
     * String array to store 'deploy' commands
     */
    private static String[] testDeployCommands;
    /**
     * String array to store 'gameplayer' commands
     */
    private static String[] testGamePlayerCommands;
    /**
     * String array to store 'editcontinent' commands
     */
    private static String[] testEditContinentCommands;

    /**
     * String array to store 'editcountry' commands
     */
    private static String[] testEditCountryCommands;

    /**
     * String array to store 'editneighbor' commands
     */
    private static String[] testEditNeighborCommands;
    /**
     * command validator object to call on tested methods
     */
    CommandValidator cmd = new CommandValidator();

    /**
     * initializing the string arrays testDeployCommands,testGamePlayerCommands,
     * testEditContinentCommands, testEditCountryCommands and testEditNeighborCommands
     * for testing
     */
    @BeforeClass
    public static void setTestCommands()
    {
        testDeployCommands = new String[12];
        testDeployCommands[0] = "deploy alaska 1.90f";
        testDeployCommands[1] = "deploy south_carolina elephant";
        testDeployCommands[2] = "deploy south_carolina true";
        testDeployCommands[3] = "deploy south_carolina false";
        testDeployCommands[4] = "deploy south_carolina 123.456";
        testDeployCommands[5] = "deploy south_carolina 9879573210L";
        testDeployCommands[6] = "deploy south_carolina -12";
        testDeployCommands[7] = "deploy south_carolina -0.002";
        testDeployCommands[8] = "deploy south_carolina -0.000";
        testDeployCommands[9] = "deploy south_carolina 0.000";
        testDeployCommands[10] = "deploy south_carolina 0.002";
        testDeployCommands[11] = "deploy south_carolina 0";

        testGamePlayerCommands = new String[8];
        testGamePlayerCommands[0] = "gameplayer india brazil argentina concordia";
        testGamePlayerCommands[1] = "gameplayer sachin -add sachin_tendulkar";
        testGamePlayerCommands[2] = "gameplayer -add -add sachin_tendulkar";
        testGamePlayerCommands[3] = "gameplayer -add -remove sachin_tendulkar";
        testGamePlayerCommands[4] = "gameplayer -remove -remove sachin_tendulkar";
        testGamePlayerCommands[5] = "gameplayer -remove -add sachin_tendulkar";
        testGamePlayerCommands[6] = "gameplayer  -add sachin_tendulkar -timhortons robert_downey";
        testGamePlayerCommands[7] = "gameplayer  -add";
        testGamePlayerCommands[7] = "gameplayer  -remove";

        testEditContinentCommands = new String[10];
        testEditContinentCommands[0] = "editcontinent -timmy North_America 12 -remove Asia";
        testEditContinentCommands[1] = "editcontinent -timmy North_America 12";
        testEditContinentCommands[2] = "editcontinent -timmy Asia";
        testEditContinentCommands[3] = "editcontinent canada montreal quebec";
        testEditContinentCommands[4] = "editcontinent -add North_America -remove Asia";
        testEditContinentCommands[5] = "editcontinent -add North_America 12 -remove -add Asia 100";
        testEditContinentCommands[6] = "editcontinent -add -add North_America 12";
        testEditContinentCommands[7] = "editcontinent -remove -remove Asia 100";
        testEditContinentCommands[8] = "editcontinent -add -remove Asia";
        testEditContinentCommands[9] = "editcontinent -remove -add Asia 100";

        testEditCountryCommands = new String[10];
        testEditCountryCommands[0] = "editcountry -timmy North_America 12 -remove Asia";
        testEditCountryCommands[1] = "editcountry -timmy North_America 12";
        testEditCountryCommands[2] = "editcountry -timmy Asia";
        testEditCountryCommands[3] = "editcountry canada montreal quebec";
        testEditCountryCommands[4] = "editcountry -add North_America 12 -remove -add Asia 100";
        testEditCountryCommands[5] = "editcountry -add -add North_America 12";
        testEditCountryCommands[6] = "editcountry -remove -remove Asia 100";
        testEditCountryCommands[7] = "editcountry -add -remove Asia";
        testEditCountryCommands[8] = "editcountry -remove -add Asia 100";

        testEditNeighborCommands = new String[13];
        testEditNeighborCommands[0] = "editneighbor -timmy Bangladesh India -remove Bangladesh";
        testEditNeighborCommands[1] = "editneighbor -timmy Bangladesh India";
        testEditNeighborCommands[2] = "editneighbor -timmy India";
        testEditNeighborCommands[3] = "editneighbor canada montreal quebec";
        testEditNeighborCommands[4] = "editneighbor -add Bangladesh India -remove -add Australia New_Zealand";
        testEditNeighborCommands[5] = "editneighbor -add -add Bangladesh India";
        testEditNeighborCommands[6] = "editneighbor -remove -remove Bangladesh India";
        testEditNeighborCommands[7] = "editneighbor -add -remove India";
        testEditNeighborCommands[8] = "editneighbor -remove -add Bangladesh India";
        testEditNeighborCommands[9] = "editneighbor -timmy Bangladesh India -remove Australia New_Zealand";
        testEditNeighborCommands[10] = "editneighbor -timmy Bangladesh -remove Australia New_Zealand";
        testEditNeighborCommands[11] = "editneighbor -add Bangladesh India -add Australia";
        testEditNeighborCommands[12] = "editneighbor -remove Bangladesh India -remove Australia";
    }

    /**
     * this method to used to test deploy command validity for 0th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity1() {
        cmd.checkDeployCommandValidity(testDeployCommands[0].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 1st index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity2() {
        cmd.checkDeployCommandValidity(testDeployCommands[1].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 2nd index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity3() {
        cmd.checkDeployCommandValidity(testDeployCommands[2].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 3rd index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity4() {
        cmd.checkDeployCommandValidity(testDeployCommands[3].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 4th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity5() {
        cmd.checkDeployCommandValidity(testDeployCommands[4].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 5th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity6() {
        cmd.checkDeployCommandValidity(testDeployCommands[5].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 6th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity7() {
        cmd.checkDeployCommandValidity(testDeployCommands[6].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 7th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity8() {
        cmd.checkDeployCommandValidity(testDeployCommands[7].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 8th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity9() {
        cmd.checkDeployCommandValidity(testDeployCommands[8].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 9th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity10() {
        cmd.checkDeployCommandValidity(testDeployCommands[9].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 10th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity11() {
        cmd.checkDeployCommandValidity(testDeployCommands[10].split(" "));
    }
    /**
     * this method to used to test deploy command validity for 11th index string of testDeployCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testCheckDeployCommandValidity12() {
        cmd.checkDeployCommandValidity(testDeployCommands[11].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 0th index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity1() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[0].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 1st index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity2() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[1].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 2nd index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity3() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[2].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 3rd index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity4() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[3].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 4th index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity5() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[4].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 5th index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity6() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[5].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 6th index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity7() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[6].split(" "));
    }
    /**
     * this method to used to test gameplayer command validity for 7th index string of testGamePlayerCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testGamePlayerCommandValidity8() throws InvalidCommandException {
        cmd.checkGamePlayerCommandValidity(testGamePlayerCommands[7].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 0th index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity1() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[0].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 1st index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity2() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[1].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 2nd index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity3() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[2].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 3rd index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity4() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[3].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 4th index string of testEditContinentCommands string array
     */
    @Test(expected = NumberFormatException.class)
    public void testEditContinentValidity5() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[4].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 5th index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity6() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[5].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 6th index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity7() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[6].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 7th index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity8() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditContinentCommands[7].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 8th index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity9() throws InvalidCommandException{
        cmd.checkEditCommandsValidity(testEditContinentCommands[8].split(" "));
    }
    /**
     * this method to used to test editcontinent command validity for 9th index string of testEditContinentCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditContinentValidity10() throws InvalidCommandException{
        cmd.checkEditCommandsValidity(testEditContinentCommands[9].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 0th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity1() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[0].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 1st index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity2() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[1].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 2nd index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity3() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[2].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 3rd index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity4() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[3].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 4th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity5() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[4].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 5th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity6() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[5].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 6th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity7() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[6].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 7th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity8() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditCountryCommands[7].split(" "));
    }
    /**
     * this method to used to test editcountry command validity for 8th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditCountryValidity9() throws InvalidCommandException{
        cmd.checkEditCommandsValidity(testEditCountryCommands[8].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 0th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity1() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[0].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 1st index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity2() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[1].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 2nd index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity3() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[2].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 3rd index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity4() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[3].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 4th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity5() throws NumberFormatException, InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[4].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 5th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity6() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[5].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 6th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity7() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[6].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 7th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity8() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[7].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 8th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity9() throws InvalidCommandException{
        cmd.checkEditCommandsValidity(testEditNeighborCommands[8].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 9th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity10() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[9].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 10th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity11() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[10].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 11th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity12() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[11].split(" "));
    }
    /**
     * this method to used to test editneighbor command validity for 12th index string of testEditCountryCommands string array
     */
    @Test(expected = InvalidCommandException.class)
    public void testEditNeighborValidity13() throws InvalidCommandException {
        cmd.checkEditCommandsValidity(testEditNeighborCommands[12].split(" "));
    }
}