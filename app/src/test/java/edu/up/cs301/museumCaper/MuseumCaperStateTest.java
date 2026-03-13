package edu.up.cs301.museumCaper;


import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class MuseumCaperStateTest extends TestCase {

    public void testGetBoard() {
        MuseumCaperState testBoard = new MuseumCaperState();
        List<List<MapTile>> board = testBoard.getBoard();
        // if it exist
        assertNotNull(board);
        // how tall
        assertEquals(13, board.get(0).size());
        // how width
        assertEquals(12,board.size());
    }

    public void testGetTurn() {
    }

    public void testGetIsVisible() {
    }

    public void testGetStolenPaintings() {
    }

    public void testSetTurn() {
        MuseumCaperState setTurn = new MuseumCaperState();

    }

    public void testSetVisible() {
    }

    public void testSetStolenPaintings() {
    }

    public void testTestToString() {
    }

    public void testSetTopWall() {
    }

    public void testSetLeftWall() {
    }

    public void testSetWalls() {
    }
}