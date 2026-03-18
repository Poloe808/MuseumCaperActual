package edu.up.cs301.museumCaper;

/**
 * camera object, contains the id of the camera and if it's functioning or not
 *
 * @author Logan Ortogero
 * @author Paloma Wilson
 * @author Alberto Lucero
 * @author Felipe Lucas Pablo
 */

public class Camera {
    private boolean working;
    private int cameraNum;

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

    public void disableCamera(){working = false;}
}

