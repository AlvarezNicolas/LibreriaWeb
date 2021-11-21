/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Editorial;
import LibreriaWeb.Repositorios.EditorialRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anico
 */
@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio EditorialRepositorio;
 
    @Transactional
    public void agregarEditorial(String nombre, Boolean alta) throws Exception {
        
        Editorial editorial = new Editorial();
        validar(nombre);

        editorial.setNombre(nombre);
        editorial.setAlta(alta);
        
        EditorialRepositorio.save(editorial);
    }
    
    @Transactional
    public void modificarEditorial(String idEditorial, String nombre, Boolean alta) throws Exception{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = EditorialRepositorio.findById(idEditorial);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
           editorial.setNombre(nombre);
           editorial.setAlta(alta);
           EditorialRepositorio.save(editorial);
        }else{
            throw new Exception("La editorial que esta deseando modificar no existe");
        }
    }
    
    @Transactional
    public void eliminarEditorial(String idEditorial, String nombre) throws Exception{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = EditorialRepositorio.findById(idEditorial);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            EditorialRepositorio.save(editorial);
        }else{
            throw new Exception("La editorial que desea eliminar no existe o ya a sido eliminado anteriormente");
        }
    }

    public void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar vacio");
        }
    }
}
