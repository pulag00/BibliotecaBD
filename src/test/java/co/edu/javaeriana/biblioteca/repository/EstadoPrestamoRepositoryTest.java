package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoPrestamoCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
