package com.example.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class PlayerBall implements IDrawable {
    RectF hitBox;
    Paint paint;
    float startX;
    float startY;
    float currentX;
    float currentY;
    float radius;

    public PlayerBall(float currentX, float currentY, float radius){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        this.startX = currentX;
        this.startY = currentY;
        this.currentX = currentX;
        this.currentY = currentY;
        this.radius = radius;
        hitBox = new RectF(currentX - radius, currentY - radius, currentX + radius, currentY + radius);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(currentX, currentY, radius, paint);
    }

    @Override
    public boolean contactPlayer(PlayerBall playerBall) {
        return false;
    }

    public void move(float x, float y, float maximumX, float maximumY){
        currentX += x;
        currentY += y;
        if(currentX + radius > maximumX || currentX - radius < 0)
            currentX -= x;
        if(currentY + radius > maximumY || currentY - radius < 0)
            currentY -= y;
        hitBox.left = currentX - radius;
        hitBox.top = currentY - radius;
        hitBox.right = currentX + radius;
        hitBox.bottom = currentY + radius;
    }

    public void moveToStart(){
        currentX = startX;
        currentY = startY;
    }
}
