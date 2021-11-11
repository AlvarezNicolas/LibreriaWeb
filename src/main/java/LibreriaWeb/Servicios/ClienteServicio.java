/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Cliente;
import LibreriaWeb.Repositorios.ClienteRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anico
 */
@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienterepositorio;
    
    @Transactional
    public void CrearCliente(String nombre, String apellido, long documento, String telefono, String contrasenia1, String contrasenia2) throws Exception {
        if (documento <= 10000000) {
            throw new Exception("Debe indicar un numero de documento valido");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("El apellido no puede estar vacio");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new Exception("El numero de telefono no puede estar vacio");
        }
        if (contrasenia1.isEmpty()) {
            throw new Exception("La contraseña no puede estar vacia o es menor a 6 caracteres");
        }
        if (!contrasenia1.equals(contrasenia2)) {
            throw new Exception("Las contraseñas no coinciden");
        }
        
        Cliente cliente = new Cliente();
        boolean alta = true;
        cliente.setAlta(alta == true);
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
//        cliente.setSexo(sexo);
        cliente.setContrasenia1(contrasenia1);
        
        clienterepositorio.save(cliente);
    }
}
