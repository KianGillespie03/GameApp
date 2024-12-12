package com.example.gameapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class HighScoresActivity extends AppCompatActivity {

    private Database dbHelper;
    private ListView lvHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        dbHelper = new Database(this);
        lvHighScores = findViewById(R.id.lvHighScores);

        // Retrieve high scores from the database
        Cursor cursor = dbHelper.getAllHighScores();
        // Mapping columns to UI elements
        String[] columns = new String[] {"name", "score"};
        int[] toViews = new int[] {R.id.tvName, R.id.tvScore};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_items, cursor, columns, toViews, 0);
        lvHighScores.setAdapter(adapter);
    }
}
