package edu.up.cs301.museumCaper;

/*
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
* */

public class Lock {
    private boolean unlocked;
    public Lock(boolean locked){
        unlocked = !locked;
    }
    public Lock(Lock l){
        this.unlocked = l.unlocked;
    }
}
