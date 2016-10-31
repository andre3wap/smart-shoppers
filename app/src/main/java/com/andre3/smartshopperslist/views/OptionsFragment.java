package com.andre3.smartshopperslist.views;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre3.smartshopperslist.R;

/**
 * Created by ODBBROW on 10/26/2016.
 */

public class OptionsFragment extends PreferenceFragment{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference);
    }
}
