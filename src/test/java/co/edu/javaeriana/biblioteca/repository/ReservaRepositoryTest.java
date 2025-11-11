package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservaRepositoryTest {

    @Autowired private ReservaRepository reservaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TipoUsuarioCatalogoRepository tipoUsuarioRepository;
    @Autowired private EstadoUsuarioCatalogoRepository estadoUsuarioRepository;
    @Autowired private LibroRepository libroRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private EstadoReservaCatalogoRepository estadoReservaRepository;

    private Usuario crearUsuario() {
        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Profesor");
        tipoUsuarioRepository.save(tipo);

        EstadoUsuarioCatalogo estadoUsuario = new EstadoUsuarioCatalogo();
        estadoUsuario.setNombre("Activo");
        estadoUsuarioRepository.save(estadoUsuario);

        Usuario usuario = new Usuario();
        usuario.setUsername("carlos.mendoza");
        usuario.setNombre("Carlos Mendoza");
        usuario.setContrasena("Pwd_carlos!");
        usuario.setEmail("carlos.mendoza@universidad.edu.co");
        usuario.setIntentosFallidos(0);
        usuario.setRequiereCambioPass(false);
        usuario.setEstadoUsuario(estadoUsuario);
        usuario.setTipoUsuario(tipo);
        usuario.setFechaRegistro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    private Libro crearLibro() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Literatura");
        categoria.setDescripcion("Obras literarias y narrativas");
        categoriaRepository.save(categoria);

        Libro libro = new Libro();
        libro.setIsbn("9780001112223");
        libro.setTitulo("El Principito");
        libro.setAutor("Antoine de Saint-Exupéry");
        libro.setCategoria(categoria);
        libro.setEditorial("Reynal & Hitchcock");
        libro.setAnoPublicacion("1943");
        libro.setDescripcion("Una historia poética sobre la infancia y el amor.");
        libro.setCantidadTotal(5);
        libro.setCantidadDisponible(5);
        return libroRepository.save(libro);
    }

    private EstadoReservaCatalogo crearEstado(String nombre) {
        EstadoReservaCatalogo estado = new EstadoReservaCatalogo();
        estado.setNombre(nombre);
        return estadoReservaRepository.save(estado);
    }

    private Reserva crearReservaBase() {
        Usuario usuario = crearUsuario();
        Libro libro = crearLibro();
        EstadoReservaCatalogo estado = crearEstado("Activa");

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setLibro(libro);
        reserva.setEstadoReserva(estado);
        reserva.setFechaReserva(LocalDateTime.now());
        return reservaRepository.save(reserva);
    }

    // --- TESTS ---

    @Test
    @Order(1)
    void testCrearReserva() {
        Reserva reserva = crearReservaBase();

        Assertions.assertNotNull(reserva.getIdReserva());
        Assertions.assertEquals("carlos.mendoza", reserva.getUsuario().getUsername());
        Assertions.assertEquals("El Principito", reserva.getLibro().getTitulo());
        Assertions.assertEquals("Activa", reserva.getEstadoReserva().getNombre());
    }

    @Test
    @Order(2)
    void testBuscarReserva() {
        Reserva reserva = crearReservaBase();

        Optional<Reserva> reservaOpt = reservaRepository.findById(reserva.getIdReserva());
        Assertions.assertTrue(reservaOpt.isPresent());
        Assertions.assertEquals("carlos.mendoza", reservaOpt.get().getUsuario().getUsername());
    }

    @Test
    @Order(3)
    void testActualizarReserva() {
        Reserva reserva = crearReservaBase();

        EstadoReservaCatalogo nuevoEstado = crearEstado("Completada");
        reserva.setEstadoReserva(nuevoEstado);
        Reserva actualizada = reservaRepository.save(reserva);

        Assertions.assertEquals("Completada", actualizada.getEstadoReserva().getNombre());
    }

    @Test
    @Order(4)
    void testEliminarReserva() {
        Reserva reserva = crearReservaBase();
        reservaRepository.delete(reserva);
        Assertions.assertFalse(reservaRepository.findById(reserva.getIdReserva()).isPresent());
    }

    @Test
    @Order(5)
    void testFKViolation() {
        EstadoReservaCatalogo estado = crearEstado("Activa");
        Reserva reservaInvalida = new Reserva();
        reservaInvalida.setUsuario(null);
        reservaInvalida.setLibro(null);
        reservaInvalida.setEstadoReserva(estado);
        reservaInvalida.setFechaReserva(LocalDateTime.now());

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            reservaRepository.saveAndFlush(reservaInvalida);
        });
    }
}
