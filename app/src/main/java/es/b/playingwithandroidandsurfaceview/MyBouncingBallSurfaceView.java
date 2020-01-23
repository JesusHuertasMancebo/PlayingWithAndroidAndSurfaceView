package es.b.playingwithandroidandsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyBouncingBallSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private BouncingBallAnimationThread bbThread = null;
    //The position of the ball
    private int x,y;

    //The vector speed of the ball
    private int xDirection = 20;
    private int yDirection = 20;

    private int radius = 20;
    private static int ballColor = Color.RED;

    Paint paint = new Paint();
    boolean primerClick = false;

    private int xPosition = 0;
    private int yPosition = 0;


    public MyBouncingBallSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public MyBouncingBallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBouncingBallSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyBouncingBallSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (bbThread != null) {
            return;
        }

        bbThread = new BouncingBallAnimationThread(getHolder());
        bbThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopThread();
    }

    private void stopThread() {
        bbThread.stop = true;
    }

    public class BouncingBallAnimationThread extends Thread{
        public boolean stop = true;
        private SurfaceHolder surfaceHolder;

        public BouncingBallAnimationThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }
        public void run(){
            while (!stop) {
// The movement of the ball
                x += xDirection;
                y += yDirection;
// The conditions: Bouncing on the left
                if (x < radius) {
                    x = radius;
                    xDirection = -xDirection;
                }
// Bouncing on the right
                if (x > getWidth() - radius) {
                    x = getWidth() - radius;
                    xDirection = -xDirection;
                }
// Bouncing on the top
                if (y < radius) {
                    y = radius;
                    yDirection = -yDirection;
                }
// Bouncing on the bottom
                if (y > getHeight() - radius) {
                    y = getHeight()-radius;
                    yDirection = -yDirection;
                }

                Canvas c = null;
                try { c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        newDraw (c);
                    }
                } finally { if (c != null) surfaceHolder.unlockCanvasAndPost(c); }
            }
        }
    }

    public void newDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setColor(ballColor);

        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
        canvas.drawCircle(x,y,radius,paint);
    }

    public boolean onTouchEvent(MotionEvent event){

        //Getting the position of the click
        xPosition = (int) event.getX(0);
        yPosition = (int) event.getY(0);

        switch (event.getAction()){
            //Action Down detected
            case MotionEvent.ACTION_DOWN:{
                if (primerClick){

                }else {

                }
                break;
            }
        }


        return true;
    }
}
