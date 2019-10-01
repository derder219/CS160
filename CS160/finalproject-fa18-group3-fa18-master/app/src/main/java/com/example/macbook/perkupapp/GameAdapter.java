package com.example.macbook.perkupapp;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Game> {
    private Context mContext;
    public GameAdapter(Context context, List<Game> games) {
        super(context, 0, games);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Game currentGame = getItem(position);
        TextView points = listItemView.findViewById(R.id.points);
        TextView location = listItemView.findViewById(R.id.location);

        Button button = listItemView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mContext.startActivity(intent);
            }
        });
        points.setText("Earn " + Integer.toString(currentGame.getNumPoints()) + " points at");
        location.setText(currentGame.getLocation());

        return listItemView;
    }
}
