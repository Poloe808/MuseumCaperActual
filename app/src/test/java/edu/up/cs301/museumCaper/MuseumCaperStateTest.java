package edu.up.cs301.museumCaper;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 *  junit tests for each action currently in the game and the deep copy constructor
 *
 *  @author Logan Ortogero
 *  @author Paloma Wilson
 *  @author Alberto Lucero
 *  @author Felipe Lucas Pablo
 *
 *  @version February 2026
 */

public class MuseumCaperStateTest extends TestCase {

    @Test
    public void testConstructor() {
        MuseumCaperState test = new MuseumCaperState();
        int turn = test.getTurn();
        assertEquals(0,turn);
    }

    // ====================================================================================================================
    // ============================ (This is the start of the test cases for actions) =====================================

    @Test
    public void testCopyConstructor() {
        MuseumCaperState firstInstance = new MuseumCaperState(0);
        MuseumCaperState firstCopy = new MuseumCaperState(firstInstance, 0);

        //Thief Moves from window towards painting
        boolean result = firstInstance.move(new MuseumCaperMoveAction(null, 1,0));
        assertTrue(result);

        //Steals painting
        result = firstInstance.stealPainting(new MuseumCaperStealPaintingAction(null));
        assertTrue(result);

        //Thief Moves from painting towards window
        result = firstInstance.move(new MuseumCaperMoveAction(null, -1,0));
        assertTrue(result);

        //Thief checks lock to escape
        result = firstInstance.checkLock(new MuseumCaperCheckLockAction(null));
        assertTrue(result);

        //Thief ends turn
        result = firstInstance.endTurn(new MuseumCaperEndTurnAction(null));
        assertTrue(result);

        MuseumCaperState secondInstance = new MuseumCaperState(0);
        MuseumCaperState secondCopy = new MuseumCaperState(secondInstance, 0);

        assertEquals(firstCopy.toString(),secondCopy.toString());
        assertNotSame(firstInstance.toString(), firstCopy.toString());
    }

    public void testGetBoard() {
        MuseumCaperState testBoard = new MuseumCaperState();
        List<List<MapTile>> board = testBoard.getBoard();
        //if it exist
        assertNotNull(board);
        // how tall
        assertEquals(13, board.get(0).size());
        // how width
        assertEquals(12,board.size());
    }

    public void testGetTurn() {
        MuseumCaperState test = new MuseumCaperState();
        int p1 = test.getTurn();
        assertEquals(0,p1);
    }

    public void testGetIsVisible() {
        MuseumCaperState test = new MuseumCaperState();
        boolean p1 = test.getIsVisible();
        assertFalse(p1);
    }
    // ====================================================================================================================
    // =========================== (This is the start of the test cases for actions) ====================================

    /**
     * This be the test for the Stealin a painting
     */
    @Test
    public void testMuseumCaperStealPaintingAction() {
        MuseumCaperState state = new MuseumCaperState();
        MapTile testTile = state.getBoard().get(0).get(0);
        testTile.setHasPainting(new Painting(7));
        state.setThiefLocation(0, 0);

        //Test to make sure the painting is correctly "placed" onto the tile
        assertTrue(testTile.hasPainting());

        //Create the steal action, If steal action is called when the thief is on the same tile
        //  as a painting, it should go through
        MuseumCaperStealPaintingAction steal = new MuseumCaperStealPaintingAction(null);
        boolean result = state.stealPainting(steal);
        assertTrue(result);

        //Once the painting is stolen, it should no longer exist on that Tile.
        assertTrue(!testTile.hasPainting());

        //if there is no longer a painting on that square, it should fail
        MuseumCaperStealPaintingAction steal2 = new MuseumCaperStealPaintingAction(null);
        boolean result2 = state.stealPainting(steal2);
        assertTrue(!result2);
    }

    /**
     * This be the test for the disabling a camera
     */
    @Test
    public void testMuseumCaperDisableCameraAction(){
        MuseumCaperState state = new MuseumCaperState();
        boolean result;

        MuseumCaperStealPaintingAction action = new MuseumCaperStealPaintingAction(null);

        state.setThiefLocation(1, 1);
        state.setCamera(1, 1, 9);

        assertTrue(state.getBoard().get(1).get(1).hasCamera());
        assertEquals("Working", state.getBoard().get(1).get(1).getCamera().toString());
        result = state.disableCamera(action);

        assertTrue(result);
        assertEquals("Disabled", state.getBoard().get(1).get(1).getCamera().toString());

        result = state.disableCamera(action);
        assertFalse(result);

        assertEquals("Disabled", state.getBoard().get(1).get(1).getCamera().toString());
    }


