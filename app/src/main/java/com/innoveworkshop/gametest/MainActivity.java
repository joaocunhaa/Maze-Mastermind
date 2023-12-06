package com.innoveworkshop.gametest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.innoveworkshop.gametest.assets.DroppingRectangle;
import com.innoveworkshop.gametest.engine.Circle;
import com.innoveworkshop.gametest.engine.GameObject;
import com.innoveworkshop.gametest.engine.GameSurface;
import com.innoveworkshop.gametest.engine.Rectangle;
import com.innoveworkshop.gametest.engine.Vector;

public class MainActivity extends AppCompatActivity {
    protected GameSurface gameSurface;
    protected Button upButton;
    protected Button downButton;
    protected Button leftButton;
    protected Button rightButton;

    protected Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSurface = (GameSurface) findViewById(R.id.gameSurface);
        game = new Game();
        gameSurface.setRootGameObject(game);

        setupControls();
    }

    private void setupControls() {
        upButton = (Button) findViewById(R.id.up_button);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.circle.position.y -= 50;
            }
        });

        downButton = (Button) findViewById(R.id.down_button);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.circle.position.y += 50;
            }
        });

        leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.circle.position.x -= 50;
            }
        });

        rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.circle.position.x += 50;
            }
        });
    }

    class Game extends GameObject {
        public Circle circle;

        @Override
        public void onStart(GameSurface surface) {
            super.onStart(surface);

            circle = new Circle(surface.getWidth() / 2, surface.getHeight() / 2, 100, Color.RED);
            surface.addGameObject(circle);

            surface.addGameObject(new Rectangle(new Vector(surface.getWidth() / 3, surface.getHeight() / 3),
                    200, 100, Color.GREEN));

            surface.addGameObject(new DroppingRectangle(new Vector(surface.getWidth() / 3, surface.getHeight() / 3),
                    100, 100, 10, Color.rgb(128, 14, 80)));
        }

        @Override
        public void onFixedUpdate() {
            super.onFixedUpdate();

            if (!circle.isFloored() && !circle.hitRightWall() && !circle.isDestroyed()) {
                circle.setPosition(circle.position.x + 1, circle.position.y + 1);
            } else {
                //circle.destroy();
            }
        }
    }
}