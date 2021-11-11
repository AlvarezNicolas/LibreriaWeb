/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Autor;
import LibreriaWeb.Entidades.Libro;
import LibreriaWeb.Repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anico
 */
@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void agregarAutor(String nombre, Boolean alta) throws Exception {
        
        Autor autor = new Autor();
        validar(nombre);

        autor.setNombre(nombre);
        autor.setAlta(alta);
        
        autorRepositorio.save(autor);
    }
    
    @Transactional
    public void modificarAutor(String id, String nombre, Boolean alta) throws Exception{
        
        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
           autor.setNombre(nombre);
           autor.setAlta(alta);
           autorRepositorio.save(autor);
        }else{
            throw new Exception("El autor que esta deseando modificar no existe");
        }
    }
    
    @Transactional
    public void eliminarAutor(String id) throws Exception{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor);
        }else{
            throw new Exception("El autor que desea eliminar no existe o ya a sido eliminado anteriormente");
        }
    }

    public void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar vacio");
        }
    }

     public List<Autor> listarAutores(){
        List<Autor> autor = autorRepositorio.ListarAutores();
        return autor;
    }

}
