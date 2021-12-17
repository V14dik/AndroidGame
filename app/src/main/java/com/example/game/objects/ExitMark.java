package com.example.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class ExitMark implements IDrawable {

    RectF exitMark;

    public ExitMark(float left, float top, float right, float bottom){
        exitMark = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRect(exitMark, paint);
    }

    @Override
    public boolean contactPlayer(PlayerBall playerBall) {
        return exitMark.contains(playerBall.hitBox);
    }
}
