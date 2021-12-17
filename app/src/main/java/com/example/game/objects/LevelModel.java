package com.example.game.objects;

import android.content.Context;

import com.example.game.Player;
import com.example.game.objects.IDrawable;
import com.example.game.objects.PlayerBall;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LevelModel {
    public List<IDrawable> drawables = new ArrayList<>();
    public List<IMovable> movables = new ArrayList<>();
    private float maximumX;
    private float maximumY;
    protected Context context;

    public LevelModel(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        maximumX = (float) metrics.widthPixels;
        maximumY = (float) metrics.heightPixels;
        this.context = context;
    }

    public boolean update(float x, float y) {
        PlayerBall playerBall = (PlayerBall) drawables.get(0);
        playerBall.move(0.5f * x, 0.5f * y, maximumX, maximumY);
        for (IDrawable drawable : drawables) {
            if (drawable.contactPlayer(playerBall)) {
                Object currentClass = drawable.getClass();
                if (currentClass == Wall.class) {
                    playerBall.move(-0.5f * x, -0.5f * y, maximumX, maximumY);
                }
                if (currentClass == Enemy.class){
                    playerBall.moveToStart();
                }
                else if (currentClass == ExitMark.class){
                    return false;
                }
            }
        }

        return  true;
    }

    public List<IDrawable> getDrawables() {
        return drawables;
    }
    public List<IMovable> getMovables() {
        return movables;
    }

    public float getMaximumX() {
        return maximumX;
    }

    public float getMaximumY() {
        return  maximumY;
    }
}
