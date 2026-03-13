package edu.up.cs301.museumCaper;

/**
 * @author Logan Ortogero
 *
 * Description
 * The MapTile object stores information about any given space on the board.
 * This object is meant to be stored in the 2D Array, board, in gameState. To utilize this object,
 * call a position on the board array, and use the getters/setters associated in this class
 */
public class MapTile {
    //todo add lock stuff
    private boolean hasCamera;
    private boolean hasThief;
    private boolean hasGuard;
    private boolean hasLeftWall;
    private boolean hasTopWall;
    private boolean hasPainting;
    public MapTile(){
        this.hasCamera = true;
        this.hasThief = true;
        this.hasGuard = true;
    }

    public MapTile(MapTile m){
        this.hasCamera = m.hasCamera;
        this.hasThief = m.hasThief;
        this.hasGuard = m.hasGuard;
        this.hasLeftWall = m.hasLeftWall;
        this.hasTopWall = m.hasLeftWall;
    }

    //Getters
    public boolean getCamera(){return hasCamera;}
    public boolean getThief(){return hasThief;}
    public boolean getGuard(){return hasGuard;}
    public boolean getLeftWall(){return hasLeftWall;}
    public boolean getTopWall(){return hasTopWall;}
    public boolean getHasPainting(){return hasPainting;}

    //Setters
    public void setCamera(boolean status){hasCamera = status;}
    public void setThief(boolean status){hasThief = status;}
    public void setGuard(boolean status){hasGuard = status;}
    public void setLeftWall(boolean status){hasLeftWall = status;}
    public void setTopWall(boolean status){hasTopWall = status;}
    public void setHasPainting(boolean status){hasPainting = status;}

}
