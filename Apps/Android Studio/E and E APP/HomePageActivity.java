package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class HomePageActivity extends AppCompatActivity {
    ImageView sr1, sr2, sr3, sr4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sr1 = (ImageView) findViewById(R.id.videoView3);
        sr2 = (ImageView) findViewById(R.id.videoView4);
        sr3 = (ImageView) findViewById(R.id.videoView5);
        sr4 = (ImageView) findViewById(R.id.videoView6);

        sr1.setOnClickListener(view -> {
            Intent intent1 =  new Intent(getApplicationContext(), Exoplayer.class);
            startActivity(intent1);
        });
        sr2.setOnClickListener(view -> {
            Intent intent1 =  new Intent(getApplicationContext(), Exoplayer.class);
            startActivity(intent1);
        });
        sr3.setOnClickListener(view -> {
            Intent intent1 =  new Intent(getApplicationContext(), Exoplayer.class);
            startActivity(intent1);
        });
        sr4.setOnClickListener(view -> {
            Intent intent1 =  new Intent(getApplicationContext(), Exoplayer.class);
            startActivity(intent1);
        });

    }

}