package com.example.asad.homebuyerproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Asad on 11/14/2016.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView txtDate;

    public DateDialog()
    {

    }
    public DateDialog(TextView view)
    {
        txtDate=view;

    }



    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day + "-" + (month + 1) + "-" + year;
        Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();
         txtDate.setText("Availabe From : " + date);
    }
}
