package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * This contains the Use Camera Action that will be sent to the LocalGame.
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */
public class MuseumCaperUseCameraAction extends GameAction {
    int cameraNumber;

    public MuseumCaperUseCameraAction(GamePlayer player, int cameraNum) {
        super(player);
        cameraNumber = cameraNum;
    }

    public int getCameraNumber(){return cameraNumber;}
}
