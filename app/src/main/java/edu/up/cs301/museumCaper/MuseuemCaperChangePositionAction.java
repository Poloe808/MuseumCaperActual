package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class MuseuemCaperChangePositionAction extends GameAction {
    private int direction;

    public MuseuemCaperChangePositionAction(GamePlayer player, int dir){
        super(player);
        direction = dir;
    }

    public int getDirection(){return direction;}
}
