package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import com.example.demo.clases.clsInventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class modelInventory {
    private clsDbData dbData = new clsDbData();

    public List<clsInventory> listarInventario() {
        List<clsInventory> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_inventory";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lista.add(new clsInventory(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("stock"),
                    rs.getDouble("price")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean crearItem(clsInventory item) {
        String query = "INSERT INTO tb_inventory (code, name, category, stock, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getCode());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getCategory());
            stmt.setInt(4, item.getStock());
            stmt.setDouble(5, item.getPrice());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarItem(int id) {
        String query = "DELETE FROM tb_inventory WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}