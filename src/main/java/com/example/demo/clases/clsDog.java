package com.example.demo.clases;

public class clsDog extends clsPet {
    private boolean pedigree;

    public clsDog() {
        super();
    }

    public clsDog(int id, String code, String name, int bornYear, String color, String healthStatus, String breed, boolean pedigree) {
        super(id, code, name, bornYear, color, healthStatus, breed);
        this.pedigree = pedigree;
    }

    public boolean isPedigree() { return pedigree; }
    public void setPedigree(boolean pedigree) { this.pedigree = pedigree; }
}