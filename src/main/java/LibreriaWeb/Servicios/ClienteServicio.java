/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Cliente;
import LibreriaWeb.Errores.ErrorServicio;
import LibreriaWeb.Repositorios.ClienteRepositorio;
import java.util.Date;
import java.util.Optional;
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
    public void crearCliente(String nombre, String apellido, long documento, String email, String telefono, String contrasenia1) throws ErrorServicio {

        validar(nombre, apellido, email, telefono, contrasenia1);

        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setContrasenia1(contrasenia1);
        cliente.setEmail(email);
        cliente.setAlta(new Date());

        clienterepositorio.save(cliente);
    }

    public void modificarCliente(String id, String nombre, String apellido, String email, String telefono, String contrasenia1) throws ErrorServicio {

        validar(nombre, apellido, email, telefono, contrasenia1);

        Optional<Cliente> respuesta = clienterepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setContrasenia1(contrasenia1);

            clienterepositorio.save(cliente);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Cliente> respuesta = clienterepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setBaja(new Date());

            clienterepositorio.save(cliente);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void habilitar(String id) throws ErrorServicio {
        Optional<Cliente> respuesta = clienterepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setBaja(null);

            clienterepositorio.save(cliente);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void validar(String nombre, String apellido, String email, String telefono, String contrasenia1) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacio");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErrorServicio("El numero de telefono no puede estar vacio");
        }
        if (contrasenia1 == null || contrasenia1.isEmpty() || contrasenia1.length() <= 6) {
            throw new ErrorServicio("La contraseña no puede estar vacia o es menor a 6 caracteres");
        }
    }
}
