package com.esm.jogodaforcaapp;

/*ww  w . j  av  a2  s. c  om*/
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Async extends android.os.AsyncTask<Void, String, Exception> {
    private String url;
    private int port;
   // private ConnectionHandler connectionHandler;

    private BufferedReader in;
    private PrintStream out;
    private Socket socket;
    private boolean interrupted = false;

    private String TAG = getClass().getName();

    public Async(String url, int port, ConnectionHandler connectionHandler) {
        this.url = url;
        this.port = port;
        //this.connectionHandler = connectionHandler;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Exception result) {
        super.onPostExecute(result);
        Log.d(TAG, "Finished communication with the socket. Result = " + result);
        //TODO If needed move the didDisconnect(error); method call here to implement it on UI thread.
    }

    @Override
    protected Exception doInBackground(Void... params) {
        Exception error = null;

        try {
            Log.d(TAG, "Opening socket connection.");
            socket = new Socket();
            socket.connect(new InetSocketAddress(url, port));

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());

            //connectionHandler.didConnect();

            while(!interrupted) {
                String line = in.readLine();
                //connectionHandler.didReceiveData(line);
            }
        } catch (UnknownHostException ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } catch (IOException ex) {
            Log.d(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } catch (Exception ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } finally {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (Exception ex) {}
        }

        //connectionHandler.didDisconnect(error);
        return error;
    }

    public void write(final String data) {
        try {
            Log.d(TAG, "writ(): data = " + data);
            out.println(data);
        } catch (NullPointerException ex) {
            Log.e(TAG, "write(): " + ex.toString());
        } catch (Exception ex) {
            Log.e(TAG, "write(): " + ex.toString());
        }
    }

    public void disconnect() {
        try {
            Log.d(TAG, "Closing the socket connection.");

            interrupted = true;
            if(socket != null) {
                socket.close();
            }
            if(out != null & in != null) {
                out.close();
                in.close();
            }
        } catch (IOException ex) {
            Log.e(TAG, "disconnect(): " + ex.toString());
        }
    }
}