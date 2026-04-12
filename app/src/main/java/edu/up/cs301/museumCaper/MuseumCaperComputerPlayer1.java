package edu.up.cs301.museumCaper;

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
        if ( (this.playerNum == state.getCurrentPlayer()) && !state.getIsThiefTurn()){
            Random rng = new Random();

            if(state.getMoveCount() > 0){
                int moveDirection = rng.nextInt(4) + 1;
                if(moveDirection == 1){
                    game.sendAction(new MuseumCaperMoveAction(this, 1, 0));
                }
                else if(moveDirection == 2){
                    game.sendAction(new MuseumCaperMoveAction(this, -1, 0));
                }
                else if(moveDirection == 3){
                    game.sendAction(new MuseumCaperMoveAction(this, 0, 1));
                }
                else if(moveDirection == 4){
                    game.sendAction(new MuseumCaperMoveAction(this, 0, -1));
                }

                try{
                    Thread.sleep(250);
                }
                catch(Exception e) {
                    //do nothign <3
                }
            }
            else{
                game.sendAction(new MuseumCaperEndTurnAction(this));
            }
        }
    }
	
	/**
	 * callback method: the timer ticked
	 */
}
