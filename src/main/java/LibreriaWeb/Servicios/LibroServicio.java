/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Autor;
import LibreriaWeb.Entidades.Editorial;
import LibreriaWeb.Entidades.Libro;
import LibreriaWeb.Repositorios.LibroRepositorio;
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
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void PersistirLibro(String titulo, Integer anio, Long isbn, Integer ejemplares, Autor autor, Editorial editorial, Boolean alta) throws Exception {

        if (isbn == null) {
            throw new Exception("Debe cargar un numero para el campo ISBN");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("El nombre del libro no puede estar vacio");
        }
        if (anio == null) {
            throw new Exception("El anio del libro no puede estar vacio");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new Exception("La cantidad de ejemplares debe ser mayor a 0");
        }
        if (autor == null || autor.getNombre().isEmpty()) {
            throw new Exception("El nombre del autor no puede estar vacio");
        }
        if (editorial == null || editorial.getNombre().isEmpty()) {
            throw new Exception("El nombre de la editorial no puede estar vacio");
        }

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(alta);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional
    public Libro modificarLibro(String id, String titulo, Integer ejemplares) throws Exception {

        validarDatos(titulo, ejemplares);

        Libro libro = libroRepositorio.buscarPorId(id);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);

        return libroRepositorio.save(libro);

    }

    @Transactional
    public void eliminarLibro(String id) throws Exception {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new Exception("El autor que desea eliminar no existe o ya a sido eliminado anteriormente");
        }
    }

    private void validar(String titulo) throws Exception {
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("El nombre del libro no puede estar vacio");
        }
    }

    private void validarDatos(String titulo, Integer ejemplares) throws Exception {
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("El nombre del libro no puede estar vacio");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new Exception("La cantidad de ejemplares debe ser mayor a 0");
        }
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = libroRepositorio.ListarLibros();
        return libros;
    }

    @Transactional
    public Libro listarLibro(String id) {
        return libroRepositorio.buscarPorId(id);
    }
}
