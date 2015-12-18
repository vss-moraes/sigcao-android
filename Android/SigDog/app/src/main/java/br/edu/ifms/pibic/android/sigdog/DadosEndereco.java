package br.edu.ifms.pibic.android.sigdog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DadosEndereco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_endereco);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerCidade = (Spinner) findViewById(R.id.spinner_cidade);
        ArrayAdapter<CharSequence> adapterCidade = ArrayAdapter.createFromResource(this,
                R.array.cidades_array, android.R.layout.simple_spinner_dropdown_item);
        adapterCidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(adapterCidade);

        ArrayAdapter<String> adapterLogradouro = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, LOGRADOURO);
        Spinner spinnerLogradouro = (Spinner) findViewById(R.id.spinner_logradouro);
        spinnerLogradouro.setAdapter(adapterLogradouro);

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
        } else if(id == R.id.action_prox){
            CharSequence text = "Ocorrência enviada!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String[] LOGRADOURO = new String[] {
            "Selecione",
            "Aeroporto",
            "Alameda",
            "Área",
            "Avenida",
            "Campo",
            "Chácara",
            "Colônia",
            "Condomínio",
            "Conjunto",
            "Distrito",
            "Esplanada",
            "Estação",
            "Estrada",
            "Favela",
            "Fazenda",
            "Feira",
            "Jardim",
            "Ladeira",
            "Lago",
            "Lagoa",
            "Largo",
            "Loteamento",
            "Morro",
            "Núcleo",
            "Parque",
            "Passarela",
            "Pátio",
            "Praça",
            "Quadra",
            "Recanto",
            "Residencial",
            "Rodovia",
            "Rua",
            "Setor",
            "Sítio",
            "Travessa",
            "Trecho",
            "Trevo",
            "Vale",
            "Vereda",
            "Via",
            "Viaduto",
            "Viela",
            "Vila"
    };
}
