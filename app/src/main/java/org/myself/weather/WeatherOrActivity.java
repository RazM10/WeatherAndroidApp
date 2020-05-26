package org.myself.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherOrActivity extends AppCompatActivity {

    TextView textViewTemp, textViewWeek, textViewDate, textViewTempFeelLike, textViewSunRiseTime, textViewSunSetTime,
            textViewCityName, textViewCountryName;
    ImageView imageViewSituation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_or);

        textViewTemp=findViewById(R.id.weather_or_temp_tv);
        textViewTempFeelLike=findViewById(R.id.weather_or_temp_feel_tv);
        textViewSunRiseTime=findViewById(R.id.weather_or_sunrise_time_tv);
        textViewSunSetTime=findViewById(R.id.weather_or_sunset_time_tv);
        textViewCityName=findViewById(R.id.weather_or_city_tv);
        textViewCountryName=findViewById(R.id.weather_or_country_tv);

        textViewWeek=findViewById(R.id.weather_or_week_tv);
        textViewDate=findViewById(R.id.weather_or_date_tv);

        imageViewSituation=findViewById(R.id.weather_or_situation_imgv);
    }

    public void goForCity(View view) {
    }
}
