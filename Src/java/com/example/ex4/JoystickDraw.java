package com.example.ex4;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static android.view.KeyEvent.ACTION_UP;
import static android.view.MotionEvent.ACTION_CANCEL;

public class JoystickDraw extends View {
    private Paint SmallCellPaint;
    private Paint MadiumPaint;
    private Paint BigPaint;
    private int joysticWidth, joysticHeight,startBigWidth,startBigHeight ;

    private double x,y;
    private TcpClient tcpClient;





    public JoystickDraw(Context context, TcpClient tcpClient) {
        super(context);
        SmallCellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        SmallCellPaint.setColor(Color.GRAY);
        SmallCellPaint.setStyle(Paint.Style.FILL);
        MadiumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        MadiumPaint.setColor(Color.BLACK);
        MadiumPaint.setStyle(Paint.Style.FILL);
        BigPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BigPaint.setColor(Color.DKGRAY);
        BigPaint.setStyle(Paint.Style.FILL);
        this.tcpClient=tcpClient;


    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int xpad = getPaddingLeft() + getPaddingRight();
        int ypad = getPaddingTop() + getPaddingBottom();
        joysticWidth = w - xpad;
        joysticHeight = h - ypad;
        x= joysticWidth/2;
        y= joysticHeight/2;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int k = (Math.min(joysticWidth, joysticHeight));
        startBigWidth=getPaddingLeft();
        startBigHeight=getPaddingTop();
        if (joysticHeight>joysticWidth){
            startBigHeight =startBigHeight+(joysticHeight-joysticWidth)/2;
        }else{
            startBigWidth=startBigHeight+(-joysticHeight+joysticWidth)/2;
        }
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.noplane4)
                , null,new RectF(0,0,joysticWidth,joysticHeight), null);
        RectF rect = new RectF(startBigWidth +5, startBigHeight+5, startBigWidth+k -5 , startBigHeight+k -5 );
        canvas.drawOval(rect, BigPaint);
         rect = new RectF(startBigWidth+k/5 +5, startBigHeight+k/5+5, startBigWidth+k*4/5 -5 , startBigHeight+k*4/5 -5 );
        canvas.drawOval(rect, MadiumPaint);

         rect = new RectF( (float)x -k / 6 , (float)y -k /6 ,
                 (float)x +k / 6 ,(float)y +k /6 );
        canvas.drawOval(rect, SmallCellPaint);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int r= (Math.min(joysticWidth, joysticHeight))/2;
        if(action==ACTION_CANCEL || action==ACTION_UP){
            x= joysticWidth/2;
            y= joysticHeight/2;
            tcpClient.sendMessage(0,0);
        }else {
            double tempX = event.getX();
            double tempY = event.getY();

            if(Math.sqrt(Math.pow( tempX - startBigWidth - r,2)+Math.pow(tempY - startBigHeight - r,2)) + r/3<=r ){
                x=tempX;
                y=tempY;

                double aileron = (tempX - startBigWidth - r)/(r*2/3);
                double elevator = -(tempY - startBigHeight - r)/(r*2/3);
                tcpClient.sendMessage(elevator,aileron);
            }
        }
        invalidate();


        return true;
    }





}
