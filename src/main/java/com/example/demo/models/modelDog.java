package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import com.example.demo.clases.clsDog;
import java.sql.*;

public class modelDog {
    private clsDbData dbData = new clsDbData();

    public boolean crearDog(clsDog dog) {
        // Validar si el código ya existe antes de insertar
        if (buscarPorCodigo(dog.getCode()) != null) {
            return false; 
        }

        String queryPet = "INSERT INTO tb_pet (code, name, born_year, color, health_status) VALUES (?, ?, ?, ?, ?)";
        String queryDog = "INSERT INTO tb_dog (breed, pedigree, id_pet) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            conn.setAutoCommit(false);
            PreparedStatement stmtPet = conn.prepareStatement(queryPet, Statement.RETURN_GENERATED_KEYS);
            stmtPet.setString(1, dog.getCode());
            stmtPet.setString(2, dog.getName());
            stmtPet.setInt(3, dog.getBornYear());
            stmtPet.setString(4, dog.getColor());
            stmtPet.setString(5, dog.getHealthStatus());
            if (stmtPet.executeUpdate() == 0) { conn.rollback(); return false; }
            
            int idPet = 0;
            try (ResultSet keys = stmtPet.getGeneratedKeys()) {
                if (keys.next()) idPet = keys.getInt(1);
            }
            PreparedStatement stmtDog = conn.prepareStatement(queryDog);
            stmtDog.setString(1, dog.getBreed());
            stmtDog.setBoolean(2, dog.isPedigree());
            stmtDog.setInt(3, idPet);
            stmtDog.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public clsDog buscarPorCodigo(String code) {
        String query = "SELECT p.*, d.breed, d.pedigree FROM tb_pet p JOIN tb_dog d ON p.id = d.id_pet WHERE p.code = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new clsDog(rs.getInt("id"), rs.getString("code"), rs.getString("name"), 
                                      rs.getInt("born_year"), rs.getString("color"), rs.getString("health_status"), 
                                      rs.getString("breed"), rs.getBoolean("pedigree"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean actualizarDog(clsDog dog) {
        String getId = "SELECT id FROM tb_pet WHERE code = ?";
        String updatePet = "UPDATE tb_pet SET name=?, born_year=?, color=?, health_status=? WHERE id=?";
        String updateDog = "UPDATE tb_dog SET breed=?, pedigree=? WHERE id_pet=?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            conn.setAutoCommit(false);
            int idPet = -1;
            try (PreparedStatement sGet = conn.prepareStatement(getId)) {
                sGet.setString(1, dog.getCode());
                try (ResultSet rs = sGet.executeQuery()) { if (rs.next()) idPet = rs.getInt("id"); }
            }
            if (idPet == -1) { conn.rollback(); return false; }

            try (PreparedStatement s1 = conn.prepareStatement(updatePet);
                 PreparedStatement s2 = conn.prepareStatement(updateDog)) {
                s1.setString(1, dog.getName()); s1.setInt(2, dog.getBornYear());
                s1.setString(3, dog.getColor()); s1.setString(4, dog.getHealthStatus()); s1.setInt(5, idPet);
                s1.executeUpdate();

                s2.setString(1, dog.getBreed()); s2.setBoolean(2, dog.isPedigree()); s2.setInt(3, idPet);
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