package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Enables the player to start playing the game after deciding where to start
 * Is an action sent over to LocalGame after being created by the player.
 *
 * @author Logan Ortogero
 *
 * @version April 2026
 */

public class MuseumCaperEndPlacementAction extends GameAction {
    public MuseumCaperEndPlacementAction(GamePlayer player){
        super(player);
    }
}
