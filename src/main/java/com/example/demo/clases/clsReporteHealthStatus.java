package com.example.demo.clases;

public class clsReporteHealthStatus {
    private String petCode;
    private String petName;
    private String healthStatus;
    private String breed;

    public clsReporteHealthStatus() {}

    public clsReporteHealthStatus(String petCode, String petName, String healthStatus, String breed) {
        this.petCode = petCode;
        this.petName = petName;
        this.healthStatus = healthStatus;
        this.breed = breed;
    }

    public String getPetCode() { return petCode; }
    public void setPetCode(String petCode) { this.petCode = petCode; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
}