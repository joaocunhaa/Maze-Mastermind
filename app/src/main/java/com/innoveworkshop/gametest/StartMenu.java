package com.innoveworkshop.gametest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartMenu extends AppCompatActivity {

    private Button startGame;
    private Button quitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("PLAY BUTTON", "started game");
                Intent intent = new Intent(StartMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        quitGame = (Button) findViewById(R.id.quitButton);
        quitGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("QUIT BUTTON", "quit game");
                finish();
            }
        });
    }
}