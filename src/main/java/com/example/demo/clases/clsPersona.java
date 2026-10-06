package com.example.demo.clases;

public class clsPersona {
    private int id;
    private String nombre;

    public clsPersona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String tipoPersona() {
        return "Cliente";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}