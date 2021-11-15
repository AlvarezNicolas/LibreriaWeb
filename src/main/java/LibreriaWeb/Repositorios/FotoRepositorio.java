/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Repositorios;

import LibreriaWeb.Entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anico
 */
@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>{
    
}
