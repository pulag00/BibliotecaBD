package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoReservaCatalogo;
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
class EstadoReservaCatalogoRepositoryTest {

    @Autowired
    private EstadoReservaCatalogoRepository estadoReservaCatalogoRepository;

    private EstadoReservaCatalogo estado;

    @BeforeEach
    void setUp() {
        estado = new EstadoReservaCatalogo();
        estado.setNombre("Pendiente");
        estadoReservaCatalogoRepository.save(estado);
    }

    @Test
    @DisplayName("Debe guardar un estado de reserva correctamente")
    void testGuardarEstadoReserva() {
        EstadoReservaCatalogo nuevo = new EstadoReservaCatalogo();
        nuevo.setNombre("Confirmada");

        EstadoReservaCatalogo guardado = estadoReservaCatalogoRepository.save(nuevo);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdEstadoReserva()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Confirmada");
    }

    @Test
    @DisplayName("Debe buscar un estado de reserva por ID")
    void testBuscarPorId() {
        Optional<EstadoReservaCatalogo> encontrado = estadoReservaCatalogoRepository.findById(estado.getIdEstadoReserva());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Pendiente");
    }

    @Test
    @DisplayName("Debe listar todos los estados de reserva")
    void testListarTodos() {
        List<EstadoReservaCatalogo> lista = estadoReservaCatalogoRepository.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Debe actualizar un estado de reserva existente")
    void testActualizarEstadoReserva() {
        EstadoReservaCatalogo existente = estadoReservaCatalogoRepository.findById(estado.getIdEstadoReserva()).orElseThrow();
        existente.setNombre("Cancelada");
        estadoReservaCatalogoRepository.save(existente);

        EstadoReservaCatalogo actualizado = estadoReservaCatalogoRepository.findById(estado.getIdEstadoReserva()).orElseThrow();
        assertThat(actualizado.getNombre()).isEqualTo("Cancelada");
    }

    @Test
    @DisplayName("Debe eliminar un estado de reserva por ID")
    void testEliminarEstadoReserva() {
        estadoReservaCatalogoRepository.deleteById(estado.getIdEstadoReserva());

        Optional<EstadoReservaCatalogo> eliminado = estadoReservaCatalogoRepository.findById(estado.getIdEstadoReserva());
        assertThat(eliminado).isEmpty();
    }
}
