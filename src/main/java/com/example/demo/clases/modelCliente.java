package com.example.demo.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class modelCliente {
    clsDbData dbData = new clsDbData();

    public boolean crearCliente(clsPersona p) {
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            String query = "INSERT INTO persona(nombre) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, p.getNombre());
            int resultado = statement.executeUpdate();
            return resultado > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}