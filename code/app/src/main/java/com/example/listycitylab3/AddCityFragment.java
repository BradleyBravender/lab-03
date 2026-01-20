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

/*
* Fragments are reusable pieces of UI that can be attached to activities.
* DialogFragments display dialog boxes. AddCityFragment is a customizable dialog box.
*/
public class AddCityFragment extends DialogFragment {

    // Only the listener class needs to implement the methods to this internal interface (i.e. MainActivity).
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City oldCity, City newCity);
    }

    private AddCityDialogListener listener;

    /*
    * A lifecycle method called when the fragment is attached to an activity. 'Context' is the
    * activity that hosts the fragment. If the activity implements AddCityDialogListener, this
    * fragment saves it in listener.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    /*
    * This is a lifecycle method of DialogFragment. Called by Android when the fragment wants to
    * create a dialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Turns the XML layout fragment_add_city into an actual view object
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        // Stores the edit text into this new view object
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if we are editing an existing city, or adding a city
        final City cityToEdit = getArguments() != null ? (City) getArguments().getSerializable("city") : null;
        Bundle args = getArguments();

        // Pre-fill the fields if editing
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        // Build the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                // Change the (+) button text based on if we are adding or editing a city
                .setTitle(cityToEdit == null ? "Add a city" : "Edit city")
                // The (-) button will just be to exit the dialog box
                .setNegativeButton("Cancel", null)
                // If there is not city to edit, we are adding a city. O.w. we are saving to an existing city.
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

    // This method is called on the class itself and preloads a newly creating class with a city.
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