    /**
     * This be the test for the MoveAction
     */
    @Test
    public void testMuseumCaperMoveAction(){
        MuseumCaperState state = new MuseumCaperState("John caperState is moving");
        boolean result;

        MuseumCaperMoveAction moveRight = new MuseumCaperMoveAction(null, 1, 0);
        MuseumCaperMoveAction moveLeft= new MuseumCaperMoveAction(null, -1, 0);
        MuseumCaperMoveAction moveUp = new MuseumCaperMoveAction(null, 0, 1);
        MuseumCaperMoveAction moveDown = new MuseumCaperMoveAction(null, 0, -1);

        //move up no obstruction should PASS *and* should move the piece up one
        state.setThiefLocation(10, 0);
        result = state.move(moveUp);
        assertTrue(result);
        assertTrue(checkCoords(state.getThiefLocation(), 9, 0));

        //move down no obstruction should pass and should move the piece down one
        result = state.move(moveDown);
        assertTrue(result);
        assertTrue(checkCoords(state.getThiefLocation(), 10, 0));

        //move right no obstruction should pass and should move the piece right one
        result = state.move(moveRight);
        assertTrue(result);
        assertTrue(checkCoords(state.getThiefLocation(), 10, 1));

        //move left no obstruction should PASS and should move the piece left one
        result = state.move(moveLeft);
        assertTrue(result);
        assertTrue(checkCoords(state.getThiefLocation(), 10, 0));

        //move against obstruction WITH moves left should fail, and should stay in the same position
        assertTrue(state.getMoveCount() > 0);
        state.setThiefLocation(1, 1);

        //move with no moves left should fail
        //move up
        result = state.move(moveUp);
        assertFalse(result);
        assertTrue(checkCoords(state.getThiefLocation(), 1, 1));

        //move down
        result = state.move(moveDown);
        assertFalse(result);
        assertTrue(checkCoords(state.getThiefLocation(), 1, 1));

        //move right
        result = state.move(moveRight);
        assertFalse(result);
        assertTrue(checkCoords(state.getThiefLocation(), 1, 1));

        //move left
        result = state.move(moveLeft);
        assertFalse(result);
        assertTrue(checkCoords(state.getThiefLocation(), 1, 1));

        //move with NO obstruction but with 0 moveCount should FAIL.
        state.setThiefLocation(0,0);
        state.move(moveRight);

        assertEquals(0, state.getMoveCount());
        result = state.move(moveRight);

        assertFalse(result);
        assertTrue(checkCoords(state.getThiefLocation(), 0, 1));
    }

    /**
     * Helper method that checks whether or not the coordinates of a player match the expected ones
     * @param thiefLocation
     *      The list of the player that contains what row and col they are actually in
     * @param expectedRow
     *      The row (y-position) we expect them to be in
     * @param expectedCol
     *      the col (x-position) we expect them to be in
     */
    //helper method to help with 2d coordinates for the moveAction test
    public boolean checkCoords(List<Integer> thiefLocation, int expectedRow, int expectedCol){
        return (thiefLocation.get(0) == expectedCol && thiefLocation.get(1) == expectedRow);
    }

    /**
     * This be the test for the EndTurn
     */
    @Test
    public void testMuseumCaperEndTurnAction(){
        MuseumCaperState state = new MuseumCaperState();
        boolean result;

        MuseumCaperEndTurnAction action = new MuseumCaperEndTurnAction(null);

        assertTrue(state.getIsThiefTurn());
        result = state.endTurn(action);

        assertTrue(result);
        assertFalse(state.getIsThiefTurn());
        assertEquals(1, state.getTurn());

        result = state.endTurn(action);
        assertTrue(result);

        assertTrue(state.getIsThiefTurn());
        assertEquals(1, state.getTurn());
    }

