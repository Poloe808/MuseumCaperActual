package edu.up.cs301.museumCaper;

/*
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 */

public class Painting {
    int paintingNum;
    public Painting(int number){
        paintingNum = number;
    }
    public Painting(Painting p){
        this.paintingNum = p.paintingNum;
    }
}
