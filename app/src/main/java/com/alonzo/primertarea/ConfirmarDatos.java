package com.alonzo.primertarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ConfirmarDatos extends AppCompatActivity {
    TextView textViewDate;
    TextView textViewDetails;
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewPhone;
    Button botonEditarDatos;
    ArrayList<String> datos_array;
    TextView[] textViews;
    byte i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmar_datos);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDetails = (TextView) findViewById(R.id.textViewDetails);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViews = new TextView[]{textViewName, textViewDate, textViewPhone, textViewEmail, textViewDetails};
        botonEditarDatos = (Button) findViewById(R.id.botonEditarDatos);
        datos_array = (ArrayList<String>) getIntent().getSerializableExtra("datos");
        Llenado();
        botonEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarDatos();
            }
        });
    }

    public void EditarDatos(){
        this.onBackPressed();
        this.finish();

    }
    public void Llenado(){
        i = 0;
        for (TextView e:textViews) {
            if (e == textViewDate){
                e.setText(e.getText()+" "+datos_array.get(datos_array.size()-1));
            }
            else{
                e.setText(e.getText()+" "+datos_array.get(i));
            }
            i++;
        }
    }
}