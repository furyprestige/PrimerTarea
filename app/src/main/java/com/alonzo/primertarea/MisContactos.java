package com.alonzo.primertarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MisContactos extends AppCompatActivity implements Globals {
    ArrayList contactos = new ArrayList<Contacto>();
    private RecyclerView recyclerViewContactos;
    LinearLayout miLinearLayout;
    private Button botonNuevoContacto;
    private Toolbar toolbarMiToolBar;
    private Button buttonVerFavoritos;
    private ArrayList<Contacto> data;
    TextView textViewCantidad;
    BroadcastReceiver broadcastReceiver;
    ContactoAdaptador adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_contactos);
        toolbarMiToolBar = (Toolbar) findViewById(R.id.toolbarMiToolBar);
        botonNuevoContacto = (Button) findViewById(R.id.botonNuevoContacto);
        buttonVerFavoritos = (Button) findViewById(R.id.buttonVerFavoritos);

        if (Globals.misContactos.size() == 0){
            Intent intent = new Intent(MisContactos.this,FormularioContacto.class);
            startActivity(intent);
            this.finish();
        }
        else{
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals("finish_activity")){
                        finish();
                    }

                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
            buttonVerFavoritos.setText(String.valueOf(GlobalVariables.cantFavoritos));
            recyclerViewContactos = (RecyclerView) findViewById(R.id.recyclerViewContactos);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewContactos.setLayoutManager(llm);
            data = new ArrayList<Contacto>();
            for (Contacto c:Globals.misContactos) {
                data.add(c);
            }
            adaptador = new ContactoAdaptador(data,this);
            if (GlobalVariables.viendoFavoritos){
                buttonVerFavoritos.setBackground(getDrawable(R.drawable.ic_staryellow));
            }
            else {
                buttonVerFavoritos.setBackground(getDrawable(R.drawable.ic_star));
            }
            recyclerViewContactos.setAdapter(adaptador);
        }
        botonNuevoContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoContacto = new Intent(MisContactos.this,FormularioContacto.class);
                startActivity(nuevoContacto);
            }
        });
        buttonVerFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVariables.viendoFavoritos){
                    GlobalVariables.viendoFavoritos = false;
                    buttonVerFavoritos.setBackground(getDrawable(R.drawable.ic_star));
                    inicializarAdaptador(adaptador);

                }
                else{
                    if (GlobalVariables.cantFavoritos != 0){
                        GlobalVariables.viendoFavoritos = true;
                        buttonVerFavoritos.setBackground(getDrawable(R.drawable.ic_staryellow));
                        inicializarAdaptador(adaptador);
                    }
                    else{
                        Snackbar.make(v,"No tienes contactos favoritos",BaseTransientBottomBar.LENGTH_SHORT).show();
                    }


                }
            }
        });


    }

    @Override
    public void onBackPressed(){
        Snackbar.make(findViewById(R.id.recyclerViewContactos),getResources().getString(R.string.snackBarSalir), BaseTransientBottomBar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.salir), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setActionTextColor(getColor(R.color.colorPrimary))
                .show();
    }
    public void inicializarAdaptador(ContactoAdaptador adaptador){
        if (GlobalVariables.viendoFavoritos){
            if (Globals.misFavoritos.size() != 0){
                data.clear();
                for (Contacto c:Globals.misFavoritos) {
                    data.add(c);
                }
                adaptador.notifyDataSetChanged();
            }

        }
        else{
            if (Globals.misContactos.size() != 0){
                data.clear();
                for (Contacto c:Globals.misContactos) {
                    data.add(c);
                }
                adaptador.notifyDataSetChanged();
            }

            }

        }

    }
