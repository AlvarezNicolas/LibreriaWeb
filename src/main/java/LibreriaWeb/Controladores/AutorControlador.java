/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import LibreriaWeb.Entidades.Autor;
import LibreriaWeb.Servicios.AutorServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/CargarAutor")
public class AutorControlador {

    @GetMapping("")
    public ModelAndView CargarAutor() {
        return new ModelAndView("CargarAutor");
    }

    @Autowired
    private AutorServicio as;

    @PostMapping("/PersistirAutor")
    public String guardar(ModelMap modelo, @RequestParam String nombre) throws Exception {
        try {
            Autor autor = new Autor();
            autor.setNombre(nombre);
            as.agregarAutor(nombre, Boolean.TRUE);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/CargarAutor";
        } catch (Exception ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "El nombre del autor no puede ser nulo");
            return "/index";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(RedirectAttributes atributos, @PathVariable String id) throws Exception {
        atributos.addAttribute("autor", as.listarAutor(id));
        return "/ModificarAutor";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(RedirectAttributes atributos, @PathVariable String id, @RequestParam String nombre) throws Exception {
        try {
            as.modificarAutor(id, nombre);
            return "redirect:/ListaAutores";
        } catch (Exception e) {
            return "redirect:/ModificiarAutor";
        }
    }
}
