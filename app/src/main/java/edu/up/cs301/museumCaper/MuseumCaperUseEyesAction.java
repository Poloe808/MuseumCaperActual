package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * This contains the Use Eyes Action that will be sent to the LocalGame.
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */


public class MuseumCaperUseEyesAction extends GameAction {
    private int currentCol;
    private int currentRow;

    public MuseumCaperUseEyesAction(GamePlayer player, int myCol, int myRow) {
        super(player);
        currentCol = myCol;
        currentRow = myRow;
    }

    public int getCol(){return currentCol;}
    public int getRow(){return currentRow;}
}