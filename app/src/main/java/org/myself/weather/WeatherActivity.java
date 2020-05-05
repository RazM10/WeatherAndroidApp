package org.myself.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WeatherActivity extends AppCompatActivity {

    String APP_ID="bd8a9ac5b7a888e5043810688582f61f";
    final String WEATHER_URL="http://api.openweathermap.org/data/2.5/weather";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }
}
