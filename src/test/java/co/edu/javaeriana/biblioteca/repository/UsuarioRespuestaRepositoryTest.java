package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.show-sql=true",
        "spring.flyway.enabled=false",
        "spring.liquibase.enabled=false",
        "spring.sql.init.mode=never"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EntityScan("co.edu.javaeriana.biblioteca.model")
@EnableJpaRepositories("co.edu.javaeriana.biblioteca.repository")
class UsuarioRespuestaRepositoryTest {

    @Autowired private UsuarioRespuestaRepository usuarioRespuestaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PreguntaSeguridadRepository preguntaSeguridadRepository;
    @Autowired private TipoUsuarioCatalogoRepository tipoUsuarioCatalogoRepository;
    @Autowired private EstadoUsuarioCatalogoRepository estadoUsuarioCatalogoRepository;

    private Usuario usuario;
    private PreguntaSeguridad pregunta;

    @BeforeEach
    void setUp() {
        // Crear tipo de usuario
        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Estudiante");
        tipo = tipoUsuarioCatalogoRepository.save(tipo);

        // Crear estado de usuario
        EstadoUsuarioCatalogo estado = new EstadoUsuarioCatalogo();
        estado.setNombre("Activo");
        estado = estadoUsuarioCatalogoRepository.save(estado);

        // Crear un usuario de prueba
        usuario = new Usuario();
        usuario.setUsername("juan.perez");
        usuario.setNombre("Juan Perez");
        usuario.setContrasena("Password123!");
        usuario.setEmail("juan.perez@correo.com");
        usuario.setTipoUsuario(tipo);
        usuario.setEstadoUsuario(estado);
        usuario.setIntentosFallidos(0);
        usuario.setRequiereCambioPass(false);
        usuario = usuarioRepository.save(usuario);

        // Crear una pregunta de seguridad
        pregunta = new PreguntaSeguridad();
        pregunta.setTextoPregunta("¿Cuál es tu color favorito?");
        pregunta = preguntaSeguridadRepository.save(pregunta);
    }

    @Test
    void testGuardarYBuscarRespuesta() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        respuesta.setUsuario(usuario);
        respuesta.setPregunta(pregunta);
        respuesta.setRespuesta("Azul");

        UsuarioRespuesta guardada = usuarioRespuestaRepository.save(respuesta);

        assertThat(guardada.getIdRespuesta()).isNotNull();

        Optional<UsuarioRespuesta> encontrada = usuarioRespuestaRepository.findById(guardada.getIdRespuesta());
        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getRespuesta()).isEqualTo("Azul");
    }

    @Test
    void testListarRespuestas() {
        UsuarioRespuesta r1 = new UsuarioRespuesta();
        r1.setUsuario(usuario);
        r1.setPregunta(pregunta);
        r1.setRespuesta("Azul");
        usuarioRespuestaRepository.save(r1);

        UsuarioRespuesta r2 = new UsuarioRespuesta();
        r2.setUsuario(usuario);
        r2.setPregunta(pregunta);
        r2.setRespuesta("Rojo");
        usuarioRespuestaRepository.save(r2);

        List<UsuarioRespuesta> respuestas = usuarioRespuestaRepository.findAll();
        assertThat(respuestas).hasSize(2);
    }

    @Test
    void testEliminarRespuesta() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        respuesta.setUsuario(usuario);
        respuesta.setPregunta(pregunta);
        respuesta.setRespuesta("Verde");
        UsuarioRespuesta guardada = usuarioRespuestaRepository.save(respuesta);

        usuarioRespuestaRepository.deleteById(guardada.getIdRespuesta());
        Optional<UsuarioRespuesta> eliminada = usuarioRespuestaRepository.findById(guardada.getIdRespuesta());

        assertThat(eliminada).isEmpty();
    }
}
