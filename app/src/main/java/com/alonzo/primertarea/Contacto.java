package com.alonzo.primertarea;

import java.util.ArrayList;
import java.util.function.BiPredicate;

public class Contacto {
    private String name,email,description,phone,date;
    private int day,month,year;
    private Boolean favorito;

    public Contacto(String name, String phone, String email, String description, String date,int day, int month, int year) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.phone = phone;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.favorito = false;
        Globals.misContactos.add(this);
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getDate() {
        return this.date;
    }

    public Boolean getFavorito() {return this.favorito;}

    public void marcarFavorito(){this.favorito = true; GlobalVariables.cantFavoritos+=1; Globals.misFavoritos.add(this);}

    public void quitarFavorito(){
        if (this.favorito){
            this.favorito = false;
            GlobalVariables.cantFavoritos-=1;
            Globals.misFavoritos.remove(this);
        }

    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
    public void actualizarDatos(String name, String phone, String email, String description, String date,int day, int month, int year){
        this.name = name;
        this.email = email;
        this.description = description;
        this.phone = phone;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
