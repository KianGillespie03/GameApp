package com.example.gameapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_HIGH_SCORES = "high_scores";

    // High Scores Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HIGH_SCORES_TABLE = "CREATE TABLE " + TABLE_HIGH_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);

        // Create tables again
        onCreate(db);
    }

    // Add a new high score
    public void addHighScore(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertQuery = "INSERT INTO " + TABLE_HIGH_SCORES + " (" + KEY_NAME + ", " + KEY_SCORE + ") VALUES (?, ?)";
        db.execSQL(insertQuery, new Object[]{name, score});
        db.close();
    }

    // Get all high scores
    public Cursor getAllHighScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HIGH_SCORES + " ORDER BY " + KEY_SCORE + " DESC LIMIT 5", null);
    }
}
