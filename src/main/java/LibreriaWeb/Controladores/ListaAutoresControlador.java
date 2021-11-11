/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import LibreriaWeb.Entidades.Autor;
import LibreriaWeb.Servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/ListaAutores")
public class ListaAutoresControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("")
    public String lista(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "listaAutores.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            autorServicio.eliminarAutor(id);
            return "redirect:/ListaAutores";
        } catch (Exception e) {
            return "index";
        }
    }
}
