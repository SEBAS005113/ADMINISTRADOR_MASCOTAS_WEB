package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import com.example.demo.clases.clsOwner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class modelOwner {
    private clsDbData dbData = new clsDbData();

    public List<clsOwner> listarPropietarios() {
        List<clsOwner> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_owner";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lista.add(new clsOwner(
                    rs.getInt("id"),
                    rs.getString("document"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("email")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean crearPropietario(clsOwner owner) {
        String query = "INSERT INTO tb_owner (document, name, phone, address, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, owner.getDocument());
            stmt.setString(2, owner.getName());
            stmt.setString(3, owner.getPhone());
            stmt.setString(4, owner.getAddress());
            stmt.setString(5, owner.getEmail());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarPropietario(int id) {
        String query = "DELETE FROM tb_owner WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}