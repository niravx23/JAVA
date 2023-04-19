package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import android.os.Bundle;

public class Exoplayer extends AppCompatActivity {
    StyledPlayerView playerView ;
    ExoPlayer player;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);

        playerView = (StyledPlayerView)findViewById(R.id.exo);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri("https://www.youtube.com/watch?v=Tkgad9gngOQ");
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}