
package edu.up.cs301.museumCaper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    //All le paints
    private final Paint spaceGrey = new Paint();
    private final Paint green = new Paint();
    private final Paint copBlue  = new Paint();

    private Bitmap board =
            BitmapFactory.decodeResource(getResources(), R.drawable.coloredboard);
    private Bitmap art1 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artone);
    private Bitmap art2 =
            BitmapFactory.decodeResource(getResources(), R.drawable.arttwo);
    private Bitmap art3 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artthree);
    private Bitmap art4 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artfour);
    private Bitmap art5 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artfive);
    private Bitmap art6 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artsix);
    private Bitmap art7 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artseven);
    private Bitmap art8 =
            BitmapFactory.decodeResource(getResources(), R.drawable.arteight);
    private Bitmap art9 =
            BitmapFactory.decodeResource(getResources(), R.drawable.artnine);

    private List<Bitmap> artList;
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
        canvas.drawRect(225+(57*x),(225+(57*y)),(225+(57*x))+55,(225+(57*y))+55, color);
    }

    public void drawPainting(Canvas canvas, int col, int row, Bitmap painting){
        canvas.drawBitmap(painting, 225+(57*col),(225+(57*row)), null);
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
        art1 = Bitmap.createScaledBitmap(art1, 40, 57, false);
        art2 = Bitmap.createScaledBitmap(art2, 40, 57, false);
        art3 = Bitmap.createScaledBitmap(art3, 40, 57, false);
        art4 = Bitmap.createScaledBitmap(art4, 40, 57, false);
        art5 = Bitmap.createScaledBitmap(art5, 40, 57, false);
        art6 = Bitmap.createScaledBitmap(art6, 40, 57, false);
        art7 = Bitmap.createScaledBitmap(art7, 40, 57, false);
        art8 = Bitmap.createScaledBitmap(art8, 40, 57, false);
        art9 = Bitmap.createScaledBitmap(art9, 40, 57, false);

        artList = new ArrayList<Bitmap>();
        artList.add(art1);
        artList.add(art2);
        artList.add(art3);
        artList.add(art4);
        artList.add(art5);
        artList.add(art6);
        artList.add(art7);
        artList.add(art8);
        artList.add(art9);

    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(board, 0, 130, null);

        //This is the beginning of the color declaration section
        //The format for the Player Pieces is as follows:
        //Color

        drawPawn(canvas,thiefCol, thiefRow,spaceGrey);
        drawPawn(canvas, guardOneCol, guardOneRow, copBlue);
        drawPawn(canvas, guardTwoCol, guardTwoRow, copBlue);
        drawPawn(canvas, guardThreeCol, guardThreeRow, copBlue);

        //draw the paintings
        //IF they're in the array, draw them on the board. ELSE, draw them in the bank
        if(paintings != null) {
            int i = 1;
            int bankRow = 1;
            for (Painting p : paintings) {
                if (i == p.paintingNum) {
                    if (!p.isStolen){
                        drawPainting(canvas, p.col, p.row, artList.get(i - 1));
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
}
// Attributions for the assets used in this project:
// https://www.sprinttosave.com/product-p-674011.html
