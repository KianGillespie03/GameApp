package com.example.gameapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
        handler.post(new Runnable() {
            int index = 0;

            @Override
            public void run() {
                if (index < colorSequence.size()) {
                    String color = colorSequence.get(index);

                    // Highlight the button
                    highlightButton(color);

                    // Schedule button reset after 500ms
                    handler.postDelayed(() -> resetButton(color), 500);

                    // Schedule the next color after 1 second
                    index++;
                    handler.postDelayed(this, 1000);
                } else {
                    // Sequence display complete, enable user interaction
                    onSequenceDisplayed();
                }
            }
        });
    }

    private void onSequenceDisplayed() {
        // Enable tilt gesture detection
        enableTiltDetection();

        Toast.makeText(this, "Replicate the sequence by tilting the phone!", Toast.LENGTH_SHORT).show();
    }

    private void enableTiltDetection() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener tiltListener = new SensorEventListener() {
            private int userInputIndex = 0;

            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                String direction = null;
                if (y < -5) {
                    direction = "RED"; // North
                } else if (y > 5) {
                    direction = "GREEN"; // South
                } else if (x > 5) {
                    direction = "BLUE"; // East
                } else if (x < -5) {
                    direction = "YELLOW"; // West
                }

                if (direction != null) {
                    checkUserInput(direction, userInputIndex++);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Not used
            }
        };

        // Register the accelerometer listener
        sensorManager.registerListener(tiltListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void checkUserInput(String direction, int index) {
        if (index >= colorSequence.size()) {
            return; // No more input expected
        }

        if (colorSequence.get(index).equals(direction)) {
            if (index == colorSequence.size() - 1) {
                // Sequence matched successfully
                onSequenceMatched();
            }
        } else {
            // Sequence mismatched
            onGameOver();
        }
    }

    private void onSequenceMatched() {
        // Reset user input index
        handler.post(() -> {
            colorSequence.clear();
            currentRound++;
            startGame(); // Start the next round
        });
    }

    private void onGameOver() {
        handler.post(() -> {
            Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
            startActivity(intent);
            finish();
        });
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
