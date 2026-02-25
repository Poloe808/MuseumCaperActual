package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A CounterMoveAction is an action that is a "move" the game: either increasing
 * or decreasing the counter value.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2012
 */
public class MuseumCaperMoveAction extends GameAction {
	private int row;
    private int col;
	// to satisfy the serializable interface
	private static final long serialVersionUID = 28062013L;

	//whether this move is a plus (true) or minus (false)

	
	/**
	 * Constructor for the CounterMoveAction class.
	 * 
	 * @param player
	 *            the player making the move
	 *
	 */
	public MuseumCaperMoveAction(GamePlayer player, int row, int col) {
		super(player);
        this.row = row;
        this.col = col;
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
}//class CounterMoveAction
