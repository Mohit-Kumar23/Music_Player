package com.mohit.musicplayer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolder extends RecyclerView.ViewHolder
{
    //public ImageView img_song;
    public TextView song_name;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        //img_song = (ImageView)itemView.findViewById(R.id.img_music);
        song_name = (TextView)itemView.findViewById(R.id.song_name);
    }
}
