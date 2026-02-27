package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/*
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 */

public class MuseumCaperStealPaintingAction extends GameAction {
    public MuseumCaperStealPaintingAction(GamePlayer player){
        super(player);
        //make painting value on tile false (remove from view)
        //add painting to stolen painting bank

    }
}
