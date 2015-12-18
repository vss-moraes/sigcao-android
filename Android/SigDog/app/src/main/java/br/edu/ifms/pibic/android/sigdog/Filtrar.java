package br.edu.ifms.pibic.android.sigdog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Filtrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerDoencas = (Spinner) findViewById(R.id.spinner_doenca);
        ArrayAdapter<CharSequence> adapterDoencas = ArrayAdapter.createFromResource(this,
                R.array.doencas_array, android.R.layout.simple_spinner_dropdown_item);
        adapterDoencas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoencas.setAdapter(adapterDoencas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
