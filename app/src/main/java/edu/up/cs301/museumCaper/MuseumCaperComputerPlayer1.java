package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

/**
 * A computer-version of a museumCaper-player. Head empty
 *
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version September 2013
 */
public class MuseumCaperComputerPlayer1 extends GameComputerPlayer implements Tickable {

    private MuseumCaperState state;

    /**
     * Constructor for objects of class CounterComputerPlayer1
     * 
     * @param name
     * 		the player's name
     */
    public MuseumCaperComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name);
    }
    
    /**
     * callback method--game's state has changed
     * 
     * @param info
     * 		the information (presumably containing the game's state)
     */
	@Override
	protected void receiveInfo(GameInfo info) {
        // ignore the message if it's not a MuseumCaperState message
        if (!(info instanceof MuseumCaperState)) return;

        this.state = (MuseumCaperState) info;
        makeMove();
	}

    /**
     * the logic for the computerPlayer when it is their turn to act
     */
    private void makeMove(){
        if ( (this.playerNum == (state.getTurn() % 3)) && !state.getIsThiefTurn()){
            game.sendAction(new MuseumCaperMoveAction(this, 0, -1));
        }
        game.sendAction(new MuseumCaperEndTurnAction(this));
    }
	
	/**
	 * callback method: the timer ticked
	 */
}
