package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * This contains the Roll Move Action that will be sent to the LocalGame.
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */
public class MuseumCaperRollMoveDie extends GameAction {
    public MuseumCaperRollMoveDie(GamePlayer player){
        super(player);
    }
}
