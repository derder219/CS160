package com.example.lukeestes.volleytester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CongressionalView extends AppCompatActivity {

    //String holding value for which method was called in last activity
    String methodType;
    //Strings holding values for user longitude and latitude (or random ones)
    String userLatitude;
    String userLongitude;
    //String holding zip code value input
    String inputZipcode;

    //Instantiate arrays now so not saved globally across multiple senators being searched
    //Also resets list every time opening activity
    ArrayList<Representative> senatorList = new ArrayList<>();
    ArrayList<Representative> houseList = new ArrayList<>();

    //Rep name array list for testing recycler view
    ArrayList<Representative> repList = new ArrayList<>();
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(repList, this);

    //Personal geocod.io API key
    String geocodioAPIKey = "6b580b1a95ba15abbb625eb8b55959ebbbe5b1c";

    //Saved zip codes to use for testing
    int fourDistrictZip = 30252; // 4 districts, 6 reps: 2 senators, 4 reps
    int discussionSlideZip = 94608; // 1 district, 3 reps: 2 senators, 1 rep

    //Make view object to manipulate background color
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);

        //Change background color
        view= this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.BackgroundBlue);

        //Set up everything via geocodio call with user method
        Intent intent = getIntent();
        methodType = intent.getStringExtra("Method_Type");
        if (methodType.equals("Input")) {
            inputZipcode = intent.getStringExtra("Zip");
            String url = geocodioZipcodeURL(inputZipcode);
            geocodioCall(url);
        } else if (methodType.equals("User_Location")) {
            userLatitude = intent.getStringExtra("Lat");
            userLongitude = intent.getStringExtra("Long");
            String url = geocodioLongLatURL(userLatitude, userLongitude);
            geocodioCall(url);
        } else if (methodType.equals("Random")) {
            userLatitude = intent.getStringExtra("Lat");
            userLongitude = intent.getStringExtra("Long");
            String url = geocodioLongLatURL(userLatitude, userLongitude);
            geocodioCall(url);
        } else {
            //TextView text = findViewById(R.id.volleyResponse);
            //text.setText("No Method!?");
        }
    }

    //TODO: Change to my stuff
    public void geocodioCall(String url) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int numDistricts = response.getJSONArray("results").getJSONObject(0).getJSONObject("fields").
                                    getJSONArray("congressional_districts").length();
                            for (int i = 0; i < numDistricts; i++) {
                                //Pass in each district into array manipulation function
                                fillRepArraysWithDistrict(response.getJSONArray("results").getJSONObject(0).getJSONObject("fields").
                                        getJSONArray("congressional_districts").getJSONObject(i));
                            }
                            TextView distrctText = findViewById(R.id.DistrictsLocation);
                            if (houseList.size() == 1) {
                                Representative rep = houseList.get(0);
                                distrctText.setText(distrctText.getText() + " " + rep.districtNumber);
                            } else {
                                int numHouseReps = houseList.size();
                                String districtsToDisplay = "";
                                for (int i = 0; i < numHouseReps; i++) {
                                    Representative currRep = houseList.get(i);
                                    if (i == numHouseReps - 1) {
                                        districtsToDisplay += currRep.districtNumber;
                                    } else {
                                        districtsToDisplay += currRep.districtNumber + " ";
                                    }
                                }
                                distrctText.setText("Districts: " + districtsToDisplay);
                            }
                            repList.addAll(houseList);
                            repList.addAll(senatorList);
                            /*for (Representative currHouse: houseList) {
                                repList.add(currHouse);
                            }
                            for (Representative currSenator: senatorList) {
                                repList.add(currSenator);
                            }*/
                            initRecyclerView();
                            //Display reps found
                            /*TextView text = findViewById(R.id.volleyResponse);
                            String repsToShow = "";
                            for (Representative currHouse: houseList) {
                                repsToShow += currHouse.toString() + " ";
                            }
                            for (Representative currSenator: senatorList) {
                                repsToShow += currSenator.toString() + " ";
                            }
                            if (methodType.equals("Input")) {
                                repsToShow += " Latitude: " + userLatitude + " Longitude: " + userLongitude;
                            }
                            text.setText(repsToShow);*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    //Fill both representative array list variables with corresponding unique reps from given district object
    private void fillRepArraysWithDistrict (JSONObject districtObject) {
        try {
            String districtNumber = districtObject.getString("district_number");
            JSONArray districtLegislators = districtObject.getJSONArray("current_legislators");
            // Go through 3 legislators in district, if unique add them to array list
            for (int i = 0; i < 3; i++) {
                JSONObject currRep = districtLegislators.getJSONObject(i);
                String currRepType = currRep.getString("type");
                String currRepContactURL = currRep.getJSONObject("contact").getString("url");
                String currRepContactForm = currRep.getJSONObject("contact").getString("contact_form");
                if (uniqueRep(currRepType, currRepContactURL)) {
                    addRep(currRepType, currRepContactURL, currRepContactForm, districtNumber, currRep);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Compare input contact url to same rep type list to make sure no duplicates
    //Returns true if contact url doesn't match any of the emails in the list
    //(type adds as a filter to which list to go through)
    private boolean uniqueRep (String type, String url) {
        if (type.equals("representative")) {
            for (Representative currRep: houseList) {
                if (currRep.sameURL(url)) {
                    return false;
                }
            }
            return true;
        } else {
            for (Representative currRep: senatorList) {
                if (currRep.sameURL(url)) {
                    return false;
                }
            }
            return true;
        }
    }

    //Add a rep that has already been deemed unique (not checked in this function!)
    private void addRep (String type, String contactURL, String contactForm, String districtNumber, JSONObject Rep) {
        try {
            String repFirstName = Rep.getJSONObject("bio").getString("first_name");
            String repLastName = Rep.getJSONObject("bio").getString("last_name");
            String repFullName = repFirstName + " " + repLastName;
            String repParty = Rep.getJSONObject("bio").getString("party");
            Representative repToAdd = new Representative(type, contactURL, contactForm, districtNumber, repFullName, repParty);
            if (type.equals("representative")) {
                houseList.add(repToAdd);
            } else {
                senatorList.add(repToAdd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Make geocodio URL for given latitude, longitude
    private String geocodioLongLatURL(String latitude, String longitude) {
        String a = "https://api.geocod.io/v1.3/reverse?q=" + latitude;
        String b = ", " + longitude;
        String c = "&fields=cd&api_key=" + geocodioAPIKey;
        return a + b + c;
    }

    //Make geocodio URL for given Zip Code
    private String geocodioZipcodeURL(String zipcode) {
        String a = " https://api.geocod.io/v1.3/geocode?q=" + zipcode;
        String b = "&fields=cd&api_key=" + geocodioAPIKey;
        return a + b;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(repList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
}
