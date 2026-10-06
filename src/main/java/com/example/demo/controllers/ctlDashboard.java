package com.example.demo.controllers;

import com.example.demo.models.modelInventory;
import com.example.demo.models.modelAppointment;
import com.example.demo.models.modelPet;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ctlDashboard {

    private modelPet modelPet = new modelPet();
    private modelInventory modelInventory = new modelInventory();
    private modelAppointment modelAppointment = new modelAppointment();

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        
        // Pasar métricas reales a las tarjetas del dashboard
        model.addAttribute("totalMascotas", modelPet.listarMascotas().size());
        model.addAttribute("totalCitas", modelAppointment.listarCitas().size());
        model.addAttribute("totalInventario", modelInventory.listarInventario().size());
        
        return "dashboard";
    }
}