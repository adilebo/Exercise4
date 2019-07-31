package com.example.ex4;

import android.util.Log;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    private   Socket socket;
    public TcpClient(final String ip, final String port){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    InetAddress serverAddr = InetAddress.getByName(ip);
                    int portNum =Integer.parseInt(port);

                    socket = new Socket(serverAddr, portNum);


                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public void sendMessage(final double send_elevator, final double  send_aileron) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket!=null) {
                        OutputStream output = socket.getOutputStream();

                        PrintWriter writer = new PrintWriter(output, true);
                        writer.println("set controls/flight/elevator " + send_elevator + "\r\n");
                        writer.println("set controls/flight/aileron " + send_aileron + "\r\n");
                    }
                } catch (Exception e) {
                    Log.e("TCP", "S: Error", e);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public void close(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
        socket.close();
                } catch (Exception e) {
                    Log.e("TCP", "S: Error", e);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
