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
    // we have it pick a random place in the board.
    int rowGoal = -1;
    int colGoal = -1;
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
     */
    private void makeMove(){
        if(this.playerNum != state.getCurrentPlayer()){
            return;
        }
        if(state.getIsThiefTurn()){
            return;
        }

        try{
            Thread.sleep(300);
        }
        catch(Exception e){
            //do nothing
        }

        int[] guardPos = getGuardPosition();

        // end the turn if no moves left
        if(state.getMoveCount() == 0){
            do {
                rowGoal = random.nextInt(12);
                colGoal = random.nextInt(13);
            } while ((state.getBoard().get(rowGoal).get(colGoal).getLeftWall() || state.getBoard().get(rowGoal).get(colGoal).getTopWall()));
            game.sendAction(new MuseumCaperEndTurnAction(this));
            return;
        }

        // if we already reached the goal, dont end the turn but instead give a new goal
        if(rowGoal != -1 && guardPos[0] == rowGoal && guardPos[1] == colGoal){
            rowGoal = -1;
            colGoal = -1;
        }
        // pick new goal if we've reached our previous
        if(rowGoal == -1){
            // now check that we arent crashing into walls (blocked)
            do {
                rowGoal = random.nextInt(12);
                colGoal = random.nextInt(13);
            } while ((state.getBoard().get(rowGoal).get(colGoal).getLeftWall() || state.getBoard().get(rowGoal).get(colGoal).getTopWall()));
        }


        // now we use the algorithm to move the guards
        int[] move = PathFinding.getMoveNext(state, guardPos[0], guardPos[1], rowGoal, colGoal);

        // check for null (no path found) before using move
        if(move == null){
            rowGoal = -1;
            colGoal = -1;
            game.sendAction(new MuseumCaperEndTurnAction(this));
            return; // instead of ending turn we pick a new goal on receiveInfo
        }

        if(move[0] != 0 || move[1] != 0){
            game.sendAction(new MuseumCaperMoveAction(this, move[0], move[1]));
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
}

/**
 * @author Logan Ortogero
 * @author Felipe Lucas Pablo
External Citation
Date: 4 April 2026
Problem: The guards need pathfinding algorithms! dunno what the heck is a bread first is but we need it
Referenced several websites pertaining to pathfinding, as well as the Oracle Java documentation for
           hashmaps, linked lists, as well as the queue interface.
Resources: https://www.geeksforgeeks.org/dsa/breadth-first-search-or-bfs-for-a-graph/
          https://www.redblobgames.com/pathfinding/a-star/introduction.html
          https://www.geeksforgeeks.org/dsa/shortest-path-unweighted-graph/
          https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
          https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
          https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
Solution: Utilized the information in the documentation, as well as some code snippets from
          each of the non-oracle websites in order to build up the pathfinding to what we needed.
 */