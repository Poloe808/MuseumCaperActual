package edu.up.cs301.museumCaper;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for the Counter game. The state consist of simply
 * the value of the counter.
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version July 2013
 */

/**
 * sequence of moves to finish game quickly
 * thief enters through window in green room, moves two spaces to the nearest painting
 *      MuseumCaperMoveAction()
 *      MuseumCaperMoveAction()
 * thief steals painting
 *      MuseumCaperStealPaintingAction()
 * guard one takes turn - rolls a one on move die
 *      MuseumCaperUseEyesAction()
 *      MuseumCaperMoveAction()
 * thief moves back to the window
 *      MuseumCaperMoveAction()
 *      MuseumCaperMoveAction()
 * thief checks lock, comes back unlocked
 *      MuseumCaperCheckLockAction()
 * thief escapes
 *
 */

public class MuseumCaperState extends GameState {

    //these are statics that just make the code more readable
    private static final boolean TOP = true;
    private static final boolean LEFT = false;
	private int turn;
    private boolean isVisible;
    private int stolenPaintings;
    private int currentPlayer;     // whose turn
    //change this so the thief has a boolean (is turn) and the guards have the int
    //end of every thief turn change boolean to false, at the end of every guard turn
    //  change boolean to true, increment by one
    private int thiefPlayerId;     // which player is the thief
    private int numPlayers;
    private Camera[][] cameras;        // camera locations
    private boolean thiefEscaped;
    private boolean thiefCaught;
    private boolean thiefVisible;
    //fun fact, the board is 12 tiles long, 11 tiles tall, but with the way i'm implementing walls
    //its gonna be a 12x11 2D ArrayList
    //Changed this into a 2D Array list, that carries MapTiles for each coordinate plane
    private List<List<MapTile>> board;
    int x;
    int y;
    private boolean unlocked;
    boolean[] locks = {true,true,true,true,true,true,true,true,true,true,true};

    Point theifLoc;
    Point guardOne;
    Point guardTwo;
    Point guardThree;

    Point[] playerLocs = {theifLoc, guardOne, guardTwo, guardThree};

    /**
	 * constructor, initializing the counter value from the parameter
	 *
     */
	public MuseumCaperState() {
        turn = 0;
        isVisible = false;
        stolenPaintings = 0;

        board = new ArrayList(11);
        //set up the board and maptiles
        for(int row = 0; row < 12; row++){
            board.add(new ArrayList<>(12));
            for(int col = 0; col < 13; col++){
                board.get(row).add(new MapTile());
            }
        }

        //player location initialization
        theifLoc = new Point(0,0);
        guardOne = new Point(0,0);
        guardTwo = new Point(0,0);
        guardThree = new Point(0,0);


        //set up the walls manually (I'll double check this with pen&paper -Logan <3)

        //This represents the purple room (top left)
        setWalls(TOP, 0, 2, 2);
        setWalls(TOP, 0, 2, 5);
        setWalls(LEFT, 2, 4, 0);
        setWalls(LEFT, 2, 3, 3);

        //This represents the red room (top)
        setWalls(TOP, 3, 8, 0);
        setWalls(LEFT, 0, 1, 3);
        setWalls(LEFT, 0, 1, 9);
        setTopWall(2, 3);
        setTopWall(2, 5);
        setTopWall(2, 6);
        setTopWall(2, 8);

        //this represents the blue room (top right)
        setWalls(TOP, 9, 11, 3);
        setWalls(TOP, 9, 11, 5);
        setLeftWall(2,9);
        setLeftWall(2, 12);
        setLeftWall(3, 12);

        //this represents the green room (bottom right)
        setWalls(TOP, 9, 11, 5);
        setWalls(TOP, 9, 11, 10);
        //this one is unique as it encroaches one above it's room (just the one below dis one)
        setWalls(LEFT, 4,8, 12);
        setWalls(LEFT, 5,6,9);
        setLeftWall(9, 9);

        //this represents the white room (center) (may need modifications to make more readable)
        setWalls(LEFT, 3, 7,5);
        //open up a pooka in the set walls
        board.get(6).get(4).setLeftWall(false);
        setWalls(LEFT, 3, 7, 8);
        board.get(5).get(8).setLeftWall(false);
        setTopWall(3,4);
        setTopWall(3, 7);
        setTopWall(8,4);
        setTopWall(8, 7);

        //these represent the rooms on the bottom
        setTopWall(9,3);
        setTopWall(9,8);
        setWalls(TOP, 3,8,11);
        setWalls(LEFT, 9, 10, 3);
        setWalls(LEFT, 9,10,5);
        setWalls(LEFT, 9, 10, 7);
        setWalls(LEFT, 9, 10, 9);

        //Yellow room =)
        setWalls(TOP, 0, 2, 6);
        setWalls(TOP, 0, 2, 9);
        setWalls(LEFT, 6, 8,0);
        setLeftWall(6,3);
        setLeftWall(8,3);

        //set cameras manually
        //set locks manually
        //set paintings manually
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
        this.board = orig.board;
        this.x = orig.x;
        this.y = orig.y;
        this.locks = orig.locks;
        this.unlocked = orig.unlocked;

        this.theifLoc = new Point(theifLoc);
        this.guardOne = new Point(guardOne);
        this.guardTwo = new Point(guardTwo);
        this.guardThree = new Point(guardThree);

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
    public List<List<MapTile>> getBoard() {
        return this.board;
    }
	public int getTurn() {
        return this.turn;
	}

    public boolean getIsVisible() {
        return this.isVisible;
    }

    public int getStolenPaintings() {
        return this.stolenPaintings;
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
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

    //this is a helper method to set ONE top wall in the specified coordinates
    public void setTopWall(int row, int col){
        board.get(row).get(col).setTopWall(true);
    }

    public void setLeftWall(int row, int col){
        board.get(row).get(col).setLeftWall(true);
    }

    //This is a helper method to help set the walls in the constructor (like big rows of walls)
    //for side, true top walls, false for left walls
    //startBound for where u wanna begin, endBound for where to end
    //pos is the row/col the placing is taking place in
    //example: setWalls(true, 3, 5, 0); will place top walls in board positions (0,3), (0,4), & (0,5)
    //probably should've just split this into two methods but it too late :sob:
    public void setWalls(boolean side, int startBound, int endBound, int pos){
        //Top walls!!
        if(side){
            for(int col = startBound; col <= endBound; col++){
                setTopWall(pos, col);
            }
        }
        //Left Walls!
        else if(!side){
            for(int row = startBound; row <= endBound; row++){
                setLeftWall(row, pos);
            }
        }
    }
}
