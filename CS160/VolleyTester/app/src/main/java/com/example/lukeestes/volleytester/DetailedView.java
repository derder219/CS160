package com.example.lukeestes.volleytester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class DetailedView extends AppCompatActivity {

    //Make view object to manipulate background color
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        //Change background color
        view= this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.BackgroundBlue);

        Intent intent = getIntent();
        String type = intent.getStringExtra("Type") + " ";
        String contactUrl = intent.getStringExtra("CTU") + " ";
        String contactForm = intent.getStringExtra("CTF") + " ";
        String districtNumber = intent.getStringExtra("DistrictNumber") + " ";
        String name = intent.getStringExtra("Name") + " ";
        String party = intent.getStringExtra("Party");

        TextView repTypeTitleView = findViewById(R.id.repTypeTitle);
        TextView repFullNameView = findViewById(R.id.repFullName);
        TextView districtTextView = findViewById(R.id.districtNumber);
        TextView contactURLView = findViewById(R.id.websiteURL);
        TextView contactFormView = findViewById(R.id.contactURL);
        String repTypeTitle;
        if (type.equals("representative ")) {
            repTypeTitle = party + " House Rep:";
            districtTextView.setText(districtTextView.getText() + " " + districtNumber);
        } else {
            repTypeTitle = party + " Senator:";
            districtTextView.setText("");
        }
        if (party.equals("Republican")) {
            repTypeTitleView.setBackgroundColor(getResources().getColor(R.color.RepubRed));
            repFullNameView.setBackgroundColor(getResources().getColor(R.color.RepubRed));
        } else {
            repTypeTitleView.setBackgroundColor(getResources().getColor(R.color.DemocBlue));
            repFullNameView.setBackgroundColor(getResources().getColor(R.color.DemocBlue));
        }

        if (contactUrl != null) {
            contactURLView.setClickable(true);
            contactURLView.setMovementMethod(LinkMovementMethod.getInstance());
            contactUrl = "<a href='" + contactUrl + "'> Public Website</a>";
            contactURLView.setText(Html.fromHtml(contactUrl));
        } else {
            contactURLView.setText("");
        }
        if (contactForm != null) {
            contactFormView.setClickable(true);
            contactFormView.setMovementMethod(LinkMovementMethod.getInstance());
            contactForm = "<a href='" + contactForm + "'> Contact Form</a>";
            contactFormView.setText(Html.fromHtml(contactForm));
        } else {
            contactFormView.setText("");
        }
        repTypeTitleView.setText(repTypeTitle);
        repFullNameView.setText(name);
    }
}
