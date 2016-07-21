package br.edu.ifms.pibic.android.sigdog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView data;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, ano, mes, dia);
    }

    @Override
    public void onDateSet (DatePicker view, int ano, int mes, int dia){

    }
}
