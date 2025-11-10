package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoUsuarioCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
