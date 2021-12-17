package com.example.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    public String name;
    public int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " \t" + score;
    }
}
