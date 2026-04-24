package edu.up.cs301.museumCaper;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameState;


/**
 * This contains the state for Museum Caper. Creates board, sets positions,
 * action's, and deep copy cntr's. Game's current data.
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version February 2026
 */


public class MuseumCaperState extends GameState {

    //these are statics that just make the code more readable
    //TOP and LEFT are used for the wall setters
    private static final boolean TOP = true;
    private static final boolean LEFT = false;
    //LOCKED and UNLOCKED are used for the lock helper methods
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
    private int thiefPlayerId;     // which player is the thief (it'll be whoever the human player is.) TODO
    private int guardOneId = -1;
    private int guardTwoId = -1;
    private int guardThreeId = -1;
    private int numPlayers;
    private List<Camera> cameras;        // camera locations
    private List<Painting> paintings;
    private List<Lock> locksList;
    //these variables r redundant im removing them
    private boolean thiefEscaped;
    private boolean thiefCaught;
    private boolean thiefVisible;
    //fun fact, the board is 12 tiles long/cols, 11 tiles tall/rows, but with the way i'm
    //    implementing walls
    //its gonna be a 12x11 2D ArrayList
    //Changed this into a 2D Array list, that carries MapTiles for each coordinate plane
    private List<List<MapTile>> board;
    int x;
    int y;
    private boolean unlocked;

    //variables for checking if the player still has moves available
    private int moveCount; //how many moves the player whose turn it is has left

    //may not be necessary but am keeping this for now TODO
    private int guardMoveTotal; //the random number (1-6) that the guard "rolls" at the start of their turnprivate int guardMoveTotal; //the random number (1-6) that the guard "rolls" at the start of their turn

    private List<Integer> thiefLocation;
    private List<Integer> guardOneLocation;
    private List<Integer> guardTwoLocation;
    private List<Integer> guardThreeLocation;
    private int guardAction;
    private boolean guardActionUsed;


    /**
	 * constructor, initializes ALL variables, sets up board
     */
	public MuseumCaperState() {
        turn = 0;
        isVisible = false;
        thiefVisible = false;
        stolenPaintings = 0;
        isThiefTurn = true;
        thiefEscaped = false;
        thiefCaught = false;

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
        thiefLocation = new ArrayList<Integer>(2);
        thiefLocation.add(0);
        thiefLocation.add(3);
        //Guard Locations r static for now <3
        guardOneLocation = new ArrayList<Integer>(2);
        guardOneLocation.add(8);
        guardOneLocation.add(2);

        guardTwoLocation = new ArrayList<Integer>(2);
        guardTwoLocation.add(6);
        guardTwoLocation.add(5);

        guardThreeLocation = new ArrayList<Integer>(2);
        guardThreeLocation.add(4);
        guardThreeLocation.add(9);

        //The thief player always goes first!
        currentPlayer = 0;

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
        setWalls(TOP, 9, 11, 2);
        setWalls(TOP, 9, 11, 4);
        setLeftWall(2,9);
        setLeftWall(2, 12);
        setLeftWall(3, 12);

        //this represents the green room (bottom right)
        setWalls(TOP, 9, 11, 5);
        setWalls(TOP, 9, 11, 9);
        //this one is unique as it encroaches one above it's room (just the one below dis one)
        setWalls(LEFT, 4,8, 12);
        setWalls(LEFT, 5,6,9);
        setLeftWall(8, 9);

        //this represents the white room (center) (may need modifications to make more readable)
        setWalls(LEFT, 3, 7,4);
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
        setCamera(6, 11,5);
        setCamera(8,8,6);

        //set locks manually
        setLocks(0,4, UNLOCKED);
        setLocks(0, 7, UNLOCKED);
        setLocks(3, 0, UNLOCKED);
        setLocks(5, 0, UNLOCKED);
        setLocks(7, 0, UNLOCKED);
        setLocks(8, 1, UNLOCKED);
        setLocks(2, 10, UNLOCKED);
        setLocks(4,11, UNLOCKED);
        setLocks(7, 11 , UNLOCKED);
        setLocks(10,5,UNLOCKED);
        setLocks(10,6, UNLOCKED);

        //now RANDOMIZE THEM!!!!!!!!!
        randomizeLocks();

        //set paintings manually
        setPainting(0, 6,1);
        setPainting(1, 3,2);
        setPainting(2, 9,3);
        setPainting(3, 1,4);
        setPainting(3, 4,5);
        setPainting(5, 11,6);
        setPainting(6, 6,7);
        setPainting(8, 0,8);
        setPainting(8, 11,9);
	}

