package edu.up.cs301.museumCaper;

/**
 * Description
 * The MapTile object stores information about any given space on the board.
 * This object is meant to be stored in the 2D Array, board, in gameState. To utilize this object,
 * call a position on the board array, and use the getters/setters associated in this class
 *
 * @author Logan Ortogero
 *
 * @version April 2026
 */
public class MapTile {
    private Camera Camera;
    private boolean hasThief;
    private boolean hasGuard;
    private boolean hasLeftWall;
    private boolean hasTopWall;
    private Painting painting;
    private Lock lock;


    public MapTile(){
        this.hasThief = false;
        this.hasGuard = false;
    }

    public MapTile(MapTile m){
        if(m.Camera != null){
            this.Camera = new Camera(m.Camera);
        }
        if(m.painting != null){
            this.painting = new Painting(m.painting);
        }
        if(m.lock != null){
            this.lock = new Lock(m.lock);
        }
        this.hasThief = m.hasThief;
        this.hasGuard = m.hasGuard;
        this.hasLeftWall = m.hasLeftWall;
        this.hasTopWall = m.hasTopWall;
    }

    //Getters
    public Camera getCamera(){return Camera;}
    public boolean hasCamera(){return (Camera != null);}
    public Painting getHasPainting(){return painting;}
    public boolean hasPainting(){return (painting != null);}
    public Lock getLock(){return lock;}
    public boolean hasLock(){return (lock != null);}
    public boolean getThief(){return hasThief;}
    public boolean getGuard(){return hasGuard;}
    public boolean getLeftWall(){return hasLeftWall;}
    public boolean getTopWall(){return hasTopWall;}

    //Setters
    public void setThief(boolean status){hasThief = status;}
    public void setGuard(boolean status){hasGuard = status;}
    public void setLeftWall(boolean status){hasLeftWall = status;}
    public void setTopWall(boolean status){hasTopWall = status;}
    public void setCamera(Camera c){Camera = c;}
    public void setHasPainting(Painting p){painting = p;}
    public void setLock(Lock l){lock = l;}
    public void removeCamera(){Camera.disableCamera(); Camera = null;}
    public void removePainting(){painting = null;}


}
