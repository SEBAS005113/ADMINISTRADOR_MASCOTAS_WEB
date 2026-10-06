package com.example.demo.clases;

public class clsDbData {
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String user = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/administrador_mascotas?useSSL=false&serverTimezone=UTC";

    public String getDriver() { return driver; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getUrl() { return url; }
}