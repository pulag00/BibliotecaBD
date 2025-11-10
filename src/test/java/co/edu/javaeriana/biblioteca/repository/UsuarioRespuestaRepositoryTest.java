package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.PreguntaSeguridad;
import co.edu.javaeriana.biblioteca.model.Usuario;
import co.edu.javaeriana.biblioteca.model.UsuarioRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRespuestaRepositoryTest {

    @Autowired
    private UsuarioRespuestaRepository usuarioRespuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PreguntaSeguridadRepository preguntaSeguridadRepository;

    private Usuario usuario;
    private PreguntaSeguridad pregunta;

    @BeforeEach
    void setUp() {
        // Crear un usuario de prueba
        usuario = new Usuario();
        usuario.setUsername("juan.perez");
        usuario.setNombre("Juan Perez");
        usuario.setContrasena("Password123!");
        usuario.setEmail("juan.perez@correo.com");
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
