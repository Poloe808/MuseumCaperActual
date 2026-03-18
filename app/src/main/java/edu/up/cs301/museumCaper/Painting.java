package edu.up.cs301.museumCaper;

/**
 * Painting object, contains painting's id number
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 */

public class Painting {
    int paintingNum;

    /**
     * constructor
     * @param number id number
     */
    public Painting(int number){
        paintingNum = number;
    }

    /**
     * copy constructor
     * @param p painting being passed in
     */
    public Painting(Painting p){
        this.paintingNum = p.paintingNum;
    }
}
