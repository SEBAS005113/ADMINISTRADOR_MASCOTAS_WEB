package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import com.example.demo.clases.clsAppointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class modelAppointment {
    private clsDbData dbData = new clsDbData();

    public List<clsAppointment> listarCitas() {
        List<clsAppointment> lista = new ArrayList<>();
        String query = "SELECT a.*, p.name AS pet_name FROM tb_appointment a JOIN tb_pet p ON a.id_pet = p.id ORDER BY a.appointment_date DESC";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lista.add(new clsAppointment(
                    rs.getInt("id"),
                    rs.getInt("id_pet"),
                    rs.getString("pet_name"),
                    rs.getString("appointment_date"),
                    rs.getString("reason"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al listar citas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crearCita(clsAppointment app) {
        if (app.getAppointmentDate() == null || app.getAppointmentDate().isEmpty()) {
            return false;
        }

        // Limpiar formato datetime-local ('T' -> espacio y asegurar segundos)
        String fechaFormateada = app.getAppointmentDate().replace("T", " ");
        if (fechaFormateada.length() == 16) {
            fechaFormateada += ":00";
        }

        String query = "INSERT INTO tb_appointment (id_pet, appointment_date, reason, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, app.getIdPet());
            stmt.setString(2, fechaFormateada);
            stmt.setString(3, app.getReason());
            stmt.setString(4, app.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("❌ ERROR SQL AL CREAR CITA: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCita(int id) {
        String query = "DELETE FROM tb_appointment WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}