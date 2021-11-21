/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Repositorios;

import LibreriaWeb.Entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anico
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT c FROM Libro c where alta=1")
    public List<Libro> ListarLibros();

    @Query("SELECT c FROM Libro c WHERE c.idLibro = :idLibro")
    public Libro buscarPorId(@Param("idLibro") String idLibro);
    
}
