package org.myself.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    TextView textViewWeatherData;
    public static boolean isCity = false;

    String APP_ID = "bd8a9ac5b7a888e5043810688582f61f";
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        textViewWeatherData = findViewById(R.id.WeatherData_tv);
        if (isCity)
            getDataForCity();
        else
            getData();
    }

//    api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
//    api.openweathermap.org/data/2.5/weather?q={city name},{state}&appid={your api key}

    //    api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}
    private void getData() {
        AndroidNetworking.get(WEATHER_URL + "?lat=" + LocationEnablecheckActivity.currentLatitude +
                "&lon=" + LocationEnablecheckActivity.currentLongitude + "&appid=" + APP_ID)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("WeatherData", "onResponse: " + response);
                        double temp = 0;
                        try {
                            temp = response.getJSONObject("main").getDouble("temp") - 273.15;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int roundValue = (int) Math.rint(temp);
                        textViewWeatherData.setText(response.toString() + "\n\n\n\n\n Temp For Co.: " + roundValue);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("WeatherData", "onError: " + anError.getMessage());
                    }
                });
    }

    private void getDataForCity() {
        AndroidNetworking.get(WEATHER_URL + "?q=" + LocationEnablecheckActivity.cityName + "&appid=" + APP_ID)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("WeatherData", "onResponse: " + response);
                        double temp = 0;
                        try {
                            temp = response.getJSONObject("main").getDouble("temp") - 273.15;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int roundValue = (int) Math.rint(temp);
                        textViewWeatherData.setText(response.toString() + "\n\n\n\n\n Temp For City: " + roundValue);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("WeatherData", "onError: " + anError.getMessage());
                    }
                });
    }

    public void goForCity(View view) {
        startActivity(new Intent(WeatherActivity.this, CityActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (WeatherActivity.isCity) {
            getDataForCity();
        }
        else
            getData();
    }
}
