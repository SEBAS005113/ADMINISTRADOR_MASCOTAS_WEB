package com.example.demo.controllers;

import com.example.demo.models.modelAdmin;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ctlLogin {

    private modelAdmin modelAdmin = new modelAdmin();

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        // Si ya está logueado, redirigir directamente al panel
        if (session.getAttribute("admin") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                HttpSession session,
                                Model model) {
        boolean valido = modelAdmin.validarLogin(username, password);
        if (valido) {
            session.setAttribute("admin", username);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruir la sesión
        return "redirect:/login";
    }
}
