package com.example.demo.clases;

public class clsIpsPersonas {
    private int id;
    private int idPersona;
    private int idHospital;
    private String rol;

    public clsIpsPersonas() {}

    public clsIpsPersonas(int id, int idPersona, int idHospital, String rol) {
        this.id = id;
        this.idPersona = idPersona;
        this.idHospital = idHospital;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public int getIdHospital() { return idHospital; }
    public void setIdHospital(int idHospital) { this.idHospital = idHospital; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}