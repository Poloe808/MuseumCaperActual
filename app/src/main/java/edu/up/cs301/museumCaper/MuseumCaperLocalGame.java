package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

import android.graphics.Point;
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
            return move((MuseumCaperMoveAction)action);
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
        // I fix later - Logan
        //if(gameState.getBoard()[gameState.x][gameState.y] == '?') {
        //    gameState.setStolenPaintings(gameState.getStolenPaintings() + 1);
        //}
        if (gameState.getCurrentPlayer() == 0){
            if (gameState.getBoard().get(gameState.theifLoc.y).get(gameState.theifLoc.x).hasPainting()){
                gameState.getBoard().get(gameState.theifLoc.y).get(gameState.theifLoc.x).removePainting();
                //chjange the turn order (via setting boolean to false)
                //increment stolen paintings by 1
                gameState.setStolenPaintings(gameState.getStolenPaintings()+1);
                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }

    public boolean checkLock(GameAction action) {
        //int rand = (int)(Math.random()*2);
        //for(int i=2;i< gameState.locks.length;i++) {
        //}
        return true;
    }

    public boolean move(MuseumCaperMoveAction action) {
        //click button to move (left, right, up, down)


        //creates move action -- passes in who made request + direction they want to move in (sets x or y away from 0)
        int xDir = action.getX();
        int yDir = action.getY();

        if (gameState.getCurrentPlayer() == 0){
            Point currentPoint = gameState.playerLocs[0];
            Point destPoint = new Point(currentPoint.x + xDir, currentPoint.y + yDir);

            MapTile currentTile = gameState.getBoard().get(currentPoint.y).get(currentPoint.x);

            if(gameState.getBoard().get(destPoint.y).get(destPoint.x) == null){
                return false;
            }
            MapTile destTile = gameState.getBoard().get(destPoint.y).get(destPoint.x);

            if(xDir == -1){
                if (currentTile.getLeftWall()){
                    return false;
                }
                else{
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    gameState.playerLocs[0] = destPoint;
                }
            }
            if(xDir == 1) {
                if (destTile.getLeftWall()) {
                    return false;
                } else {
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    gameState.playerLocs[0] = destPoint;
                }
            }
            if(yDir == -1) {
                if (destTile.getTopWall()) {
                    return false;
                } else {
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    gameState.playerLocs[0] = destPoint;
                }
            }
            if(yDir == 1) {
                if (currentTile.getTopWall()) {
                    return false;
                } else {
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    gameState.playerLocs[0] = destPoint;
                }
            }
        }

        //localGame receives pos of player who made move request from move action and compares to turn order (check turn)


        //check if move is valid via comparing current pos to dest pos -- is there a wall in the way
        //anticipating 1(+) out of bounds errors
        //check if move is valid -- conflicting object on dest tile
        //if valid, move piece by setting player boolean on current tile to false + dest tile to true
        return true;
    }

    public boolean disableCamera(GameAction action) {
        return true;
    }

    public boolean useEyes(GameAction action) {
        //check in all directions until u hit a wall, then stop
        //start checking in one direction
        //if no thief, check if wall,
        //if no wall, check one above
        //keep checking until there is a thief or a wall, thren swithch craridnal direcitons
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
