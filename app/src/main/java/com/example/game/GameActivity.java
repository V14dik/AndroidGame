package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.util.Date;

import com.example.game.objects.GameEngine;

public class GameActivity extends AppCompatActivity {

    Player currentPlayer;
    SurfaceView surface;
    GameEngine engine;
    Date date;
    Long timeStart;
    private DatabasePlayers db = new DatabasePlayers(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPlayer = (Player)this.getIntent().getSerializableExtra("player");
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        date = new Date();
        timeStart = System.currentTimeMillis();

        surface = findViewById(R.id.surface);
        try {
            engine = new GameEngine(surface, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Exit(){
        Intent intent = new Intent(this, MainActivity.class);
        int score = (int)((System.currentTimeMillis() - timeStart) / 100);
        score = Math.max(score, 0);
        currentPlayer.score += (1000 - score);
        db.updatePlayer(currentPlayer);
        startActivity(intent);
        finish();
    }
}