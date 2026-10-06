package com.example.demo.controllers;

import com.example.demo.clases.clsMedicalRecord;
import com.example.demo.models.modelMedicalRecord;
import com.example.demo.models.modelPet;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ctlMedicalRecord {

    private modelMedicalRecord modelRecord = new modelMedicalRecord();
    private modelPet modelPet = new modelPet();

    @GetMapping("/historial")
    public String listarHistorial(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        model.addAttribute("historiales", modelRecord.listarHistoriales());
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "medical-history";
    }

    @PostMapping("/historial/guardar")
    public String guardarHistorial(@RequestParam("idPet") int idPet,
                                   @RequestParam("visitDate") String visitDate,
                                   @RequestParam("diagnosis") String diagnosis,
                                   @RequestParam("treatment") String treatment,
                                   @RequestParam("weight") double weight,
                                   HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelRecord.crearHistorial(new clsMedicalRecord(0, idPet, "", visitDate, diagnosis, treatment, weight));
        model.addAttribute(res ? "exito" : "error", res ? "¡Consulta médica registrada con éxito!" : "Error al registrar.");
        model.addAttribute("historiales", modelRecord.listarHistoriales());
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "medical-history";
    }

    @GetMapping("/historial/eliminar/{id}")
    public String eliminarHistorial(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        modelRecord.eliminarHistorial(id);
        return "redirect:/historial";
    }
}