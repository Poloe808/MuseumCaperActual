package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.utilities.Tickable;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * A Computer-version of a Museum Caper-player.
 *
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */

public class MuseumCaperComputerPlayer2 extends GameComputerPlayer implements Tickable {
	
	/*
	 * instance variables
	 */
    private MuseumCaperState state;
    private Random rng = new Random();
	
	/**
	 * constructor
	 * 
	 * @param name
	 * 		the player's name
	 */
	public MuseumCaperComputerPlayer2(String name) {
		super(name);
	}
	
    /**
     * callback method--game's state has changed
     * 
     * @param info
     * 		the information (presumably containing the game's state)
     */
	@Override
	protected void receiveInfo(GameInfo info) {
        // ignore the message if it's not a MuseumCaperState message
        if (!(info instanceof MuseumCaperState)) return;

        this.state = (MuseumCaperState) info;
        makeMove();

        Log.i("computer player", "receiving");
    }
    private void makeMove(){
        GameAction action = null;

        if(this.playerNum != state.getCurrentPlayer()){
            return;
        }
        if(state.getIsThiefTurn()){
            return;
        }

        try{
            Thread.sleep(200);
        }
        catch(Exception e){
            //do nothing
        }

        if(rng.nextInt(2) == 0){
            int dir = rng.nextInt(4);

            if (dir == 0){
                action = new MuseumCaperMoveAction(this, 0, 1);
            }
            else if (dir == 1){
                action = new MuseumCaperMoveAction(this, 0, -1);
            }
            else if (dir == 2){
                action = new MuseumCaperMoveAction(this, 1, 0);
            }
            else{
                action = new MuseumCaperMoveAction(this, -1, 0);
            }
        }
        else{
            if (rng.nextInt(1) == 0 && !state.getGuardActionUsed()){
                action = new MuseumCaperUseEyesAction(this, getGuardPosition()[1], getGuardPosition()[0]);
            }
        }

        if(state.getMoveCount() == 0 || state.getGuardActionUsed()){
            action = new MuseumCaperEndTurnAction(this);
        }

        if (action != null) {
            game.sendAction(action);
        }
    }

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