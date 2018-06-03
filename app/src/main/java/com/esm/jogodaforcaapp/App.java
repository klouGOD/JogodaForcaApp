package com.esm.jogodaforcaapp;

import android.app.Application;

public class App extends Application {

    public Async task;
    private static final int porta = 2222;
    private static final String serverIP = "192.168.1.36";

    @Override
    public void onCreate() {
        super.onCreate();
        task = new Async(serverIP, porta);
        task.execute();
    }

    public void conectar(){
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