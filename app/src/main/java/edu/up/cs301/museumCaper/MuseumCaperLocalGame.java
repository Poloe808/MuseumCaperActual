package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

import android.graphics.Point;
import android.util.Log;

/**
 * A class that represents the state of a game.
 *
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version February 2026
 */
public class MuseumCaperLocalGame extends LocalGame {


	// the game's state
	private MuseumCaperState gameState;
	
	/**
	 * can this player move
	 * 
	 * @param playerIdx the player id of the player trying to make a move
     * @return true if player if matches current player in turn order
	 *
	 */
	@Override
	protected boolean canMove(int playerIdx) {
        if(playerIdx == gameState.getCurrentPlayer()){
            return true;
        }
        return false;
	}

    /**
     * constructor for LocalGame
     */
    public MuseumCaperLocalGame() {
        gameState = new MuseumCaperState();
    }
	/**
	 * This ctor should be called when a new counter game is started
	 */

    /**
     *
     * @param state
     */
	public MuseumCaperLocalGame(GameState state) {
		// initialize the game state, with the counter value starting at 0
		if (! (state instanceof MuseumCaperState)) {
			state = new MuseumCaperState();
		}
		this.gameState = (MuseumCaperState)state;
		super.state = state;
	}


	@Override
	protected boolean makeMove(GameAction action) {
        if(!(action instanceof GameAction)) {
            return false;
        }
        if(action instanceof MuseumCaperStealPaintingAction) {
            return gameState.stealPainting((MuseumCaperStealPaintingAction)action);
        }
        if(action instanceof MuseumCaperCheckLockAction) {
            return gameState.checkLock((MuseumCaperCheckLockAction)action);
        }
        if(action instanceof MuseumCaperMoveAction) {
            return gameState.move((MuseumCaperMoveAction)action);
        }
        if(action instanceof MuseumCaperDisableCameraAction) {
            return gameState.disableCamera((MuseumCaperDisableCameraAction)action);
        }
        if(action instanceof MuseumCaperUseEyesAction) {
            return gameState.useEyes((MuseumCaperUseEyesAction)action);
        }
        if(action instanceof MuseumCaperMotionDetectorAction) {
            return gameState.motionDetector((MuseumCaperMotionDetectorAction)action);
        }
        if(action instanceof MuseumCaperEndTurnAction) {
            return gameState.endTurn((MuseumCaperEndTurnAction)action);
        }
        return false;
	}//makeMove


	/**
	 * send the updated state to a given player
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// this is a perfect-information game, so we'll make a
		// complete copy of the state to send to the player
		p.sendInfo(new MuseumCaperState(this.gameState, getPlayerIdx(p)));
	}//sendUpdatedSate
	
	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
        boolean thiefEscape = gameState.getIsThiefEscaped();
        boolean thiefCaught = gameState.getIsThiefCaught();
        int paintingsStolen = gameState.getStolenPaintings();

        if (thiefEscape){
            if (paintingsStolen == 0){
                return "The thief has escaped.. but with no riches to their name... \nGame Over! ";
            }
            else if(paintingsStolen == 1) {
                return "The thief has escaped with a painting in tow! \nGame Over! ";
            }
            else{
                return "The thief has escaped with " + paintingsStolen + " paintings! \nGame Over!";
            }
        }

        if (thiefCaught){
            return "The thief was caught by the guards.. \nGame Over!";
        }

        return null;
	}


}// class CounterLocalGame
