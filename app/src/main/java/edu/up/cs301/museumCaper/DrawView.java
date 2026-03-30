
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
    int rowDirection;

    //All le paints
    private final Paint spaceGrey = new Paint();
    private final Paint green = new Paint();
    private final Paint copBlue  = new Paint();

    private Bitmap board =
            BitmapFactory.decodeResource(getResources(), R.drawable.coloredboard);

    public void drawPawn(Canvas canvas, int x, int y, Paint color) {
        //Head of piece (circle)
        //canvas.drawCircle((x*100)+8, y*415, 15, color);

        //Top left corner to bottom right of rectangle
        //canvas.drawRect((x*55)+225,(y*60)+225,(((x*55)+225)+55),(((y*60)+225)+60),color);
        canvas.drawRect(225+(57*x),(225+(57*y)),(225+(57*x))+55,(225+(57*y))+55, color);
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

        //Makes the board smaller
        board = Bitmap.createScaledBitmap(board, 1112, 834, false);
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
