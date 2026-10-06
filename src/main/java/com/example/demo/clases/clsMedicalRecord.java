package com.example.demo.clases;

public class clsMedicalRecord {
    private int id;
    private int idPet;
    private String petName; // Para mostrar el nombre de la mascota en la tabla
    private String visitDate;
    private String diagnosis;
    private String treatment;
    private double weight;

    public clsMedicalRecord(int id, int idPet, String petName, String visitDate, String diagnosis, String treatment, double weight) {
        this.id = id;
        this.idPet = idPet;
        this.petName = petName;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.weight = weight;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPet() { return idPet; }
    public void setIdPet(int idPet) { this.idPet = idPet; }
    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }
    public String getVisitDate() { return visitDate; }
    public void setVisitDate(String visitDate) { this.visitDate = visitDate; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}