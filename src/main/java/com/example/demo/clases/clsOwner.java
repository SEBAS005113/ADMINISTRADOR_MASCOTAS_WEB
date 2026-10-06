package com.example.demo.clases;

public class clsOwner {
    private int id;
    private String document;
    private String name;
    private String phone;
    private String address;
    private String email;

    public clsOwner(int id, String document, String name, String phone, String address, String email) {
        this.id = id;
        this.document = document;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}