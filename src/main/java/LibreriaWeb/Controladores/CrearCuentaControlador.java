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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public String guardar(ModelMap modelo, @RequestParam @Nullable String nombre,
            @RequestParam @Nullable String apellido,
            @Nullable MultipartFile archivo,
            @RequestParam @Nullable Long documento,
            @RequestParam @Nullable String email,
            @RequestParam @Nullable String telefono,
            @RequestParam @Nullable String contrasenia1,
            @RequestParam @Nullable String contrasenia2) throws Exception {
        try {
            cl.crearCliente(archivo, nombre, apellido, documento, email, telefono, contrasenia1, contrasenia2);
           
        } catch (ErrorServicio ex) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apeliido", apellido);
            modelo.put("documento", documento);
            modelo.put("email", email);
            modelo.put("telefono", telefono);
            modelo.put("contrasenia1", contrasenia1);
            modelo.put("contrasenia2", contrasenia2);
            
            return "redirect:/CrearCuenta";
        }
         modelo.put("exito", "Bienvenido a History Book");
         modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "redirect:/IniciarSesion";
    }
}
