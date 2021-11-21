/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibreriaWeb.Servicios;

import LibreriaWeb.Entidades.Cliente;
import LibreriaWeb.Entidades.Foto;
import LibreriaWeb.Errores.ErrorServicio;
import LibreriaWeb.Repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author anico
 */
@Service
public class ClienteServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Transactional
    public void crearCliente(MultipartFile archivo, String nombre, String apellido, long documento, String email, String telefono, String contrasenia1, String contrasenia2) throws ErrorServicio, Exception {

        validar(nombre, apellido, email, telefono, contrasenia1, contrasenia2);

        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        String encriptada = new BCryptPasswordEncoder().encode(contrasenia1);
        cliente.setContrasenia1(encriptada);
        cliente.setEmail(email);
        cliente.setAlta(true);

        Foto foto = fotoServicio.guardar(archivo);
        cliente.setFoto(foto);

        clienteRepositorio.save(cliente);

        notificacionServicio.enviar("Usted se a creado una cuenta en el portal de libros, podra disfrutar de todos ellos en un solo lugar", "Bienvenido a History Book", cliente.getEmail());
    }

    @Transactional
    public void modificarCliente(MultipartFile archivo, String idCliente, String nombre, String apellido, String email, String telefono, String contrasenia1, String contrasenia2) throws ErrorServicio {

        validar(nombre, apellido, email, telefono, contrasenia1, contrasenia2);

        Optional<Cliente> respuesta = clienteRepositorio.findById(idCliente);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            String encriptada = new BCryptPasswordEncoder().encode(contrasenia1);
            cliente.setContrasenia1(encriptada);

            String idFoto = null;
            if (cliente.getFoto() != null) {
                idFoto = cliente.getFoto().getIdFoto();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            cliente.setFoto(foto);

            clienteRepositorio.save(cliente);

            notificacionServicio.enviar("Usted a modificado los datos de su cuenta con exito", "Modificacion cuenta", cliente.getEmail());
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void deshabilitar(String idCliente) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(idCliente);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setAlta(false);

            clienteRepositorio.save(cliente);

            notificacionServicio.enviar("Su cuenta a sido dada de baja, lamentamos no poder cumplir con todas sus expectativas. Estamos en constante crecimiento para tratar de satisfacer las doferentes necesidades de todos nuestros usuarios", "BAJA CUENTA", cliente.getEmail());
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void habilitar(String idCliente) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(idCliente);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setAlta(true);

            clienteRepositorio.save(cliente);

            notificacionServicio.enviar("Su cuenta a sido dada de alta, nos alegra tenerlo con nosotros nuevamente", "ALTA CUENTA", cliente.getEmail());
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void validar(String nombre, String apellido, String email, String telefono, String contrasenia1, String contrasenia2) throws ErrorServicio {

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
        if (!contrasenia2.equals(contrasenia1)) {
            throw new ErrorServicio("Las contraseñas no coinciden");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.buscarporEmail(email);

        if (cliente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
             HttpSession session = attr.getRequest().getSession(true);
             session.setAttribute("usuariosession", cliente);

            User user = new User(cliente.getEmail(), cliente.getContrasenia1(), permisos);
            return user;
        } else {
            return null;
        }
    }
}
