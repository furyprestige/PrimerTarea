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

public class ConfirmarDatos extends AppCompatActivity implements Globals {
    TextView textViewDate;
    TextView textViewDetails;
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewPhone;
    Button botonEditarDatos;
    Button botonAgregarContacto;
    ArrayList<String> datos_array;
    int[] fecha;
    public ArrayList<Contacto> contactos;
    TextView[] textViews;
    Intent paginaInicio;
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
        botonAgregarContacto = (Button) findViewById(R.id.botonAgregarContacto);
        Bundle bundle = new Bundle(getIntent().getExtras());
        datos_array =(ArrayList<String>) bundle.getSerializable("datos");
        fecha = new int[3];
        fecha =  bundle.getIntArray("fecha");
        Llenado();
        botonEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarDatos();
            }
        });
        botonAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle.getInt("contacto",-1) == -1){
                    Contacto contacto = new Contacto(datos_array.get(0),datos_array.get(1),datos_array.get(2),datos_array.get(3),datos_array.get(4),fecha[0],fecha[1],fecha[2]);
                }
                else{
                   Globals.misContactos.get(bundle.getInt("contacto",-1)).actualizarDatos(datos_array.get(0),datos_array.get(1),datos_array.get(2),datos_array.get(3),datos_array.get(4),fecha[0],fecha[1],fecha[2]);
                }

                paginaInicio = new Intent(ConfirmarDatos.this,MisContactos.class);
                paginaInicio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(paginaInicio);
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
                i++;
            }

        }
    }
}