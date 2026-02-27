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
    //double check this every now and again to make sure we dont need a
    //deep copy constructor
	public MuseumCaperState(MuseumCaperState orig) {
        this.turn = orig.turn;
        this.isVisible = orig.isVisible;
        this.stolenPaintings = orig.stolenPaintings;
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
                + isVisible + "Amount of Stolen Paintings: " + stolenPaintings;

    }
}
