package edu.up.cs301.museumCaper;

/*
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 */

public class Camera {
    private boolean working;
    private int cameraNum;

    public Camera(int number){
        working = true;
        cameraNum = number;
    }
    public Camera(Camera c){
        this.working = c.working;
        this.cameraNum = c.cameraNum;
    }

    public void disableCamera(){working = false;}
}

