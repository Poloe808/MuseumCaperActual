package edu.up.cs301.museumCaper;

/**
 * Lock object, contains if a lock is locked/unlocked
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */

public class Lock {
    private boolean unlocked;
    public int col;
    public int row;

    /**
     * constructor
     * @param locked is lock currently locked/unlocked
     */
    public Lock(boolean locked){
        unlocked = !locked;
    }

    /**
     * copy constructor
     * @param l lock being passed in
     */
    public Lock(Lock l){
        this.unlocked = l.unlocked;
    }
    public void setUnlocked(boolean unlocked){
        this.unlocked = unlocked;
    }
    public boolean getLockValue(){
        return unlocked;
    }
    public void setCol(int _col){
        col = _col;
    }
    public void setRow(int _row){
        row = _row;
    }
}
