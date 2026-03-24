
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

    private Bitmap board =
            BitmapFactory.decodeResource(getResources(), R.drawable.coloredboard);

    public void drawPawn(Canvas canvas, int x, int y, Paint color) {
        //Head of piece (circle)
        canvas.drawCircle((x*100)+8, y*415, 15, color);

        //Top left corner to bottom right of rectangle
        canvas.drawRect((x*55)+225,(y*60)+225,(((x*55)+225)+55),(((y*60)+225)+60),color);
    }
private Paint green = new Paint();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        green.setColor(0xFF00FF00);
        green.setStyle(Paint.Style.FILL);

        //Makes the board smaller
        board = Bitmap.createScaledBitmap(board, 1112, 834, false);
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(board, 0, 130, null);

        //This is the beginning of the color declaration section
        //The format for the Player Pieces is as follows:
        //Color
        Paint spaceGrey = new Paint();
        spaceGrey.setColor(0xFF343d46);
        spaceGrey.setStyle(Paint.Style.FILL);

        drawPawn(canvas,thiefCol, thiefRow,spaceGrey);
        drawPawn(canvas,4,1,spaceGrey);


        Paint red = new Paint();
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);

        canvas.drawCircle(822, 470, 15, red);
        canvas.drawRect(817,465, 828, 497,red);

        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);

        canvas.drawCircle(425, 800, 15, blue);
        canvas.drawRect(420,800, 430, 830,blue);

        Paint green = new Paint();
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);


        canvas.drawCircle(255, 473, 15, green);
        canvas.drawRect(250,475, 260, 497,green);




        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        canvas.drawRect(505,225, 560, 280,yellow);

        canvas.drawCircle(535, 245, 15, yellow);
        canvas.drawRect(530,245, 540, 278,yellow);

        //end of the declaration section

        //test rects
        canvas.drawRect(225,340, 280, 395,yellow);
        canvas.drawRect(225,225, 280, 280,green);
    }

    public void setThiefLocation(int row, int col){
        thiefRow = row;
        thiefCol = col;
    }
}
// Attributions for the assets used in this project:
// https://www.sprinttosave.com/product-p-674011.html
