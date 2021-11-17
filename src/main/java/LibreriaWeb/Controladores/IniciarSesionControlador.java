/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author anico
 */
@Controller
@RequestMapping("/IniciarSesion")
public class IniciarSesionControlador {

    @GetMapping("")
    public ModelAndView IniciarSesion(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Nombre de usuario o contraseña incorrectos");
        }
        return new ModelAndView("IniciarSesion");
    }
}
