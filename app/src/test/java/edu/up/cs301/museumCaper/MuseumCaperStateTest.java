package edu.up.cs301.museumCaper;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

public class MuseumCaperStateTest extends TestCase {

    public void testConstructor() {
        MuseumCaperState test = new MuseumCaperState();
        int turn = test.getTurn();
        assertEquals(0,turn);
    }

    public void testCopyConstructor() {
        MuseumCaperState firstInstance = new MuseumCaperState();
        MuseumCaperState firstCopy = new MuseumCaperState(firstInstance, 0);
        MuseumCaperLocalGame local = new MuseumCaperLocalGame();

        //Thief Moves from unlocked window twice towards painting
        local.move(new MuseumCaperMoveAction(local.getPlayers()[0], 1,1));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        local.move(new MuseumCaperMoveAction(local.getPlayers()[0], 1,1));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        //Steals painting
        firstCopy.setStolenPaintings(firstInstance.getStolenPaintings()+1);
        assertTrue(firstInstance.getStolenPaintings()==1);

        //Guard 1 turn
        firstCopy.setTurn(firstInstance.getTurn()+1);
        assertTrue(firstInstance.getTurn()==1);

        local.useEyes(new MuseumCaperUseEyesAction(local.getPlayers()[1]));
        assertTrue(local.useEyes(new MuseumCaperUseEyesAction(local.getPlayers()[1])));

        local.move(new MuseumCaperMoveAction(local.getPlayers()[1],1,1));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        //Thief turn again, Moves back to window twice
        local.move(new MuseumCaperMoveAction(local.getPlayers()[0], -1,-1));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        local.move(new MuseumCaperMoveAction(local.getPlayers()[0], -1,-1));
        assertTrue(local.canMove(firstInstance.getCurrentPlayer()));

        local.checkLock(new MuseumCaperCheckLockAction(local.getPlayers()[0]));
        assertTrue(local.checkLock(new MuseumCaperCheckLockAction(local.getPlayers()[0])));

        local.checkIfGameOver();
        assertTrue(local.checkIfGameOver()==null);

        MuseumCaperState secondInstance = new MuseumCaperState();
        MuseumCaperState secondCopy = new MuseumCaperState(secondInstance, 0);

        firstCopy.toString();
        secondCopy.toString();
        assertEquals( firstCopy.toString(),secondCopy.toString());
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

    public void testGetStolenPaintings() {
        MuseumCaperState test = new MuseumCaperState();
        int stolen = test.getStolenPaintings();
        assertEquals(0,stolen);
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