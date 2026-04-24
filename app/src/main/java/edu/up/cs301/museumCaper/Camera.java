package edu.up.cs301.museumCaper;

/**
 * camera object, contains the id of the camera and if it's functioning or not
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 *
 * @version April 2026
 */

public class Camera {
    private boolean working;
    private int cameraNum;
    public int col;
    public int row;

    /**
     * constructor
     * @param number the number id of the camera being created
     */
    public Camera(int number){
        working = true;
        cameraNum = number;
    }

    /**
     * copy constructor
     * @param c camera object being passed in
     */
    public Camera(Camera c){
        this.working = c.working;
        this.cameraNum = c.cameraNum;
    }

    @Override
    public String toString(){
        if(working){
            return "Working";
        }
        else{
            return "Disabled";
        }
    }
    public int getCameraNum(){
        return cameraNum;
    }
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isCameraWorking() {
        return working;
    }
    public void disableCamera(){working = false;}
    public void setCol(int _col){
        col = _col;
    }
    public void setRow(int _row){
        row = _row;
    }
}

