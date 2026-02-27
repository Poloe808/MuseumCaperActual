package edu.up.cs301.museumCaper;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for the Counter game. The state consist of simply
 * the value of the counter.
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */


public class MuseumCaperState extends GameState {

	private int turn;
    private boolean isVisible;
    private int stolenPaintings;
    private int currentPlayer;     // whose turn
    private int thiefPlayerId;     // which player is the thief
    private int numPlayers;
    private Camera[][] cameras;        // camera locations
    private boolean thiefEscaped;
    private boolean thiefCaught;
    private boolean thiefVisible;
    private int[][] board = {{},{}};
    int x;
    int y;
    private boolean unlocked;
    boolean[] locks = {true,true,true,true,true,true,true,true,true,true,true};

    /**
	 * constructor, initializing the counter value from the parameter
	 *
     */
	public MuseumCaperState() {
        turn = 0;
        isVisible = false;
        stolenPaintings = 0;
	}
	
	/**
	 * copy constructor; makes a copy of the original object
	 * 
	 * @param orig
	 * 		the object from which the copy should be made
	 */
	public MuseumCaperState(MuseumCaperState orig, int playerID) {
        this.turn = orig.turn;
        this.isVisible = orig.isVisible;
        this.stolenPaintings = orig.stolenPaintings;
        this.currentPlayer = orig.currentPlayer;
        this.thiefPlayerId = orig.thiefPlayerId;
        this.numPlayers = orig.numPlayers;
        this.cameras = orig.cameras;
        this.thiefEscaped = orig.thiefEscaped;
        this.thiefCaught = orig.thiefCaught;
        this.thiefVisible = orig.thiefVisible;
        this.x = orig.x;
        this.y = orig.y;
        this.locks = orig.locks;
        this.unlocked = orig.unlocked;

        if(playerID == thiefPlayerId){
            // have the coordinates of the thief
        }else{
            //have it hidden from everyone else.
        }

	}

	/**
	 * getter method for the counter
	 * 
	 * @return
	 * 		the value of the counter
	 */
	public int getTurn() {
        return this.turn;
	}

    public boolean getIsVisible() {
        return this.isVisible;
    }

    public int getStolenPaintings() {
        return this.stolenPaintings;
    }
	/**
	 * setter method for the counter
	 * 
	 * @param newTurn
	 *
     * the value to which the counter should be set
	 */
	public void setTurn(int newTurn) {
        turn = newTurn;
	}

    public void setVisible(boolean visibilityCheck) {
        isVisible = visibilityCheck;
    }

    public void setStolenPaintings(int newStolenPaintings) {
        stolenPaintings = newStolenPaintings;

    }

    public String toString() {
        return "Turn: " + turn + "Is Thief Visible: "
                + isVisible + "Amount of Stolen Paintings: " + stolenPaintings +
                "\nThief Escaped: " + thiefEscaped +
                "\nThief Caught: " + thiefCaught;
    }
    public boolean stealPainting(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        if(board[x][y] == '?') {
            setStolenPaintings(getStolenPaintings() + 1);
        }
        return true;
    }

    public boolean checkLock(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        int rand = (int)(Math.random()*2);

        return true;
    }

    public boolean move(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        return true;
    }

    public boolean disableCamera(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        return true;
    }

    public boolean useEyes(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        return true;
    }

    public boolean motionDetector(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        return true;
    }

    public boolean endTurn(GameState action) {
        if(!(action instanceof GameState)) {
            return false;
        }
        return true;
    }
}
