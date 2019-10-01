package com.example.lukeestes.volleytester;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Random;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    // Lat Long double array for arranging picking a random location
    Double[][] randomLocations;
    // Variables to transfer to congressional view via intent
    public static String methodSelected = "";
    public static String latitude = "";
    public static String longitude = "";
    public static String zipcode = "";

    //Fused location provider client to get user location
    private FusedLocationProviderClient mFusedLocationClient;

    //Make view object to manipulate background color
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set random locations array
        randomLocations = new Double[11][2];
        randomLocations[0][0] = 37.773972;
        randomLocations[0][1] = -122.431297;
        randomLocations[1][0] = 39.318523;
        randomLocations[1][1] = -75.507141;
        randomLocations[2][0] = 40.730610;
        randomLocations[2][1] = -73.935242;
        randomLocations[3][0] = 31.845682;
        randomLocations[3][1] = -102.367645;
        randomLocations[4][0] = 26.616756;
        randomLocations[4][1] = -80.068451;
        randomLocations[5][0] = 42.480591;
        randomLocations[5][1] = -83.475494;
        randomLocations[6][0] = 37.129986;
        randomLocations[6][1] = -84.084122;
        randomLocations[7][0] = 21.094318;
        randomLocations[7][1] = -157.498337;
        randomLocations[8][0] = 43.452492;
        randomLocations[8][1] = -71.563896;
        randomLocations[9][0] = 40.150032;
        randomLocations[9][1] = -111.862434;
        randomLocations[10][0] = 44.268543;
        randomLocations[10][1] = -89.616508;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Change background color
        view= this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.BackgroundBlue);

        // Ask to access user location
        requestPermission();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set button on click listeners
        Button userInputCaller = findViewById(R.id.userInputCaller);
        userInputCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodSelected = "Input";
                TextView editText = findViewById(R.id.ZipcodeInput);
                zipcode = editText.getText().toString();
                if (zipcode.length() == 5) {
                    sendSelection();
                } else {
                }
            }
        });
        Button locationCaller = findViewById(R.id.userLocationCaller);
        locationCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodSelected = "User_Location";
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude = location.getLatitude() + "";
                            longitude = location.getLongitude() + "";
                            sendSelection();
                        }
                        else {
                            //Toast.makeText(this, "No Location Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Button randomCaller = findViewById(R.id.randomCaller);
        randomCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodSelected = "Random";
                Random rand = new Random();
                int randomLocationIndex = rand.nextInt(11);
                latitude = randomLocations[randomLocationIndex][0] + "";
                longitude = randomLocations[randomLocationIndex][1] + "";
                sendSelection();
            }
        });
    }

    //Go to congressional view and put respective intent extras
    private void sendSelection() {
        Intent intent = new Intent(this, CongressionalView.class);
        intent.putExtra("Method_Type", methodSelected);
        // If method is input, must send zip code along with method selected
        if (methodSelected.equals("Input")) {
            intent.putExtra("Zip", zipcode);
        }
        // If method is user location or random one, must send latitude and longitude with method selected
        if (methodSelected.equals("User_Location") || methodSelected.equals("Random")) {
            intent.putExtra("Lat", latitude);
            intent.putExtra("Long", longitude);
        }
        startActivity(intent);
    }

    //Request permission to get user location
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
