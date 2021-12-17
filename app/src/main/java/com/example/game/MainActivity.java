package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button leaderboardButton;
    private Button exitButton;
    private Button registerButton;
    private Button backButton;
    private TextView playerName;
    private TextView info;
    private EditText createPlayer;
    private ListView leaderboard;
    private ArrayList<Player> players;
    private DatabasePlayers db = new DatabasePlayers(this);
    public Player currentPlayer = new Player("Guest", 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {
            e.getMessage();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.play_button);
        leaderboardButton = findViewById(R.id.leaderboard_button);
        exitButton = findViewById(R.id.exit_button);
        registerButton = findViewById(R.id.register_button);
        backButton = findViewById(R.id.back_to_menu_button);
        playerName = findViewById(R.id.player_name);
        info = findViewById(R.id.info);
        createPlayer = findViewById(R.id.create_account);
        leaderboard = findViewById(R.id.leaderboard_list);
        registerForContextMenu(leaderboard);

        try {
            db.getPlayers();
        }
        catch (Exception exception){
            db.openDatabase();
            db.createDatabase();
        }

        playerName.setText(getResources().getString(R.string.hello) + " " + currentPlayer.name);

        UpdateLeaderboard();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.choose:
                choosePlayer(info.position);
                return true;
            case R.id.delete:
                deletePlayer(players.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void choosePlayer(int position) {
        currentPlayer = players.get(position);
        playerName.setText(getResources().getString(R.string.hello) + " " + currentPlayer.name);
        Toast.makeText(getApplicationContext(), "Welcome, " + currentPlayer.name, Toast.LENGTH_SHORT).show();
    }

    private void deletePlayer(Player player) {
        db.removePlayer(player);
        UpdateLeaderboard();
    }

    private void UpdateLeaderboard() {
        players = db.getPlayers();
        ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this, R.layout.player, R.id.player_info, players){
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.player_info);
                textView.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
                return view;
            }
        };
        leaderboard.setAdapter(adapter);
    }

    public void OnRegisterClick(View view) {
        if (createPlayer.getText() != null) {
            db.addPlayer(new Player(createPlayer.getText().toString(), 0));
            createPlayer.setText("");
            UpdateLeaderboard();
        }
        else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    public void OnPlayButtonClick(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("player", currentPlayer);
        startActivity(intent);
        finish();
    }

    public void onExitClick(View view) {
        this.finish();
        this.onDestroy();
    }

    public void OnLeaderboardClick(View view) {
        playerName.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        leaderboardButton.setVisibility(View.INVISIBLE);
        exitButton.setVisibility(View.INVISIBLE);

        info.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        leaderboard.setVisibility(View.VISIBLE);
        createPlayer.setVisibility(View.VISIBLE);
    }

    public void OnBackClick(View view) {
        playerName.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        leaderboardButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.VISIBLE);

        info.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        leaderboard.setVisibility(View.INVISIBLE);
        createPlayer.setVisibility(View.INVISIBLE);
    }
}