	/**
	 * copy constructor; makes a copy of the original object
	 * 
	 * @param orig
	 * 		the object from which the copy should be made
     * @param playerID the player the copy of the gameState is being sent to
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

        this.moveCount = orig.moveCount;
        this.guardMoveTotal = orig.guardMoveTotal;

        board = new ArrayList(11);
        for(int row = 0; row < orig.board.toArray().length; row++){
            this.board.add(new ArrayList<>(12));
            for(int col = 0; col < orig.board.get(0).toArray().length; col++){
                this.board.get(row).add(new MapTile(orig.board.get(row).get(col)));
            }
        }

        thiefLocation = new ArrayList<Integer>(2);
        thiefLocation.add(orig.thiefLocation.get(0));
        thiefLocation.add(orig.thiefLocation.get(1));

        guardOneLocation = new ArrayList<Integer>(2);
        guardOneLocation.add(orig.guardOneLocation.get(0));
        guardOneLocation.add(orig.guardOneLocation.get(1));

        guardTwoLocation = new ArrayList<Integer>(2);
        guardTwoLocation.add(orig.guardTwoLocation.get(0));
        guardTwoLocation.add(orig.guardTwoLocation.get(1));

        guardThreeLocation = new ArrayList<Integer>(2);
        guardThreeLocation.add(orig.guardThreeLocation.get(0));
        guardThreeLocation.add(orig.guardThreeLocation.get(1));

        if(orig.cameras != null){
            this.cameras = new ArrayList<Camera>(orig.cameras);
        }
        if (orig.paintings != null){
            this.paintings = new ArrayList<Painting>(orig.paintings);
        }
        if (orig.locksList != null){
            this.locksList = new ArrayList<Lock>(orig.locksList);
        }

    //TODO: pls fix <3
        if(playerID == thiefPlayerId){
            // have the coordinates of the thief
        }
	}

    // ===============================================================================================================
    // For the most part, these constructors are for the JUnit tests. you can, for the most part, keep them collapsed.
    /**
     * constructor for the copyConstructorTest
     * @param test the gameState being tested
     */
    public MuseumCaperState(int test){
        thiefEscaped = false;
        board = new ArrayList(11);
        //set up the board and maptiles
        for(int row = 0; row < 12; row++){
            board.add(new ArrayList<>(12));
            for(int col = 0; col < 13; col++){
                board.get(row).add(new MapTile());
            }
        }
        thiefLocation = new ArrayList<Integer>(2);
        thiefLocation.add(0);
        thiefLocation.add(3);
        board.get(3).get(0).setThief(true);
        currentPlayer = getCurrentPlayer();
        locksList = new ArrayList<Lock>();
        paintings = new ArrayList<Painting>();
        setPainting(3, 1, 21);
        setLocks(3,0, UNLOCKED);
        moveCount = 3;
        isThiefTurn = true;
        guardOneLocation = new ArrayList<Integer>(2);
        guardOneLocation.add(8);
        guardOneLocation.add(2);

        guardTwoLocation = new ArrayList<Integer>(2);
        guardTwoLocation.add(6);
        guardTwoLocation.add(5);

        guardThreeLocation = new ArrayList<Integer>(2);
        guardThreeLocation.add(4);
        guardThreeLocation.add(9);
    }
    public MuseumCaperState(String moveActionTest){
        board = new ArrayList(11);
        //set up the board and maptiles
        for(int row = 0; row < 12; row++){
            board.add(new ArrayList<>(12));
            for(int col = 0; col < 13; col++){
                board.get(row).add(new MapTile());
            }
        }

        thiefLocation = new ArrayList<Integer>(2);
        thiefLocation.add(0);
        thiefLocation.add(0);

        board.get(1).get(1).setTopWall(true);
        board.get(1).get(1).setLeftWall(true);
        board.get(2).get(1).setTopWall(true);
        board.get(1).get(2).setLeftWall(true);
        moveCount = 5;
    }
    // ====================================== End of test constructors ==============================================
    // ==============================================================================================================

