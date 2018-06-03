package com.esm.jogodaforcaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    App state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = ((App) getApplicationContext());
    }

    public void onClickConectar(View v){
        ProgressBar pbJogadores = findViewById(R.id.pbJogadores);
        EditText txtNome = findViewById(R.id.txtNome);
        String msg = txtNome.getText().toString();
        state.sendMessage(msg);
        new Thread(new ProgressUpdater(pbJogadores, v)).start();
    }

    class ProgressUpdater implements Runnable {
        ProgressUpdater(ProgressBar pb, View v){
            this.pb = pb;
            this.v = v;
            pb.setMax(3);
            pb.setVisibility(View.VISIBLE);
        }

        private ProgressBar pb;
        private View v;

        @Override
        public void run(){
            try{
                state.sendMessage("jogadores");
                String jogadores = state.task.getMessage();
                while(jogadores == null){
                    jogadores = state.task.getMessage();
                }
                while(Integer.parseInt(jogadores) < 3){
                    if(Integer.parseInt(jogadores) == 3){
                        updateProgress();
                        break;
                    }
                    Thread.sleep(1000);
                    state.sendMessage("jogadores");
                    jogadores = state.task.getMessage();
                }
                state.sendMessage("jogador");
            }catch(InterruptedException e){
                //erro no sleep do thread
            }finally{
                mudarTela();
            }
        }
        private void updateProgress(){
            if(pb != null){
                pb.setProgress(3);
            }
        }
        private void mudarTela(){
            state.sendMessage("jogador");
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){

            }
            String jogador = state.task.getMessage();
            Log.d("bosta: ", jogador);
            if(jogador.equals("adivinhador")){
                Intent intent = new Intent(v.getContext(), AguardarEscolha.class);
                v.getContext().startActivity(intent);
            }
            else if(jogador.equals("escolhedor")){
                Intent intent = new Intent(v.getContext(), escolherPalavra.class);
                v.getContext().startActivity(intent);
            }

        }

    }

}
