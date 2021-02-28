package com.mohit.musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

public class SongsList extends AppCompatActivity {

    RecyclerView recyclerView;
    public Uri uri;
    public ImageView playbtn,pausebtn,stopbtn;
    RecyclerViewAdapter mAdapter;
    View.OnClickListener myListener;
    LottieAnimationView music_anim;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        //player = new SimpleExoPlayer.Builder(getApplicationContext()).build();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new RecyclerViewAdapter(dataQueue(),getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        playbtn = (ImageView)findViewById(R.id.iv_play);
        pausebtn = (ImageView)findViewById(R.id.iv_pause);
        stopbtn = (ImageView)findViewById(R.id.iv_stop);

        music_anim=(LottieAnimationView)findViewById(R.id.lottie_music_anim);


        myListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId())
                {
                    case R.id.iv_stop : mAdapter.buttonClicked(1);
                                        music_anim.pauseAnimation();
                                    break;
                    case R.id.iv_play : mAdapter.buttonClicked(2);
                                        music_anim.playAnimation();
                                    break;
                    case R.id.iv_pause : mAdapter.buttonClicked(3);
                                        music_anim.pauseAnimation();
                                     break;
                }
            }
        };

        playbtn.setOnClickListener(myListener);
        pausebtn.setOnClickListener(myListener);
        stopbtn.setOnClickListener(myListener);
    }


    public ArrayList<model> dataQueue()
    {
        ArrayList<model> dataHolder = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.TITLE,MediaStore.MediaColumns.DATA};
        Cursor audioCursor = getContentResolver().query(uri,projection,null,null,null);

        if(audioCursor!=null)
        {
            while(audioCursor.moveToNext())
            {
                model mObj = new model();
                String name = audioCursor.getString(0);
                String song_path = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                mObj.setSong_name(name);
                mObj.setSong_path(song_path);
                dataHolder.add(mObj);
            }
            audioCursor.close();
        }

        return dataHolder;
    }

}