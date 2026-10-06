package com.example.demo.clases;

public class clsDoctor {
    private int id;
    private String name;
    private String specialization;

    public clsDoctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void specialization(String specialization) { this.specialization = specialization; }
}
