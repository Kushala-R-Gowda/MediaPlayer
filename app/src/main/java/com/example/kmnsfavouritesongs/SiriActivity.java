package com.example.kmnsfavouritesongs;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SiriActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer mediaPlayer;
    private ImageView artistimage;
    private TextView lefttime, righttime, songname, moviename;
    private SeekBar seekBar;
    private Button previous, play, next;
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siri);

        setUpUI();

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.khariath);

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
        artistimage = findViewById(R.id.photo);
        lefttime = findViewById(R.id.lefttime);
        righttime = findViewById(R.id.righttime);
        seekBar = findViewById(R.id.seekBar);
        previous = findViewById(R.id.mediaprevious);
        play = findViewById(R.id.mediaplay);
        next = findViewById(R.id.medianext);
        songname = findViewById(R.id.song_name);
        moviename = findViewById(R.id.song_film);
        previous.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mediaprevious:
                if(mediaPlayer!=null){
                    changeSong1();
                }
                break;

            case R.id.mediaplay:
                if(mediaPlayer.isPlaying()){
                    pauseMusic();
                }else {
                    playMusic();
                }
                break;

            case R.id.medianext:
                if(mediaPlayer!=null){
                    changeSong();
                }
                break;

        }
    }

    public void pauseMusic(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            play.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void playMusic(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            updateThread();
            play.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }

    public void changeSong(){
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.chennameraya);
        mediaPlayer.start();
        updateThread();
        artistimage.setImageResource(R.drawable.channamereya);
        songname.setText("CHANNA MEREYA");
        moviename.setText("Ae Dil Hai Mushkil");

    }

    public void changeSong1(){
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.khariath);
        mediaPlayer.start();
        updateThread();
        artistimage.setImageResource(R.drawable.khariat);
        songname.setText("KHARIATH PUCHO");
        moviename.setText("Chicchhore");
    }

    public void updateThread() {
        thread = new Thread() {
            @Override
            public void run() {

                try {
                    while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int newposition = mediaPlayer.getCurrentPosition();
                                int maxposition = mediaPlayer.getDuration();

                                seekBar.setMax(maxposition);
                                seekBar.setProgress(newposition);

                                lefttime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss")
                                        .format(new Date(mediaPlayer.getCurrentPosition()))));
                                righttime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss")
                                        .format(new Date(mediaPlayer.getDuration()))));
                            }
                        });
                    }

                } catch (InterruptedException e) {
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
