package com.example.gameapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private int score;
    private Button PlayAgainButton, HighScoreButton;
    private TextView ScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Intent intent = getIntent();
        score = intent.getIntExtra("SCORE", 0);

        PlayAgainButton = findViewById(R.id.PlayAgainButton);
        ScoreTextView = findViewById(R.id.ScoretextView);
        ScoreTextView.setText("Your score is " + score);

        PlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent2);
                finish();
            }
        });


    }
}
