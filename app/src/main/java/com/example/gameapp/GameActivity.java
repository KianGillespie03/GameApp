package com.example.gameapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private List<String> colorSequence; // Holds the current sequence of colors
    private int currentRound = 1; // Tracks the round
    private Button redButton, blueButton, greenButton, yellowButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize buttons
        redButton = findViewById(R.id.RedButton);
        blueButton = findViewById(R.id.BlueButton);
        greenButton = findViewById(R.id.GreenButton);
        yellowButton = findViewById(R.id.YellowButton);

        handler = new Handler();
        colorSequence = new ArrayList<>();

        startGame();
    }

    private void startGame() {
        generateSequence();
        displaySequence();
    }

    private void generateSequence() {
        Random random = new Random();
        String[] colors = {"RED", "BLUE", "GREEN", "YELLOW"};

        // Add two new random colors for the current round
        for (int i = 0; i < 2; i++) {
            colorSequence.add(colors[random.nextInt(colors.length)]);
        }
    }

    private void displaySequence() {
        new Thread(() -> {
            for (int i = 0; i < colorSequence.size(); i++) {
                String color = colorSequence.get(i);

                // Highlight the button for the current color
                runOnUiThread(() -> highlightButton(color));

                // Wait for 1 second before resetting the button
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Reset the button background
                runOnUiThread(() -> resetButton(color));
            }

            // Logic for user interaction goes here (after sequence display)
        }).start();
    }

    private void highlightButton(String color) {
        switch (color) {
            case "RED":
                redButton.setBackgroundResource(R.drawable.red_button_highlighted);
                break;
            case "BLUE":
                blueButton.setBackgroundResource(R.drawable.blue_button_highlighted);
                break;
            case "GREEN":
                greenButton.setBackgroundResource(R.drawable.green_button_highlighted);
                break;
            case "YELLOW":
                yellowButton.setBackgroundResource(R.drawable.yellow_button_highlighted);
                break;
        }
    }

    private void resetButton(String color) {
        switch (color) {
            case "RED":
                redButton.setBackgroundResource(R.drawable.red_button_background);
                break;
            case "BLUE":
                blueButton.setBackgroundResource(R.drawable.blue_button_background);
                break;
            case "GREEN":
                greenButton.setBackgroundResource(R.drawable.green_button_background);
                break;
            case "YELLOW":
                yellowButton.setBackgroundResource(R.drawable.yellow_button_background);
                break;
        }
    }
}
