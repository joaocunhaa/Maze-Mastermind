package com.innoveworkshop.gametest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.innoveworkshop.gametest.engine.BounceRectangle;
import com.innoveworkshop.gametest.engine.Circle;
import com.innoveworkshop.gametest.engine.GameObject;
import com.innoveworkshop.gametest.engine.GameSurface;
import com.innoveworkshop.gametest.engine.HoleCircle;
import com.innoveworkshop.gametest.engine.Rectangle;
import com.innoveworkshop.gametest.engine.Vector;

public class MainActivity extends AppCompatActivity {
    protected GameSurface gameSurface;
    protected Game game;

    boolean movingUp = false;
    boolean movingDown = false;
    boolean movingRight = false;
    boolean movingLeft = false;
    int playerSpeed = 20;

    public enum CollisionSide {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    public enum CollisionSideBounce {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSurface = (GameSurface) findViewById(R.id.gameSurface);
        game = new Game();
        gameSurface.setRootGameObject(game);

        gameSurface.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Log.d("SWIPE UP", "swipe up");
                movingUp = true;
                movingDown = false;
                movingRight = false;
                movingLeft = false;
            }
            public void onSwipeRight() {
                Log.d("SWIPE RIGHT", "swipe right");
                movingUp = false;
                movingDown = false;
                movingRight = true;
                movingLeft = false;
            }
            public void onSwipeLeft() {
                Log.d("SWIPE LEFT", "swipe left");
                movingUp = false;
                movingDown = false;
                movingRight = false;
                movingLeft = true;
            }
            public void onSwipeBottom() {
                Log.d("SWIPE BOTTOM", "swipe bottom");
                movingUp = false;
                movingDown = true;
                movingRight = false;
                movingLeft = false;
            }
        });
    }

    class Game extends GameObject {
        public Circle circle;
        public Rectangle[] rectangles = new Rectangle[7];
        public BounceRectangle[] bounceRectangles = new BounceRectangle[1];
        public HoleCircle[] holeCircles = new HoleCircle[1];

        @Override
        public void onStart(GameSurface surface) {
            super.onStart(surface);

            // Player Circle
            if (circle == null) {
                circle = new Circle(85, surface.getHeight() - 85, 50, Color.GREEN);
                surface.addGameObject(circle);
            }

            // Corner Walls
            if (rectangles[0] == null) {
                rectangles[0] = new Rectangle(new Vector(0, surface.getHeight()),
                        surface.getWidth() * 2, 75, Color.rgb(102, 51, 0));
            }

            if (rectangles[1] == null) {
                rectangles[1] = new Rectangle(new Vector(0, 0),
                        surface.getWidth() * 2, 75, Color.rgb(102, 51, 0));
            }

            if (rectangles[2] == null) {
                rectangles[2] = new Rectangle(new Vector(0, 0),
                        75, surface.getHeight() * 2, Color.rgb(102, 51, 0));
            }

            if (rectangles[3] == null) {
                rectangles[3] = new Rectangle(new Vector(surface.getWidth(), 0),
                        75, surface.getHeight() * 2, Color.rgb(102, 51, 0));
            }

            // Walls
            if (rectangles[4] == null) {
                rectangles[4] = new Rectangle(new Vector(0, surface.getHeight() - 150),
                        500, 37, Color.rgb(102, 51, 0));
            }

            if (rectangles[5] == null) {
                rectangles[5] = new Rectangle(new Vector(480, surface.getHeight() - 150),
                        37, 500, Color.rgb(102, 51, 0));
            }

            if (rectangles[6] == null) {
                rectangles[6] = new Rectangle(new Vector(360, surface.getHeight() - 380),
                        37, 500, Color.rgb(102, 51, 0));
            }

            // Bounce Walls
            if (bounceRectangles[0] == null) {
                bounceRectangles[0] = new BounceRectangle(new Vector(0, 200),
                        surface.getWidth() * 2, 75, Color.BLUE);
            }

            // Hole Circles
            if (holeCircles[0] == null) {
                holeCircles[0] = new HoleCircle(surface.getWidth() / 2, surface.getHeight() / 2, 50, Color.BLACK);
            }

            // For loop to draw each rectangle
            for (int i = 0; i < rectangles.length; i++) {
                surface.addGameObject(rectangles[i]);
            }

            // For loop to draw each bounce rectangle
            for (int i = 0; i < bounceRectangles.length; i++) {
                surface.addGameObject(bounceRectangles[i]);
            }

            // For loop to draw each hole circle
            for (int i = 0; i < holeCircles.length; i++) {
                surface.addGameObject(holeCircles[i]);
            }
        }

        @Override
        public void onFixedUpdate() {
            super.onFixedUpdate();

            // For loop to detect collisions for each rectangle
            for (int i = 0; i < rectangles.length; i++) {

                CollisionSide temp = GetCollisionSide(circle.position.x, circle.position.y, circle.radius,
                        rectangles[i].position.x, rectangles[i].position.y, rectangles[i].width, rectangles[i].height);

                // Switch function to disable player movement in the direction of the collision
                switch(temp) {
                    case TOP:
                        movingDown = false;
                        break;
                    case BOTTOM:
                        movingUp = false;
                        break;
                    case RIGHT:
                        movingLeft = false;
                        break;
                    case LEFT:
                        movingRight = false;
                        break;
                }
            }

            // For loop to detect collisions for each bounce rectangle
            for (int i = 0; i < bounceRectangles.length; i++) {
                CollisionSideBounce temp = GetCollisionSideBounce(circle.position.x, circle.position.y, circle.radius,
                        bounceRectangles[i].position.x, bounceRectangles[i].position.y, bounceRectangles[i].width, bounceRectangles[i].height);

                // Switch function to disable player movement in the direction of the collision
                switch(temp) {
                    case TOP:
                        movingUp = true;
                        movingDown = false;
                        movingRight = false;
                        movingLeft = false;
                        break;
                    case BOTTOM:
                        movingUp = false;
                        movingDown = true;
                        movingRight = false;
                        movingLeft = false;
                        break;
                    case RIGHT:
                        movingUp = false;
                        movingDown = false;
                        movingRight = true;
                        movingLeft = false;
                        break;
                    case LEFT:
                        movingUp = false;
                        movingDown = false;
                        movingRight = false;
                        movingLeft = true;
                        break;
                }
            }

            // For loop to detect collisions for each hole circle
            for (int i = 0; i < holeCircles.length; i++) {
                HoleCircleCollision(circle.position.x, circle.position.y, circle.radius, holeCircles[i].position.x, holeCircles[i].position.y);
            }

            // If statements to enable collisions to stop the player
            if (!circle.hitRoof()) {
            } else { movingUp = false; }

            if (!circle.isFloored()) {
            } else { movingDown = false; }

            if (!circle.hitRightWall()) {
            } else { movingRight = false; }

            if (!circle.hitLeftWall()) {
            } else { movingLeft = false; }

            // Setting the positions for each player direction
            if (movingUp) { circle.setPosition(circle.position.x, circle.position.y - playerSpeed); }

            if (movingDown) { circle.setPosition(circle.position.x, circle.position.y + playerSpeed); }

            if (movingRight) { circle.setPosition(circle.position.x + playerSpeed, circle.position.y); }

            if (movingLeft) { circle.setPosition(circle.position.x - playerSpeed, circle.position.y); }

            // TEST
            if (!circle.isFloored() && !circle.hitRightWall() && !circle.isDestroyed())
            {
                //circle.setPosition(circle.position.x + 1, circle.position.y + 1);

            } else {
                //circle.destroy();
            }
        }

        // Rectangles Collisions Function
        private CollisionSide GetCollisionSide(float circleX, float circleY, float circleRadius,
                                               float rectX, float rectY, float rectWidth, float rectHeight) {
            // Calculate the closest point
            float closestX = Clamp(circleX, rectX - rectWidth / 2, rectX + rectWidth / 2);
            float closestY = Clamp(circleY, rectY - rectHeight / 2, rectY + rectHeight / 2);

            // Calculate the distance between the closest point and the ball position
            float distanceX = circleX - closestX;
            float distanceY = circleY - closestY;
            float distance = (float) Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

            // If the distance is smaller than the circle radius, the ball collided
            if (distance <= circleRadius) {
                float overlapX = circleRadius - Math.abs(distanceX);
                float overlapY = circleRadius - Math.abs(distanceY);

                // If the overlap y is smaller than the x, the ball collided in the y axis
                if (overlapX > overlapY) {
                    return (distanceY < 0) ? CollisionSide.TOP : CollisionSide.BOTTOM;
                }
                // If is not, the ball collided in the x axis
                else {
                    return (distanceX < 0) ? CollisionSide.LEFT : CollisionSide.RIGHT;
                }
            }
            return CollisionSide.NONE;
        }

        // Bounce Rectangles Collisions Function
        private CollisionSideBounce GetCollisionSideBounce(float circleX, float circleY, float circleRadius,
                                               float rectX, float rectY, float rectWidth, float rectHeight) {
            // Calculate the closest point
            float closestX = Clamp(circleX, rectX - rectWidth / 2, rectX + rectWidth / 2);
            float closestY = Clamp(circleY, rectY - rectHeight / 2, rectY + rectHeight / 2);

            // Calculate the distance between the closest point and the ball position
            float distanceX = circleX - closestX;
            float distanceY = circleY - closestY;
            float distance = (float) Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

            // If the distance is smaller than the circle radius, the ball collided
            if (distance <= circleRadius) {
                float overlapX = circleRadius - Math.abs(distanceX);
                float overlapY = circleRadius - Math.abs(distanceY);

                // If the overlap y is smaller than the x, the ball collided in the y axis
                if (overlapX > overlapY) {
                    return (distanceY < 0) ? CollisionSideBounce.TOP : CollisionSideBounce.BOTTOM;
                }
                // If is not, the ball collided in the x axis
                else {
                    return (distanceX < 0) ? CollisionSideBounce.LEFT : CollisionSideBounce.RIGHT;
                }
            }
            return CollisionSideBounce.NONE;
        }

        // Hole Circles Collisions Function
        private void HoleCircleCollision(float circleX, float circleY, float circleRadius,
                                               float holeCircleX, float holeCircleY) {
            // Calculate the distance between the closest point and the ball position
            float distanceX = circleX - holeCircleX;
            float distanceY = circleY - holeCircleY;
            float distance = (float) Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

            // If the distance is smaller than the circle radius, the ball collided
            if (distance <= circleRadius) {
                circle.destroy();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        private float Clamp(float value, float min, float max) {
            return Math.max(min, Math.min(max, value));
        }
    }
}