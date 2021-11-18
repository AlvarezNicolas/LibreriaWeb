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

    private ClienteServicio cl;

    @GetMapping("")
    public ModelAndView CrearCuenta() {
        return new ModelAndView("CrearCuenta");
    }

    @PostMapping("/CrearUsuario")
    public String guardar(RedirectAttributes atributo, @RequestParam @Nullable @NonNull String nombre,
            @RequestParam @Nullable @NonNull String apellido,
            @Nullable @NonNull MultipartFile archivo,
            @RequestParam @Nullable @NonNull Long documento,
            @RequestParam @Nullable @NonNull String email,
            @RequestParam @Nullable @NonNull String telefono,
            @RequestParam @Nullable @NonNull String contrasenia1,
            @RequestParam @Nullable @NonNull String contrasenia2) throws Exception {
        try {
            cl.crearCliente(archivo, nombre, apellido, documento, email, telefono, contrasenia1, contrasenia2);
           
        } catch (ErrorServicio ex) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            atributo.addAttribute("error", ex.getMessage());
            atributo.addAttribute("nombre", nombre);
            atributo.addAttribute("apeliido", apellido);
            atributo.addAttribute("documento", documento);
            atributo.addAttribute("email", email);
            atributo.addAttribute("telefono", telefono);
            atributo.addAttribute("contrasenia1", contrasenia1);
            atributo.addAttribute("contrasenia2", contrasenia2);
            
            return "redirect:/CrearCuenta";
        }
         atributo.addAttribute("exito", "Bienvenido a History Book");
         atributo.addAttribute("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "redirect:/IniciarSesion";
    }
}
