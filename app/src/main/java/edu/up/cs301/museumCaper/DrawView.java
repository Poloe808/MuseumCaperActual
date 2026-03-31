
package edu.up.cs301.museumCaper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

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
        drawPainting(canvas, 0,6, art1);
        drawPainting(canvas, 1,1, art2);
        drawPainting(canvas, 2,9, art3);
        drawPainting(canvas, 3,1, art4);
        drawPainting(canvas, 3,4, art5);
        drawPainting(canvas, 5,10, art6);
        drawPainting(canvas, 6,6, art7);
        drawPainting(canvas, 8,0, art8);
        drawPainting(canvas, 8,10, art9);

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
}
// Attributions for the assets used in this project:
// https://www.sprinttosave.com/product-p-674011.html
