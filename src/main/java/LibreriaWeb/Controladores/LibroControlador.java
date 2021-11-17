/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import LibreriaWeb.Entidades.Autor;
import LibreriaWeb.Entidades.Editorial;
import LibreriaWeb.Servicios.LibroServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/CargarLibro")
public class LibroControlador {

    @Autowired
    private LibroServicio ls;

    @GetMapping("")
    public ModelAndView CargarLibro() {
        return new ModelAndView("CargarLibro");
    }

    @PostMapping("/PersistirLibro")
    public String guardar(ModelMap modelo, @RequestParam @Nullable Long isbn, @RequestParam @Nullable String titulo, @RequestParam @Nullable Integer anio, @RequestParam @Nullable Integer ejemplares, @RequestParam @Nullable Boolean alta, @RequestParam @Nullable String NombreAutor, @RequestParam @Nullable String NombreEditorial) throws Exception {
        try {
            Autor autor = new Autor();
            autor.setNombre(NombreAutor);
            Editorial editorial = new Editorial();
            editorial.setNombre(NombreEditorial);
            ls.PersistirLibro(titulo, anio, isbn, ejemplares, autor, editorial, Boolean.TRUE);

        } catch (Exception ex) {
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "/CargarLibro";
        }
        modelo.put("exito", "El libro se ha registrado exitosamente");
        return "/CargarLibro";
    }
}
