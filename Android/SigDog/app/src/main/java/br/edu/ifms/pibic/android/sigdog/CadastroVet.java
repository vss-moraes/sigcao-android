package br.edu.ifms.pibic.android.sigdog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class CadastroVet extends AppCompatActivity {

    EditText etCRMV;
    String resposta;
    TextView tvDadosVet, tvConfere;
    Button btnEnviar, btnBuscar;
    JSONObject veterinario;
    final String VET = "vet";
    SharedPreferences sharedPref;

    public void buscaCRMV(View v){

        Log.i("Busca CRMV", "clicou");
        String crmvFornecido = etCRMV.getText().toString();

        final String url = "http://10.0.2.2:8000/veterinario/busca" + "?crmv=" + crmvFornecido;

        Thread t = new Thread(){
            @Override
            public void run(){
                HttpHelper enviaDados = new HttpHelper();
                try {
                    resposta = enviaDados.doGet(url);
                    Log.i("livroandroid", "String retorno: " + resposta);

                    veterinario = new JSONObject(resposta);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String dadosVeterinario = "";
        try {
            dadosVeterinario =
                    "Nome: " + veterinario.getString("nome").trim() +
                            "\nEstado: " + veterinario.getString("estado").trim() +
                            "\nSituação: " + veterinario.getString("situacao").trim() +
                            "\nCRMV: " + veterinario.getString("crmv").trim();
            btnEnviar.setVisibility(Button.VISIBLE);
            btnEnviar.setEnabled(true);
        } catch (Exception e) {
            dadosVeterinario = "Alguma coisa deu errado. Verifique o número do seu CRMV.";
        }
        tvConfere.setText("Confira seus dados:");
        tvDadosVet.setText(dadosVeterinario);

        Log.i("CRMV: ", sharedPref.getString("crmv", "VAZIO"));
        Log.i("ID: ", sharedPref.getString("id", "VAZIO"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vet);
        sharedPref = getSharedPreferences(VET, Context.MODE_PRIVATE);
        etCRMV = (EditText) findViewById(R.id.etCRMV);
        tvDadosVet = (TextView) findViewById(R.id.tvDadosVet);
        tvConfere = (TextView) findViewById(R.id.tvConfere);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                buscaCRMV(v);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final SharedPreferences.Editor editor = sharedPref.edit();
                try {
                    editor.putString("crmv", veterinario.getString("crmv"));
                    editor.putString("id", veterinario.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                editor.apply();

                Intent principal = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(principal);
            }
        });
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
        } else if(id == R.id.action_prox) {
            return true;
        }
        return false;
    }
}
