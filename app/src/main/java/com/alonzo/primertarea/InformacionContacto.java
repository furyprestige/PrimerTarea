package com.alonzo.primertarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

public  class InformacionContacto extends AppCompatActivity implements Serializable {
    Contacto contacto;
    TextView textViewNombreInfoContacto;
    TextView textViewTelefonoInfoContacto;
    TextView textViewEmailInfoContacto;
    TextView textViewFechaNacimientoInfoContacto;
    TextView textViewDetallesInfoContacto;
    Button botonEditarDatosInfoContacto;
    Button botonEliminar;
    ImageButton imageButtonBack;
    Toolbar toolBarInfoContacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_contacto);
        contacto = Globals.misContactos.get(getIntent().getIntExtra("info",0));
        Button botonEdiarDatosInfoContacto = (Button) findViewById(R.id.botonEditarDatosInfoContacto);
        imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        botonEdiarDatosInfoContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edicion = new Intent(InformacionContacto.this,FormularioContacto.class);
                edicion.putExtra("contacto",Globals.misContactos.indexOf(contacto));
                startActivity(edicion);
                finish();
            }
        });
        botonEliminar = (Button) findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.misContactos.remove(contacto);
                contacto.quitarFavorito();
                Intent intent = new Intent("finish_activity");
                sendBroadcast(intent);
                Intent regreso = new Intent(InformacionContacto.this,MisContactos.class);
                startActivity(regreso);
                finish();

            }
        });
        textViewNombreInfoContacto = (TextView) findViewById(R.id.textViewNombreInfoContacto);
        textViewTelefonoInfoContacto = (TextView) findViewById(R.id.textViewTelefonoInfoContacto);
        textViewEmailInfoContacto = (TextView) findViewById(R.id.textViewEmailInfoContacto);
        textViewFechaNacimientoInfoContacto = (TextView) findViewById(R.id.textViewFechaNacimientoInfoContacto);
        textViewDetallesInfoContacto = (TextView) findViewById(R.id.textViewDetallesInfoContacto);
        textViewNombreInfoContacto.setText(textViewNombreInfoContacto.getText()+" "+contacto.getName());
        textViewTelefonoInfoContacto.setText(textViewTelefonoInfoContacto.getText()+" "+contacto.getPhone());
        textViewEmailInfoContacto.setText(textViewEmailInfoContacto.getText()+" "+contacto.getEmail());
        textViewFechaNacimientoInfoContacto.setText(textViewFechaNacimientoInfoContacto.getText()+" "+ contacto.getDate());
        textViewDetallesInfoContacto.setText(textViewDetallesInfoContacto.getText()+" "+contacto.getDescription());

    }


}