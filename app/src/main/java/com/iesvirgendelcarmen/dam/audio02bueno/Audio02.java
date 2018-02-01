package com.iesvirgendelcarmen.dam.audio02bueno;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Audio02 extends AppCompatActivity {
    TextView cancion,duracion;
    ImageView vitru;
    ImageButton retroceder,pausar,reproducir,avanzar;
    SeekBar barra;
    MediaPlayer mediaPlayer;
    double tiempoTotal,tiempoPasado;
    int avance=2000,retroceso=2000;

    private Handler controladorTiempo=new Handler();

    private Runnable actualizaBarra=new Runnable() {
        @Override
        public void run() {
            tiempoPasado=mediaPlayer.getCurrentPosition();
            barra.setProgress((int)tiempoPasado);
            double tiempoRestante=tiempoTotal-tiempoPasado;
            duracion.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) tiempoRestante),
                    TimeUnit.MILLISECONDS.toSeconds((long) tiempoRestante)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    tiempoRestante))));
            controladorTiempo.postDelayed(this,100);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio02);

        retroceder=(ImageButton)findViewById(R.id.retroceder);
        pausar=(ImageButton)findViewById(R.id.pausar);
        reproducir=(ImageButton)findViewById(R.id.reproducir);
        avanzar=(ImageButton)findViewById(R.id.avanzar);

        cancion=(TextView)findViewById(R.id.cancion);
        barra=(SeekBar)findViewById(R.id.barra);
        barra.setClickable(false);

        mediaPlayer=MediaPlayer.create(this,R.raw.codigo_davinci);

        tiempoTotal=mediaPlayer.getDuration();
        barra.setMax((int)tiempoTotal);

        reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                tiempoPasado=mediaPlayer.getCurrentPosition();
                barra.setProgress((int)tiempoPasado);
                cancion.setText("codigo_davinci.mp3");
                controladorTiempo.postDelayed(actualizaBarra,100);
            }
        });

        pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((tiempoPasado+avance)<=tiempoTotal){
                    tiempoPasado=tiempoPasado+avance;
                    mediaPlayer.seekTo((int)tiempoPasado);
                }
            }
        });

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((tiempoPasado-retroceso)>0){
                    tiempoPasado=tiempoPasado-retroceso;
                    mediaPlayer.seekTo((int)tiempoPasado);
                }
            }
        });
    }







}