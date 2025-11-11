package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.TipoUsuarioCatalogo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
class TipoUsuarioRepositoryTest {

    @Autowired
    private TipoUsuarioCatalogoRepository tipoUsuarioCatalogoRepository;

    private TipoUsuarioCatalogo tipoBase;

    @BeforeEach
    void setUp() {
        tipoUsuarioCatalogoRepository.deleteAll(); // evitar duplicados si el contexto persiste datos
        tipoBase = new TipoUsuarioCatalogo();
        tipoBase.setNombre("Administrador");
        tipoUsuarioCatalogoRepository.save(tipoBase);
    }

    @Test
    @Order(1)
    void testGuardarTipoUsuario() {
        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Bibliotecario");

        TipoUsuarioCatalogo guardado = tipoUsuarioCatalogoRepository.save(tipo);

        Assertions.assertNotNull(guardado.getIdTipoUsuario());
        Assertions.assertEquals("Bibliotecario", guardado.getNombre());
    }

    @Test
    @Order(2)
    void testListarTiposUsuario() {
        List<TipoUsuarioCatalogo> tipos = tipoUsuarioCatalogoRepository.findAll();

        Assertions.assertFalse(tipos.isEmpty(), "La lista de tipos no debería estar vacía");
        Assertions.assertTrue(
                tipos.stream().anyMatch(t -> t.getNombre().equals("Administrador")),
                "Debería existir un tipo de usuario 'Administrador'"
        );
    }

    @Test
    @Order(3)
    void testActualizarTipoUsuario() {
        TipoUsuarioCatalogo existente = tipoUsuarioCatalogoRepository.findAll().get(0);
        existente.setNombre("Bibliotecario");

        TipoUsuarioCatalogo actualizado = tipoUsuarioCatalogoRepository.save(existente);

        Assertions.assertEquals("Bibliotecario", actualizado.getNombre());
    }

    @Test
    @Order(4)
    void testDuplicadoNombre() {
        TipoUsuarioCatalogo tipoDuplicado = new TipoUsuarioCatalogo();
        tipoDuplicado.setNombre("Administrador"); // nombre duplicado

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            tipoUsuarioCatalogoRepository.saveAndFlush(tipoDuplicado);
        });
    }

    @Test
    @Order(5)
    void testBuscarPorId() {
        TipoUsuarioCatalogo existente = tipoUsuarioCatalogoRepository.findAll().get(0);
        Optional<TipoUsuarioCatalogo> encontrado =
                tipoUsuarioCatalogoRepository.findById(existente.getIdTipoUsuario());

        Assertions.assertTrue(encontrado.isPresent());
        Assertions.assertEquals(existente.getNombre(), encontrado.get().getNombre());
    }

    @Test
    @Order(6)
    void testEliminarTipoUsuario() {
        TipoUsuarioCatalogo existente = tipoUsuarioCatalogoRepository.findAll().get(0);
        Integer id = existente.getIdTipoUsuario();

        tipoUsuarioCatalogoRepository.deleteById(id);

        Optional<TipoUsuarioCatalogo> eliminado = tipoUsuarioCatalogoRepository.findById(id);
        Assertions.assertTrue(eliminado.isEmpty());
    }
}
