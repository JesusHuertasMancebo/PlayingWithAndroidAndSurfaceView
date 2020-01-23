package es.b.playingwithandroidandsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyBallSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    int radi = 50;
    int x = 50;
    int y = 50;
    private Paint circle;

    //To control the animation
    private MyAnimationThread animThread = null;
    private long pressStartTime;
    private float pressedX;
    private float pressedY;

    //Constructores
    public MyBallSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        //Pintamos el círculo de azul en el constructor y luego lo cambiamos en cualquier otro método
        circle = new Paint();
        circle.setColor(Color.BLUE);
    }

    public MyBallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBallSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyBallSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // The method for drawing the animation
    public void newDraw(Canvas canvas) {
        // Paint canvas objects: circle, rectangle, text … }
        int width = canvas.getWidth(), high = canvas.getHeight();
        int middleW = width / 2, middleH = high / 2;
        canvas.drawColor(Color.WHITE);
        circle.setStyle(Paint.Style.FILL);
        //canvas.drawCircle( middleW, middleH, radi, circle );
        canvas.drawCircle( x, y, radi, circle );
    }

    private long timeDown = 0;
    private long timeUp;
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {// Action down detected
                if (radi < 100) {
                    radi = radi + 20;
                } else {
                    radi = 10;
                }
                x = (int) event.getX();
                y = (int) event.getY();

                timeDown = System.currentTimeMillis();
                System.out.println("TimeDown: " + timeDown);
                break;
            }
            case MotionEvent.ACTION_UP: {
                   timeUp = System.currentTimeMillis();

                if (timeUp - timeDown > 3000 && timeDown > 0) {
                    System.out.println("Simulation long click");
                    //Move the circle to the left top position
                    x = 100;
                    y = 100;
                } else {
                    x = (int) event.getX();
                    y = (int) event.getY();
                }


                if (timeDown - timeUp < 300 && timeDown > 0) {
                    System.out.println("Simulation Double click");
                    circle.setColor(Color.RED);

                }



                System.out.println("TimeUp: " + timeUp);
                System.out.println("Diferencia: " + (timeUp - timeDown));
                break;
            }
        }
        return true;
    }

        //Metodos
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
// To create and run the thread to control the animation
        if (animThread!=null) return;
        animThread = new MyAnimationThread(getHolder());
        animThread.start(); // To run the animation
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopThread();
    }

    public void stopThread() {
        animThread.stop = true;
    }

    // The inner thread class
    private class MyAnimationThread extends Thread {
        public boolean stop = false;
        private SurfaceHolder surfaceHolder;
        // Constructor
        public MyAnimationThread(SurfaceHolder surfaceHolder ) {
            this.surfaceHolder = surfaceHolder;
        }
        // Thread operations
        public void run() {
            while (!stop) {
// TODO: New values for the attributes: position, colors, ...
// New painting on the canvas
                Canvas c = null;
                try { // Getting the canvas for drawing
                    c = surfaceHolder.lockCanvas(null);
// Drawing into the canvas
                    synchronized (surfaceHolder) { newDraw (c); }
                } finally { // Showing the canvas on the screen
                    if (c != null) surfaceHolder.unlockCanvasAndPost(c);
                }
            } // while
        }// run
    }// My Animation Thread
}
