package com.andre3.smartshopperslist.tools;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.andre3.smartshopperslist.R;

import java.util.Calendar;

/**
 * Created by ODBBROW on 10/24/2016.
 */

public class DatePickerFrg extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Dialog dialog;

    public DatePickerFrg(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView list_rem_tv = (TextView)dialog.findViewById(R.id.list_rem_tv);
        list_rem_tv.setText(month+"/"+day+"/"+year);
    }


}