    /**
     * lock method so that we can have the locks randomly be set to lock or unlock
     * but also preventing them from all being lock or unlock
     */
    private void randomizeLocks() {
        List<Integer> rList = new ArrayList<>();

        for(int i = 0; i < 11; i++){
            rList.add(i);
        }

        Random rand = new Random();

        //TODO: could make helper methods here lol
        if (locksList != null) {
            // have a for loop to guarantee 3 of them to be unlock
            for(int i = 0; i < 3; i++){
                int chosenLock = rand.nextInt(rList.size());
                rList.remove(chosenLock);
                locksList.get(chosenLock).setUnlocked(true);
            }

            // a for loop to make sure that at least three of them are locked
            for (int i = 0; i < 3; i++){
                int chosenLock = rand.nextInt(rList.size());
                rList.remove(chosenLock);
                locksList.get(chosenLock).setUnlocked(false);
            }

            // another loop to randomize the rest of the locks for a total of 11
            for(Integer i : rList){
                boolean result = (rand.nextInt(2) == 0);
                locksList.get(i).setUnlocked(result);

            }
        }
    }

    //==============================================================================================
    //================================ START OF THE GETTERS ========================================
    public List<List<MapTile>> getBoard() {
        return this.board;
    }
	public int getTurn() {
        return this.turn;
	}

    public boolean getThiefTurn() {
        return this.isThiefTurn;
    }
    public boolean getIsThiefEscaped(){return thiefEscaped;}
    public boolean getIsThiefCaught(){return thiefCaught;}
    public boolean getIsVisible() {
        return this.isVisible;
    }
    public boolean getThiefVisible(){return thiefVisible;}

