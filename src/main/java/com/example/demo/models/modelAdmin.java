package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class modelAdmin {
    private clsDbData dbData = new clsDbData();

    public boolean validarLogin(String username, String password) {
        String query = "SELECT * FROM tb_admin WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true si encuentra el administrador
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}