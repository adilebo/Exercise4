package com.example.ex4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.Socket;

public class My_Joystick extends AppCompatActivity {

    private TcpClient tcpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String ip = getIntent().getStringExtra("ip");
        String port = getIntent().getStringExtra("port");
        tcpClient=new TcpClient(ip,port);
        JoystickDraw joystickDraw = new JoystickDraw(this,tcpClient );
        setContentView(joystickDraw);


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        tcpClient.close();
    }
}
