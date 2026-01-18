package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City oldCity, City newCity);  // FIXME
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        View view =
//                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
//        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
//        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        return builder
//                .setView(view)
//                .setTitle("Add a city")
//                .setNegativeButton("Cancel", null)
//                .setPositiveButton("Add", (dialog, which) -> {
//                    String cityName = editCityName.getText().toString();
//                    String provinceName = editProvinceName.getText().toString();
//                    listener.addCity(new City(cityName, provinceName));
//                })
//                .create();
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if a city was passed in (for editing)
        final City cityToEdit = getArguments() != null ? (City) getArguments().getSerializable("city") : null;
        Bundle args = getArguments();

        // Pre-fill the fields if editing
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(cityToEdit == null ? "Add a city" : "Edit city") // dynamic title
                .setNegativeButton("Cancel", null)
                .setPositiveButton(cityToEdit == null ? "Add" : "Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City newCity = new City(cityName, provinceName);

                    if (cityToEdit == null) {
                        listener.addCity(newCity); // adding a new city
                    } else {
                        listener.editCity(cityToEdit, newCity); // editing an existing city
                    }
                })
                .create();
    }

    // This method is called on the class itself
    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        // Stores city under the key 'city' (works because the city class implements Serializable)
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        // Saves the bundle inside the new fragment
        fragment.setArguments(args);
        // Return a new fragment that already 'knows' about the city
        return fragment;
    }
}