
package edu.up.cs301.museumCaper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends SurfaceView {

    //instance variables
    int thiefCol;
    int thiefRow;
    int guardOneRow;
    int guardOneCol;
    int guardTwoRow;
    int guardTwoCol;
    int guardThreeRow;
    int guardThreeCol;
    List<Painting> paintings;
    List<Lock> lockList;
    List<Camera> workingCameraList;
    List<Camera> disabledCameraList;

    //All le paints
    private final Paint spaceGrey = new Paint();
    private final Paint green = new Paint();
    private final Paint copBlue  = new Paint();

    private Bitmap board =
            BitmapFactory.decodeResource(getResources(), R.drawable.coloredboard);
    private Bitmap lockArt =
            BitmapFactory.decodeResource(getResources(), R.drawable.lock);


    private List<Bitmap> artList;
    private List<Bitmap> cameraArtList;
    /**
     * callback method--game's state has changed
     *
     * @param canvas
     * 		the information (hypothermically containing the game's state)
     * @param x
     *      the x coordinate of the player's pawn
     * @param y
     *      the y coordinate of the player's pawn
     * @param color
     *      the color of the pawn
     */
    public void drawPawn(Canvas canvas, int x, int y, Paint color) {
        //Head of piece (circle)
        //canvas.drawCircle((x*100)+8, y*415, 15, color);

        //Top left corner to bottom right of rectangle
        //canvas.drawRect((x*55)+225,(y*60)+225,(((x*55)+225)+55),(((y*60)+225)+60),color);

        //THIS ONE
        //canvas.drawRect(225+(57*x),(225+(57*y)),(225+(57*x))+55,(225+(57*y))+55, color);

        //external citation: https://stackoverflow.com/questions/3501126/how-to-draw-a-filled-triangle-in-android-canvas

        //draw triangle pawn body
        color.setStrokeWidth(2);
        color.setStyle(Paint.Style.FILL_AND_STROKE);
        color.setAntiAlias(true);

        Point point1_draw = new Point((255+(57*x)),(255+(55*y))-10);
        Point point2_draw = new Point(225+(57*x)+15, (225+(55*y))+48);
        Point point3_draw = new Point((225+(57*x))+45,(225+(55*y))+48);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1_draw.x,point1_draw.y);
        path.lineTo(point2_draw.x,point2_draw.y);
        path.lineTo(point3_draw.x,point3_draw.y);
        path.lineTo(point1_draw.x,point1_draw.y);
        path.close();

        canvas.drawPath(path, color);

        //draw pawn head
        canvas.drawCircle((255+(57*x)),(255+(55*y))-10, 12 ,color);

    }

    public void drawObject(Canvas canvas, int col, int row, Bitmap art){
        canvas.drawBitmap(art, 225+(57*col),(225+(57*row)), null);
    }


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        green.setColor(0xFF00FF00);
        green.setStyle(Paint.Style.FILL);

        spaceGrey.setColor(0xFF343d46);
        spaceGrey.setStyle(Paint.Style.FILL);

        copBlue.setColor(0xFF2200ff);
        copBlue.setStyle(Paint.Style.FILL);

        //Makes the board and arts smaller
        board = Bitmap.createScaledBitmap(board, 1112, 834, false);

        //define the list of painting arts then size down to appropriate size
        artList = new ArrayList<Bitmap>();
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artone));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.arttwo));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artthree));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artfour));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artfive));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artsix));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artseven));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.arteight));
        artList.add(BitmapFactory.decodeResource(getResources(), R.drawable.artnine));

        sizeDownArt(artList, 40, 57);

        //define the list of camera arts then size down to appropriate size
        cameraArtList = new ArrayList<Bitmap>();
        cameraArtList.add(BitmapFactory.decodeResource(getResources(), R.drawable.cameraone));
        cameraArtList.add(BitmapFactory.decodeResource(getResources(), R.drawable.cameratwo));
        cameraArtList.add(BitmapFactory.decodeResource(getResources(), R.drawable.camerathree));
        cameraArtList.add(BitmapFactory.decodeResource(getResources(), R.drawable.camerafour));
        cameraArtList.add(BitmapFactory.decodeResource(getResources(), R.drawable.camerafive));
        cameraArtList.add(BitmapFactory.decodeResource(getResources(), R.drawable.camerasix));

        sizeDownArt(cameraArtList, 57, 57);
        //myfacewhenlockart
        lockArt = Bitmap.createScaledBitmap(lockArt, 57, 57, false);

    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(board, 0, 130, null);

        //Now draw the locks
        if(lockList != null){
            for (int i = 0; i < lockList.size(); i++){
                drawObject(canvas, lockList.get(i).col, lockList.get(i).row, lockArt);
            }
        }

        //now draw the cameras
        if(workingCameraList != null) {
            int i = 1;
            for (Camera c : workingCameraList) {
                if (i == c.getCameraNum()) {
                    if (c.toString() == "Working"){
                        drawObject(canvas, c.col, c.row, cameraArtList.get(i - 1));
                    }
                }
                i++;
            }
        }

        //draw the players where they're positioned
        drawPawn(canvas,thiefCol, thiefRow,spaceGrey);
        drawPawn(canvas, guardOneCol, guardOneRow, copBlue);
        drawPawn(canvas, guardTwoCol, guardTwoRow, copBlue);
        drawPawn(canvas, guardThreeCol, guardThreeRow, copBlue);

        //draw the paintings
        //IF they're in the array, draw them on the board. ELSE, draw them in the bank
        if(paintings != null) {
            int i = 1;
            for (Painting p : paintings) {
                if (i == p.paintingNum) {
                    if (!p.isStolen){
                        drawObject(canvas, p.col, p.row, artList.get(i - 1));
                    }
                }
                i++;
            }
        }
    }

    public void setThiefLocation(int row, int col){
        thiefRow = row;
        thiefCol = col;
    }
    public void setGuardOneLocation(int row, int col){
        guardOneRow = row;
        guardOneCol = col;
    }
    public void setGuardTwoLocation(int row, int col){
        guardTwoRow = row;
        guardTwoCol = col;
    }
    public void setGuardThreeLocation(int row, int col){
        guardThreeRow = row;
        guardThreeCol = col;
    }

    public void setPaintings(List<Painting> list){
        paintings = list;
    }

    public void setLockList(List<Lock> list){
        lockList = list;
    }
    public void setCameraList(List<Camera> list){
        workingCameraList = list;
    }

    //could use tweaking maybe??
    private void sizeDownArt(List<Bitmap> list, int width, int height){
        for(int i = 0; i < list.size(); i++){
            list.set(i, Bitmap.createScaledBitmap(list.get(i), width, height, false));
        }
    }
}
// Attributions for the assets used in this project:
// https://www.sprinttosave.com/product-p-674011.html

/**
 * @author Logan Ortogero
 External Citation
 Date: 25 March 2026
 Problem: drawView seemed to be drawing the opposite of what occured on screen, only in the y-direction.
          The x-dir seemed fine. Ex: move down one, the gui showed that the player moved up one,
          and vice versa.

 Resource:
 My good friend Jack Stewart, whom I attended highschool with and regularly play games with.
 He is a college graduate who also majored in computer science.
 I did some good o'l rubber duck debugging with him, talking with him about my code and
 how it's "fundamentally..." "...impossible to translate [it]." on the two axis' if the behavior is like this.

 Solution:
 So as it turned out it WAS fundamentally impossible because I was not looking at where the root
 of the real problem was, which was in the movement code, as the player was actually moving in the
 opposite direction in the y-direction only, due to a confusing part in the code where the arraylist did
 the opposite of what was intended.
 */