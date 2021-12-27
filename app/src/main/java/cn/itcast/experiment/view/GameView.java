package cn.itcast.experiment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import cn.itcast.experiment.MyData.Circle;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;

    public GameView(Context context) {
        super(context);
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
    }

    private void initView() {
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
    }

    private DrawThread drawThread;
    private boolean isTouched=false;
    private float touchedX=-1;
    private float touchedY=-1;
    private boolean click = false;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        drawThread = new DrawThread();
        drawThread.start();
        GameView.this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if( MotionEvent.ACTION_DOWN==motionEvent.getAction())
                {
                    touchedX=motionEvent.getX();
                    touchedY=motionEvent.getY();
                    isTouched=true;
                }
                return false;
            }
        });
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.myStop();
        try {
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DrawThread extends Thread
    {
        private final ArrayList<Circle> circle=new ArrayList<>();
        private boolean isStopped=false;

        public DrawThread()
        {
            circle.add(new Circle(400,350,50));
        }

        public void myStop()
        {
            isStopped=true;
        }
        @Override
        public void run() {
            super.run();
            Canvas canvas = null;

            while(!isStopped) {
                try {
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.WHITE);

                    float x = circle.get(0).getX();
                    float y = circle.get(0).getY();
                    for (int i = 0; i < 5000; ++i) {
                        circle.get(0).draw(canvas);
                        if (GameView.this.isTouched) {
                            if (circle.get(0).isShot(GameView.this.touchedX, GameView.this.touchedY)) {
                                for (int j = 0; j < 4000; ++j) {
                                    circle.get(0).undraw(canvas, x, y);
                                }
                                break;
                            }
                        }
                    }
                    circle.get(0).position();

                    //spriters.get(0).mov();






                    GameView.this.isTouched=false;

                } catch (Exception e) {

                } finally {
                    if (null != canvas) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

