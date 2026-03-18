package edu.up.cs301.museumCaper;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
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
 * @version February 2026
 */

/**
 * sequence of moves to finish game quickly
 * thief enters through window in green room, moves one space to the nearest painting
 *      MuseumCaperMoveAction()
 *      MuseumCaperMoveAction()
 * thief steals painting
 *      MuseumCaperStealPaintingAction()
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
    private static final boolean LOCKED = true;
    private static final boolean UNLOCKED = false;
	private int turn;
    private boolean isVisible;
    private int stolenPaintings;
    private int currentPlayer;     // whose turn
    //change this so the thief has a boolean (is turn) and the guards have the int
    //end of every thief turn change boolean to false, at the end of every guard turn
    //  change boolean to true, increment by one
    private boolean isThiefTurn; //because thief goes every other turn
    private int thiefPlayerId;     // which player is the thief
    private int numPlayers;
    private List<Camera> cameras;        // camera locations
    private List<Painting> paintings;
    private List<Lock> locksList;
    private boolean thiefEscaped;
    private boolean thiefCaught;
    private boolean thiefVisible;
    //fun fact, the board is 12 tiles long/cols, 11 tiles tall/rows, but with the way i'm implementing walls
    //its gonna be a 12x11 2D ArrayList
    //Changed this into a 2D Array list, that carries MapTiles for each coordinate plane
    private List<List<MapTile>> board;
    int x;
    int y;
    private boolean unlocked;

    //variables for checking if the player still has moves available
    private int moveCount; //counts up how many spaces a player has moved on their turn
    private int guardMoveTotal; //the random number (1-6) that the guard "rolls" at the start of their turn

    Point thiefLoc;
    Point guardOne;
    Point guardTwo;
    Point guardThree;

    Point[] playerLocs = {thiefLoc, guardOne, guardTwo, guardThree};

    /**
	 * constructor, initializing the counter value from the parameter
	 *
     */
	public MuseumCaperState() {
        turn = 0;
        isVisible = false;
        stolenPaintings = 0;
        isThiefTurn = true;

        //how many times players are allowed to move
        moveCount = 3;

        board = new ArrayList(11);
        //set up the board and maptiles
        for(int row = 0; row < 12; row++){
            board.add(new ArrayList<>(12));
            for(int col = 0; col < 13; col++){
                board.get(row).add(new MapTile());
            }
        }

        //player location initialization
        thiefLoc = new Point(0,0);
        guardOne = new Point(0,0);
        guardTwo = new Point(0,0);
        guardThree = new Point(0,0);

        cameras = new ArrayList<Camera>();
        locksList = new ArrayList<Lock>();
        paintings = new ArrayList<Painting>();
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
        setCamera(2, 3, 1);
        setCamera(1, 7, 2);
        setCamera(4,5,3);
        setCamera(7, 1, 4);
        setCamera(7, 11,5);
        setCamera(8,8,6);
        //set locks manually
        setLocks(0,4, UNLOCKED);
        setLocks(0, 7, UNLOCKED);
        setLocks(3, 0, UNLOCKED);
        setLocks(5, 0, UNLOCKED);
        setLocks(7, 0, UNLOCKED);
        setLocks(1, 8, UNLOCKED);
        setLocks(2, 10, UNLOCKED);
        //setLocks();
        //setLocks();
        //setLocks();
        //setLocks();

        //set paintings manually
        setPainting(0,0, 1);
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
        this.isThiefTurn = orig.isThiefTurn;
        this.thiefPlayerId = orig.thiefPlayerId;
        this.numPlayers = orig.numPlayers;
        this.cameras = orig.cameras;
        this.thiefEscaped = orig.thiefEscaped;
        this.thiefCaught = orig.thiefCaught;
        this.thiefVisible = orig.thiefVisible;

        this.x = orig.x;
        this.y = orig.y;
        this.locksList = orig.locksList;
        this.unlocked = orig.unlocked;

        this.thiefLoc = new Point(thiefLoc);
        this.guardOne = new Point(guardOne);
        this.guardTwo = new Point(guardTwo);
        this.guardThree = new Point(guardThree);

        this.moveCount = orig.moveCount;
        this.guardMoveTotal = orig.guardMoveTotal;

        board = new ArrayList<>(11);
        for(int row = 0; row < board.toArray().length; row++){
        board = new ArrayList(12);
            for(int col = 0; col < orig.board.get(0).toArray().length; col++){
                this.board.get(row).add(new MapTile(orig.board.get(row).get(col)));
            }
        }
        if(orig.cameras != null){
            this.cameras = new ArrayList<Camera>(orig.cameras);
        }
        if (orig.paintings != null){
            this.paintings = new ArrayList<Painting>(orig.paintings);
        }
        if (orig.locksList != null){
            this.locksList = new ArrayList<Lock>(orig.locksList);
        }


        if(playerID == thiefPlayerId){
            // have the coordinates of the thief
            this.thiefLoc = new Point(orig.thiefLoc);
        }
	}

    //this is the constructor for the copyConstructorTest
    public MuseumCaperState(int test){
        super();
        board = new ArrayList(11);
        //set up the board and maptiles
        for(int row = 0; row < 12; row++){
            board.add(new ArrayList<>(12));
            for(int col = 0; col < 13; col++){
                board.get(row).add(new MapTile());
            }
        }
        thiefLoc = new Point(0, 3);
        locksList = new ArrayList<Lock>();
        paintings = new ArrayList<Painting>();
        setPainting(3, 1, 21);
        setLocks(0,3, UNLOCKED);
    }

    /**
     * lock method so that we can have the locks randomly be set to lock or unlock
     * but also preventing them from all being lock or unlock
     */
    private void startOfLocks() {
        locksList = new ArrayList<>();
        Random rand = new Random();
        // have a for loop to gurantee 3 of them to be unlock
        for(int randLock = 3; randLock <=3; randLock++){
            locksList.add(new Lock(true));
        }
        // a for loop to make sure that at least two of them are lock
        for(int randLock = 2; randLock <=2; randLock++){
            locksList.add(new Lock(false));
        }
        // another loop to randomize the rest of the locks for a total of 11
        for(int randLock = 5; randLock <=11; randLock++){
            locksList.add(new Lock(rand.nextBoolean()));
        }
    }

    public List<List<MapTile>> getBoard() {
        return this.board;
    }
	public int getTurn() {
        return this.turn;
	}

    public boolean getThiefTurn() {
        return this.isThiefTurn;
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

    public Point getThiefLoc(){
        return this.thiefLoc;
    }

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

    public void setIsThiefTurn(){
        isThiefTurn = !(isThiefTurn);
    }

    public void setThiefLoc(int row, int col){
        thiefLoc.x = col;
        thiefLoc.y = row;
    }

    public void setCamera(int row, int col, int number){
        Camera c = new Camera(number);
        cameras.add(c);
        board.get(row).get(col).setCamera(c);
    }
    public void setPainting(int row, int col, int number){
        Painting p = new Painting(number);
        paintings.add(p);
        board.get(row).get(col).setHasPainting(p);
    }

    public void setLocks(int row, int col, boolean locked){
        Lock l = new Lock(locked);
        locksList.add(l);
        board.get(row).get(col).setLock(l);
    }

    //these are the action methods
    public boolean stealPainting(GameAction action) {

        MapTile mt = getBoard().get(thiefLoc.y).get(thiefLoc.x);
        if (getThiefTurn()){
            if (mt.hasPainting()){
                mt.removePainting();
                //change the turn order (via setting boolean to false) and incrementing turn order
                setIsThiefTurn();
                setTurn(getTurn()+1);
                //increment stolen paintings by 1
                setStolenPaintings(getStolenPaintings()+1);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //These r the start of the action methods

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

        //if the player is the thief (the human player) and they have available moves left
        if (getCurrentPlayer() == 0 && moveCount > 0 && moveCount <= 3){
            Point currentPoint = playerLocs[0];
            Point destPoint = new Point(currentPoint.x + xDir, currentPoint.y + yDir);

            MapTile currentTile = getBoard().get(currentPoint.y).get(currentPoint.x);

            if(getBoard().get(destPoint.y).get(destPoint.x) == null){
                return false;
            }
            MapTile destTile = getBoard().get(destPoint.y).get(destPoint.x);

            if(xDir == -1){
                if (currentTile.getLeftWall()){
                    return false;
                }
                else{
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    playerLocs[0] = destPoint;
                }
            }
            if(xDir == 1) {
                if (destTile.getLeftWall()) {
                    return false;
                } else {
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    playerLocs[0] = destPoint;
                }
            }
            if(yDir == -1) {
                if (destTile.getTopWall()) {
                    return false;
                } else {
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    playerLocs[0] = destPoint;
                }
            }
            if(yDir == 1) {
                if (currentTile.getTopWall()) {
                    return false;
                } else {
                    currentTile.setThief(false);
                    destTile.setThief(true);
                    playerLocs[0] = destPoint;
                }
            }
            //reduce thief's move total by one
            moveCount--;
        }

        //localGame receives pos of player who made move request from move action and compares to turn order (check turn)
        //check if move is valid via comparing current pos to dest pos -- is there a wall in the way
        //anticipating 1(+) out of bounds errors
        //check if move is valid -- conflicting object on dest tile
        //if valid, move piece by setting player boolean on current tile to false + dest tile to true
        return true;
    }

    public boolean disableCamera(GameAction action) {
        //check if it's thief's turn
        if((getCurrentPlayer() == 0)){
            //get tile the thief is on
            Point currentPoint = playerLocs[0];
            MapTile currentTile = getBoard().get(currentPoint.y).get(currentPoint.x);
            //check if that tile has a camera
            if(currentTile.hasCamera()){
                //turn off the camera on that tile - removing it from the board
                currentTile.removeCamera();
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean useEyes(GameAction action) {
        //get current position of the guard
        int tempLoc = currentPlayer;
        Point currentPoint = playerLocs[tempLoc];

        //check in all directions until u hit a wall, then stop
        //start checking in one direction
        //if no thief, check if wall,
        for(int i = 0; i <= 4; i++){
            MapTile currentTile = getBoard().get(currentPoint.y).get(currentPoint.x);
            //tiles for checking if wall is below/to the right
            MapTile bottomTile = getBoard().get(currentPoint.y-1).get(currentPoint.x);
            MapTile rightTile = getBoard().get(currentPoint.y).get(currentPoint.x+1);
            for(int a = 0; a < 12; a++){ //todo change hardcoded 12 to a variable ?
                if(currentTile.getThief()){
                    return true;
                }
                //check if the tile has a wall in the way
                else if(i == 1 && !(currentTile.getTopWall())){
                    //move to the next tile
                    currentTile = getBoard().get(currentPoint.y+a).get(currentPoint.x);
                }
                //keep checking until there is a thief or a wall, then switch cardinal directions
                else if(i == 2 && !(rightTile.getLeftWall())){
                    currentTile = getBoard().get(currentPoint.y).get(currentPoint.x+a);
                }
                else if(i == 3 &&  !(currentTile.getTopWall())){
                    currentTile = getBoard().get(currentPoint.y-a).get(currentPoint.x);
                }
                else if(i == 4 &&  !(bottomTile.getTopWall())){
                    currentTile = getBoard().get(currentPoint.y).get(currentPoint.x-a);
                }
            }
        }
        return false; //thief isn't able to be seen
    }

    public boolean motionDetector(GameAction action) {
        //get color of tile thief is currently on
        //route guards towards that room for two turns?
        //display smth to tell the player what happened
        return true;
    }

    public boolean endTurn(GameAction action) {
        //increment turn order
        //make thief turn opposite
        //reset thief's number of tiles they can move if it was a guard's turn
        if(currentPlayer != 0){
            moveCount = 3;
        }
        return true;
    }
}
