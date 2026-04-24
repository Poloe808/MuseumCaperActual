package edu.up.cs301.museumCaper;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.gameConfiguration.*;

/**
 * this is the primary activity for MuseumCaper game
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 *
 * //////////////////////// (Project #I Portion) ////////////////////////
 * =============================================================================================
 * All of the main functionality detailed in the requirements for the Thief is present in the game.
 * This includes:
 * - Thief movement
 * - Stealing paintings
 * - Disabling cameras
 * - Checking Locks
 * - Ending their turn
 *
 * Note: There is no indication on the GUI whether or not the Thief has failed a lock check.
 *       If the thief checks a lock that is unlocked, they escape, and if they check one that *IS*
 *       locked, nothing occurs.
 *
 * For the Guard, or the computer player, their pathfinding algorithm (at least for the "Smart AI")
 * is implemented. The actions that are implemented are as follows:
 * - Movement
 * - using Eyes
 * - using Cameras
 *
 * Notably, the motion detection action and scan actions are absent from this list.
 * ===================================================================================
 * We indeed have a 'smart' AI and 'dumb' AI. Computer Player 1 is the "Smart" AI, and
 * Computer Player 2 is the "dumb" AI.
 * The smart AI utilizes some information that they're given in order to pathfind make what some
 * would call "actual plays," such as chasing the thief once they become visible on the board.
 * The dumb AI just does whatever it feels like, randomly.
 * ===================================================================================
 * The graphics/pngs were cleaned up, and the board now looks a lot cleaner. I believe it looks
 * appealing, thank u Paloma (^▽^)
 * ==========================================================================================
 * It is currently possible to play with 1 human player and 3 computers; No more, no less.
 * *IMPORTANT* the game only works with the human player in the first slot of configuration,
 * with the computer players filling up the rest of the spaces. Any configuration of
 * "dumb" and "smart" ai should work.
 * This was something being worked on, however I couldn't find the place to access playerNum in
 * a nice and healthy manner in order to pass it into the State, so the order is hardcoded.
 * While this wasn't directly addressed in our requirements, Id've liked to implement this.
 * =========================================================================================
 * All of the elements in the GUI (except for the settings button), are in working order,
 * and display the information the player needs in order to play effectively.
 * There are also some extra indicators such as the current player being highlighted in
 * the top right, and buttons highlighting when actions become available to the player.
 * =====================================================================================
 * POTENTIAL BUGS
 * Use eyes may not work at it's edges?
 * =================================================================
 * NOTE: It may seem like the AI freezes a bit. Just give it time to think! Promise
 * //////////////////////// (Project #I Portion) ////////////////////////
 *
 *
 * //////////////////////// (Project #H Portion) ////////////////////////
 * =========================================================================
 * Most of the same stuff from the Project #I portion!
 * ======================================================================
 * Some added Functionality:
 * The game now starts off in a placement phase
 * - Use the left and right buttons to change where the pawn starts off
 * - press the "end placement" button once you're satisfied, and start playing.
 *
 * SMART vs. DUMB
 * Smart AI - ComputerPlayer1
 * Dumb AI - ComputerPlayer2
 *
 * ComputerPlayer3 is just a copy of 1. I'm not quite sure why it was created.
 * I too wonder that about myself.
 * ============================================================================
 * Added the class headers
 * ============================================================================
 * //////////////////////// (Project #H Portion) ////////////////////////
 */
public class MuseumCaperMainActivity extends GameMainActivity {
	
	// the port number that this game will use when playing over the network
	private static final int PORT_NUMBER = 2234;

	/**
	 * Create the default configuration for this game:
	 * - one human player vs. three computer player
	 * - minimum of 1 human player, maximum of 3 computers
	 * - two kind of computer player and one kind of human player available
	 * 
	 * @return
	 * 		the new configuration object, representing the default configuration
	 */
	@Override
	public GameConfig createDefaultConfig() {
		
		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
		
		// a human player player type (player type 0)
		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new MuseumCaperHumanPlayer(name);
			}});
		
		// a computer player type (player type 1)
		playerTypes.add(new GamePlayerType("Computer Player 1") {
			public GamePlayer createPlayer(String name) {
				return new MuseumCaperComputerPlayer1(name);
			}});
		
		// a computer player type (player type 2)
		playerTypes.add(new GamePlayerType("Computer Player 2") {
			public GamePlayer createPlayer(String name) {
				return new MuseumCaperComputerPlayer2(name);
			}});
        // a computer player type (player type 2)
        playerTypes.add(new GamePlayerType("Computer Player 3") {
            public GamePlayer createPlayer(String name) {
                return new MuseumCaperComputerPlayer3(name);
            }});

		// Create a game configuration class for Museum Caper:
		// - player types as given above
		// - 4 players only
		// - name of game is "Clue: The Great Museum Caper"
		// - port number as defined above
		GameConfig defaultConfig = new GameConfig(playerTypes, 4, 4, "Clue: The Great Museum Caper",
				PORT_NUMBER);

		// Add the default players to the configuration
		defaultConfig.addPlayer("Human", 0); // player 1: a human player
		defaultConfig.addPlayer("Computer", 1); // player 2: a computer player
        defaultConfig.addPlayer("Computer2", 2);
        defaultConfig.addPlayer("Computer3", 3);
		
		// Set the default remote-player setup:
		// - player name: "Remote Player"
		// - IP code: (empty string)
		// - default player type: human player
		defaultConfig.setRemoteData("Remote Player", "", 0);
		
		// return the configuration
		return defaultConfig;
	}//createDefaultConfig

	/**
	 * create a local game
	 * 
	 * @return
	 * 		the local game, a museumCaper game
	 */
	@Override
	public LocalGame createLocalGame(GameState state) {
		if (state == null) state = new MuseumCaperState();
		return new MuseumCaperLocalGame(state);
	}

}
