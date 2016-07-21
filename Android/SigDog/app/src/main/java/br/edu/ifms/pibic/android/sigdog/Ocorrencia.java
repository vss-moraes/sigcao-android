package br.edu.ifms.pibic.android.sigdog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Ocorrencia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerDoencas = (Spinner) findViewById(R.id.spinner_doenca);
        ArrayAdapter<CharSequence> adapterDoencas = ArrayAdapter.createFromResource(this,
                R.array.doencas_array, android.R.layout.simple_spinner_dropdown_item);
        adapterDoencas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoencas.setAdapter(adapterDoencas);

        Spinner spinnerLogradouro = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapterLogradouro = ArrayAdapter.createFromResource(this,
                R.array.logradouro_array,android.R.layout.simple_spinner_dropdown_item);
        adapterLogradouro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLogradouro.setAdapter(adapterLogradouro);

        Spinner spinnerCidade = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapterCidade = ArrayAdapter.createFromResource(this,
                R.array.cidades_array, android.R.layout.simple_spinner_dropdown_item);
        adapterCidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(adapterCidade);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
