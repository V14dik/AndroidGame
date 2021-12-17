package com.example.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Wall implements IDrawable {

    RectF wall;

    public Wall(float left, float top, float right, float bottom){
        wall = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        canvas.drawRect(wall, paint);
    }

    @Override
    public boolean contactPlayer(PlayerBall playerBall) {
        return RectF.intersects(wall, playerBall.hitBox);
    }
}
