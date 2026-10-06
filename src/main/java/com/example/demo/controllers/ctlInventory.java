package com.example.demo.controllers;

import com.example.demo.clases.clsInventory;
import com.example.demo.models.modelInventory;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ctlInventory {

    private modelInventory modelInv = new modelInventory();

    @GetMapping("/inventario")
    public String listarInventario(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        model.addAttribute("inventario", modelInv.listarInventario());
        return "inventory";
    }

    @PostMapping("/inventario/guardar")
    public String guardarItem(@RequestParam("code") String code,
                              @RequestParam("name") String name,
                              @RequestParam("category") String category,
                              @RequestParam("stock") int stock,
                              @RequestParam("price") double price,
                              HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelInv.crearItem(new clsInventory(0, code, name, category, stock, price));
        model.addAttribute(res ? "exito" : "error", res ? "¡Producto registrado con éxito!" : "Error al registrar.");
        model.addAttribute("inventario", modelInv.listarInventario());
        return "inventory";
    }

    @GetMapping("/inventario/eliminar/{id}")
    public String eliminarItem(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        modelInv.eliminarItem(id);
        return "redirect:/inventario";
    }
}