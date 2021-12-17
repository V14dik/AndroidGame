package com.example.game.objects;

import android.content.Context;

public class LevelFirst extends LevelModel {
    public  LevelFirst (Context context) {
        super(context);
        //Шарик
        drawables.add(new PlayerBall(500, 1730, 50));

        //Метка выхода
        drawables.add(new ExitMark(80, 0, 280, 190));

        //Левая стенка
        drawables.add(new Wall(0, 0, 80, 1920));

        /*
        //Нижняя стенка
        drawables.add(new Wall(0, 1830, 1080, 1920));

        //Правая стенка
        drawables.add(new Wall(1000, 0, 1080, 1830));

        //Центральная стенка над шариком с выходом справа
        drawables.add(new Wall(0, 1000, 850, 1080));

        //Стенка над шариком выше центральной с выходом слева
        drawables.add(new Wall(230, 720, 1080, 800));

        //Нижний противник
        Enemy enemy = new Enemy(400, 1300, 700, 1400, 80, 1000);
        drawables.add(enemy);
        movables.add(enemy);

        //Нижняя левая стенка
        drawables.add(new Wall(80, 1430, 400, 1510));

        //Нижняя правая стенка
        drawables.add(new Wall(600, 1430, 1000, 1510));

        //Верхний противник
        enemy = new Enemy(80, 300, 130, 500, 80, 400);
        drawables.add(enemy);
        movables.add(enemy);

        //Стенка справа от верхнего противника
        drawables.add(new Wall(400, 190, 480, 500));

        //Горизонтальная верхняя стенка справа от верхнего противника
        drawables.add(new Wall(480, 420, 850, 500));

        //Верхняя вертикальная стенка по центру
        drawables.add(new Wall(650, 0, 730, 240));*/
    }
}
