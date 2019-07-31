package com.example.ex4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void connectClick(View view)
    {
        TextView appCompatAutoCompleteTextView = (TextView) findViewById(R.id.ipText);
        String ip =  appCompatAutoCompleteTextView.getText().toString();
         appCompatAutoCompleteTextView = (TextView) findViewById(R.id.portText);
        String port =  appCompatAutoCompleteTextView.getText().toString();

        Intent intent = new Intent( MainActivity.this, My_Joystick.class);

        intent.putExtra("ip",ip);
        intent.putExtra("port",port);
        startActivity(intent);

    }


}
