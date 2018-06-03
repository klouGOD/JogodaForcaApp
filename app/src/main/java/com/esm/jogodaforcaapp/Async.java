package com.esm.jogodaforcaapp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Async extends android.os.AsyncTask<Void, String, Exception> {
    private String url;
    private int port;

    private BufferedReader in;
    private PrintStream out;
    private Socket socket;
    private boolean interrupted = false;
    private String line;

    private String TAG = getClass().getName();

    protected Async(String url, int port) {
        this.url = url;
        this.port = port;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Exception result) {
        super.onPostExecute(result);
        Log.d(TAG, "Finished communication with the socket. Result = " + result);
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

            while(!interrupted) {
                line = in.readLine();
                Log.d(TAG, "rola suja de mendigo:" + line);
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
            } catch (Exception ex) {

            }
        }

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

    public String getMessage(){
        return line;
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