package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.*;
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
class AuditoriaLibroRepositoryTest {

    @Autowired private AuditoriaLibroRepository auditoriaLibroRepository;
    @Autowired private LibroRepository libroRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private EstadoUsuarioCatalogoRepository EstadoUsuarioRepository;
    @Autowired private TipoUsuarioCatalogoRepository TipoUsuarioRepository;
    @Autowired private TestEntityManager em;

    private Libro libro;
    private Usuario usuarioAdmin;

    @BeforeEach
    void setUp() {
        // Crear categoría
        Categoria categoria = new Categoria();
        categoria.setNombre("Historia");
        categoria.setDescripcion("Categoría de prueba");
        categoria = categoriaRepository.save(categoria);

        // Crear libro
        libro = new Libro();
        libro.setIsbn("9780307474278");
        libro.setTitulo("Sapiens: De animales a dioses");
        libro.setAutor("Yuval Noah Harari");
        libro.setCategoria(categoria);
        libro.setEditorial("Debate");
        libro.setAnoPublicacion("2014");
        libro.setDescripcion("Historia de la humanidad desde una perspectiva moderna.");
        libro.setCantidadTotal(10);
        libro.setCantidadDisponible(10);
        libro = libroRepository.save(libro);

        EstadoUsuarioCatalogo estado = new EstadoUsuarioCatalogo();
        estado.setNombre("Activo"); // o como se llame el campo
        estado = em.persistFlushFind(estado); // guarda y obtiene el ID

        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Admin"); // o como se llame el campo
        tipo = em.persistFlushFind(tipo); // guarda y obtiene el ID


        // Crear usuario administrador
        usuarioAdmin = new Usuario();
        usuarioAdmin.setUsername("admin01");
        usuarioAdmin.setNombre("Administrador General");
        usuarioAdmin.setContrasena("Admin_123!");
        usuarioAdmin.setEmail("admin@biblioteca.edu.co");
        usuarioAdmin.setIntentosFallidos(0);
        usuarioAdmin.setRequiereCambioPass(false);
        usuarioAdmin.setFechaRegistro(LocalDateTime.now());
        usuarioAdmin.setEstadoUsuario(estado);
        usuarioAdmin.setTipoUsuario(tipo);
        usuarioAdmin = usuarioRepository.save(usuarioAdmin);

        em.flush();
    }

    @Test
    @DisplayName("Debería guardar una auditoría de libro correctamente")
    void testGuardarAuditoriaLibro() {
        AuditoriaLibro auditoria = new AuditoriaLibro();
        auditoria.setLibro(libro);
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setDescripcionCambio("Se actualizó la cantidad de ejemplares disponibles.");

        AuditoriaLibro guardado = auditoriaLibroRepository.saveAndFlush(auditoria);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getLibro().getTitulo()).isEqualTo("Sapiens: De animales a dioses");
        assertThat(guardado.getUsuarioAdmin().getUsername()).isEqualTo("admin01");
    }

    @Test
    @DisplayName("Debería encontrar una auditoría de libro por su ID")
    void testBuscarPorId() {
        AuditoriaLibro auditoria = new AuditoriaLibro();
        auditoria.setLibro(libro);
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setDescripcionCambio("Se modificó el autor del libro.");
        auditoria = auditoriaLibroRepository.saveAndFlush(auditoria);

        Optional<AuditoriaLibro> encontrado = auditoriaLibroRepository.findById(auditoria.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getDescripcionCambio()).contains("modificó el autor");
    }

    @Test
    @DisplayName("Debería listar todas las auditorías de libros")
    void testListarAuditorias() {
        AuditoriaLibro a1 = new AuditoriaLibro();
        a1.setLibro(libro);
        a1.setUsuarioAdmin(usuarioAdmin);
        a1.setFechaEvento(LocalDateTime.now());
        a1.setDescripcionCambio("Cambio 1");
        auditoriaLibroRepository.save(a1);

        AuditoriaLibro a2 = new AuditoriaLibro();
        a2.setLibro(libro);
        a2.setUsuarioAdmin(usuarioAdmin);
        a2.setFechaEvento(LocalDateTime.now());
        a2.setDescripcionCambio("Cambio 2");
        auditoriaLibroRepository.save(a2);

        em.flush();

        List<AuditoriaLibro> auditorias = auditoriaLibroRepository.findAll();
        assertThat(auditorias).hasSize(2);
    }

    @Test
    @DisplayName("Debería eliminar una auditoría correctamente")
    void testEliminarAuditoria() {
        AuditoriaLibro auditoria = new AuditoriaLibro();
        auditoria.setLibro(libro);
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setDescripcionCambio("Se eliminó el libro de la base de datos.");
        auditoria = auditoriaLibroRepository.saveAndFlush(auditoria);

        auditoriaLibroRepository.delete(auditoria);
        em.flush();

        Optional<AuditoriaLibro> eliminado = auditoriaLibroRepository.findById(auditoria.getId());
        assertThat(eliminado).isEmpty();
    }

    @Test
    @DisplayName("Debería actualizar una auditoría correctamente")
    void testActualizarAuditoria() {
        AuditoriaLibro auditoria = new AuditoriaLibro();
        auditoria.setLibro(libro);
        auditoria.setUsuarioAdmin(usuarioAdmin);
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setDescripcionCambio("Versión inicial");
        auditoria = auditoriaLibroRepository.saveAndFlush(auditoria);

        auditoria.setDescripcionCambio("Versión corregida y actualizada");
        auditoria = auditoriaLibroRepository.saveAndFlush(auditoria);

        AuditoriaLibro actualizada = auditoriaLibroRepository.findById(auditoria.getId()).orElseThrow();
        assertThat(actualizada.getDescripcionCambio()).isEqualTo("Versión corregida y actualizada");
    }
}
