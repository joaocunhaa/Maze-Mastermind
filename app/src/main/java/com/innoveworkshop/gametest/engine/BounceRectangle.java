package com.innoveworkshop.gametest.engine;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BounceRectangle extends GameObject implements Caged {
    public float width;
    public float height;
    public Paint paint;

    public BounceRectangle(Vector position, float width, float height, int color) {
        super(position);
        this.width = width;
        this.height = height;

        // Set up the paint.
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate the positions of the points that form the rectangle.
        float left = position.x - (width / 2);
        float top = position.y - (height / 2);
        float right = position.x + (width / 2);
        float bottom = position.y + (height / 2);

        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public boolean hitLeftWall() {
        return (position.x - width / 2) <= gameSurface.getWidth();
    }

    @Override
    public boolean hitRightWall() {
        return (position.x + width / 2) >= gameSurface.getWidth();
    }

    @Override
    public boolean isFloored() {
        return (position.y + height / 2) >= gameSurface.getHeight();
    }
}