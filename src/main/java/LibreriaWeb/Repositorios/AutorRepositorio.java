/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Repositorios;

import LibreriaWeb.Entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anico
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
//    
//    @Query("SELECT c FROM Libro WHERE c.nomre = :nombre")
//    public Autor buscarLibroPorAutor(@Param("nombre") String nombre);
    
     @Query("SELECT c FROM Autor c WHERE alta=1")
    public List<Autor> ListarAutores();
    
}
