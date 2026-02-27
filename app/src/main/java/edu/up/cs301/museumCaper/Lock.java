package edu.up.cs301.museumCaper;

public class Lock {
    private boolean unlocked;
    public Lock(){
        unlocked = false;
    }

    public void unlock(Lock lock){
        this.unlocked = true;
    }
}
