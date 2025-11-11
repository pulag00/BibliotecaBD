package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.model.TipoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class UsuarioRepositoryTest {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TipoUsuarioCatalogoRepository tipoUsuarioCatalogoRepository;
    @Autowired private EstadoUsuarioCatalogoRepository estadoUsuarioCatalogoRepository;

    private TipoUsuarioCatalogo tipoUsuario;
    private EstadoUsuarioCatalogo estadoUsuario;

    @BeforeEach
    void setUp() {
        // Crear tipo de usuario
        tipoUsuario = new TipoUsuarioCatalogo();
        tipoUsuario.setNombre("Estudiante");
        tipoUsuarioCatalogoRepository.save(tipoUsuario);

        // Crear estado de usuario
        estadoUsuario = new EstadoUsuarioCatalogo();
        estadoUsuario.setNombre("Activo");
        estadoUsuarioCatalogoRepository.save(estadoUsuario);
    }

    @Test
    @DisplayName("Guardar usuario correctamente")
    void testGuardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan.perez");
        usuario.setNombre("Juan Pérez");
        usuario.setContrasena("password123");
        usuario.setEmail("juan.perez@universidad.edu.co");
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEstadoUsuario(estadoUsuario);
        usuario.setIntentosFallidos(0);
        usuario.setRequiereCambioPass(false);

        Usuario guardado = usuarioRepository.save(usuario);

        assertThat(guardado.getIdUsuario()).isNotNull();
        assertThat(guardado.getUsername()).isEqualTo("juan.perez");
        assertThat(guardado.getTipoUsuario().getNombre()).isEqualTo("Estudiante");
    }

    @Test
    @DisplayName("Buscar usuario por ID")
    void testBuscarPorId() {
        Usuario usuario = new Usuario();
        usuario.setUsername("laura.gomez");
        usuario.setNombre("Laura Gómez");
        usuario.setContrasena("secure123");
        usuario.setEmail("laura.gomez@universidad.edu.co");
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEstadoUsuario(estadoUsuario);
        usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findById(usuario.getIdUsuario());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail()).isEqualTo("laura.gomez@universidad.edu.co");
    }

    @Test
    @DisplayName("Buscar usuario por email")
    void testBuscarPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setUsername("carlos.rios");
        usuario.setNombre("Carlos Ríos");
        usuario.setContrasena("clave321");
        usuario.setEmail("carlos.rios@universidad.edu.co");
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEstadoUsuario(estadoUsuario);
        usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findByEmail("carlos.rios@universidad.edu.co");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getUsername()).isEqualTo("carlos.rios");
    }

    @Test
    @DisplayName("Listar todos los usuarios")
    void testListarUsuarios() {
        Usuario u1 = new Usuario();
        u1.setUsername("user1");
        u1.setNombre("Usuario Uno");
        u1.setContrasena("123");
        u1.setEmail("user1@uni.edu.co");
        u1.setTipoUsuario(tipoUsuario);
        u1.setEstadoUsuario(estadoUsuario);

        Usuario u2 = new Usuario();
        u2.setUsername("user2");
        u2.setNombre("Usuario Dos");
        u2.setContrasena("456");
        u2.setEmail("user2@uni.edu.co");
        u2.setTipoUsuario(tipoUsuario);
        u2.setEstadoUsuario(estadoUsuario);

        usuarioRepository.save(u1);
        usuarioRepository.save(u2);

        List<Usuario> usuarios = usuarioRepository.findAll();
        assertThat(usuarios).hasSize(2);
    }

    @Test
    @DisplayName("Eliminar usuario por ID")
    void testEliminarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("eliminar");
        usuario.setNombre("Eliminar Test");
        usuario.setContrasena("test123");
        usuario.setEmail("eliminar@uni.edu.co");
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEstadoUsuario(estadoUsuario);
        usuarioRepository.save(usuario);

        usuarioRepository.deleteById(usuario.getIdUsuario());
        Optional<Usuario> eliminado = usuarioRepository.findById(usuario.getIdUsuario());

        assertThat(eliminado).isEmpty();
    }
}
