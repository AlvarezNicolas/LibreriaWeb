/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author anico
 */
@Data
@Entity
public class Libro implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    protected Long isbn;
    protected String titulo;
    protected Integer anio;
    protected Integer ejemplares;
    protected Integer ejemplaresPrestados;
    protected Integer ejemplaresRestantes;
    protected Boolean alta;
        
    @OneToOne
        (cascade = CascadeType.PERSIST)
    protected Autor autor;

    @OneToOne
        (cascade = CascadeType.PERSIST)
    protected Editorial editorial;

}
