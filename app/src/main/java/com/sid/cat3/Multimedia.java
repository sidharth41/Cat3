package com.sid.cat3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class Multimedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        VideoView vw=findViewById(R.id.videoview);

        MediaPlayer mp=new MediaPlayer();
        MediaController mc=new MediaController(this);
        mc.setAnchorView(vw);
       // File file=new File(Environment.getExternalStorageDirectory().getPath()+"/Download/Test.mp3");

            try {

                   mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Download/Test.mp3");
                   mp.prepare();
                   mp.start();


               Toast.makeText(this, ""+mp.isPlaying(), Toast.LENGTH_LONG).show();
           }

    catch(Exception e){
        Toast.makeText(this, ""+e.toString(), Toast.LENGTH_LONG).show();;}
           Uri uri= Uri.parse("/storage/emulated/0/Download/The Weeknd - Less Than Zero (Official Lyric Video).mp4");
        vw.setVideoURI(uri);
        vw.start();


}

}