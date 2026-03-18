package edu.up.cs301.museumCaper;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;
import java.util.Map;

public class MuseumCaperStateTest extends TestCase {

    @Test
    public void testConstructor() {
        MuseumCaperState test = new MuseumCaperState();
        int turn = test.getTurn();
        assertEquals(0,turn);
    }


    @Test
    public void testCopyConstructor() {
        MuseumCaperState firstInstance = new MuseumCaperState(0);
        MuseumCaperState firstCopy = new MuseumCaperState(firstInstance, 0);
        MuseumCaperLocalGame local = new MuseumCaperLocalGame();

        //Thief Moves from window towards painting
        firstInstance.move(new MuseumCaperMoveAction(local.getPlayers()[0], 1,0));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        //Steals painting
        firstCopy.setStolenPaintings(firstInstance.getStolenPaintings()+1);
        assertTrue(firstInstance.getStolenPaintings()==1);

        //Thief Moves from painting towards window
        firstInstance.move(new MuseumCaperMoveAction(local.getPlayers()[0], -1,0));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        //Thief checks lock to escape
        firstInstance.checkLock(new MuseumCaperCheckLockAction(local.getPlayers()[0]));
        assertTrue(firstInstance.checkLock(new MuseumCaperCheckLockAction(local.getPlayers()[0])));

        //Thief ends turn
        local.checkIfGameOver();
        assertTrue(local.checkIfGameOver()==null);

        MuseumCaperState secondInstance = new MuseumCaperState(0);
        MuseumCaperState secondCopy = new MuseumCaperState(secondInstance, 0);

        assertEquals(firstCopy.toString(),secondCopy.toString());
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

    @Test
    public void testMuseumCaperStealPaintingAction() {
        MuseumCaperState state = new MuseumCaperState();
        MapTile testTile = state.getBoard().get(0).get(0);
        testTile.setHasPainting(new Painting(7));
        state.setThiefLoc(0, 0);

        //Test to make sure the painting is correctly "placed" onto the tile
        assertTrue(testTile.hasPainting());

        //Create the steal action, If steal action is called when the thief is on the same tile as a painting, it should go through
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

    public void testSetWalls() {
    }
}