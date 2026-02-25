package edu.up.cs301.museumCaper;

public class Camera {
    private boolean working;
    private int cameraNum;

    public Camera(int number){
        working = true;
        cameraNum = number;
    }

    public void disableCamera(Camera camera){
        camera.working = false;
    }
}

