package com.andre3.smartshopperslist.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre3.smartshopperslist.R;

/**
 * Created by ODBBROW on 10/26/2016.
 */

public class OptionsFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load fragment and display shopping lists
        View view = inflater.inflate(R.layout.options_frg, container, false);

        return view;
    }

}
