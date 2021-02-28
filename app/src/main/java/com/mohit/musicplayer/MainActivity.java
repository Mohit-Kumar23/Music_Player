package com.mohit.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    Animation top_anim,bottom_anim;
    ImageView logo;
    LottieAnimationView loading_anim;
    public final static int STORAGE_PERMISSION_CODE=1;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        logo=(ImageView)findViewById(R.id.logo_img);
        loading_anim=(LottieAnimationView)findViewById(R.id.lottie_loading);

        top_anim=AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anim=AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        top_anim.setDuration(6000);
        bottom_anim.setDuration(6000);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    logo.setAnimation(top_anim);
                    loading_anim.setAnimation(bottom_anim);
                }
            },3000);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,SongsList.class);
                    startActivity(intent);
                }
            },6000);

            //Toast.makeText(this,"Permission Granted Successfully",Toast.LENGTH_SHORT).show();
        }
        else
        {
            requestStoragePermission();
        }

    }

    private void requestStoragePermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);
            alert_builder.setMessage("Permission Required to Access Media Files");
            alert_builder.setTitle("Permission Needed");
            alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                                                       new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                       STORAGE_PERMISSION_CODE);
                }
            });
            alert_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alert_builder.create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                                               new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                               STORAGE_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                logo.setAnimation(top_anim);
                loading_anim.setAnimation(bottom_anim);
                Intent intent1 = new Intent(this,SongsList.class);
                startActivity(intent1);
                //Toast.makeText(this,"Permission Granted Successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }
}