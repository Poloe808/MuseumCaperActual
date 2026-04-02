package edu.up.cs301.museumCaper;

import android.view.View;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A CounterMoveAction is an action that is a "move" the game: either increasing
 * or decreasing the counter value.
 *
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version September 2012
 */
public class MuseumCaperMoveAction extends GameAction {
    private int col;
    private int row;
    private boolean valid;
    // to satisfy the serializable interface
    private static final long serialVersionUID = 28062013L;

    //whether this move is a plus (true) or minus (false)


    /**
     * Constructor for the CaperMoveAction class.
     *
     * @param player
     *            the player making the move
     * @param col horizontal move value (1 moves right, -1 moves left)
     * @param row vertical move value (1 moves up, -1 moves down)
     *
     */
    public MuseumCaperMoveAction(GamePlayer player, int col, int row) {
        super(player);
        this.col = col;
        this.row = row;
    }

    /**
     * getter method, for the row and col
     *
     */
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    //public GamePlayer getGamePlayer() {return player;}


    //click button to move (left, right, up, down)
    //creates move action -- passes in who made request + direction they want to move in (sets x or y away from 0)
    //localGame receives pos of player who made move request from move action and compares to turn order (check turn)
    //check if move is valid via comparing current pos to dest pos -- is there a wall in the way
    //anticipating 1(+) out of bounds errors
    //check if move is valid -- conflicting object on dest tile
    //if valid, move piece by setting player boolean on current tile to false + dest tile to true


}//class CounterMoveAction
