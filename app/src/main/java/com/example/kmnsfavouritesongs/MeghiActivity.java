package com.example.kmnsfavouritesongs;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MeghiActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    private TextView lefttime, righttime, songname, moviename;
    private Button play, previous, next;
    private SeekBar seekBar;
    private Thread thread;
    private ImageView artistname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meghi);
        setUpUI();

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.palpal);

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i);
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                int currentposition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();

                lefttime.setText(dateFormat.format(new Date(currentposition)));
                righttime.setText(dateFormat.format(new Date(duration)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setUpUI(){
        lefttime = findViewById(R.id.lefttime);
        righttime = findViewById(R.id.righttime);
        play = findViewById(R.id.mediaplay);
        previous = findViewById(R.id.mediaprevious);
        next = findViewById(R.id.medianext);
        seekBar = findViewById(R.id.seekBar);
        artistname = findViewById(R.id.photo);
        songname = findViewById(R.id.song_name);
        moviename = findViewById(R.id.song_film);
        play.setOnClickListener(this);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mediaprevious:
                changesong1();
                break;

            case R.id.mediaplay:
                if (mediaPlayer!=null && mediaPlayer.isPlaying()){
                    pauseMusic();
                }else{
                    playMusic();
                }
                break;

            case R.id.medianext:
                changesong();
                break;
        }
    }

    public void pauseMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            play.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void playMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
            updateThread();
            play.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }

    public void changesong(){
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.ero);
        mediaPlayer.start();
        updateThread();
        artistname.setImageResource(R.drawable.ero);
        songname.setText("EDO JARUGUTONDI");
        moviename.setText("Fida");

    }

    public void changesong1(){
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.palpal);
        mediaPlayer.start();
        updateThread();
        artistname.setImageResource(R.drawable.palpal);
        songname.setText("PAL PAL");
        moviename.setText("Pal Pal Dil Ke Paas");
    }

    public void updateThread(){
        thread = new Thread(){
            @Override
            public void run() {
                try{
                    while (mediaPlayer!=null && mediaPlayer.isPlaying()){
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int newcurrentposition = mediaPlayer.getCurrentPosition();
                                int newduration = mediaPlayer.getDuration();

                                seekBar.setMax(newduration);
                                seekBar.setProgress(newcurrentposition);

                                lefttime.setText(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaPlayer.getCurrentPosition())));
                                righttime.setText(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaPlayer.getDuration())));
                            }
                        });

                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        thread.interrupt();
        thread = null;

        super.onDestroy();
    }
}
