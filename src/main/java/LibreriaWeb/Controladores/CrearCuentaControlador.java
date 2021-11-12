/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import LibreriaWeb.Entidades.Cliente;
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
                                           @RequestParam @Nullable Long documento,
//                                           @RequestParam @Nullable String sexo,
                                           @RequestParam @Nullable String telefono,
                                           @RequestParam @Nullable String contrasenia1, 
                                           @RequestParam @Nullable String contrasenia2) throws Exception {
        try {
            cl.CrearCliente(nombre, apellido, documento, telefono, contrasenia1, contrasenia2);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/IniciarSesion";
        } catch (Exception ex) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "redirect:/CrearCuenta";
        }
    }
}
