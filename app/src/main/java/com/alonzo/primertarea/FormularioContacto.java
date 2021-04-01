package com.alonzo.primertarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FormularioContacto extends AppCompatActivity {
    private EditText editTextDetails;
    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextPhone;
    private Button buttonNext;
    private DatePicker datePicker;
    private LinearLayout linearLayout;
    private EditText editTexts[];
    private TextWatcher textWatcher;
    ArrayList datos;
    int[] fecha;
    int contacto;
    protected View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_contacto);
        editTextDetails = (EditText) findViewById(R.id.editTextDetails);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        linearLayout = findViewById(R.id.linearLayout);
        editTexts = new EditText[]{editTextName,editTextPhone,editTextEmail,editTextDetails};
        fecha = new int[3];
        datos = new ArrayList<String>();
        contacto = getIntent().getIntExtra("contacto",-1);

        if (contacto != -1){
            llenado(Globals.misContactos.get(getIntent().getIntExtra("contacto",-1)));
        }
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarCampos(v);
            }
        });


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(FormularioContacto.this);
                clearFocus();
            }
        });
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString().isEmpty())){
                    for (EditText e:editTexts) {
                        if (s.toString().matches(e.getText().toString())){
                            e.setCompoundDrawablesWithIntrinsicBounds(e.getCompoundDrawables()[0],null,getDrawable(R.drawable.ic_baseline_check_circle_24),null);
                            break;
                        }


                    }

                }
                else{
                    for (EditText e: editTexts) {
                        if (e.getText().toString().isEmpty()){
                            if (e.getCompoundDrawables().length != 1)
                            e.setCompoundDrawablesWithIntrinsicBounds(e.getCompoundDrawables()[0],null,null,null);
                            }
                        }
                    }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
        agregarTextWatches();
    }

    public void Siguiente() {
        datos.clear();
        Intent next = new Intent(FormularioContacto.this,ConfirmarDatos.class);
        for (EditText e:editTexts) {
            datos.add(e.getText().toString());
        }
        datos.add(datePicker.getDayOfMonth()+"/"+(((int)datePicker.getMonth())+1)+"/"+datePicker.getYear());
        fecha[0] = datePicker.getDayOfMonth();
        fecha[1] = datePicker.getMonth();
        fecha[2] = datePicker.getYear();
        next.putExtra("datos",datos);
        next.putExtra("fecha",fecha);
        next.putExtra("contacto",contacto);
        startActivity(next);
    }

    public void comprobarCampos(View v) {
        Boolean pass = true;
        for (EditText e : editTexts) {
            if (e.getText().toString().isEmpty()) {
                pass = false;
                break;
            }
        }
        if (pass){
            Siguiente();
        }
        else{
            Snackbar.make(v,getResources().getString(R.string.datosIncompletos),BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }



    public void hideKeyboard(FormularioContacto activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(FormularioContacto.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void clearFocus(){
        for (EditText e:editTexts) {
            if (e.isFocused()){
                e.clearFocus();
                break;
            }
        }
    }

    public void agregarTextWatches(){
        for (EditText e:editTexts) {
            e.addTextChangedListener(textWatcher);
        }
    }

    public void llenado(Contacto contacto){
        editTextName.setText(contacto.getName());
        editTextDetails.setText(contacto.getDescription());
        editTextEmail.setText(contacto.getEmail());
        editTextPhone.setText(contacto.getPhone());
        datePicker.updateDate(contacto.getYear(),contacto.getMonth(),contacto.getDay());
    }

    @Override
    public void onBackPressed(){
        Snackbar.make(findViewById(R.id.linearLayout),getResources().getString(R.string.snackBarSalir),BaseTransientBottomBar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.salir), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isTaskRoot()){
                            finishAffinity();
                            System.exit(0);
                        }
                        else{
                            onBackPressed();
                            finish();
                        }

                    }
                })
                .setActionTextColor(getColor(R.color.colorPrimary))
                .show();
    }

}