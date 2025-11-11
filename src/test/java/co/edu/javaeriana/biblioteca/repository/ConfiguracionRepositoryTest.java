package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.Configuracion;
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
class ConfiguracionRepositoryTest {

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    private Configuracion configuracion;

    @BeforeEach
    void setUp() {
        configuracion = new Configuracion();
        configuracion.setClave("sitio_nombre");
        configuracion.setValor("Biblioteca Javaeriana");
        configuracionRepository.save(configuracion);
    }

    @Test
    @DisplayName("Debe guardar una configuración correctamente")
    void testGuardarConfiguracion() {
        Configuracion nueva = new Configuracion();
        nueva.setClave("tema");
        nueva.setValor("oscuro");

        Configuracion guardada = configuracionRepository.save(nueva);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getClave()).isEqualTo("tema");
        assertThat(guardada.getValor()).isEqualTo("oscuro");
    }

    @Test
    @DisplayName("Debe buscar una configuración por clave")
    void testBuscarPorClave() {
        Optional<Configuracion> encontrada = configuracionRepository.findById("sitio_nombre");

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getValor()).isEqualTo("Biblioteca Javaeriana");
    }

    @Test
    @DisplayName("Debe listar todas las configuraciones")
    void testListarTodas() {
        List<Configuracion> lista = configuracionRepository.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Debe actualizar una configuración existente")
    void testActualizarConfiguracion() {
        Configuracion existente = configuracionRepository.findById("sitio_nombre").orElseThrow();
        existente.setValor("Biblioteca Central");
        configuracionRepository.save(existente);

        Configuracion actualizada = configuracionRepository.findById("sitio_nombre").orElseThrow();
        assertThat(actualizada.getValor()).isEqualTo("Biblioteca Central");
    }

    @Test
    @DisplayName("Debe eliminar una configuración por clave")
    void testEliminarConfiguracion() {
        configuracionRepository.deleteById("sitio_nombre");

        Optional<Configuracion> eliminada = configuracionRepository.findById("sitio_nombre");
        assertThat(eliminada).isEmpty();
    }
}
