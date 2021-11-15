package LibreriaWeb;

import LibreriaWeb.Servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibreriaWebApplication {

    @Autowired
    private ClienteServicio clienteServicio;

    public static void main(String[] args) {
        SpringApplication.run(LibreriaWebApplication.class, args);
    }

    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clienteServicio).passwordEncoder(new BCryptPasswordEncoder());
    }

}
