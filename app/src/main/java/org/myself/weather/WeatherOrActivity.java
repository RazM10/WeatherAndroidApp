package org.myself.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
            textViewCityName, textViewCountryName, textViewDescription;
    ImageView imageViewSituation;
    RelativeLayout relativeLayout;

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
        textViewDescription=findViewById(R.id.weather_or_description);

        textViewWeek=findViewById(R.id.weather_or_week_tv);
        textViewDate=findViewById(R.id.weather_or_date_tv);

        imageViewSituation=findViewById(R.id.weather_or_situation_imgv);

        relativeLayout=findViewById(R.id.weather_or_full_layout);
        relativeLayout.setVisibility(View.GONE);

        if (isCity)
            getDataForCity();
        else {
            getData();
        }
    }

    private void getData() {
        AndroidNetworking.get(WEATHER_URL + "?lat=" + LocationEnablecheckActivity.currentLatitude +
                "&lon=" + LocationEnablecheckActivity.currentLongitude + "&appid=" + APP_ID)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("WeatherData", "onResponse: " + response);

                        relativeLayout.setVisibility(View.VISIBLE);

                        int id;
                        double temp = 0, tempF=0;
                        long miliSecSunRise = 0, miliSecSunSet = 0;
                        String country="", city="", description="", iconName;
                        try {
                            id = response.getJSONArray("weather").getJSONObject(0).getInt("id");
                            iconName=updateIconName(id);
                            int resourceID=getResources().getIdentifier(iconName,"drawable",getPackageName());
                            imageViewSituation.setImageResource(resourceID);


                            description = response.getJSONArray("weather").getJSONObject(0).getString("description");
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
                        String[] rise1=sunRise.split(",");
                        String[] rise2=rise1[1].split(" ");
                        String[] time=rise1[0].split(" ");

                        String sunSet = convertSunRiseSetTime(miliSecSunSet);
                        String[] set1=sunSet.split(",");
                        String[] set2=set1[1].split(" ");

                        int tempT = (int) Math.rint(temp);
                        int tempFInt = (int) Math.rint(tempF);

                        textViewTemp.setText(tempT+"°");
                        textViewTempFeelLike.setText("Feels like "+tempFInt+"°");
                        textViewSunRiseTime.setText(rise2[2]);
                        textViewSunSetTime.setText(set2[2]);
                        textViewWeek.setText(time[0]);
                        textViewDate.setText(time[1]);
                        textViewDescription.setText(description.toUpperCase());
                        textViewCityName.setText(city);
                        textViewCountryName.setText(country);
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

                        relativeLayout.setVisibility(View.VISIBLE);

                        int id;
                        double temp = 0, tempF=0;
                        long miliSecSunRise = 0, miliSecSunSet = 0;
                        String country="", city="", description="", iconName;
                        try {
                            id = response.getJSONArray("weather").getJSONObject(0).getInt("id");
                            iconName=updateIconName(id);
                            int resourceID=getResources().getIdentifier(iconName,"drawable",getPackageName());
                            imageViewSituation.setImageResource(resourceID);


                            description = response.getJSONArray("weather").getJSONObject(0).getString("description");
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
                        String[] rise1=sunRise.split(",");
                        String[] rise2=rise1[1].split(" ");
                        String[] time=rise1[0].split(" ");

                        String sunSet = convertSunRiseSetTime(miliSecSunSet);
                        String[] set1=sunSet.split(",");
                        String[] set2=set1[1].split(" ");

                        int tempT = (int) Math.rint(temp);
                        int tempFInt = (int) Math.rint(tempF);

                        textViewTemp.setText(tempT+"°");
                        textViewTempFeelLike.setText("Feels like "+tempFInt+"°");
                        textViewSunRiseTime.setText(rise2[2]);
                        textViewSunSetTime.setText(set2[2]);
                        textViewWeek.setText(time[0]);
                        textViewDate.setText(time[1]);
                        textViewDescription.setText(description.toUpperCase());
                        textViewCityName.setText(city);
                        textViewCountryName.setText(country);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("WeatherData", "onError: " + anError.getMessage());
                    }
                });
    }

    public static String updateIconName(int condition) {
        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    private String convertSunRiseSetTime(Long miliSecSunRise){
        long dv = Long.valueOf(miliSecSunRise)*1000;// its need to be in milisecond
        Date df = new Date(dv);
        return new SimpleDateFormat("MMM dd, yyyy hh:mma").format(df);
    }

    public void goForCity(View view) {
        startActivity(new Intent(WeatherOrActivity.this, CityOrActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (WeatherActivity.isCity) {
            getDataForCity();
        } else
            getData();
    }
}
