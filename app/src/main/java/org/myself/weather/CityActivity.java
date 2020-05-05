package org.myself.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CityActivity extends AppCompatActivity {

    EditText editTextCItyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        editTextCItyName=findViewById(R.id.city_name_tv);
    }

    public void goWithCity(View view) {
        LocationEnablecheckActivity.cityName=editTextCItyName.getText().toString();
        WeatherActivity.isCity=true;
        finish();
    }
}
