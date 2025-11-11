package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoUsuarioCatalogo;
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
class EstadoUsuarioCatalogoRepositoryTest {

    @Autowired
    private EstadoUsuarioCatalogoRepository estadoUsuarioCatalogoRepository;

    private EstadoUsuarioCatalogo estado;

    @BeforeEach
    void setUp() {
        estado = new EstadoUsuarioCatalogo();
        estado.setNombre("Activo");
        estadoUsuarioCatalogoRepository.save(estado);
    }

    @Test
    @DisplayName("Debe guardar un estado de usuario correctamente")
    void testGuardarEstadoUsuario() {
        EstadoUsuarioCatalogo nuevo = new EstadoUsuarioCatalogo();
        nuevo.setNombre("Inactivo");

        EstadoUsuarioCatalogo guardado = estadoUsuarioCatalogoRepository.save(nuevo);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdEstadoUsuario()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Inactivo");
    }

    @Test
    @DisplayName("Debe buscar un estado de usuario por ID")
    void testBuscarPorId() {
        Optional<EstadoUsuarioCatalogo> encontrado = estadoUsuarioCatalogoRepository.findById(estado.getIdEstadoUsuario());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Activo");
    }

    @Test
    @DisplayName("Debe listar todos los estados de usuario")
    void testListarTodos() {
        List<EstadoUsuarioCatalogo> lista = estadoUsuarioCatalogoRepository.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Debe actualizar un estado de usuario existente")
    void testActualizarEstadoUsuario() {
        EstadoUsuarioCatalogo existente = estadoUsuarioCatalogoRepository.findById(estado.getIdEstadoUsuario()).orElseThrow();
        existente.setNombre("Suspendido");
        estadoUsuarioCatalogoRepository.save(existente);

        EstadoUsuarioCatalogo actualizado = estadoUsuarioCatalogoRepository.findById(estado.getIdEstadoUsuario()).orElseThrow();
        assertThat(actualizado.getNombre()).isEqualTo("Suspendido");
    }

    @Test
    @DisplayName("Debe eliminar un estado de usuario por ID")
    void testEliminarEstadoUsuario() {
        estadoUsuarioCatalogoRepository.deleteById(estado.getIdEstadoUsuario());

        Optional<EstadoUsuarioCatalogo> eliminado = estadoUsuarioCatalogoRepository.findById(estado.getIdEstadoUsuario());
        assertThat(eliminado).isEmpty();
    }
}
