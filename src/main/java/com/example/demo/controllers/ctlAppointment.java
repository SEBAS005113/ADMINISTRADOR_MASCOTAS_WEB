package com.example.demo.controllers;

import com.example.demo.clases.clsAppointment;
import com.example.demo.models.modelAppointment;
import com.example.demo.models.modelPet;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ctlAppointment {

    private modelAppointment modelApp = new modelAppointment();
    private modelPet modelPet = new modelPet();

    @GetMapping("/citas")
    public String listarCitas(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        model.addAttribute("citas", modelApp.listarCitas());
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "appointments";
    }

    @PostMapping("/citas/guardar")
    public String guardarCita(@RequestParam("idPet") int idPet,
                              @RequestParam("appointmentDate") String appointmentDate,
                              @RequestParam("reason") String reason,
                              @RequestParam("status") String status,
                              HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelApp.crearCita(new clsAppointment(0, idPet, "", appointmentDate, reason, status));
        model.addAttribute(res ? "exito" : "error", res ? "¡Cita agendada con éxito!" : "Error al agendar.");
        model.addAttribute("citas", modelApp.listarCitas());
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "appointments";
    }

    @GetMapping("/citas/eliminar/{id}")
    public String eliminarCita(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        modelApp.eliminarCita(id);
        return "redirect:/citas";
    }
}