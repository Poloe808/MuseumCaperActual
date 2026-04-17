package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


/**
* A computer-version of a counter-player.  Since this is such a simple game,
* it just sends "+" and "-" commands with equal probability, at an average
* rate of one per second. This computer player does, however, have an option to
* display the game as it is progressing, so if there is no human player on the
* device, this player will display a GUI that shows the value of the counter
* as the game is being played.
*
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
* @version September 2013
*/
public class MuseumCaperComputerPlayer2 extends MuseumCaperComputerPlayer1 {
	
	/*
	 * instance variables
	 */
	
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
        // perform superclass behavior
        super.receiveInfo(info);

        Log.i("computer player", "receiving");
    }
}
