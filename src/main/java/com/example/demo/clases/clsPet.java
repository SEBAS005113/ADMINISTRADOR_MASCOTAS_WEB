package com.example.demo.clases;

public class clsPet {
    private int id;
    private String code;
    private String name;
    private int bornYear;
    private String color;
    private String healthStatus;
    private String breed;

    public clsPet() {}

    public clsPet(int id, String code, String name, int bornYear, String color, String healthStatus, String breed) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.bornYear = bornYear;
        this.color = color;
        this.healthStatus = healthStatus;
        this.breed = breed;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBornYear() { return bornYear; }
    public void setBornYear(int bornYear) { this.bornYear = bornYear; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
}