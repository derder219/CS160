package com.example.lukeestes.volleytester;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Representative> repList;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Representative> repInputList, Context mContext) {
        this.repList = repInputList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Representative repSelected = repList.get(position);
        // Set string to display based on rep type (house or senator)
        String textToDisplay;
        if (repSelected.type.equals("representative")) {
            textToDisplay = "House Rep: " + repSelected.name;
        } else {
            textToDisplay = "Senator: " + repSelected.name;
        }
        //Set up background color of the textview corresponding to party of rep
        Resources res = holder.itemView.getResources();
        if(repSelected.party.equals("Republican")) {
            int colorToShow = res.getColor(R.color.RepubRed);
            holder.repNameView.setBackgroundColor(colorToShow);
        } else {
            int colorToShow = res.getColor(R.color.DemocBlue);
            holder.repNameView.setBackgroundColor(colorToShow);
        }
        holder.repNameView.setText(textToDisplay);
        //TODO: Set on click listener for each rep to go to their detailed view
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailedView.class);
                intent.putExtra("Type", repSelected.type);
                intent.putExtra("CTU", repSelected.contactURL);
                intent.putExtra("CTF", repSelected.contactForm);
                intent.putExtra("DistrictNumber", repSelected.districtNumber);
                intent.putExtra("Name", repSelected.name);
                intent.putExtra("Party", repSelected.party);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView repNameView;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            repNameView = itemView.findViewById(R.id.repName);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
