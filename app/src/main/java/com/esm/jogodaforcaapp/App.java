package com.esm.jogodaforcaapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Application;

public class App extends Application {

    public Async task;
    private static final int porta = 2222;
    private static final String serverIP = "192.168.1.36";

    @Override
    public void onCreate() {
        super.onCreate();
        task = new Async(serverIP, porta, null);
        task.execute();
    }

    public void sendMessage(String msg){
        new Thread(new Message(msg)).start();
    }

    class Message implements Runnable{
        Message(String message){
            this.message = message;
        }
        private String message;
        @Override
        public void run() {
            task.write(message);
        }
    }

}