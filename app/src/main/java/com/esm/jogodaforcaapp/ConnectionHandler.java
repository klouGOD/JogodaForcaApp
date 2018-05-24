package com.esm.jogodaforcaapp;

public interface ConnectionHandler {

    public void didReceiveData(String data);

    public void didDisconnect(Exception error);

    public void didConnect();
}