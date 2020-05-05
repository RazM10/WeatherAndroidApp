package org.myself.weather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class LocationEnablecheckActivity extends AppCompatActivity {

    Context context;
    Boolean isLocationTurnOn = false;
    boolean gps_enabled, network_enabled;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_enablecheck);

        context = LocationEnablecheckActivity.this;

        onLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocationInfo();
        if (gps_enabled && network_enabled){
            startActivity(new Intent(LocationEnablecheckActivity.this, Main3Activity.class));
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void clickRetryToOnLocation(View view) {
        onLocation();
    }

    private void onLocation() {
        getLocationInfo();

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(context)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
        } else {
            isLocationTurnOn = true;
            onResume();
        }
    }

    private void getLocationInfo(){
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = false;
        network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
    }
}
