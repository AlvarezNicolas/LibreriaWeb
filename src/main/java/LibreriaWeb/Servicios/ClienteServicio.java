/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Cliente;
import LibreriaWeb.Entidades.Foto;
import LibreriaWeb.Enumeraciones.Sexo;
import LibreriaWeb.Errores.ErrorServicio;
import LibreriaWeb.Repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author anico
 */
@Service
public class ClienteServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienterepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearCliente(MultipartFile archivo, String nombre, String apellido, long documento, String email, String telefono, String contrasenia1, Sexo sexo) throws ErrorServicio {

        validar(nombre, apellido, email, telefono, contrasenia1, sexo);

        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        String encriptada = new BCryptPasswordEncoder().encode(contrasenia1);
        cliente.setContrasenia1(encriptada);
        cliente.setEmail(email);
        cliente.setSexo(sexo);
        cliente.setAlta(new Date());

        Foto foto = fotoServicio.guardar(archivo);
        cliente.setFoto(foto);

        clienterepositorio.save(cliente);
    }

    public void modificarCliente(MultipartFile archivo, String id, String nombre, String apellido, String email, String telefono, String contrasenia1, Sexo sexo) throws ErrorServicio {

        validar(nombre, apellido, email, telefono, contrasenia1, sexo);

        Optional<Cliente> respuesta = clienterepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            String encriptada = new BCryptPasswordEncoder().encode(contrasenia1);
            cliente.setContrasenia1(encriptada);
            cliente.setSexo(sexo);

            String idFoto = null;
            if (cliente.getFoto() != null) {
                idFoto = cliente.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            cliente.setFoto(foto);

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

    public void validar(String nombre, String apellido, String email, String telefono, String contrasenia1, Sexo sexo) throws ErrorServicio {

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
        if (sexo == null) {
            throw new ErrorServicio("Debe seleccionar una opcion en genero");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienterepositorio.buscarPorEmail(email);

        if (cliente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
            permisos.add(p1);
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_CLIENTES");
            permisos.add(p2);
            User user = new User(cliente.getEmail(), cliente.getContrasenia1(), permisos);

            return user;
        } else {
            return null;
        }
    }
}
