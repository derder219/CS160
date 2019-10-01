package com.example.macbook.perkupapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

public class HomePage extends AppCompatActivity {
    Button enterAR;
    Button chatWithPenny;
    Button viewProgress;
    private boolean mUserRequestedInstall = true;
    public Session mSession = null;

    @Override
    protected void onResume() {
        super.onResume();

        // ARCore requires camera permission to operate.
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this);
            return;
        }
        try {
            if (mSession == null) {
                switch (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    case INSTALLED:
                        // Success, create the AR session.
                        mSession = new Session(this);
                        break;
                    case INSTALL_REQUESTED:
                        // Ensures next invocation of requestInstall() will either return
                        // INSTALLED or throw an exception.
                        mUserRequestedInstall = false;
                        return;
                }
            }
        } catch (UnavailableUserDeclinedInstallationException e) {
            // Display an appropriate message to the user and return gracefully.
            Toast.makeText(this, "TODO: handle exception " + e, Toast.LENGTH_LONG)
                    .show();
            return;
        } catch(UnavailableDeviceNotCompatibleException e) {
            return;
        } catch (UnavailableArcoreNotInstalledException e) {
            return;
        } catch (UnavailableApkTooOldException e) {
            return;
        } catch (UnavailableSdkTooOldException e) {
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                    .show();
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(this);
            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        init();
    }

    private void init() {


        TextView textview = (TextView) findViewById(R.id.appLogo);
        // adjust this line to get the TextView you want to change

        Typeface typeface = Typeface.createFromAsset(getAssets(),"AllertaStencil-Regular.ttf");
        // create a typeface from the raw ttf
        textview.setTypeface(typeface);

        //find three main buttons on home page.
        enterAR = findViewById(R.id.enterAR);
        chatWithPenny = findViewById(R.id.chatWithPenny);
        viewProgress = findViewById(R.id.viewProgress);

        chatWithPenny.setOnClickListener(new chatButton());
        enterAR.setOnClickListener(new ARbutton());
        viewProgress.setOnClickListener(new ProgressButton());
    }

    private class chatButton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(HomePage.this, ChatActivity.class);
            startActivity(intent);
        }
    }
    private class ARbutton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(HomePage.this, GamePage.class);
            startActivity(intent);
        }
    }

    private class ProgressButton implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(HomePage.this, EnvironmentChoose.class);
            startActivity(intent);
        }
    }
}
