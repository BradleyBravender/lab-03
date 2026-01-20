package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);  // Adds the city to the dataList being run in cityAdapter
        cityAdapter.notifyDataSetChanged();  // Redraws the ListView to incorporate this change
    }

    @Override
    public void editCity(City oldCity, City newCity) {
        int index = dataList.indexOf(oldCity);
        if (index != -1) {
            // Replace the old city object (at `index`) with the new city object
            dataList.set(index, newCity);
            cityAdapter.notifyDataSetChanged();  // Redraws the ListView to incorporate this change
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        // Fill the dataList with the initial values
        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // Listen for clicks on the ListView
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City existingCity = dataList.get(position);
            // Launch the dialog pre-filled with the selected city
            AddCityFragment.newInstance(existingCity)
                    .show(getSupportFragmentManager(), "editCity");
        });
    }
}