package com.example.demo.clases;

public class clsCat extends clsPet {
    private boolean toxicFree;

    public clsCat(int id, String code, String name, int bornYear, String color, String healthStatus, String breed, boolean toxicFree) {
        super(id, code, name, bornYear, color, healthStatus, breed);
        this.toxicFree = toxicFree;
    }

    public boolean isToxicFree() { return toxicFree; }
    public void setToxicFree(boolean toxicFree) { this.toxicFree = toxicFree; }
}