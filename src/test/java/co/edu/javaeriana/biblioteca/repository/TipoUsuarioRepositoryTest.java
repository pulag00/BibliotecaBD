package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.TipoUsuarioCatalogo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TipoUsuarioRepositoryTest {

    @Autowired
    private TipoUsuarioCatalogoRepository tipoUsuarioCatalogoRepository;

    @Test
    @Order(1)
    void testGuardarTipoUsuario() {
        TipoUsuarioCatalogo tipo = new TipoUsuarioCatalogo();
        tipo.setNombre("Administrador");

        TipoUsuarioCatalogo guardado = tipoUsuarioCatalogoRepository.save(tipo);

        Assertions.assertNotNull(guardado.getIdTipoUsuario());
        Assertions.assertEquals("Administrador", guardado.getNombre());
    }

    @Test
    @Order(2)
    void testListarTiposUsuario() {
        List<TipoUsuarioCatalogo> tipos = tipoUsuarioCatalogoRepository.findAll();
        Assertions.assertFalse(tipos.isEmpty());
        Assertions.assertTrue(tipos.stream().anyMatch(t -> t.getNombre().equals("Administrador")));
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
        tipoDuplicado.setNombre("Bibliotecario"); // nombre duplicado

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            tipoUsuarioCatalogoRepository.saveAndFlush(tipoDuplicado);
        });
    }

    @Test
    @Order(5)
    void testBuscarPorId() {
        TipoUsuarioCatalogo existente = tipoUsuarioCatalogoRepository.findAll().get(0);
        Optional<TipoUsuarioCatalogo> encontrado = tipoUsuarioCatalogoRepository.findById(existente.getIdTipoUsuario());

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
