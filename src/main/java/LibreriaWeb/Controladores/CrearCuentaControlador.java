/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import LibreriaWeb.Errores.ErrorServicio;
import LibreriaWeb.Servicios.ClienteServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/CrearCuenta")
public class CrearCuentaControlador {

    private ClienteServicio cs;

    @GetMapping("")
    public ModelAndView CrearCuenta() {
        return new ModelAndView("CrearCuenta");
    }

    @PostMapping("/CrearUsuario")
    public String guardar(RedirectAttributes atributo, 
            @RequestParam @Nullable String nombre,
            @RequestParam @Nullable String apellido,
            @Nullable MultipartFile archivo,
            @RequestParam @Nullable Long documento,
            @RequestParam @Nullable String email,
            @RequestParam @Nullable String telefono,
            @RequestParam @Nullable String contrasenia1,
            @RequestParam @Nullable String contrasenia2) throws Exception {
        try {

            cs.crearCliente(archivo, nombre, apellido, documento, email, telefono, contrasenia1, contrasenia2);

        } catch (Exception ex) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            atributo.addAttribute("error", ex.getMessage());
            return "redirect:/CrearCuenta";
        }
        atributo.addAttribute("exito", "Bienvenido a History Book");
        atributo.addAttribute("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "redirect:/IniciarSesion";
    }
}
