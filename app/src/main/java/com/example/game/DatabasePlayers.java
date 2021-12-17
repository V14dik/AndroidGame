package com.example.game;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

class DatabasePlayers implements Serializable {

    private String databaseName = "game.db";
    private String tableName = "Players";
    private Context context;

    DatabasePlayers(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        return context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
    }

    public Boolean addPlayer(Player newPlayer) {
        return makeQuery("INSERT INTO " + tableName + " VALUES ('" + newPlayer.name + "', " + newPlayer.score + ");");
    }

    public Boolean removePlayer(Player rmPlayer) {
        return makeQuery("DELETE FROM " + tableName + " WHERE (Name = '" + rmPlayer.name + "');");
    }

    public Boolean updatePlayer(Player updPlayer) {
        return makeQuery("UPDATE " + tableName + " SET Score = " + updPlayer.score + " WHERE Name = '"+ updPlayer.name + "';");
    }

    public Boolean createDatabase() {
        makeQuery("DROP TABLE IF EXISTS " + tableName + ";");
        makeQuery("CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(Name TEXT NOT NULL PRIMARY KEY, " +
                "Score INTEGER);");
        makeQuery("INSERT INTO " + tableName + " VALUES " +
                "('Somebody', 3700), " +
                "('That', 520), " +
                "('I', 310), " +
                "('Used', 1700), " +
                "('To', 750), " +
                "('Know', 800);");
        return true;
    }

    public ArrayList<Player> getPlayers() {
        SQLiteDatabase db = openDatabase();
        ArrayList<Player> players = new ArrayList<Player>();
        Cursor query = null;
        try{
            String currentQuery = "SELECT Name, Score " +
                    " AS result FROM " + tableName +
                    " ORDER BY Score DESC;";
            query = db.rawQuery(currentQuery, null);
            while (query.moveToNext())
                players.add(new Player(query.getString(0), query.getInt(1)));
        }
        catch (Exception e) {
            Log.d("getPlayers", e.getMessage().toString());
        }
        query.close();
        db.close();
        return players;
    }

    private Boolean makeQuery(String sql) {
        SQLiteDatabase db = openDatabase();
        try{
            db.execSQL(sql);
            return true;
        }
        catch (Exception e) {
            Log.d("makeQuery", e.getMessage().toString());
            return false;
        }
        finally {
            db.close();
        }
    }
}
