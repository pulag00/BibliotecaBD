package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoPrestamoCatalogo;
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
class EstadoPrestamoCatalogoRepositoryTest {

    @Autowired
    private EstadoPrestamoCatalogoRepository estadoPrestamoCatalogoRepository;

    private EstadoPrestamoCatalogo estado;

    @BeforeEach
    void setUp() {
        estado = new EstadoPrestamoCatalogo();
        estado.setNombre("Activo");
        estadoPrestamoCatalogoRepository.save(estado);
    }

    @Test
    @DisplayName("Debe guardar un estado de préstamo correctamente")
    void testGuardarEstadoPrestamo() {
        EstadoPrestamoCatalogo nuevo = new EstadoPrestamoCatalogo();
        nuevo.setNombre("Finalizado");

        EstadoPrestamoCatalogo guardado = estadoPrestamoCatalogoRepository.save(nuevo);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdEstadoPrestamo()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Finalizado");
    }

    @Test
    @DisplayName("Debe buscar un estado de préstamo por ID")
    void testBuscarPorId() {
        Optional<EstadoPrestamoCatalogo> encontrado = estadoPrestamoCatalogoRepository.findById(estado.getIdEstadoPrestamo());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Activo");
    }

    @Test
    @DisplayName("Debe listar todos los estados de préstamo")
    void testListarTodos() {
        List<EstadoPrestamoCatalogo> lista = estadoPrestamoCatalogoRepository.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Debe actualizar un estado de préstamo existente")
    void testActualizarEstadoPrestamo() {
        EstadoPrestamoCatalogo existente = estadoPrestamoCatalogoRepository.findById(estado.getIdEstadoPrestamo()).orElseThrow();
        existente.setNombre("En proceso");
        estadoPrestamoCatalogoRepository.save(existente);

        EstadoPrestamoCatalogo actualizado = estadoPrestamoCatalogoRepository.findById(estado.getIdEstadoPrestamo()).orElseThrow();
        assertThat(actualizado.getNombre()).isEqualTo("En proceso");
    }

    @Test
    @DisplayName("Debe eliminar un estado de préstamo por ID")
    void testEliminarEstadoPrestamo() {
        estadoPrestamoCatalogoRepository.deleteById(estado.getIdEstadoPrestamo());

        Optional<EstadoPrestamoCatalogo> eliminado = estadoPrestamoCatalogoRepository.findById(estado.getIdEstadoPrestamo());
        assertThat(eliminado).isEmpty();
    }
}
