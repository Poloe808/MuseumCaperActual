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

/**
 * sequence of moves to finish game quickly
 * thief enters through window in green room, moves two spaces to the nearest painting
 *      move()
 *      move()
 * thief steals painting
 *      stealPainting()
 * guard one takes turn - rolls a one on move die
 *      useEyes()
 *      move()
 * thief moves back to the window
 *      move()
 *      move()
 * thief checks lock, comes back unlocked
 *      checkLock()
 * thief escapes
 *
 */