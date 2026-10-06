package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import com.example.demo.clases.clsMedicalRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class modelMedicalRecord {
    private clsDbData dbData = new clsDbData();

    public List<clsMedicalRecord> listarHistoriales() {
        List<clsMedicalRecord> lista = new ArrayList<>();
        String query = "SELECT h.*, p.name AS pet_name FROM tb_medical_record h JOIN tb_pet p ON h.id_pet = p.id ORDER BY h.visit_date DESC";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lista.add(new clsMedicalRecord(
                    rs.getInt("id"),
                    rs.getInt("id_pet"),
                    rs.getString("pet_name"),
                    rs.getString("visit_date"),
                    rs.getString("diagnosis"),
                    rs.getString("treatment"),
                    rs.getDouble("weight")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean crearHistorial(clsMedicalRecord record) {
        String query = "INSERT INTO tb_medical_record (id_pet, visit_date, diagnosis, treatment, weight) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, record.getIdPet());
            stmt.setString(2, record.getVisitDate());
            stmt.setString(3, record.getDiagnosis());
            stmt.setString(4, record.getTreatment());
            stmt.setDouble(5, record.getWeight());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarHistorial(int id) {
        String query = "DELETE FROM tb_medical_record WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}