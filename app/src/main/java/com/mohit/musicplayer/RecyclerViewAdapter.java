package com.mohit.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.EntityIterator;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<viewHolder> {
    ArrayList<model> data;
    public SimpleExoPlayer player;
    Context context;

    public RecyclerViewAdapter(ArrayList<model> data, Context context) {
        this.data = data;
        this.context = context;
        player = new SimpleExoPlayer.Builder(context).build();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.song_name.setText(data.get(position).getSong_name());
        holder.song_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(player.isPlaying())
                    player.stop();

                String mPath = data.get(position).getSong_path();
                Toast.makeText(context, mPath, Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse(mPath);
                MediaItem mediaItem = MediaItem.fromUri(uri);
                player.setMediaItem(mediaItem);
                player.prepare();
                player.play();


            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public void buttonClicked(int btnNumber) {
        switch (btnNumber)
        {
            case 1 : stop_song();
                    break;
            case 2 : play_song();
                    break;
            case 3 : pause_song();
                    break;
        }

    }

    public void play_song() {
        if(player.isPlaying())
            Toast.makeText(context,"Song is Already Playing",Toast.LENGTH_LONG).show();
        else
            player.play();
    }

    public void stop_song() {
        if (player.isPlaying())
            player.stop(true);
        else
            Toast.makeText(context, "No Song is Playing", Toast.LENGTH_LONG).show();

    }

    public void pause_song() {
        if(player.isPlaying())
            player.pause();
        else
            Toast.makeText(context,"No Song is Playing",Toast.LENGTH_LONG).show();
    }
}

