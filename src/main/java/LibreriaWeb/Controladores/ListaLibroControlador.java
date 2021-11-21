/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import LibreriaWeb.Entidades.Libro;
import LibreriaWeb.Servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/ListaLibros")
public class ListaLibroControlador {

    @Autowired
    private LibroServicio ls;

    @GetMapping("")
    public String lista(ModelMap modelo) {
        List<Libro> libros = ls.listarLibros();
        modelo.addAttribute("libros", libros);
        return "listaLibros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            ls.eliminarLibro(id);
            return "redirect:/ListaLibros";
        } catch (Exception e) {
            return "index";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) throws Exception {
        modelo.put("libro", ls.listarLibro(id));
        return "/ModificarLibro";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String titulo, @RequestParam Integer ejemplares) throws Exception {
        try {
            ls.modificarLibro(id, titulo, ejemplares);
            return "redirect:/ListaLibros";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
