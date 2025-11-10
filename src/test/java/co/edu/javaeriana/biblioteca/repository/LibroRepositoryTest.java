package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.Categoria;
import co.edu.javaeriana.biblioteca.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LibroRepositoryTest {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setNombre("Historia");
        categoriaRepository.save(categoria);
    }

    @Test
    @DisplayName("Debería guardar un libro correctamente")
    void testGuardarLibro() {
        Libro libro = new Libro();
        libro.setIsbn("9781234567897");
        libro.setTitulo("Historia de Colombia");
        libro.setAutor("Juan Pérez");
        libro.setCategoria(categoria);
        libro.setEditorial("Editorial Andina");
        libro.setAnoPublicacion("2020");
        libro.setDescripcion("Un repaso completo por la historia del país.");
        libro.setPortadaUrl("http://example.com/portada.jpg");
        libro.setCantidadTotal(10);
        libro.setCantidadDisponible(8);

        Libro guardado = libroRepository.save(libro);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdLibro()).isNotNull();
        assertThat(guardado.getTitulo()).isEqualTo("Historia de Colombia");
        assertThat(guardado.getCategoria().getNombre()).isEqualTo("Historia");
    }

    @Test
    @DisplayName("Debería encontrar un libro por su ID")
    void testBuscarPorId() {
        Libro libro = new Libro();
        libro.setIsbn("9789876543210");
        libro.setTitulo("Historia Universal");
        libro.setAutor("María López");
        libro.setCategoria(categoria);
        libro.setEditorial("Planeta");
        libro.setAnoPublicacion("2019");
        libro.setDescripcion("Una mirada global a los eventos históricos.");
        libro.setPortadaUrl("http://example.com/universal.jpg");
        libro.setCantidadTotal(5);
        libro.setCantidadDisponible(5);
        libroRepository.save(libro);

        Optional<Libro> encontrado = libroRepository.findById(libro.getIdLibro());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTitulo()).isEqualTo("Historia Universal");
    }

    @Test
    @DisplayName("Debería listar todos los libros")
    void testListarLibros() {
        Libro libro1 = new Libro();
        libro1.setIsbn("9781111111111");
        libro1.setTitulo("Historia Antigua");
        libro1.setAutor("Carlos Torres");
        libro1.setCategoria(categoria);
        libro1.setCantidadTotal(4);
        libro1.setCantidadDisponible(4);
        libroRepository.save(libro1);

        Libro libro2 = new Libro();
        libro2.setIsbn("9782222222222");
        libro2.setTitulo("Historia Moderna");
        libro2.setAutor("Laura Gómez");
        libro2.setCategoria(categoria);
        libro2.setCantidadTotal(6);
        libro2.setCantidadDisponible(6);
        libroRepository.save(libro2);

        List<Libro> libros = libroRepository.findAll();
        assertThat(libros).hasSize(2);
    }

    @Test
    @DisplayName("Debería eliminar un libro correctamente")
    void testEliminarLibro() {
        Libro libro = new Libro();
        libro.setIsbn("9783333333333");
        libro.setTitulo("Historia Contemporánea");
        libro.setAutor("Andrés Rojas");
        libro.setCategoria(categoria);
        libro.setCantidadTotal(3);
        libro.setCantidadDisponible(3);
        libroRepository.save(libro);

        libroRepository.delete(libro);

        Optional<Libro> eliminado = libroRepository.findById(libro.getIdLibro());
        assertThat(eliminado).isEmpty();
    }
}
