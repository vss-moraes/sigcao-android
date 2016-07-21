package br.edu.ifms.pibic.android.sigdog;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DadosOcorrencia implements Parcelable {
    private String raca;
    private String faixaEtaria;
    private String doenca;
    private String sexo;
    private String data;
    private String enderecoCompleto;
    //private LatLng coordenadas;
    private String cidade;
    private String bairro;
    private String veterinario;
    private double latitude;
    private double longitude;

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setEnderecoCompleto(String endereco){
        this.enderecoCompleto = endereco;
    }

    public void setCoordenadas(LatLng coordenadas){
        this.latitude = coordenadas.latitude;
        this.longitude = coordenadas.longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public DadosOcorrencia (String raca, String faixaEtaria, String doenca, String sexo, String data, String veterinario){
        this.raca = raca;
        this.faixaEtaria = faixaEtaria;
        this.doenca = doenca;
        this.sexo = sexo;
        this.data = data;
        this.veterinario = veterinario;
    }

    //parcel part
    public DadosOcorrencia(Parcel in){
        String[] dados = new String[7];

        in.readStringArray(dados);
        this.raca = dados[0];
        this.faixaEtaria = dados[1];
        this.doenca = dados[2];
        this.sexo = dados[3];
        this.data = dados[4];
        this.enderecoCompleto = dados[5];
        this.veterinario = dados[6];
    }

    public Map<String, String> converteParaMapa (){
        DecimalFormat decimalFormat = new DecimalFormat("#.000000");

        Map<String, String> mapa = new HashMap<>();
        mapa.put("doenca", this.doenca);
        mapa.put("sexo", this.sexo);
        mapa.put("faixa_etaria", this.faixaEtaria);
        mapa.put("raca", this.raca);
        mapa.put("data", this.data);
        mapa.put("endereco_completo", this.enderecoCompleto);
        mapa.put("bairro", this.bairro);
        mapa.put("cidade", this.cidade);
        mapa.put("estado", "MS");
        mapa.put("veterinario", this.veterinario);
        mapa.put("latitude", decimalFormat.format(latitude).replace(",", "."));   
        mapa.put("longitude", decimalFormat.format(longitude).replace(",", "."));

        return mapa;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{this.raca, this.faixaEtaria, this.doenca, this.sexo,
                this.data, this.enderecoCompleto, this.veterinario});
    }

    public static final Parcelable.Creator<DadosOcorrencia> CREATOR = new Parcelable.Creator<DadosOcorrencia>() {
        @Override
        public DadosOcorrencia createFromParcel(Parcel source){
            return new DadosOcorrencia(source);
        }

        @Override
        public DadosOcorrencia[] newArray(int size){
            return new DadosOcorrencia[size];
        }
    };
}
