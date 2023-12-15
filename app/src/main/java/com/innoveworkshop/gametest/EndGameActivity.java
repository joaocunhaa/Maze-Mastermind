package com.innoveworkshop.gametest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EndGameActivity extends AppCompatActivity {

    private Button startGame;
    private Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("PLAY BUTTON", "started game");
                finish();
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mainMenuButton = (Button) findViewById(R.id.quitButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MAIN MENU BUTTON", "went back to main menu");
                finish();
                Intent intent = new Intent(EndGameActivity.this, StartMenu.class);
                startActivity(intent);
            }
        });
    }
}