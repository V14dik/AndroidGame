package com.example.game.objects;

import android.graphics.Canvas;

public interface IDrawable {
    public void draw(Canvas canvas);
    public boolean contactPlayer(PlayerBall playerBall);
}
