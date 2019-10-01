package com.example.macbook.perkupapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EnvironmentChoose extends AppCompatActivity {

    Button beach;
    Button galaxy;
    Button forest;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_environment);

        TextView textview = (TextView) findViewById(R.id.appLogo);// adjust this line to get the TextView you want to change
        Typeface typeface = Typeface.createFromAsset(getAssets(),"AllertaStencil-Regular.ttf"); // create a typeface from the raw ttf
        textview.setTypeface(typeface);

        beach = findViewById(R.id.beach);
        galaxy = findViewById(R.id.galaxy);
        forest = findViewById(R.id.forest);
        back = findViewById(R.id.back);
        beach.setOnClickListener(new beachButton());
        galaxy.setOnClickListener(new galaxyButton());
        forest.setOnClickListener(new forestButton());
        back.setOnClickListener(new backButton());


    }


    private class beachButton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
//            Intent intent = new Intent();
//            intent.setClass(EnvironmentChoose.this, EnvironmentChoose.class);
//            startActivity(intent);
            // TODO
        }
    }
    private class galaxyButton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
//            Intent intent = new Intent();
//            intent.setClass(EnvironmentChoose.this, EnvironmentChoose.class);
//            startActivity(intent);
            // TODO
        }
    }
    private class forestButton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
//            Intent intent = new Intent();
//            intent.setClass(EnvironmentChoose.this, EnvironmentChoose.class);
//            startActivity(intent);
            // TODO
        }
    }

    private class backButton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(EnvironmentChoose.this, HomePage.class);
            startActivity(intent);
        }
    }
}
