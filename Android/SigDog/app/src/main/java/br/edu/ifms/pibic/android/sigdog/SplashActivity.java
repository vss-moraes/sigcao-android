package br.edu.ifms.pibic.android.sigdog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by vinicius on 04/12/15.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class atividade;
        final String VET = "vet";

        SharedPreferences sharedPref = getSharedPreferences(VET, Context.MODE_PRIVATE);
        String crmv = sharedPref.getString("crmv", "VAZIO");


        if (crmv.equals("VAZIO")) {
            atividade = CadastroVet.class;
            Log.i("CRMV: ", crmv);
            Log.i("ID: ", sharedPref.getString("id", "VAZIO"));
        }
        else {
            Log.i("CRMV: ", crmv);
            atividade = MainActivity.class;
            Log.i("ID: ", sharedPref.getString("id", "VAZIO"));
        }

        Intent intent = new Intent(this, atividade);
        startActivity(intent);
        finish();
    }
}