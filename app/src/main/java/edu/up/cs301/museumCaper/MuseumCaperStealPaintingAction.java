package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * This contains the Steal Painting Action that will be sent to the LocalGame.
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */

public class MuseumCaperStealPaintingAction extends GameAction {
    public MuseumCaperStealPaintingAction(GamePlayer player){
        super(player);
        //make painting value on tile false (remove from view)
        //add painting to stolen painting bank

    }
}
