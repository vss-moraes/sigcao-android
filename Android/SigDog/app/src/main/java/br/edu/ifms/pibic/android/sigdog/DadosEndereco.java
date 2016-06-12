package br.edu.ifms.pibic.android.sigdog;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class DadosEndereco extends AppCompatActivity {

    private String s;

    public LatLng pegaCoordenadasDoEndereco(Context context, String enderecoString) {
        Geocoder coder = new Geocoder(context);
        List<Address> endereco;
        LatLng ponto = null;

        try {
            endereco = coder.getFromLocationName(enderecoString, 5);
            if (endereco == null)
                return null;
            Address localizacao = endereco.get(0);
            localizacao.getLatitude();
            localizacao.getLongitude();

            ponto = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
        } catch (Exception e){
            e.printStackTrace();
            Log.i("Endereco completo: ", "Exception no geocoder.");
        }
        return ponto;
    }

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
        AutoCompleteTextView logradouro = (AutoCompleteTextView) findViewById(R.id.auto_complete_logradouro);
        logradouro.setAdapter(adapterLogradouro);

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

            EditText nome = (EditText) findViewById(R.id.editText3);
            EditText logradouro = (EditText) findViewById(R.id.auto_complete_logradouro);
            String nomeLogradouro = logradouro.getText().toString();
            EditText bairro = (EditText) findViewById(R.id.editText6);
            EditText numero = (EditText) findViewById(R.id.editText5);

            Spinner cidade = (Spinner) findViewById(R.id.spinner_cidade);
            TextView cidadeSelecionada = (TextView) cidade.getSelectedView();

            if (nomeLogradouro.length() == 0){
                logradouro.setError("Preenchimento obrigatório!");
            } else if (!Arrays.asList(LOGRADOURO).contains(nomeLogradouro)){
                logradouro.setError("Logradouro Inválido!");
            } else if (nome.getText().toString().length() == 0){
                nome.setError("Preenchimento obrigatório!");
            } else if (bairro.getText().toString().length() == 0){
                bairro.setError("Preenchimento obrigatório!");
            } else if (cidadeSelecionada.getText().toString().equals("Selecione")){
                cidadeSelecionada.setError("Preenchimento obrigatório!");
            } else {

                final String enderecoCompleto = logradouro.getText().toString() + " " +
                        nome.getText().toString() + ", " + numero.getText().toString() + ". "
                        + cidadeSelecionada.getText().toString() + ", MS, Brasil.";
                Log.i("Endereco completo: ", enderecoCompleto);

                final DadosOcorrencia dadosOcorrencia = getIntent().getParcelableExtra("dadosAnimal");

                dadosOcorrencia.setEnderecoCompleto(logradouro.getText().toString() + " " +
                        nome.getText().toString() + ", " + numero.getText().toString() + ".");
                dadosOcorrencia.setBairro(bairro.getText().toString());
                dadosOcorrencia.setCidade(cidadeSelecionada.getText().toString());

                LatLng localizacao = pegaCoordenadasDoEndereco(this, enderecoCompleto);

                CharSequence text;
                if (localizacao != null){
                    text = localizacao.toString();
                    Log.i("LatLng: ", localizacao.toString());
                    dadosOcorrencia.setCoordenadas(localizacao);
                } else {
                    text = "Shift.";
                    Log.i("Endereco completo: ", "Deu merda!");
                }

                new Thread(){
                    @Override
                    public void run(){
                        HttpHelper enviaDados = new HttpHelper();

                        try {
                            s = enviaDados.doPost(
                                    "http://sigcao.herokuapp.com/ocorrencias/nova/",
                                    dadosOcorrencia.converteParaMapa(),
                                    "UTF-8");
                            Log.e("livroandroid", "String retorno: " + s);
                        } catch (IOException e){
                            Log.e("livroandroid", "Erro " + e.getMessage(), e);
                        }

                    }
                }.start();

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
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
