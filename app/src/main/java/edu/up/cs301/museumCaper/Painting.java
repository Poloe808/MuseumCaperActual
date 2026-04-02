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
    boolean isStolen;
    int col;
    int row;

    /**
     * constructor
     * @param number id number
     */
    public Painting(int number){
        paintingNum = number;
        isStolen = false;
    }

    /**
     * copy constructor
     * @param p painting being passed in
     */
    public Painting(Painting p){
        this.paintingNum = p.paintingNum;
    }

    public boolean getStolenStatus(){return isStolen;}
    public void setStolen(){isStolen = true;}
    public void setCol(int _col){col = _col;}
    public void setRow(int _row){row = _row;}
}
