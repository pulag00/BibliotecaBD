package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PrestamoRepositoryTest {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstadoPrestamoCatalogoRepository estadoPrestamoCatalogoRepository;

    private Usuario usuario;
    private Libro libro;
    private EstadoPrestamoCatalogo estadoPrestamo;

    @BeforeEach
    void setUp() {
        // Crear estado de préstamo
        estadoPrestamo = new EstadoPrestamoCatalogo();
        estadoPrestamo.setNombre("Activo");
        estadoPrestamoCatalogoRepository.save(estadoPrestamo);

        // Crear categoría y libro
        Categoria categoria = new Categoria();
        categoria.setNombre("Ciencia Ficción");
        categoriaRepository.save(categoria);

        libro = new Libro();
        libro.setIsbn("9780001112223");
        libro.setTitulo("Fundación");
        libro.setAutor("Isaac Asimov");
        libro.setCategoria(categoria);
        libro.setEditorial("Minotauro");
        libro.setAnoPublicacion("1951");
        libro.setDescripcion("Clásico de la ciencia ficción.");
        libro.setCantidadTotal(5);
        libro.setCantidadDisponible(5);
        libroRepository.save(libro);

        // Crear usuario
        usuario = new Usuario();
        usuario.setUsername("carlos.mendoza");
        usuario.setNombre("Carlos Mendoza");
        usuario.setContrasena("Pwd_carlos!");
        usuario.setEmail("carlos.mendoza@universidad.edu.co");
        usuario.setIntentosFallidos(0);
        usuario.setRequiereCambioPass(false);
        usuario.setFechaRegistro(LocalDateTime.now());
        // Se deben setear las relaciones con TipoUsuarioCatalogo y EstadoUsuarioCatalogo,
        // puedes omitirlas si están con valores default o usas @Nullable en tu entidad
        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Debería guardar un préstamo correctamente")
    void testGuardarPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setEstadoPrestamo(estadoPrestamo);
        prestamo.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(7));

        Prestamo guardado = prestamoRepository.save(prestamo);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdPrestamo()).isNotNull();
        assertThat(guardado.getUsuario().getUsername()).isEqualTo("carlos.mendoza");
        assertThat(guardado.getLibro().getTitulo()).isEqualTo("Fundación");
    }

    @Test
    @DisplayName("Debería encontrar un préstamo por su ID")
    void testBuscarPorId() {
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setEstadoPrestamo(estadoPrestamo);
        prestamo.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(5));
        prestamoRepository.save(prestamo);

        Optional<Prestamo> encontrado = prestamoRepository.findById(prestamo.getIdPrestamo());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getLibro().getTitulo()).isEqualTo("Fundación");
    }

    @Test
    @DisplayName("Debería listar todos los préstamos")
    void testListarPrestamos() {
        Prestamo prestamo1 = new Prestamo();
        prestamo1.setUsuario(usuario);
        prestamo1.setLibro(libro);
        prestamo1.setEstadoPrestamo(estadoPrestamo);
        prestamo1.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(3));
        prestamoRepository.save(prestamo1);

        Prestamo prestamo2 = new Prestamo();
        prestamo2.setUsuario(usuario);
        prestamo2.setLibro(libro);
        prestamo2.setEstadoPrestamo(estadoPrestamo);
        prestamo2.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(10));
        prestamoRepository.save(prestamo2);

        List<Prestamo> prestamos = prestamoRepository.findAll();

        assertThat(prestamos).hasSize(2);
    }

    @Test
    @DisplayName("Debería eliminar un préstamo correctamente")
    void testEliminarPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setEstadoPrestamo(estadoPrestamo);
        prestamo.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(7));
        prestamoRepository.save(prestamo);

        prestamoRepository.delete(prestamo);

        Optional<Prestamo> eliminado = prestamoRepository.findById(prestamo.getIdPrestamo());
        assertThat(eliminado).isEmpty();
    }
}
