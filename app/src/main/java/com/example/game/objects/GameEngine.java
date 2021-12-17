package com.example.game.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.GameActivity;

import java.util.ArrayList;


public class GameEngine {
    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEventListener mySensorEventListener;
    private Context context;
    private LevelFirst levelModel; // Потом заменить на список LevelModel
    private Render render;
    private float[] values = new float[3];
    private SurfaceHolder.Callback callback;
    private SurfaceHolder surfaceHolder;

    private volatile boolean stopped;

    long time = System.nanoTime();

    Runnable threadRunnable = new Runnable() {
        @Override
        public void run() {
            while (!stopped)  {
                Canvas canvas;
                if (surfaceHolder == null || (canvas = surfaceHolder.lockCanvas()) == null) {
                    synchronized (GameEngine.this) {
                        try {
                            GameEngine.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (!levelModel.update(values[1], values[0])){
                        stop();
                        ((GameActivity)context).Exit();
                    }

                    render.draw(canvas, levelModel);

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    };

    public GameEngine(SurfaceView surfaceView, Context context) throws Exception {
        levelModel = new LevelFirst(context); // Тут поменять для списка LevelModel, если захочу список лвлов.
        render = new Render();
        this.context = context;

        Thread engineThread = new Thread(threadRunnable, "EngineThread");
        engineThread.start();

        callback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                GameEngine.this.surfaceHolder = surfaceHolder;
                synchronized (GameEngine.this) {
                    GameEngine.this.notifyAll();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                GameEngine.this.surfaceHolder = surfaceHolder;
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                GameEngine.this.surfaceHolder = null;
            }
        };

        surfaceView.getHolder().addCallback(callback);

        sensorManager = (SensorManager)context.getSystemService(AppCompatActivity.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) == null)
            throw new Exception("Sensor is null.");

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);


        mySensorEventListener = new SensorEventListener(){
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event == null) return;
                if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                    float c = 57.2957549575f;
                    float limit = 25f;
                    values = event.values.clone();
                    for(int i = 0; i < 2; i++) {
                        values[i] *= c;
                        if(Math.abs(values[i]) > limit)
                            values[i] = Math.signum(values[i]) * limit;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor p0, int p1){
                return;
            }
        };
        sensorManager.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        this.stopped = true;
        sensorManager.unregisterListener(mySensorEventListener, sensor);
    }
}
