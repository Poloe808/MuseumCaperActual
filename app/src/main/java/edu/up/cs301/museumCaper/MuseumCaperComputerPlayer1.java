package edu.up.cs301.museumCaper;

import java.util.List;
import java.util.Random;

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
    // implement the algorithm BFS
    private Random random = new Random();


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
     * Returns the Player's ID
     *
     * @return playerNum
     */
    public int getPlayerID(){
        return playerNum;
    }
    
    /**
     * callback method--game's state has changed
     * 
     * @param info
     * 		the information (hypothermically containing the game's state)
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
     * Right now it's just: "When it's my turn, move down one, then end my turn"
     */
    private void makeMove(){
        if(this.playerNum != state.getCurrentPlayer()){
            return;
        }
        if(state.getIsThiefTurn()){
            return;
        }

        // we have it pick a random place in the board.
        int rowGoal = random.nextInt(12);
        int colGoal = random.nextInt(13);

        //check that its not a wall
        while (state.getBoard().get(rowGoal).get(colGoal).getLeftWall() && state.getBoard().get(rowGoal).get(colGoal).getTopWall()){
            rowGoal = random.nextInt(12);
            colGoal = random.nextInt(13);
        }
        int[] guardPos = getGuardPosition();
        // now we use the algorithm to move the guards
        int[] move = PathFinding.getMoveNext(state, guardPos[0],guardPos[1],rowGoal,colGoal);
        if(state.getMoveCount() > 0 && (move[0] != 0 || move[1] != 0)){
            game.sendAction(new MuseumCaperMoveAction(this, move[0],move[1]));
        }
        if(state.getMoveCount() == 0){
            game.sendAction(new MuseumCaperEndTurnAction(this));
        }
    }

    // create a helper function that gets the location of the guard and use it to give the algorithm of BFS
    private int[] getGuardPosition(){
        if(playerNum == 1){
            return new int[]{
                    state.getGuardOneLocation().get(1),
                    state.getGuardOneLocation().get(0)
            };
        } else if(playerNum == 2) {
            return new int[]{
                    state.getGuardTwoLocation().get(1),
                    state.getGuardTwoLocation().get(0)
            };
        } else if(playerNum == 3) {
            return new int[]{
                    state.getGuardThreeLocation().get(1),
                    state.getGuardThreeLocation().get(0)
            };
        }
        return new int[]{0,0};
    }

    // logic for the algorithm for the AI guards BFS

	
	/**
	 * callback method: the timer ticked
	 */
}
