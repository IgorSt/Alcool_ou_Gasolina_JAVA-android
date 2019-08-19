package com.igorsantos.alcoolougasolina;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private EditText editPrecoAlcool;
    private EditText editPrecoGasolina;
    private TextView textResultado;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        editPrecoAlcool = findViewById(R.id.editPrecoAlcool);
        editPrecoGasolina = findViewById(R.id.editPrecoGasolina);
        textResultado = findViewById(R.id.textResultado);
    }

    public void calcularPreco(View view){

        //recuperar valores digitados
        String precoAlcool = editPrecoAlcool.getText().toString();
        String precoGasolina = editPrecoGasolina.getText().toString();

        //validar campos preenchidos
        boolean campoAlcoolValidado = this.validaCampoAlcool(precoAlcool);
        boolean campoGasolinaValidado = this.validaCampoGasolina(precoGasolina);
        if(campoAlcoolValidado && campoGasolinaValidado){
            this.calculaMelhorPreco(precoAlcool, precoGasolina);
        }else if(campoAlcoolValidado == false){
            editPrecoAlcool.requestFocus();
        }else if(campoGasolinaValidado == false){
            editPrecoGasolina.requestFocus();
        }
    }

    public boolean validaCampoAlcool(String pAlcool){

        boolean campoAlcoolValidado = true;

        if(pAlcool == null || pAlcool.equals("")){
            campoAlcoolValidado = false;
        }

        if(campoAlcoolValidado == false){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("O preço do Álcool está em branco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        return  campoAlcoolValidado;
    }
    public boolean validaCampoGasolina(String pGasolina){

        boolean campoGasolinaValidado = true;

        if(pGasolina == null || pGasolina.equals("")){
            campoGasolinaValidado = false;
        }

        if(campoGasolinaValidado == false){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("O preço da Gasolina está em branco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        return  campoGasolinaValidado;
    }

    public  void calculaMelhorPreco(String pAlcool, String pGasolina){

        //converter String para números
        Double precoAlcool = Double.parseDouble(pAlcool);
        Double precoGasolina = Double.parseDouble(pGasolina);

        //Faz o cálculo (Alcool / Gasolina) se o resultado for >= 0.7 melhor gasolina, senão melhor álcool
        Double resultado = precoAlcool / precoGasolina;
        if(resultado >= 0.7){
            textResultado.setText("Melhor abastecer com gasolina!");
        }else if(resultado < 0.7){
            textResultado.setText("Melhor abastecer com álcool!");
        }else if(resultado == 0.7){
            textResultado.setText("Tanto faz!");
        }

    }
}
