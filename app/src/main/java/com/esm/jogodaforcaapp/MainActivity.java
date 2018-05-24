package com.esm.jogodaforcaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickConectar(View v){
        EditText txtNome = (EditText) findViewById(R.id.txtNome);
        String msg = txtNome.getText().toString();
        App state = ((App) getApplicationContext());
        state.sendMessage(msg);
    }

}
