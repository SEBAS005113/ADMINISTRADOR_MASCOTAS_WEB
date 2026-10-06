package com.example.demo.controllers;

import com.example.demo.clases.clsDog;
import com.example.demo.clases.clsCat;
import com.example.demo.models.modelDog;
import com.example.demo.models.modelCat;
import com.example.demo.models.modelPet;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import java.io.ByteArrayInputStream;

@Controller
public class ctlPet {

    private modelDog modelDog = new modelDog();
    private modelCat modelCat = new modelCat();
    private modelPet modelPet = new modelPet();

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @GetMapping("/generar-registro")
    public ResponseEntity<InputStreamResource> generarRegistroExcel(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return ResponseEntity.status(403).body(null);
        }
        ByteArrayInputStream stream = modelPet.generarExcelReporte();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte_mascotas.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }

    @PostMapping("/perro/guardar")
    public String guardarPerro(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("bornYear") int bornYear, @RequestParam("color") String color,
            @RequestParam("healthStatus") String healthStatus, @RequestParam("breed") String breed,
            @RequestParam(value = "pedigree", required = false, defaultValue = "false") boolean pedigree, 
            HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelDog.crearDog(new clsDog(0, code, name, bornYear, color, healthStatus, breed, pedigree));
        model.addAttribute(res ? "exito" : "error", res ? "¡Perro creado con éxito!" : "Error: El código ya se encuentra registrado.");
        model.addAttribute("tabActiva", "perro");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/perro/buscar")
    public String buscarPerro(@RequestParam("code") String code, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        clsDog dog = modelDog.buscarPorCodigo(code);
        if (dog != null) {
            model.addAttribute("perro", dog);
            model.addAttribute("exito", "¡Perro encontrado!");
        } else {
            model.addAttribute("error", "No se encontró ningún perro con ese código.");
        }
        model.addAttribute("tabActiva", "perro");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/perro/editar")
    public String editarPerro(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("bornYear") int bornYear, @RequestParam("color") String color,
            @RequestParam("healthStatus") String healthStatus, @RequestParam("breed") String breed,
            @RequestParam(value = "pedigree", required = false, defaultValue = "false") boolean pedigree, 
            HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelDog.actualizarDog(new clsDog(0, code, name, bornYear, color, healthStatus, breed, pedigree));
        model.addAttribute(res ? "exito" : "error", res ? "¡Perro actualizado con éxito!" : "No se pudo actualizar.");
        model.addAttribute("tabActiva", "perro");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/perro/eliminar")
    public String eliminarPerroForm(@RequestParam("code") String code, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelDog.eliminarPorCodigo(code);
        model.addAttribute(res ? "exito" : "error", res ? "¡Perro eliminado correctamente!" : "No se pudo eliminar.");
        model.addAttribute("tabActiva", "perro");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/gato/guardar")
    public String guardarGato(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("bornYear") int bornYear, @RequestParam("color") String color,
            @RequestParam("healthStatus") String healthStatus, @RequestParam("breed") String breed, 
            HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelCat.crearCat(new clsCat(0, code, name, bornYear, color, healthStatus, breed, true));
        model.addAttribute(res ? "exito" : "error", res ? "¡Gato creado con éxito!" : "Error: El código ya se encuentra registrado.");
        model.addAttribute("tabActiva", "gato");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/gato/buscar")
    public String buscarGato(@RequestParam("code") String code, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        clsCat cat = modelCat.buscarPorCodigo(code);
        if (cat != null) {
            model.addAttribute("gato", cat);
            model.addAttribute("exito", "¡Gato encontrado!");
        } else {
            model.addAttribute("error", "No se encontró ningún gato con ese código.");
        }
        model.addAttribute("tabActiva", "gato");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/gato/editar")
    public String editarGato(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("bornYear") int bornYear, @RequestParam("color") String color,
            @RequestParam("healthStatus") String healthStatus, @RequestParam("breed") String breed, 
            HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelCat.actualizarCat(new clsCat(0, code, name, bornYear, color, healthStatus, breed, true));
        model.addAttribute(res ? "exito" : "error", res ? "¡Gato actualizado con éxito!" : "No se pudo actualizar.");
        model.addAttribute("tabActiva", "gato");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @PostMapping("/gato/eliminar")
    public String eliminarGatoForm(@RequestParam("code") String code, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        boolean res = modelCat.eliminarPorCodigo(code);
        model.addAttribute(res ? "exito" : "error", res ? "¡Gato eliminado correctamente!" : "No se pudo eliminar.");
        model.addAttribute("tabActiva", "gato");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPorId(@PathVariable("id") int id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/login";
        
        model.addAttribute("tabActiva", "listar");
        model.addAttribute("mascotas", modelPet.listarMascotas());
        return "index";
    }
}