    public int getStolenPaintings() {
        return this.stolenPaintings;
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getMoveCount(){return this.moveCount;}

    public List<Integer> getThiefLocation(){
        return thiefLocation;
    }
    public List<Integer> getGuardOneLocation(){
        return guardOneLocation;
    }
    public List<Integer> getGuardTwoLocation(){return guardTwoLocation;}
    public List<Integer> getGuardThreeLocation(){return guardThreeLocation;}
    public boolean getIsThiefTurn(){return isThiefTurn;}
	public void setTurn(int newTurn) {
        turn = newTurn;
	}
    public int getGuardOneId(){return guardOneId;}
    public int getGuardTwoId(){return guardTwoId;}
    public int getGuardThreeId(){return guardThreeId;}
    public int getGuardAction(){return guardAction;}
    public boolean getGuardActionUsed(){return guardActionUsed;}
    public List<Painting> getPaintings(){return paintings;}
    public List<Lock> getLocksList(){return locksList;}
    public List<Camera> getCameras(){return cameras;}

    //==============================================================================================
    //====================== END OF THE GETTERS AND START OF THE SETTERS ===========================
    //==============================================================================================
    public void setVisible(boolean visibilityCheck) {
        isVisible = visibilityCheck;
    }

    public void setStolenPaintings(int newStolenPaintings) {
        stolenPaintings = newStolenPaintings;

    }
    /**
     * toString method - describes the most important parts of the state of the game in a string!
     * What turn it is,
     * If the thief is visible,
     * How many paintings have been stolen thus far,
     * And if the thief's escaped or if they've been caught
     */
    public String toString() {
        return "Turn: " + turn + "Is Thief Visible: "
                + isVisible + "Amount of Stolen Paintings: " + stolenPaintings +
                "\nThief Escaped: " + thiefEscaped +
                "\nThief Caught: " + thiefCaught;
    }

    /**
     * This is a helper method to help set ONE top wall in the specified coordinates
     * @param row the row (or y-position) the wall is placed
     * @param col the col (or x-position) the wall is placed
     */
    public void setTopWall(int row, int col){
        board.get(row).get(col).setTopWall(true);
    }

    /**
     * This is a helper method to help set ONE *LEFT* wall in the specified coordinates
     * @param row the row (or y-position) the wall is placed
     * @param col the col (or x-position) the wall is placed
     */
    public void setLeftWall(int row, int col){
        board.get(row).get(col).setLeftWall(true);
    }


    /**
     * This is a helper method to help set the walls in the constructor (like big rows of walls)
     *
     * @param side true for top walls, false for left walls
     * @param startBound where the walls begin
     * @param endBound where the walls end
     * @param pos the row/col the walls are placed in
     *
     *  example: setWalls(true, 3, 5, 0); will place top walls in board positions (0,3), (0,4), & (0,5)
     *  probably should've just split this into two methods but it too late :sob:
     */
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

    public void setIsThiefTurn(boolean thiefTurn){
        isThiefTurn = thiefTurn;
    }

    public void setThiefLocation(int row, int col){
        thiefLocation.set(0, col);
        thiefLocation.set(1, row);
    }
    public void setThiefVisible(boolean visibility){thiefVisible = false;}

    public void setCamera(int row, int col, int number){
        Camera c = new Camera(number);
        cameras.add(c);
        board.get(row).get(col).setCamera(c);
        c.setCol(col);
        c.setRow(row);
    }
    public void setPainting(int row, int col, int number){
        Painting p = new Painting(number);
        paintings.add(p);
        board.get(row).get(col).setHasPainting(p);
        p.setCol(col);
        p.setRow(row);
    }

    public void setLocks(int row, int col, boolean locked){
        Lock l = new Lock(locked);
        locksList.add(l);
        board.get(row).get(col).setLock(l);
        l.setCol(col);
        l.setRow(row);
    }

    public void setThiefPlayerId(int id){thiefPlayerId = id;}
    public void setGuardOneId(int id){guardOneId = id;}
    public void setGuardTwoId(int id){guardTwoId = id;}
    public void setGuardThreeId(int id){guardThreeId = id;}
    public void setGuardUsed(boolean actionUsed){guardActionUsed = actionUsed;}

    // ==================================== END OF THE SETTERS =====================================
    //==============================================================================================

    //==============================================================================================
    // ===================================== START OF ACTIONS ======================================
    /**
     * steal painting action, removes painting from the tile
     * @param action
     * @return true if the painting is successfully stolen, false if not
     */
    public boolean stealPainting(GameAction action) {

        MapTile mt = getBoard().get(thiefLocation.get(1)).get(thiefLocation.get(0));
        if (getThiefTurn()){
            if (mt.hasPainting()){
                mt.getHasPainting().setStolen();
                mt.removePainting();
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

    /**
     * check lock action, tells thief if the lock is locked/unlocked
     * @param action
     * @return true if lock is unlocked, false if locked
     * TODO: right now, no message displays when the lock is locked, so is looks like the button isn't working
     */
    public boolean checkLock(GameAction action) {
        MapTile currentTile = board.get(thiefLocation.get(1)).get(thiefLocation.get(0));
        if (currentTile.hasLock()){
            if (currentTile.getLock().getLockValue()){
                thiefEscaped = true;
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * move action
     * @param action
     * @return true if move is valid
     *
     * TODO:
     * IMPORTANT TO NOTE: This does NOT support the human player being in any position in
     * the configuation screen. Please put the human player as the first slot thx
     */
    public boolean move(MuseumCaperMoveAction action) {
        //click button to move (left, right, up, down)

        //creates move action -- passes in who made request + direction they want to move in
        //  (sets x or y away from 0)
        int colDir = action.getCol();
        int rowDir = action.getRow();

        //if the player is the thief (the human player) and they have available moves left
        if (currentPlayer == 0 && moveCount > 0){
            if (movement(thiefLocation, colDir, rowDir)){
                moveCount--;
                return true;
            }
            return false;
        }
        //guard one
        else if(currentPlayer == 1 && moveCount > 0){
            if(movement(guardOneLocation, colDir, rowDir)){
                moveCount--;
                return true;
            }
            return false;
        }
        //guard two
        else if(currentPlayer == 2 && moveCount > 0){
            if(movement(guardTwoLocation, colDir, rowDir)){
                moveCount--;
                return true;
            }
            return false;
        }
        //guard three
        else if(currentPlayer == 3 && moveCount > 0) {
            if (movement(guardThreeLocation, colDir, rowDir)) {
                moveCount--;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * move action
     * @param location
     *      the location of the person who is moving
     * @param colDir
     *      the direction the player is moving in the x-direction
     * @param rowDir
     *      the direction the player is moving in the y-direction
     * @return true if move is valid
     */
    private boolean movement(List<Integer> location, int colDir, int rowDir){
        int destPointCol = location.get(0) + colDir;
        int destPointRow =  location.get(1) - rowDir;
        MapTile currentTile = getBoard().get(location.get(1)).get(location.get(0));

        if(destPointRow < 0 || destPointCol < 0){
            return false;
        }
        MapTile destTile = getBoard().get(destPointRow).get(destPointCol);

        if(colDir == -1){
            if (currentTile.getLeftWall()){
                return false;
            }
            else{
                currentTile.setThief(false);
                destTile.setThief(true);
                location.set(0, destPointCol);
                location.set(1, destPointRow);
            }
        }
        if(colDir == 1) {
            if (destTile.getLeftWall()) {
                return false;
            } else {
                currentTile.setThief(false);
                destTile.setThief(true);
                location.set(0, destPointCol);
                location.set(1, destPointRow);
            }
        }
        if(rowDir == -1) {
            if (destTile.getTopWall()) {
                return false;
            } else {
                currentTile.setThief(false);
                destTile.setThief(true);
                location.set(0, destPointCol);
                location.set(1, destPointRow);
            }
        }
        if(rowDir == 1) {
            if (currentTile.getTopWall()) {
                return false;
            } else {
                currentTile.setThief(false);
                destTile.setThief(true);
                location.set(0, destPointCol);
                location.set(1, destPointRow);
            }
        }
        return true;
    }

    /**
     * disable camera action
     * @param action
     * @return true if camera is successfully disabled
     */
    public boolean disableCamera(GameAction action) {
        MapTile mt = getBoard().get(thiefLocation.get(1)).get(thiefLocation.get(0));
        if (getThiefTurn()){
            if (mt.hasCamera() && mt.getCamera().toString() == "Working"){
                mt.getCamera().disableCamera();
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

    /**
     * use eyes action for guard ai
     * @param action
     * @return true if thief is seen
     */
    public boolean useEyes(MuseumCaperUseEyesAction action) {
        //current position of the guard is embedded in the action
        int guardCol = action.getCol();
        int guardRow = action.getRow();
        guardActionUsed = true;
        for(int dir = 0; dir < 4; dir++){
            int checkingCol = guardCol;
            int checkingRow = guardRow;
            boolean wallInTheWay = false;

            //checking all the tiles to the left til we hit a wall
            if (dir == 0){
                while(wallInTheWay == false && thiefVisible == false){
                    if(checkingCol < 0){
                        break;
                    }
                    if(board.get(checkingRow).get(checkingCol).getLeftWall()){
                        wallInTheWay = true;
                    }
                    else{
                        checkingCol -= 1;
                        if(getThiefLocation().get(0) == checkingCol && getThiefLocation().get(1) == checkingRow){
                            thiefVisible = true;
                            return true;
                        }
                    }
                }
            }
            //checking all the tiles to the right til we hit a wall
            if (dir == 1){
                while(wallInTheWay == false && thiefVisible == false){
                    if (checkingCol >= 13){
                        break;
                    }
                    if(board.get(checkingRow).get(checkingCol + 1).getLeftWall()){
                        wallInTheWay = true;
                    }
                    else{
                        checkingCol += 1;
                        if(getThiefLocation().get(0) == checkingCol && getThiefLocation().get(1) == checkingRow){
                            thiefVisible = true;
                            return true;
                        }
                    }
                }
            }
            //checking all the tiles down
            if (dir == 2){
                while(wallInTheWay == false && thiefVisible == false){
                    if (checkingRow > 11){
                        break;
                    }
                    if(board.get(checkingRow + 1).get(checkingCol).getTopWall()){
                        wallInTheWay = true;
                    }
                    else{
                        checkingRow += 1;
                        if(getThiefLocation().get(0) == checkingCol && getThiefLocation().get(1) == checkingRow){
                            thiefVisible = true;
                            return true;
                        }
                    }
                }
            }
            //checking all the tiles up
            if (dir == 3){
                while(wallInTheWay == false && thiefVisible == false){
                    if (checkingRow < 0){
                        break;
                    }
                    if(board.get(checkingRow).get(checkingCol).getTopWall()){
                        wallInTheWay = true;
                    }
                    else{
                        checkingRow -= 1;
                        if(getThiefLocation().get(0) == checkingCol && getThiefLocation().get(1) == checkingRow){
                            thiefVisible = true;
                            return true;
                        }
                    }
                }
            }
        }

        //thief isn't able to be seen
        return false;
    }
    public boolean useCamera(MuseumCaperUseCameraAction action){
        MapTile targetTile = null;
        int targetCol = 0;
        int targetRow = 0;
        for(int row = 0; row < 12; row++){
            for(int col = 0; col < 13; col++){
                if (board.get(row).get(col).getCamera().getCameraNum() == action.getCameraNumber()){
                    targetCol = col;
                    targetRow = row;
                    targetTile = board.get(row).get(col);
                }
            }
        }
        if (targetTile == null){
            return false;
        }
        else{
            return useEyes(new MuseumCaperUseEyesAction(null, targetCol, targetRow));
        }
    }

    /**
     * helper method for useEyes
     * @param action
     * @return
     */

    /**
     * motion detector action for guard ai
     * @param action
     * @return
     */
    public boolean motionDetector(GameAction action) {
        //get color of tile thief is currently on
        //route guards towards that room for two turns?
        //display smth to tell the player what happened
        return true;
    }

    /**
     * end turn action - use to move game to next player's turn
     * @param action
     * @return
     */
    public boolean endTurn(GameAction action) {
        //increment turn order
        //make thief turn opposite
        //reset thief's number of tiles they can move if it was a guard's turn
        if(!getIsThiefTurn()){
            moveCount = 3;
            currentPlayer = 0;
        }
        else{
            Random rng = new Random();
            currentPlayer = (getTurn() % 3) + 1;
            moveCount = rng.nextInt(6) + 1;
            guardAction = rng.nextInt(2);
            guardActionUsed = false;

            setTurn(getTurn()+1);
        }
        setIsThiefTurn(!getIsThiefTurn());

        //TODO: will need to change if has less guards
        checkIfOccupyingSameSpot(thiefLocation, guardOneLocation);
        checkIfOccupyingSameSpot(thiefLocation, guardTwoLocation);
        checkIfOccupyingSameSpot(thiefLocation, guardThreeLocation);

        return true;
    }


    /**
     * check if the guard is occupying the same location as thief (thus ending the game)
     * @param thief thief's current location
     * @param guard guard's current location
     */
    private void checkIfOccupyingSameSpot(List<Integer> thief, List<Integer> guard){
        if(thief.get(0) == guard.get(0) && thief.get(1) == guard.get(1)){
            thiefCaught = true;
        }
    }

    //deez r the rolling actions for the guards
    public boolean rollActionDie(GameAction action){
        Random rng = new Random();
        int dieAction = (rng.nextInt(6)+1);
        return true;
    }

    public boolean rollMovementDie(GameAction action) {
        Random rng = new Random();
        moveCount = (rng.nextInt(6)) + 1;
        return true;
    }
}

/**
 * @author Logan Ortogero
 External Citation
 Date: 29 March 2026
 Problem: The Point class is bad and finicky so I replaced them with arrayLists.
 Referenced the android JDK a lot during this process for methods within the class.
 Resource: https://developer.android.com/reference/java/util/ArrayList
 Solution: Utilized the information in the documentation, such as how to use the method get(int x) and such.
 */

