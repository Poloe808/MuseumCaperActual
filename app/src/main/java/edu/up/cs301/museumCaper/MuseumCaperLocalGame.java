package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import android.util.Log;

/**
 * A class that represents the state of a game. In our counter game, the only
 * relevant piece of information is the value of the game's counter. The
 * CounterState object is therefore very simple.
 *
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version July 2013
 */
public class MuseumCaperLocalGame extends LocalGame {

	// When a counter game is played, any number of players. The first player
	// is trying to get the counter value to TARGET_MAGNITUDE; the second player,
	// if present, is trying to get the counter to -TARGET_MAGNITUDE. The
	// remaining players are neither winners nor losers, but can interfere by
	// modifying the counter.
	public static final int TARGET_MAGNITUDE = 10;

	// the game's state
	private MuseumCaperState gameState;
	
	/**
	 * can this player move
	 * 
	 * @return
	 * 		true, because all player are always allowed to move at all times,
	 * 		as this is a fully asynchronous game
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return true;
	}

    public MuseumCaperLocalGame() {
        MuseumCaperState gameState = new MuseumCaperState();
    }
	/**
	 * This ctor should be called when a new counter game is started
	 */
	public MuseumCaperLocalGame(GameState state) {
		// initialize the game state, with the counter value starting at 0
		if (! (state instanceof MuseumCaperState)) {
			state = new MuseumCaperState();
		}
		this.gameState = (MuseumCaperState)state;
		super.state = state;
	}

	/**
	 * The only type of GameAction that should be sent is CounterMoveAction
	 */
	@Override
	protected boolean makeMove(GameAction action) {
        if(!(action instanceof GameAction)) {
            return false;
        }
        if(action instanceof MuseumCaperStealPaintingAction) {
            return stealPainting(action);
        }
        if(action instanceof MuseumCaperCheckLockAction) {
            return checkLock(action);
        }
        if(action instanceof MuseumCaperMoveAction) {
            return move(action);
        }
        if(action instanceof MuseumCaperDisableCameraAction) {
            return disableCamera(action);
        }
        if(action instanceof MuseumCaperUseEyesAction) {
            return useEyes(action);
        }
        if(action instanceof MuseumCaperMotionDetectorAction) {
            return motionDetector(action);
        }
        if(action instanceof MuseumCaperEndTurnAction) {
            return endTurn(action);
        }
        return false;
	}//makeMove

    public boolean stealPainting(GameAction action) {
        if(gameState.getBoard()[gameState.x][gameState.y] == '?') {
            gameState.setStolenPaintings(gameState.getStolenPaintings() + 1);
        }
        return true;
    }

    public boolean checkLock(GameAction action) {
        int rand = (int)(Math.random()*2);
        for(int i=2;i< gameState.locks.length;i++) {
        }
        return true;
    }

    public boolean move(GameAction action) {
        return true;
    }

    public boolean disableCamera(GameAction action) {
        return true;
    }

    public boolean useEyes(GameAction action) {
        return true;
    }

    public boolean motionDetector(GameAction action) {
        return true;
    }

    public boolean endTurn(GameAction action) {
        return true;
    }
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
        return null;
	}


}// class CounterLocalGame
