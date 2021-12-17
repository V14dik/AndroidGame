package com.example.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.objects.IDrawable;
import com.example.game.objects.LevelModel;

public class Render {

    public void draw(Canvas canvas, LevelModel levelModel) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, levelModel.getMaximumX(),levelModel.getMaximumY(), new Paint(Paint.ANTI_ALIAS_FLAG));
        for (IMovable movable: levelModel.getMovables()){
            movable.move();
        }

        for (IDrawable drawable: levelModel.getDrawables()) {
            drawable.draw(canvas);
        }
    }
}