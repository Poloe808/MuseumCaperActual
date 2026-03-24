package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 *
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version February 2026
 */
public class MuseumCaperHumanPlayer extends GameHumanPlayer implements OnClickListener {

    /* instance variables */

    // The TextView the displays the current counter value
    private TextView counterValueTextView;

    // the most recent game state, as given to us by the CounterLocalGame
    private MuseumCaperState state;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor
     *
     * @param name the player's name
     */
    public MuseumCaperHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return the top object in the GUI's view heirarchy
     */
    public View getTopView() {

        return myActivity.findViewById(R.id.myBoard);
    }


    /**
     * sets the counter value in the text view
     */
    protected void updateDisplay() {
        // set the text in the appropriate widget
        //counterValueTextView.setText("" + state.getCounter());
    }

    /**
     * this method gets called when the user clicks the '+' or '-' button. It
     * creates a new CounterMoveAction to return to the parent activity.
     *
     * @param button the button that was clicked
     */

    @Override
    public void onClick(View button) {

		// if we are not yet connected to a game, ignore
		if (game == null) return;

		// Construct the action and send it to the game
		GameAction action = null;
		if (button.getId() == R.id.upButton) {
			// up button: creates a "move" action
			action = new MuseumCaperMoveAction(this,0,1);
		}
		else if (button.getId() == R.id.downButton) {
			// down button: creates a "move" action
			action = new MuseumCaperMoveAction(this,0,-1);
		}
        else if (button.getId() == R.id.leftButton) {
            // left button: creates a "move" action
            action = new MuseumCaperMoveAction(this,-1,0);
        }
        else if (button.getId() == R.id.rightButton) {
            // left button: creates a "move" action
            action = new MuseumCaperMoveAction(this,1,0);
            Log.i("button", "yeah you clicked the right button");
        }
		else {
			// something else was pressed: ignore
			return;
		}
		
		game.sendAction(action); // send action to the game
    }// onClick

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        // ignore the message if it's not a CounterState message
        if (!(info instanceof MuseumCaperState)) return;

        // update our state; then update the display
        this.state = (MuseumCaperState) info;
        updateDisplay();
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        this.myActivity = activity;

		//Load the layout resource for our GUI
		activity.setContentView(R.layout.activity_main);
        //Button ID's
        Button stealPainting = activity.findViewById(R.id.stealPaintingButton);
        Button disableCamera = activity.findViewById(R.id.disableCameraButton);
        Button checkLock = activity.findViewById(R.id.checkLockButton);

        Button upMove = activity.findViewById(R.id.upButton);
        Button leftMove = activity.findViewById(R.id.leftButton);
        Button rightMove = activity.findViewById(R.id.rightButton);
        Button downMove = activity.findViewById(R.id.downButton);

        Button settings = activity.findViewById(R.id.settingsButton);

        //Button Listener's
        stealPainting.setOnClickListener(this);
        disableCamera.setOnClickListener(this);
        checkLock.setOnClickListener(this);

        upMove.setOnClickListener(this);
        leftMove.setOnClickListener(this);
        rightMove.setOnClickListener(this);
        downMove.setOnClickListener(this);

        settings.setOnClickListener(this);
		// if we have a game state, "simulate" that we have just received
		// the state from the game so that the GUI values are updated
		if (state != null) {
			receiveInfo(state);
    }
}

}// class CounterHumanPlayer

