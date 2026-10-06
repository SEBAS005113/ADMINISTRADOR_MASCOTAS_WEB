package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import com.example.demo.clases.clsCat;
import java.sql.*;

public class modelCat {
    private clsDbData dbData = new clsDbData();

    public boolean crearCat(clsCat cat) {
        // Validar si el código ya existe antes de insertar
        if (buscarPorCodigo(cat.getCode()) != null) {
            return false; 
        }

        String queryPet = "INSERT INTO tb_pet (code, name, born_year, color, health_status) VALUES (?, ?, ?, ?, ?)";
        String queryCat = "INSERT INTO tb_cat (breed, id_pet) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            conn.setAutoCommit(false);
            PreparedStatement stmtPet = conn.prepareStatement(queryPet, Statement.RETURN_GENERATED_KEYS);
            stmtPet.setString(1, cat.getCode());
            stmtPet.setString(2, cat.getName());
            stmtPet.setInt(3, cat.getBornYear());
            stmtPet.setString(4, cat.getColor());
            stmtPet.setString(5, cat.getHealthStatus());
            if (stmtPet.executeUpdate() == 0) { conn.rollback(); return false; }
            
            int idPet = 0;
            try (ResultSet keys = stmtPet.getGeneratedKeys()) {
                if (keys.next()) idPet = keys.getInt(1);
            }
            PreparedStatement stmtCat = conn.prepareStatement(queryCat);
            stmtCat.setString(1, cat.getBreed());
            stmtCat.setInt(2, idPet);
            stmtCat.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public clsCat buscarPorCodigo(String code) {
        String query = "SELECT p.*, c.breed FROM tb_pet p JOIN tb_cat c ON p.id = c.id_pet WHERE p.code = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new clsCat(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
                                      rs.getInt("born_year"), rs.getString("color"), rs.getString("health_status"), 
                                      rs.getString("breed"), true);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean actualizarCat(clsCat cat) {
        String getId = "SELECT id FROM tb_pet WHERE code = ?";
        String updatePet = "UPDATE tb_pet SET name=?, born_year=?, color=?, health_status=? WHERE id=?";
        String updateCat = "UPDATE tb_cat SET breed=? WHERE id_pet=?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            conn.setAutoCommit(false);
            int idPet = -1;
            try (PreparedStatement sGet = conn.prepareStatement(getId)) {
                sGet.setString(1, cat.getCode());
                try (ResultSet rs = sGet.executeQuery()) { if (rs.next()) idPet = rs.getInt("id"); }
            }
            if (idPet == -1) { conn.rollback(); return false; }

            try (PreparedStatement s1 = conn.prepareStatement(updatePet);
                 PreparedStatement s2 = conn.prepareStatement(updateCat)) {
                s1.setString(1, cat.getName()); s1.setInt(2, cat.getBornYear());
                s1.setString(3, cat.getColor()); s1.setString(4, cat.getHealthStatus()); s1.setInt(5, idPet);
                s1.executeUpdate();

                s2.setString(1, cat.getBreed()); s2.setInt(2, idPet);
                s2.executeUpdate();
                conn.commit();
                return true;
            } catch (Exception ex) { conn.rollback(); ex.printStackTrace(); return false; }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarPorCodigo(String code) {
        String getId = "SELECT id FROM tb_pet WHERE code = ?";
        String deleteDog = "DELETE FROM tb_dog WHERE id_pet = ?";
        String deleteCat = "DELETE FROM tb_cat WHERE id_pet = ?";
        String deletePet = "DELETE FROM tb_pet WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            conn.setAutoCommit(false);
            int idPet = -1;
            try (PreparedStatement sGet = conn.prepareStatement(getId)) {
                sGet.setString(1, code);
                try (ResultSet rs = sGet.executeQuery()) { if (rs.next()) idPet = rs.getInt("id"); }
            }
            if (idPet != -1) {
                try (PreparedStatement s1 = conn.prepareStatement(deleteDog);
                     PreparedStatement s2 = conn.prepareStatement(deleteCat);
                     PreparedStatement s3 = conn.prepareStatement(deletePet)) {
                    s1.setInt(1, idPet); s1.executeUpdate();
                    s2.setInt(1, idPet); s2.executeUpdate();
                    s3.setInt(1, idPet); s3.executeUpdate();
                    conn.commit();
                    return true;
                }
            }
            conn.rollback();
            return false;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}