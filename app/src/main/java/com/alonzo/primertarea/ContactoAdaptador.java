package com.alonzo.primertarea;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactoAdaptador extends RecyclerView.Adapter<ContactoAdaptador.ContactoViewHolder>{
    ArrayList<Contacto> contactos;
    Activity activity;

    public ContactoAdaptador(ArrayList<Contacto> contactos, Activity activity){
        this.contactos = contactos;
        this.activity = activity;
    }

    public Activity getActivity(){
        return this.activity;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_contacto, parent, false);
        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder contactoViewHolder, int position) {
        Contacto contacto = contactos.get(position);
        contactoViewHolder.textViewNombre.setText(contacto.getName());
        contactoViewHolder.textViewTelefono.setText(contacto.getPhone());
        contactoViewHolder.imageFotoContacto.setImageResource(R.drawable.ic_fotouniversal);
        contactoViewHolder.imageFotoContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verInfo = new Intent(activity,InformacionContacto.class);
                verInfo.putExtra("info",position);
                activity.startActivity(verInfo);
            }
        });
        if (contacto.getFavorito()){
            contactoViewHolder.buttonFavorito.setBackground(activity.getDrawable(R.drawable.ic_staryellow));
        }
        else{
            contactoViewHolder.buttonFavorito.setBackground(activity.getDrawable(R.drawable.ic_star));
        }

        contactoViewHolder.buttonFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button cantidad = (Button) activity.findViewById(R.id.buttonVerFavoritos);
                if (contacto.getFavorito()){
                    contacto.quitarFavorito();
                    contactoViewHolder.buttonFavorito.setBackground(activity.getDrawable(R.drawable.ic_star));
                    cantidad.setText(String.valueOf(GlobalVariables.cantFavoritos));
                }
                else{
                    if (GlobalVariables.cantFavoritos == 5){
                        Snackbar.make(v,"Sólo cinco favoritos",BaseTransientBottomBar.LENGTH_SHORT).show();

                    }
                    else{
                        contacto.marcarFavorito();
                        contactoViewHolder.buttonFavorito.setBackground(activity.getDrawable(R.drawable.ic_staryellow));
                        cantidad.setText(String.valueOf(GlobalVariables.cantFavoritos));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (GlobalVariables.viendoFavoritos){
            return Globals.misFavoritos.size();
        }
        else{
            return Globals.misContactos.size();
        }

    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFotoContacto;
        private TextView textViewNombre;
        private TextView textViewTelefono;
        private ImageButton buttonFavorito;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFotoContacto = (ImageView) itemView.findViewById(R.id.imageFotoContacto);
            textViewNombre = (TextView) itemView.findViewById(R.id.textViewNombre);
            textViewTelefono = (TextView) itemView.findViewById(R.id.textViewTelefono);
            buttonFavorito = (ImageButton) itemView.findViewById(R.id.buttonFavorito);
        }

    }
}
