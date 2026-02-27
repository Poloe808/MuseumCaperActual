package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class MuseumCaperStealPaintingAction extends GameAction {
    public MuseumCaperStealPaintingAction(GamePlayer player){
        super(player);
        //make painting value on tile false (remove from view)
        //add painting to stolen painting bank

    }
}
