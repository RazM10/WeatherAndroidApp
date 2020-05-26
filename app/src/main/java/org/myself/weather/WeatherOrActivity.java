package org.myself.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherOrActivity extends AppCompatActivity {

    TextView textViewTemp, textViewWeek, textViewDate, textViewTempFeelLike, textViewSunRiseTime, textViewSunSetTime,
            textViewCityName, textViewCountryName;
    ImageView imageViewSituation;

    public static boolean isCity = false;

    String APP_ID = "bd8a9ac5b7a888e5043810688582f61f";
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

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

    private void getData() {
        AndroidNetworking.get(WEATHER_URL + "?lat=" + LocationEnablecheckActivity.currentLatitude +
                "&lon=" + LocationEnablecheckActivity.currentLongitude + "&appid=" + APP_ID)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("WeatherData", "onResponse: " + response);
                        int id;
                        double temp = 0, tempF=0;
                        long miliSecSunRise = 0, miliSecSunSet = 0;
                        String country, city, description;
                        try {
                            id = response.getJSONObject("weather").getInt("id");
                            description = response.getJSONObject("weather").getString("description");
                            temp = response.getJSONObject("main").getDouble("temp") - 273.15;
                            tempF = response.getJSONObject("main").getDouble("feels_like") - 273.15;
                            country = response.getJSONObject("sys").getString("country");
                            city = response.getString("name");
                            miliSecSunRise = response.getJSONObject("sys").getLong("sunrise");
                            miliSecSunSet = response.getJSONObject("sys").getLong("sunset");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String sunRise = convertSunRiseSetTime(miliSecSunRise);
                        String sunSet = convertSunRiseSetTime(miliSecSunSet);

                        int tempT = (int) Math.rint(temp);

                        textViewTemp.setText(tempT+"°");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("WeatherData", "onError: " + anError.getMessage());
                    }
                });
    }

    private String convertSunRiseSetTime(Long miliSecSunRise){
        long dv = Long.valueOf(miliSecSunRise)*1000;// its need to be in milisecond
        Date df = new Date(dv);
        return new SimpleDateFormat("MMM dd, yyyy hh:mma").format(df);
    }

    public void goForCity(View view) {
    }
}
