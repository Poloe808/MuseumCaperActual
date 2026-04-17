package edu.up.cs301.museumCaper;

import static android.view.View.VISIBLE;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

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

    //TextView turn list references
    TextView thiefText;
    TextView p1Text;
    TextView p2Text;
    TextView p3Text;

    //Button references
    Button stealPainting;
    Button disableCamera;
    Button checkLock;

    // the most recent game state, as given to us by the MuseumCaperLocalGame
    private MuseumCaperState state;

    // the android activity that we are running
    private GameMainActivity myActivity;
    private DrawView dv;
    private List<ImageView> paintingBankList;
    private List<ImageView> cameraBankList;

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
     * Returns the Player's ID
     *
     * @return playerNum
     */
    public int getPlayerID() {
        return playerNum;
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
        dv.setPaintings(state.getPaintings());
        dv.setLockList(state.getLocksList());
        dv.setCameraList(state.getCameras());

        //change all the texts to reflect the changes in state
        movesLeftTextView.setText("Moves Left: " + state.getMoveCount());
        if (state.getIsVisible()) {
            isSeenTextView.setText("You've been seen!");
        } else {
            isSeenTextView.setText("You've not yet been spotted...");
        }

        //change stuff about the buttons

        MapTile mt = state.getBoard().get(state.getThiefLocation().get(1)).get(state.getThiefLocation().get(0));
        //change color of steal painting button
        if (mt.hasPainting()) {
            stealPainting.setBackgroundColor(Color.parseColor("#818181ff"));
        } else if (!mt.hasPainting()) {
            stealPainting.setBackgroundColor(Color.parseColor("#ff828282"));
        }


        //change color of disable camera button
        if (mt.hasCamera()) {
            disableCamera.setBackgroundColor(Color.parseColor("#818181ff"));
        } else if (!mt.hasCamera()) {
            disableCamera.setBackgroundColor(Color.parseColor("#ff828282"));
        }

        //change color of check lock button
        if (mt.hasLock()) {
            checkLock.setBackgroundColor(Color.parseColor("#818181ff"));
        } else if (!mt.hasLock()) {
            checkLock.setBackgroundColor(Color.parseColor("#ff828282"));
        }

        //change the highlight for the current turn list
        if (state.getIsThiefTurn()) {
            thiefText.setBackgroundColor(Color.parseColor("#818181ff"));
            p1Text.setBackgroundColor(Color.parseColor("#00000000"));
            p2Text.setBackgroundColor(Color.parseColor("#00000000"));
            p3Text.setBackgroundColor(Color.parseColor("#00000000"));
        } else if (state.getCurrentPlayer() == 1) {
            thiefText.setBackgroundColor(Color.parseColor("#00000000"));
            p1Text.setBackgroundColor(Color.parseColor("#818181ff"));
            p2Text.setBackgroundColor(Color.parseColor("#00000000"));
            p3Text.setBackgroundColor(Color.parseColor("#00000000"));
        } else if (state.getCurrentPlayer() == 2) {
            thiefText.setBackgroundColor(Color.parseColor("#00000000"));
            p1Text.setBackgroundColor(Color.parseColor("#00000000"));
            p2Text.setBackgroundColor(Color.parseColor("#818181ff"));
            p3Text.setBackgroundColor(Color.parseColor("#00000000"));
        } else if (state.getCurrentPlayer() == 3) {
            thiefText.setBackgroundColor(Color.parseColor("#00000000"));
            p1Text.setBackgroundColor(Color.parseColor("#00000000"));
            p2Text.setBackgroundColor(Color.parseColor("#00000000"));
            p3Text.setBackgroundColor(Color.parseColor("#818181ff"));
        }

        if (state.getPaintings() != null) {
            for (int i = 0; i < state.getPaintings().size(); i++) {
                if (state.getPaintings().get(i).isStolen) {
                    paintingBankList.get(i).setVisibility(VISIBLE);
                }
            }
        }

        if (state.getCameras() != null) {
            for (int i = 0; i < state.getCameras().size(); i++) {
                if (state.getCameras().get(i).toString() == "Disabled") {
                    cameraBankList.get(i).setVisibility(VISIBLE);
                }
            }
        }

        //invalidate DrawView for it to reflect all the changes :)
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
        //if it is not the thief's turn, don't do that braddah
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
        else if (button.getId() == R.id.disableCameraButton){
            action = new MuseumCaperDisableCameraAction(this);
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

        //Turn List TextViews
        thiefText = activity.findViewById(R.id.thiefText);
        p1Text = activity.findViewById(R.id.playerOneText);
        p2Text = activity.findViewById(R.id.playerTwoText);
        p3Text = activity.findViewById(R.id.playerThreeText);

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

        //Image Views - paintings - cameras//
        //Paintings
        paintingBankList = new ArrayList<ImageView>();
        paintingBankList.add(activity.findViewById(R.id.artone));
        paintingBankList.add(activity.findViewById(R.id.arttwo));
        paintingBankList.add(activity.findViewById(R.id.artthree));
        paintingBankList.add(activity.findViewById(R.id.artfour));
        paintingBankList.add(activity.findViewById(R.id.artfive));
        paintingBankList.add(activity.findViewById(R.id.artsix));
        paintingBankList.add(activity.findViewById(R.id.artseven));
        paintingBankList.add(activity.findViewById(R.id.arteight));
        paintingBankList.add(activity.findViewById(R.id.artnine));

        //Cameras
        cameraBankList = new ArrayList<ImageView>();
        cameraBankList.add(activity.findViewById(R.id.cameraone));
        cameraBankList.add(activity.findViewById(R.id.cameratwo));
        cameraBankList.add(activity.findViewById(R.id.camerathree));
        cameraBankList.add(activity.findViewById(R.id.camerafour));
        cameraBankList.add(activity.findViewById(R.id.camerafive));
        cameraBankList.add(activity.findViewById(R.id.camerasix));

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

