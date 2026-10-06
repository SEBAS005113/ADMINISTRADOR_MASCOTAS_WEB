package com.example.demo.controllers;

import com.example.demo.models.modelPet;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ctlDashboard {

    private modelPet modelPet = new modelPet();

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        
        // Pasar estadísticas básicas para las tarjetas de resumen
        var mascotas = modelPet.listarMascotas();
        model.addAttribute("totalMascotas", mascotas.size());
        
        return "dashboard";
    }
}