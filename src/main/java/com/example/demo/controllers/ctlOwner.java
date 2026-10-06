package com.example.demo.controllers;

import com.example.demo.clases.clsOwner;
import com.example.demo.models.modelOwner;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ctlOwner {

    private modelOwner modelOwner = new modelOwner();

    @GetMapping("/propietarios")
    public String listarPropietarios(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        model.addAttribute("propietarios", modelOwner.listarPropietarios());
        return "owners";
    }

    @PostMapping("/propietarios/guardar")
    public String guardarPropietario(@RequestParam("document") String document,
                                     @RequestParam("name") String name,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("address") String address,
                                     @RequestParam("email") String email,
                                     HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelOwner.crearPropietario(new clsOwner(0, document, name, phone, address, email));
        model.addAttribute(res ? "exito" : "error", res ? "¡Propietario registrado con éxito!" : "Error al registrar.");
        model.addAttribute("propietarios", modelOwner.listarPropietarios());
        return "owners";
    }

    @GetMapping("/propietarios/eliminar/{id}")
    public String eliminarPropietario(@PathVariable("id") int id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        modelOwner.eliminarPropietario(id);
        return "redirect:/propietarios";
    }
}