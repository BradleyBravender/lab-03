package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/*
* ArrayAdapters are helper classes in Android that converts a list of objects into Views for types
* like ListView. This is a custom ArrayAdapter so that I can create ListView entries from a class
* that stores values, rather than a list of Strings.
 */
public class CityArrayAdapter extends ArrayAdapter<City> {
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    /*
    * Android calls getView for each item in the list.
    * position: The index of the item in the dataset
    * ConvertView: A recycled view to reuse, because creating new views is expensive
    * parent: the ListView that contains the item
    */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            // If there are no old views to resue, make a new view. Inflate using the content XML.
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,  parent, false);
        } else {
            view = convertView;
        }

        // getItem returns the city object at `position`.
        City city = getItem(position);

        // Extract the views defined in content.xml
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        // We save the fields of the City object into the TextView objects of content.xml.
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());

        return view;
    }
}
