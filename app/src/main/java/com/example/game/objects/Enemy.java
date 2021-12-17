package com.example.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Enemy implements IDrawable, IMovable {
    RectF enemy;
    int speed = 5;
    float leftBorder;
    float rightBorder;
    boolean direction = true; // true - вправо, left - влево

    public Enemy(float left, float top, float right, float bottom,
                 float leftBorder, float rightBorder){
        enemy = new RectF(left, top, right, bottom);
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(enemy, paint);
    }

    @Override
    public boolean contactPlayer(PlayerBall playerBall) {
        return RectF.intersects(enemy, playerBall.hitBox);
    }

    @Override
    public void move() {
        if (direction && enemy.right + speed < rightBorder){
            enemy.right += speed;
            enemy.left += speed;
        }
        else if (direction && enemy.right + speed >= rightBorder) {
            float width = enemy.right - enemy.left;
            enemy.right = rightBorder;
            enemy.left = enemy.right - width;
            direction = false;
        }
        else if (!direction && enemy.left - speed > leftBorder) {
            enemy.right -= speed;
            enemy.left -= speed;
        }
        else if (!direction && enemy.left - speed <= leftBorder) {
            float width = enemy.right - enemy.left;
            enemy.left = leftBorder;
            enemy.right = enemy.left + width;
            direction = true;
        }
    }
}
