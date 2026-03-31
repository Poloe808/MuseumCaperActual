package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    //this TextView Displays the amount of moves left the player has
    private TextView movesLeftTextView;
    private TextView isSeenTextView;

    //Button references
    Button stealPainting;
    Button disableCamera;
    Button checkLock;

    // the most recent game state, as given to us by the MuseumCaperLocalGame
    private MuseumCaperState state;

    // the android activity that we are running
    private GameMainActivity myActivity;
    private DrawView dv;

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
    //Creates a reference to the board image
    public View getTopView() {
        return myActivity.findViewById(R.id.myBoard);
    }


    /**
     * this is where we update ALL the things =)
     */
    protected void updateDisplay() {
        //setting the locations in DrawView in order to change the location of the pawns
        dv.setThiefLocation(state.getThiefLocation().get(1), state.getThiefLocation().get(0));
        dv.setGuardOneLocation(state.getGuardOneLocation().get(1), state.getGuardOneLocation().get(0));
        dv.setGuardTwoLocation(state.getGuardTwoLocation().get(1), state.getGuardTwoLocation().get(0));
        dv.setGuardThreeLocation(state.getGuardThreeLocation().get(1), state.getGuardThreeLocation().get(0));

        //change all the texts to reflect the changes in state
        movesLeftTextView.setText("Moves Left: " + state.getMoveCount());
        if(state.getIsVisible()){
            isSeenTextView.setText("You've been seen!");
        }
        else{
            isSeenTextView.setText("You've not yet been spotted...");
        }

        //change stuff about the buttons

        MapTile mt = state.getBoard().get(state.getThiefLocation().get(1)).get(state.getThiefLocation().get(0));
       //change color of steal painting button
        if(mt.hasPainting()){
            stealPainting.setBackgroundColor(Color.parseColor("#818181ff"));
        }
        else if(!mt.hasPainting()){
            stealPainting.setBackgroundColor(Color.parseColor("#ff828282"));
        }


        //change color of disable camera button
        if(mt.hasCamera()){
            disableCamera.setBackgroundColor(Color.parseColor("#818181ff"));
        }
        else if(!mt.hasCamera()){
            disableCamera.setBackgroundColor(Color.parseColor("#ff828282"));
        }

        //change color of check lock button
        if(mt.hasLock()){
            checkLock.setBackgroundColor(Color.parseColor("#818181ff"));
        }
        else if(!mt.hasLock()){
            checkLock.setBackgroundColor(Color.parseColor("#ff828282"));
        }

        //invalidate DrawView for it to reflect all the changes
        dv.invalidate();

    }

    /**
     * this method gets called when the user clicks on ANY button on the view It
     * creates a new MuseumCaperMoveAction to return to the parent activity.
     *
     * @param button the button that was clicked
     */

    @Override
    public void onClick(View button) {

		// if we are not yet connected to a game, ignore
		if (game == null) return;
        //if it is not the thief's turn, don't do sheet braddah (please excuse the profanities thx)
        if (!state.getIsThiefTurn()){
            return;
        }
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
        else if (button.getId() == R.id.stealPaintingButton){
            action = new MuseumCaperStealPaintingAction(this);
        }
        else if (button.getId() == R.id.endTurnButton){
            action = new MuseumCaperEndTurnAction(this);
        }
        else if (button.getId() == R.id.checkLockButton){
            action = new MuseumCaperCheckLockAction(this);
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
        // ignore the message if it's not a MuseumCaperState message
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

        //Text Views
        this.movesLeftTextView = activity.findViewById(R.id.movesLeftText);
        this.isSeenTextView = activity.findViewById(R.id.seenIndicator);

        //Button ID's
        stealPainting = activity.findViewById(R.id.stealPaintingButton);
        disableCamera = activity.findViewById(R.id.disableCameraButton);
        checkLock = activity.findViewById(R.id.checkLockButton);
        Button endTurn = activity.findViewById(R.id.endTurnButton);

        Button upMove = activity.findViewById(R.id.upButton);
        Button leftMove = activity.findViewById(R.id.leftButton);
        Button rightMove = activity.findViewById(R.id.rightButton);
        Button downMove = activity.findViewById(R.id.downButton);

        Button settings = activity.findViewById(R.id.settingsButton);

        //Button Listener's
        stealPainting.setOnClickListener(this);
        disableCamera.setOnClickListener(this);
        checkLock.setOnClickListener(this);
        endTurn.setOnClickListener(this);

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

        this.myActivity = activity;
        this.dv = (DrawView) getTopView();
}

}// class MuseumCaperHumanPlayer

