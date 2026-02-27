package edu.up.cs301.museumCaper;

/*
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
* */

public class Lock {
    private boolean unlocked;
    public Lock(){
        unlocked = false;
    }

    public void unlock(Lock lock){
        this.unlocked = true;
    }
}
