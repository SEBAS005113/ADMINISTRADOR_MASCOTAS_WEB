package com.example.demo.models;

import com.example.demo.clases.clsDbData;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class modelPet {
    private clsDbData dbData = new clsDbData();

    public List<Map<String, Object>> listarMascotas() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String query = "SELECT p.*, " +
                       "COALESCE(d.breed, c.breed) as breed, " +
                       "d.pedigree, " +
                       "CASE WHEN d.id_pet IS NOT NULL THEN 'Perro 🐶' ELSE 'Gato 🐱' END as tipo " +
                       "FROM tb_pet p " +
                       "LEFT JOIN tb_dog d ON p.id = d.id_pet " +
                       "LEFT JOIN tb_cat c ON p.id = c.id_pet";

        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("code", rs.getString("code"));
                map.put("name", rs.getString("name"));
                map.put("bornYear", rs.getInt("born_year"));
                map.put("color", rs.getString("color"));
                map.put("healthStatus", rs.getString("health_status"));
                map.put("breed", rs.getString("breed"));
                map.put("pedigree", rs.getBoolean("pedigree"));
                map.put("tipo", rs.getString("tipo"));
                lista.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminarMascota(int id) {
        String deleteDog = "DELETE FROM tb_dog WHERE id_pet = ?";
        String deleteCat = "DELETE FROM tb_cat WHERE id_pet = ?";
        String deletePet = "DELETE FROM tb_pet WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(dbData.getUrl(), dbData.getUser(), dbData.getPassword())) {
            conn.setAutoCommit(false);
            try (PreparedStatement s1 = conn.prepareStatement(deleteDog);
                 PreparedStatement s2 = conn.prepareStatement(deleteCat);
                 PreparedStatement s3 = conn.prepareStatement(deletePet)) {
                
                s1.setInt(1, id); s1.executeUpdate();
                s2.setInt(1, id); s2.executeUpdate();
                s3.setInt(1, id); s3.executeUpdate();
                
                conn.commit();
                return true;
            } catch (Exception ex) {
                conn.rollback();
                ex.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para generar el reporte Excel con conteos precisos y formato corporativo sin errores
    public ByteArrayInputStream generarExcelReporte() {
        List<Map<String, Object>> mascotas = listarMascotas();
        
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("Reporte Veterinario");

            // Estilos para encabezados
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // 1. Contadores para Estado de Salud
            Map<String, Integer> saludCounts = new HashMap<>();
            saludCounts.put("Estable", 0);
            saludCounts.put("Enfermo", 0);
            saludCounts.put("Crítico", 0);

            // 2. Contadores para Razas
            Map<String, Integer> razaCounts = new HashMap<>();

            for (Map<String, Object> m : mascotas) {
                String salud = (String) m.get("healthStatus");
                if (salud != null && saludCounts.containsKey(salud)) {
                    saludCounts.put(salud, saludCounts.get(salud) + 1);
                }
                String raza = (String) m.get("breed");
                if (raza != null) {
                    razaCounts.put(raza, razaCounts.getOrDefault(raza, 0) + 1);
                }
            }

            int r = 0;
            
            // --- Tabla 1: Conteo por Estado de Salud ---
            Row rowTitle1 = sheet.createRow(r++);
            rowTitle1.createCell(0).setCellValue("RESUMEN: ESTADO DE SALUD");
            
            Row rowHead1 = sheet.createRow(r++);
            rowHead1.createCell(0).setCellValue("Estado de Salud");
            rowHead1.createCell(1).setCellValue("Cantidad");
            rowHead1.getCell(0).setCellStyle(headerStyle);
            rowHead1.getCell(1).setCellStyle(headerStyle);

            for (Map.Entry<String, Integer> entry : saludCounts.entrySet()) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            r += 2; // Espacio

            // --- Tabla 2: Conteo por Razas ---
            Row rowTitle2 = sheet.createRow(r++);
            rowTitle2.createCell(0).setCellValue("RESUMEN: RAZAS DE MASCOTAS");

            Row rowHead2 = sheet.createRow(r++);
            rowHead2.createCell(0).setCellValue("Raza");
            rowHead2.createCell(1).setCellValue("Cantidad");
            rowHead2.getCell(0).setCellStyle(headerStyle);
            rowHead2.getCell(1).setCellStyle(headerStyle);

            for (Map.Entry<String, Integer> entry : razaCounts.entrySet()) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            r += 2; // Espacio

            // --- Tabla 3: Listado Detallado General ---
            Row rowTitle3 = sheet.createRow(r++);
            rowTitle3.createCell(0).setCellValue("LISTADO GENERAL DE REGISTROS");

            Row rowHead3 = sheet.createRow(r++);
            rowHead3.createCell(0).setCellValue("Código");
            rowHead3.createCell(1).setCellValue("Nombre");
            rowHead3.createCell(2).setCellValue("Tipo");
            rowHead3.createCell(3).setCellValue("Raza");
            rowHead3.createCell(4).setCellValue("Año Nac.");
            rowHead3.createCell(5).setCellValue("Color");
            rowHead3.createCell(6).setCellValue("Salud");
            
            for (int i = 0; i <= 6; i++) {
                rowHead3.getCell(i).setCellStyle(headerStyle);
            }

            for (Map<String, Object> m : mascotas) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue((String) m.get("code"));
                row.createCell(1).setCellValue((String) m.get("name"));
                row.createCell(2).setCellValue((String) m.get("tipo"));
                row.createCell(3).setCellValue((String) m.get("breed"));
                row.createCell(4).setCellValue((Integer) m.get("bornYear"));
                row.createCell(5).setCellValue((String) m.get("color"));
                row.createCell(6).setCellValue((String) m.get("healthStatus"));
            }

            // Autoajustar el ancho de las columnas
            for (int i = 0; i <= 6; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}