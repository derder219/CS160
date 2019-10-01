package com.example.macbook.perkupapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
//import com.example.macbook.perkupapp.UnityPlayerActivity;
public class GamePage extends Activity {
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_games);

        ListView newsListView = findViewById(R.id.list_view);

        Game one = new Game(100, "Cafe Strada");
        Game two = new Game(50, "Shattuck Cinemas");
        Game three = new Game(150, "Toss");
        List<Game> games = new ArrayList<>();
        games.add(one);
        games.add(two);
        games.add(three);
        gameAdapter = new GameAdapter(this, games);
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(view.getContext(), com.example.macbook.perkupapp.UnityPlayerActivity.class);
//                startActivity(myIntent);
//            }
//        });
        newsListView.setAdapter(gameAdapter);
    }
}
