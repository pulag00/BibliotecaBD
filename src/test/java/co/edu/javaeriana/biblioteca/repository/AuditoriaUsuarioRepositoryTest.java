package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.AuditoriaUsuario;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
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
        "spring.sql.init.mode=never",
        "logging.level.org.hibernate.tool.schema=debug",
        "logging.level.org.hibernate.SQL=debug"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EntityScan("co.edu.javaeriana.biblioteca.model")
@EnableJpaRepositories("co.edu.javaeriana.biblioteca.repository")
class AuditoriaUsuarioRepositoryTest {

    @Autowired private AuditoriaUsuarioRepository auditoriaUsuarioRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TestEntityManager em;

    private Usuario usuarioAdmin;
    private Usuario usuarioAfectado;

    @BeforeEach
    void setUp() {
        // Crear estado y tipo de usuario
        EstadoUsuarioCatalogo estado = new EstadoUsuarioCatalogo();
        estado.setNombre("Activo");
        estado = em.persistFlushFind(estado);

        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Admin");
        tipo = em.persistFlushFind(tipo);

        // Crear usuario administrador
        usuarioAdmin = new Usuario();
        usuarioAdmin.setUsername("adminUser");
        usuarioAdmin.setNombre("Administrador");
        usuarioAdmin.setContrasena("admin123");
        usuarioAdmin.setEmail("admin@biblioteca.edu.co");
        usuarioAdmin.setIntentosFallidos(0);
        usuarioAdmin.setRequiereCambioPass(false);
        usuarioAdmin.setFechaRegistro(LocalDateTime.now());
        usuarioAdmin.setEstadoUsuario(estado);
        usuarioAdmin.setTipoUsuario(tipo);
        usuarioAdmin = em.persistFlushFind(usuarioAdmin);

        // Crear usuario afectado
        usuarioAfectado = new Usuario();
        usuarioAfectado.setUsername("user1");
        usuarioAfectado.setNombre("Usuario Uno");
        usuarioAfectado.setContrasena("user123");
        usuarioAfectado.setEmail("user1@biblioteca.edu.co");
        usuarioAfectado.setIntentosFallidos(0);
        usuarioAfectado.setRequiereCambioPass(false);
        usuarioAfectado.setFechaRegistro(LocalDateTime.now());
        usuarioAfectado.setEstadoUsuario(estado);
        usuarioAfectado.setTipoUsuario(tipo);
        usuarioAfectado = em.persistFlushFind(usuarioAfectado);
    }

    @Test
    @DisplayName("Debería guardar una auditoría correctamente")
    void testGuardarAuditoria() {
        AuditoriaUsuario auditoria = new AuditoriaUsuario();
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setUsuarioAfectado(usuarioAfectado);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setTipoAccion("CREACIÓN");
        auditoria.setDescripcion("Creación de usuario nuevo");

        AuditoriaUsuario guardada = auditoriaUsuarioRepository.saveAndFlush(auditoria);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getIdAuditoriaUsuario()).isNotNull();
        assertThat(guardada.getTipoAccion()).isEqualTo("CREACIÓN");
        assertThat(guardada.getUsuarioAdmin().getUsername()).isEqualTo("adminUser");
        assertThat(guardada.getUsuarioAfectado().getUsername()).isEqualTo("user1");
    }

    @Test
    @DisplayName("Debería encontrar una auditoría por ID")
    void testBuscarPorId() {
        AuditoriaUsuario auditoria = new AuditoriaUsuario();
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setUsuarioAfectado(usuarioAfectado);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setTipoAccion("MODIFICACIÓN");
        auditoria.setDescripcion("Actualización de datos del usuario");
        auditoria = auditoriaUsuarioRepository.saveAndFlush(auditoria);

        Optional<AuditoriaUsuario> encontrada = auditoriaUsuarioRepository.findById(auditoria.getIdAuditoriaUsuario());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getTipoAccion()).isEqualTo("MODIFICACIÓN");
    }

    @Test
    @DisplayName("Debería listar todas las auditorías")
    void testListarAuditorias() {
        AuditoriaUsuario a1 = new AuditoriaUsuario();
        a1.setUsuarioAdmin(usuarioAdmin);
        a1.setUsuarioAfectado(usuarioAfectado);
        a1.setFechaEvento(LocalDateTime.now());
        a1.setTipoAccion("CREACIÓN");
        a1.setDescripcion("Creación de usuario");
        auditoriaUsuarioRepository.save(a1);

        AuditoriaUsuario a2 = new AuditoriaUsuario();
        a2.setUsuarioAdmin(usuarioAdmin);
        a2.setUsuarioAfectado(usuarioAfectado);
        a2.setFechaEvento(LocalDateTime.now());
        a2.setTipoAccion("ELIMINACIÓN");
        a2.setDescripcion("Eliminación de cuenta");
        auditoriaUsuarioRepository.save(a2);

        em.flush();

        List<AuditoriaUsuario> auditorias = auditoriaUsuarioRepository.findAll();
        assertThat(auditorias).hasSize(2);
    }

    @Test
    @DisplayName("Debería eliminar una auditoría correctamente")
    void testEliminarAuditoria() {
        AuditoriaUsuario auditoria = new AuditoriaUsuario();
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setUsuarioAfectado(usuarioAfectado);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setTipoAccion("ELIMINACIÓN");
        auditoria.setDescripcion("Eliminación de cuenta");
        auditoria = auditoriaUsuarioRepository.saveAndFlush(auditoria);

        auditoriaUsuarioRepository.delete(auditoria);
        em.flush();

        Optional<AuditoriaUsuario> eliminada = auditoriaUsuarioRepository.findById(auditoria.getIdAuditoriaUsuario());
        assertThat(eliminada).isEmpty();
    }

    @Test
    @DisplayName("Debería actualizar una auditoría correctamente")
    void testActualizarAuditoria() {
        AuditoriaUsuario auditoria = new AuditoriaUsuario();
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setUsuarioAfectado(usuarioAfectado);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setTipoAccion("MODIFICACIÓN");
        auditoria.setDescripcion("Versión inicial");
        auditoria = auditoriaUsuarioRepository.saveAndFlush(auditoria);

        auditoria.setDescripcion("Versión corregida y actualizada");
        auditoria = auditoriaUsuarioRepository.saveAndFlush(auditoria);

        AuditoriaUsuario actualizada = auditoriaUsuarioRepository.findById(auditoria.getIdAuditoriaUsuario()).orElseThrow();
        assertThat(actualizada.getDescripcion()).isEqualTo("Versión corregida y actualizada");
    }
}
