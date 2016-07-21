package br.edu.ifms.pibic.android.sigdog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class DadosAnimal extends AppCompatActivity implements View.OnClickListener{

    private TextView data;
    private SimpleDateFormat formatoData;
    private SimpleDateFormat formatoEnvio;
    private DatePickerDialog dataPickerDialog;
    private String dataEnvio;
    private String dateString;
    private Spinner spinnerDoencas;
    private Spinner spinnerIdade;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_animal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerDoencas = (Spinner) findViewById(R.id.spinner_doenca);
        ArrayAdapter<CharSequence> adapterDoencas = ArrayAdapter.createFromResource(this,
                R.array.doencas_array, android.R.layout.simple_spinner_dropdown_item);
        adapterDoencas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoencas.setAdapter(adapterDoencas);

        spinnerIdade = (Spinner) findViewById(R.id.spinner_idade);
        ArrayAdapter<CharSequence> adapterIdade = ArrayAdapter.createFromResource(this,
                R.array.idade_array, android.R.layout.simple_spinner_dropdown_item);
        adapterIdade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdade.setAdapter(adapterIdade);

        ArrayAdapter<String> adapterRacas = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, RACAS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoComplete_racas);
        textView.setAdapter(adapterRacas);

        data = (TextView) findViewById(R.id.data);

        long date = System.currentTimeMillis();
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
        formatoEnvio = new SimpleDateFormat("yyyy-MM-dd");
        dataEnvio = formatoEnvio.format(date);
        dateString = formatoData.format(date);
        data.setText(dateString);

        setDateTimeField();
    }

    private void setDateTimeField(){
        data.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dataPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                data.setText(formatoData.format(newDate.getTime()));

                dataEnvio = formatoEnvio.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view){
        dataPickerDialog.show();
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
        } else if (id == R.id.action_prox) {
            EditText raca = (EditText) findViewById(R.id.autoComplete_racas);
            TextView faixaEtariaSelecionada = (TextView) spinnerIdade.getSelectedView();
            TextView doencaSelecionada = (TextView) spinnerDoencas.getSelectedView();
            RadioGroup sexo = (RadioGroup) findViewById(R.id.radioGroup);

            int sexoId = sexo.getCheckedRadioButtonId();
            View radioButtonID = sexo.findViewById(sexoId);
            int sexoIndex = sexo.indexOfChild(radioButtonID) + 1;


            String nomeRaca = raca.getText().toString();
            int idRaca = Arrays.asList(RACAS).indexOf(nomeRaca) + 1;

            if (nomeRaca.length() == 0) {
                raca.setError("Preenchimento obrigatório!");
            } else if (!Arrays.asList(RACAS).contains(nomeRaca)){
                raca.setError("Raca Inválida!");
            } else if (faixaEtariaSelecionada.getText().toString().equals("Selecione")) {
                faixaEtariaSelecionada.setError("Preenchimento obrigatório!");
            } else if (doencaSelecionada.getText().toString().equals("Selecione")){
                doencaSelecionada.setError("Preenchimento obrigatório!");
            } else {
                sharedPref = getSharedPreferences("vet", Context.MODE_PRIVATE);
                String veterinario = sharedPref.getString("id", null);

                Log.i("SEXO: ", "Masc 1, Fem 2: " + sexoIndex);

                DadosOcorrencia dadosOcorrencia = new DadosOcorrencia(
                        idRaca + "",
                        spinnerIdade.getSelectedItemPosition() + "",
                        spinnerDoencas.getSelectedItemPosition() + "",
                        sexoIndex + "",
                        dataEnvio,
                        veterinario);
                Intent dadosEndereco = new Intent(getApplicationContext(), DadosEndereco.class);
                dadosEndereco.putExtra("dadosAnimal", dadosOcorrencia);
                startActivity(dadosEndereco);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String[] RACAS = new String[] {
            "Affenpinscher",
            "Afghan hound",
            "Aidi",
            "Airedale terrier",
            "Akita americano",
            "Akita Inu",
            "American Pit Bull",
            "American staffordshire terrier",
            "Australian cattle dog",
            "Australian silky terrier",
            "Australian terrier",
            "Azawakh",
            "Azul da Gasconha",
            "Barbet",
            "Barzoi",
            "Basenji",
            "Basset Hound",
            "Basset alemão da Vestfália",
            "Basset alpino",
            "Basset artesiano normando",
            "Basset sueco",
            "Beagle",
            "Beagle Harrier",
            "Bearded Collie",
            "Beauceron",
            "Bedlington terrier",
            "Bergamasco",
            "Bernese mountain dog",
            "Bichon bolonhês",
            "Bichon Frisé",
            "Bichon havanês",
            "Bichon maltês",
            "Billy",
            "Black and Tan terrier",
            "Bloodhound",
            "Bobtail",
            "Boerboel",
            "Boiadeiro Australiano",
            "Boiadeiro Bernês",
            "Boiadeiro de Appenzell",
            "Boiadeiro de Entlebuch",
            "Boiadeiro de Flandres",
            "Border Collie",
            "Border terrier",
            "Borzoi",
            "Boston terrier",
            "Boxer",
            "Braco alemão",
            "Braco alemão de pêlo curto",
            "Braco alemão de pêlo duro",
            "Braco alemão de pêlo longo",
            "Braco austríaco de pêlo liso",
            "Braco de Ariège",
            "Braco de Auvergne",
            "Braco de Bourbon",
            "Braco de Saint Germain",
            "Braco de Weimar",
            "Braco dinamarquês",
            "Braco eslovaco de pêlo duro",
            "Braco francês",
            "Braco húngaro",
            "Braco italiano",
            "Braco polonês",
            "Braco tirolês",
            "Briard",
            "Brittany",
            "Broholmer",
            "Buhund norueguês",
            "Buldogue americano",
            "Buldogue campeiro",
            "Buldogue francês",
            "Buldogue inglês",
            "Bull Terrier",
            "Bulldog americano",
            "Bulldog campeiro",
            "Bulldog francês",
            "Bulldog inglês",
            "Bulmastife",
            "Cairn terrier",
            "Cane Corso",
            "Cão Anglo-Francês",
            "Cão da Eurásia",
            "Cão da Groenlêndia",
            "Cão da Serra da Estrela",
            "Cão da Serra de Aires",
            "Cão de Artois",
            "Cão de Bestiar",
            "Cão de caça ao cervo sueco",
            "Cão de Canaã",
            "Cão de castro laboreiro",
            "Cão de crista chinês",
            "Cão de crista dorsal da Rodésia",
            "Cão de guarda Holandês",
            "Cão de Lontra",
            "Cão de pista de sangue da Baviera",
            "Cão de pista de sangue de Hanôver",
            "Cão de presa maiorquino",
            "Cão de santo Humberto",
            "Cão de urso da Karélia",
            "Cão do Ariège",
            "Cão do Atlas",
            "Cão do faraó",
            "Cão dos Pirineus",
            "Cão d’água americano",
            "Cão d’água espanhol",
            "Cão d’água frison",
            "Cão d’água irlandês",
            "Cão d’água português",
            "Cão d’água romagnol",
            "Cão finlandês da Lapônia",
            "Cão Hokkaido",
            "Cão Jindo da Coréia",
            "Cão Lobo checoslovaco",
            "Cão Lobo de Saarloos",
            "Cão norueguês de Macareux",
            "Cão pelado do Peru",
            "Cão pelado Inca",
            "Cão pelado Mexicano",
            "Cão Tailandês de crista dorsal",
            "Carlin",
            "Cavalier king Charles spaniel",
            "Chart polski",
            "Chesapeake bay retriever",
            "Chihuahua",
            "Chinese crested",
            "Chow Chow",
            "Cirneco do Etna",
            "Clumber spaniel",
            "Cocker spaniel americano",
            "Cocker spaniel inglês",
            "Collie",
            "Collie barbudo",
            "Coonhound",
            "Coton de Tulear",
            "Curly-coated retriever",
            "Dachbracke",
            "Dachshund (Cofap)",
            "Dachshund de pêlo curto",
            "Dachshund de pêlo longo",
            "Dálmata",
            "Dandie Dinmont terrier",
            "Deerhound",
            "Dinamarquês",
            "Doberman",
            "Dogo argentino",
            "Dogo canário",
            "Dogue alemão",
            "Dogue argentino",
            "Dogue Brasileiro",
            "Dogue de Bordeaux",
            "Dogue de Maiorca",
            "Dogue do Tibete",
            "Dogue japonês",
            "Drever",
            "Dunker norueguês",
            "Elkhound",
            "Elkhound norueguês cinza",
            "Elkhound norueguês preto",
            "Falene",
            "Field spaniel",
            "Fila brasileiro",
            "Flat-coated retriever",
            "Fox paulistinha",
            "Fox terrier",
            "Foxhound americano",
            "Foxhound inglês",
            "Foxhound sueco",
            "Francês",
            "Fulvo da Bretanha",
            "Galgo afegão",
            "Galgo escocês",
            "Galgo espanhol",
            "Galgo húngaro",
            "Galgo irlandês",
            "Galgo italiano",
            "Galgo persa",
            "Galgo polonês",
            "Galgo russo",
            "Galgo tuaregue",
            "Gascão Saintongeois",
            "Glen",
            "Golden retriever",
            "Grande Boiadeiro suíço",
            "Grande cão japonês",
            "Grande Pirineus",
            "Greyhound",
            "Grifo Belga",
            "Grifo checo",
            "Grifo da Vendéia",
            "Grifo de aponte de pêlo duro",
            "Grifo italiano",
            "Grifo nivernal",
            "Halden stovare",
            "Hamilton stovare",
            "Harrier",
            "Hokaido Ken",
            "Hovawart",
            "Husky siberiano",
            "Ibizan hound",
            "Iceland dog",
            "Illirski Ovcar",
            "Irish Glen of Imaal terrier",
            "Irish soft-coated wheaten terrier",
            "Irish water spaniel",
            "Irish wolfhound",
            "Italian greyhound",
            "Jack Russel terrier",
            "Jämthund",
            "Kai Inu",
            "Kai Ken",
            "Kangal",
            "Karelian Bear Dog",
            "Kelpie",
            "Kerry blue terrier",
            "King Charles Spaniel",
            "Kishu",
            "Komondor",
            "Kooiker",
            "Kraski Ovcar",
            "Kromfohrländer",
            "Kurzhaar",
            "Kuvasz",
            "Kyushu",
            "Labrador retriever",
            "Laika",
            "Lakeland terrier",
            "Landseer",
            "Lapinkoïra",
            "Lapinporokoïra",
            "Leonberger",
            "Lhasa apso",
            "Loup-chow",
            "Lundehund",
            "Löwchen",
            "Magyar Agar",
            "Malamute do Alasca",
            "Maltês",
            "Mastiff",
            "Mastim do Tibete",
            "Mastim dos pirineus",
            "Mastim espanhol",
            "Mastim inglês",
            "Mastino napolitano",
            "Mudi",
            "Münsterländer",
            "Norfolk terrier",
            "Norwich terrier",
            "Nova Scotia duck tolling retriever",
            "Old English sheepdog",
            "Otterhound",
            "Ovelheiro Gaúcho",
            "Papillon",
            "Pastor Alemão",
            "Pastor Alemão Branco",
            "Pastor Australiano",
            "Pastor Belga Groenandel",
            "Pastor Belga Lakinois",
            "Pastor Belga Malinois",
            "Pastor Belga Tervuren",
            "Pastor Bergamo",
            "Pastor Branco Suíço",
            "Pastor catalão",
            "Pastor Croata",
            "Pastor da Anatólia",
            "Pastor da Ásia Central",
            "Pastor da Catalunha",
            "Pastor da Islândia",
            "Pastor da Picárdia",
            "Pastor da Rússia Meridional",
            "Pastor de Beauce",
            "Pastor de Brie",
            "Pastor de Shetland",
            "Pastor do Cáucaso",
            "Pastor do Sul da Rússia",
            "Pastor dos Pirineus",
            "Pastor finlandês da Lapônia",
            "Pastor Holandês",
            "Pastor Iugoslavo",
            "Pastor Maremmano Abruzzi",
            "Pastor montanhês de Krast",
            "Pastor norueguês",
            "Pastor polonês de Podhal",
            "Pastor polonês de planície",
            "Pastor português da Serra de Aires",
            "Pequeno cão holandês",
            "Pequeno cão leão",
            "Pequeno galgo italiano",
            "Pequinês",
            "Perdigueiro alemão",
            "Perdigueiro de Burgos",
            "Perdigueiro português",
            "Pharaoh hound",
            "Pinscher",
            "Pinscher Austríaco de pêlo curto",
            "Pit Bull",
            "Podengo Ibecenco",
            "Podengo português",
            "Pointer inglês",
            "Poitevin",
            "Poodle",
            "Poodle Toy",
            "Porcelana",
            "Pudelpointer",
            "Pug",
            "Puli",
            "Pumi",
            "Rafeiro do Alentejo",
            "Retriever da Baía de Chesapeake",
            "Retriever da Nova Escócia",
            "Retriever de pêlo liso",
            "Retriever de pêlo ondulado",
            "Retriever do Labrador",
            "Retriever dourado",
            "Rhodesian",
            "Rottweiler",
            "Saarloos Wolfhound",
            "Sabujo da Istria",
            "Sabujo da Transilvânia",
            "Sabujo de Halden",
            "Sabujo de Hamilton",
            "Sabujo de Posavatz",
            "Sabujo de Schiller",
            "Sabujo de Smaland",
            "Sabujo de hygen",
            "Sabujo eslovaco",
            "Sabujo espanhol",
            "Sabujo finlandês",
            "Sabujo italiano",
            "Sabujo iugoslavo",
            "Sabujo suíço",
            "Saluki",
            "Samoieda",
            "Schapendoes",
            "Schiller stovare",
            "Schipperke",
            "Schnauzer",
            "Scottish terrier",
            "Sealyham terrier",
            "Setter Gordon",
            "Setter Irlandês",
            "Setter inglês",
            "Shar Pei",
            "Sheep Dog",
            "Shiba Inu",
            "Shih tzu",
            "Sidney terrier",
            "Skye terrier",
            "Sloughi",
            "Slovensky Cuvac",
            "Smaland Stovare",
            "Smoushond",
            "Spaniel anão continental",
            "Spaniel azul da Picárdia",
            "Spaniel Bretão",
            "Spaniel da Picárdia",
            "Spaniel de Pont-Audemar",
            "Spaniel d’água irlandês",
            "Spaniel francês",
            "Spaniel Frison",
            "Spaniel holandês",
            "Spaniel japonês",
            "Spaniel perdigueiro de Drenthe",
            "Spaniel tibetano",
            "Spinoni",
            "Spitz alemão",
            "Spitz da Lapônia",
            "Spitz de Norboten",
            "Spitz dos visigodos",
            "Spitz finlandês",
            "Spitz japonês",
            "Springer spaniel galês",
            "Springer spaniel inglês",
            "Stabyhoun",
            "Staffordshire bull terrier",
            "Sussex spaniel",
            "São Bernardo",
            "Tazi",
            "Teckel de pêlo curto",
            "Teckel de pêlo duro",
            "Teckel de pêlo longo",
            "Terranova",
            "Terrier alemão de caça",
            "Terrier australiano",
            "Terrier brasileiro",
            "Terrier checo",
            "Terrier de Boston",
            "Terrier de seda",
            "Terrier do Congo",
            "Terrier escocês",
            "Terrier irlandês",
            "Terrier japonês",
            "Terrier preto da Rússia",
            "Terrier tibetano",
            "Terrier trigueiro",
            "Thaï Ridgeback",
            "Tosa Inu",
            "Toy Fox terrier",
            "Toy Manchester terrier",
            "Toy terrier preto e castanho",
            "Vallhund sueco",
            "Veadeiro Pampeano",
            "Vizsla",
            "Volpino",
            "Vulpino Italiano",
            "Xoloitzcuintle",
            "Weimaraner",
            "Welsh Corgi Cardigan",
            "Welsh Corgi Pembroke",
            "Welsh springer spaniel",
            "Welsh terrier",
            "West highland white terrier",
            "Westie",
            "Whippet",
            "Yorkshire terrier"
    };
}
