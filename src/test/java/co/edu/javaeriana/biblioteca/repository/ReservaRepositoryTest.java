package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioCatalogoRepository tipoUsuarioRepository;

    @Autowired
    private EstadoUsuarioCatalogoRepository estadoUsuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private EstadoReservaCatalogoRepository estadoReservaRepository;

    private Usuario usuario;
    private Libro libro;
    private EstadoReservaCatalogo estadoReserva;

    @BeforeEach
    void setUp() {
        // Crear tipo de usuario
        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Estudiante");
        tipoUsuarioRepository.save(tipo);

        // Crear estado de usuario
        EstadoUsuarioCatalogo estadoUsuario = new EstadoUsuarioCatalogo();
        estadoUsuario.setNombre("Activo");
        estadoUsuarioRepository.save(estadoUsuario);

        // Crear usuario
        usuario = new Usuario();
        usuario.setUsername("juan.prueba");
        usuario.setNombre("Juan Prueba");
        usuario.setContrasena("Pwd123!");
        usuario.setEmail("juan.prueba@test.com");
        usuario.setTipoUsuario(tipo);
        usuario.setEstadoUsuario(estadoUsuario);
        usuario.setIntentosFallidos(0);
        usuario.setRequiereCambioPass(false);
        usuarioRepository.save(usuario);

        // Crear libro
        libro = new Libro();
        libro.setTitulo("El Principito");
        libro.setAutor("Antoine de Saint-Exupéry");
        libro.setEditorial("Reynal & Hitchcock");
        libro.setAnoPublicacion(String.valueOf(1943));
        libroRepository.save(libro);

        // Crear estado de reserva
        estadoReserva = new EstadoReservaCatalogo();
        estadoReserva.setNombre("Activa");
        estadoReservaRepository.save(estadoReserva);
    }

    @Test
    @Order(1)
    @Rollback(false)
    void testCrearReserva() {
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setLibro(libro);
        reserva.setEstadoReserva(estadoReserva);
        reserva.setFechaReserva(LocalDateTime.now());

        Reserva guardada = reservaRepository.save(reserva);

        Assertions.assertNotNull(guardada.getIdReserva());
        Assertions.assertEquals(usuario.getUsername(), guardada.getUsuario().getUsername());
    }

    @Test
    @Order(2)
    void testBuscarReserva() {
        List<Reserva> reservas = reservaRepository.findAll();
        Assertions.assertFalse(reservas.isEmpty());

        Optional<Reserva> reservaOpt = reservaRepository.findById(reservas.get(0).getIdReserva());
        Assertions.assertTrue(reservaOpt.isPresent());
    }

    @Test
    @Order(3)
    @Rollback(false)
    void testActualizarReserva() {
        Reserva reserva = reservaRepository.findAll().get(0);
        EstadoReservaCatalogo nuevoEstado = new EstadoReservaCatalogo();
        nuevoEstado.setNombre("Completada");
        estadoReservaRepository.save(nuevoEstado);

        reserva.setEstadoReserva(nuevoEstado);
        Reserva actualizada = reservaRepository.save(reserva);

        Assertions.assertEquals("Completada", actualizada.getEstadoReserva().getNombre());
    }

    @Test
    @Order(4)
    @Rollback(false)
    void testEliminarReserva() {
        Reserva reserva = reservaRepository.findAll().get(0);
        reservaRepository.deleteById(reserva.getIdReserva());
        Assertions.assertFalse(reservaRepository.findById(reserva.getIdReserva()).isPresent());
    }

    @Test
    @Order(5)
    void testFKViolation() {
        Reserva reservaInvalida = new Reserva();
        reservaInvalida.setUsuario(null);
        reservaInvalida.setLibro(null);
        reservaInvalida.setEstadoReserva(estadoReserva);
        reservaInvalida.setFechaReserva(LocalDateTime.now());

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            reservaRepository.saveAndFlush(reservaInvalida);
        });
    }
}
