package edu.up.cs301.museumCaper;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

public class MuseumCaperUseCameraAction extends GameAction {
    int cameraNumber;

    public MuseumCaperUseCameraAction(GamePlayer player, int cameraNum) {
        super(player);
        cameraNumber = cameraNum;
    }

    public int getCameraNumber(){return cameraNumber;}
}