    /**
     * This be the test for the UseEyes action
     */
    @Test
    public void testMuseumCaperUseEyesAction(){
        MuseumCaperState state = new MuseumCaperState();
        boolean result;

        MuseumCaperUseEyesAction eyesAction = new MuseumCaperUseEyesAction(null, 6, 5);

        //thief in line of sight up
        result = useEyesWhenThiefIs(state, eyesAction, 6, 3);
        assertTrue(result);
        assertTrue(state.getThiefVisible());

        //thief in line of sight to the right
        result = useEyesWhenThiefIs(state, eyesAction, 7, 5);
        assertTrue(result);
        assertTrue(state.getThiefVisible());

        //thief in line of sight down
        result = useEyesWhenThiefIs(state, eyesAction, 6, 8);
        assertTrue(result);
        assertTrue(state.getThiefVisible());

        //thief in line of sight to the left
        result = useEyesWhenThiefIs(state, eyesAction, 4, 5);
        assertTrue(result);
        assertTrue(state.getThiefVisible());

        //thief diagonally up and to the left
        result = useEyesWhenThiefIs(state, eyesAction, 5, 4);
        assertFalse(result);

        //thief diagonally up and to the right
        result = useEyesWhenThiefIs(state, eyesAction, 7, 4);
        assertFalse(result);

        //thief diagonally down and to the left
        result = useEyesWhenThiefIs(state, eyesAction, 5, 6);
        assertFalse(result);

        //thief diagonally down and to the right
        result = useEyesWhenThiefIs(state, eyesAction, 7, 6);
        assertFalse(result);

        state.getBoard().get(5).get(6).setTopWall(true);
        state.getBoard().get(5).get(6).setLeftWall(true);
        state.getBoard().get(6).get(6).setTopWall(true);
        state.getBoard().get(5).get(7).setLeftWall(true);

        //thief behind a wall up
        result = useEyesWhenThiefIs(state, eyesAction, 6, 3);
        assertFalse(result);

        //thief behind a wall right
        result = useEyesWhenThiefIs(state, eyesAction, 7, 5);
        assertFalse(result);

        //thief behind a wall down
        result = useEyesWhenThiefIs(state, eyesAction, 6, 8);
        assertFalse(result);

        //thief behind a wall left
        result = useEyesWhenThiefIs(state, eyesAction, 4, 5);
        assertFalse(result);

    }

    /**
     * TODO: Update this :)
     * Helper method that checks whether or not the coordinates of a player match the expected ones
     * @param state
     *      The list of the player that contains what row and col they are actually in
     * @param action
     *      The row (y-position) we expect them to be in
     * @param thiefRow
     *      the col (x-position) we expect them to be in
     * @param thiefCol
     *      the col (x-position) we expect them to be in
     */
    public boolean useEyesWhenThiefIs(MuseumCaperState state, MuseumCaperUseEyesAction action, int thiefCol, int thiefRow){
        state.setThiefVisible(false);
        state.setThiefLocation(thiefRow, thiefCol);
        return state.useEyes(action);
    }

    /**
     * This be the test for the Camera action
     */
    @Test
    public void testMuseumCaperCameraAction(){

    }

    // ============================= (This is the end of the test cases for actions) ======================================
    // ====================================================================================================================

    public void testSetTurn() {
        MuseumCaperState test = new MuseumCaperState();
        test.setTurn(0);
        assertEquals(0,test.getTurn());
    }

    public void testSetVisible() {
        MuseumCaperState test = new MuseumCaperState();
        test.setVisible(false);
        boolean test2 = test.getIsVisible();
        assertFalse(test2);
    }

    public void testSetStolenPaintings() {
        MuseumCaperState test = new MuseumCaperState();
        test.setStolenPaintings(0);
        int stolen = test.getStolenPaintings();
        assertEquals(0,stolen);
    }

    public void testTestToString() {
        MuseumCaperState test = new MuseumCaperState();
        String string = test.toString();
        assertEquals("Turn: " + 0 + "Is Thief Visible: "
                + false + "Amount of Stolen Paintings: " + 0 +
                "\nThief Escaped: " + false +
                "\nThief Caught: " + false, string);
    }

    public void testSetTopWall() {
        MuseumCaperState test = new MuseumCaperState();
        MapTile tile = new MapTile();
        tile.setTopWall(true);
        boolean tileCheck = tile.getTopWall();
        assertTrue(tileCheck);
    }

    public void testSetLeftWall() {
        MuseumCaperState test = new MuseumCaperState();
        MapTile tile = new MapTile();
        tile.setLeftWall(true);
        boolean tileCheck = tile.getLeftWall();
        assertTrue(tileCheck);
    }
}