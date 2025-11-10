package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.show-sql=true",
        "spring.flyway.enabled=false",
        "spring.liquibase.enabled=false",
        "logging.level.org.hibernate.tool.schema=debug",
        "logging.level.org.hibernate.SQL=debug"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EntityScan("co.edu.javaeriana.biblioteca.model")
@EnableJpaRepositories("co.edu.javaeriana.biblioteca.repository")
class CategoriaRepositoryTest {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    TestEntityManager em;

    Integer idInsertado;

    @BeforeEach
    void setUp() {
        Categoria c = new Categoria();
        c.setNombre("Historia");
        c.setDescripcion("Libros históricos");
        idInsertado = categoriaRepository.save(c).getIdCategoria();
        assertNotNull(idInsertado);
        em.flush(); // asegura que haya DML real
    }

    @Test
    void testGuardarCategoria() {
        Categoria c = new Categoria();
        c.setNombre("Ciencia");
        c.setDescripcion("Libros científicos");
        Categoria guardada = categoriaRepository.save(c);
        assertNotNull(guardada.getIdCategoria());
    }

    @Test
    void testBuscarPorId() {
        Optional<Categoria> cat = categoriaRepository.findById(idInsertado);
        assertTrue(cat.isPresent());
        assertEquals("Historia", cat.get().getNombre());
    }

    @Test
    void testEliminarCategoria() {
        categoriaRepository.deleteById(idInsertado);
        assertFalse(categoriaRepository.findById(idInsertado).isPresent());
    }

    @Test
    void testDuplicadoNombre() {
        Categoria dup = new Categoria();
        dup.setNombre("Historia");
        dup.setDescripcion("Repetida");
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,
                () -> categoriaRepository.saveAndFlush(dup));
    }
}
