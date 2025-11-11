package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.Categoria;
import co.edu.javaeriana.biblioteca.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
class LibroRepositoryTest {

    @Autowired private LibroRepository libroRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private TestEntityManager em;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setNombre("Ciencia Ficción");
        categoria.setDescripcion("Libros que exploran mundos futuros o tecnológicos");
        categoria = categoriaRepository.saveAndFlush(categoria);
    }

    @Test
    @DisplayName("Debería guardar un libro correctamente")
    void testGuardarLibro() {
        Libro libro = crearLibro();
        Libro guardado = libroRepository.saveAndFlush(libro);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdLibro()).isNotNull();
        assertThat(guardado.getCategoria().getNombre()).isEqualTo("Ciencia Ficción");
        assertThat(guardado.getTitulo()).isEqualTo("Dune");
    }

    @Test
    @DisplayName("Debería encontrar un libro por su ID")
    void testBuscarPorId() {
        Libro libro = libroRepository.saveAndFlush(crearLibro());

        Optional<Libro> encontrado = libroRepository.findById(libro.getIdLibro());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getAutor()).isEqualTo("Frank Herbert");
    }

    @Test
    @DisplayName("Debería listar todos los libros")
    void testListarLibros() {
        libroRepository.saveAndFlush(crearLibro());
        libroRepository.saveAndFlush(crearLibroAlternativo());

        List<Libro> libros = libroRepository.findAll();

        assertThat(libros).hasSize(2);
    }

    @Test
    @DisplayName("Debería actualizar un libro correctamente")
    void testActualizarLibro() {
        Libro libro = libroRepository.saveAndFlush(crearLibro());

        libro.setTitulo("Dune: Edición Revisada");
        libro.setCantidadDisponible(7);
        Libro actualizado = libroRepository.saveAndFlush(libro);

        assertThat(actualizado.getTitulo()).isEqualTo("Dune: Edición Revisada");
        assertThat(actualizado.getCantidadDisponible()).isEqualTo(7);
    }

    @Test
    @DisplayName("Debería eliminar un libro correctamente")
    void testEliminarLibro() {
        Libro libro = libroRepository.saveAndFlush(crearLibro());
        Integer id = libro.getIdLibro();

        libroRepository.delete(libro);
        em.flush();

        Optional<Libro> eliminado = libroRepository.findById(id);
        assertThat(eliminado).isEmpty();
    }

    private Libro crearLibro() {
        Libro libro = new Libro();
        libro.setIsbn("9780441013593");
        libro.setTitulo("Dune");
        libro.setAutor("Frank Herbert");
        libro.setCategoria(categoria);
        libro.setEditorial("Ace Books");
        libro.setAnoPublicacion("1965");
        libro.setDescripcion("Clásico de la ciencia ficción.");
        libro.setCantidadTotal(10);
        libro.setCantidadDisponible(10);
        return libro;
    }

    private Libro crearLibroAlternativo() {
        Libro libro = new Libro();
        libro.setIsbn("9780451524935");
        libro.setTitulo("1984");
        libro.setAutor("George Orwell");
        libro.setCategoria(categoria);
        libro.setEditorial("Signet Classics");
        libro.setAnoPublicacion("1949");
        libro.setDescripcion("Distopía política y social.");
        libro.setCantidadTotal(8);
        libro.setCantidadDisponible(8);
        return libro;
    }
}
