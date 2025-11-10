package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoReservaCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
