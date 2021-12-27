package cn.itcast.experiment.MyData;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Circle {
    float x,y,radius;

    float [][]coorindate =
    {  {100, 150}, {250, 150}, {400, 150},
       {100,350}, {250,350}, {400,350},
       {100, 550}, {250, 550}, {400, 550} };

    public Circle(float x, float y, float radius)
    {
        this.x=x;
        this.y=y;
        this.radius=radius;
    }

    public void draw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawCircle(x,y,radius,paint);
    }
    public void undraw(Canvas canvas, float x, float y)
    {
        Paint paint=new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(x,y,radius,paint);
    }

    public void position()
    {
        Random r = new Random();

        int i = r.nextInt(9);
        this.x = coorindate[i][0];
        this.y = coorindate[i][1];
    }

    public void keepPosition(Canvas canvas, float x, float y) {
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);

        canvas.drawCircle(x,y,radius,paint);
    }


    public boolean isShot(float touchedX, float touchedY) {
        double distance=(touchedX-this.x)*(touchedX-this.x)+(touchedY-this.y)*(touchedY-this.y);
        return distance<radius*radius;
    }

    public float getX() { return this.x; }
    public float getY() { return this.y; }

    public void mov() {
        if (x <= 20)
            x = 400;
        else
            x -= 20;
    }
}
