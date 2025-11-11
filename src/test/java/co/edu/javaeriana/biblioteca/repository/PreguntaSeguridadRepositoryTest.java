package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.PreguntaSeguridad;
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
class PreguntaSeguridadRepositoryTest {

    @Autowired private PreguntaSeguridadRepository preguntaSeguridadRepository;

    private PreguntaSeguridad pregunta;

    @BeforeEach
    void setUp() {
        pregunta = new PreguntaSeguridad();
        pregunta.setTextoPregunta("¿Cuál es el nombre de tu primera mascota?");
        preguntaSeguridadRepository.save(pregunta);
    }

    @Test
    @DisplayName("Debe guardar una pregunta de seguridad correctamente")
    void testGuardarPreguntaSeguridad() {
        PreguntaSeguridad nueva = new PreguntaSeguridad();
        nueva.setTextoPregunta("¿En qué ciudad naciste?");

        PreguntaSeguridad guardada = preguntaSeguridadRepository.save(nueva);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getIdPregunta()).isNotNull();
        assertThat(guardada.getTextoPregunta()).isEqualTo("¿En qué ciudad naciste?");
    }

    @Test
    @DisplayName("Debe buscar una pregunta de seguridad por ID")
    void testBuscarPorId() {
        Optional<PreguntaSeguridad> encontrada = preguntaSeguridadRepository.findById(pregunta.getIdPregunta());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getTextoPregunta()).isEqualTo("¿Cuál es el nombre de tu primera mascota?");
    }

    @Test
    @DisplayName("Debe listar todas las preguntas de seguridad")
    void testListarTodas() {
        List<PreguntaSeguridad> lista = preguntaSeguridadRepository.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Debe actualizar una pregunta de seguridad existente")
    void testActualizarPreguntaSeguridad() {
        PreguntaSeguridad existente = preguntaSeguridadRepository.findById(pregunta.getIdPregunta()).orElseThrow();
        existente.setTextoPregunta("¿Cuál es tu comida favorita?");
        preguntaSeguridadRepository.save(existente);

        PreguntaSeguridad actualizada = preguntaSeguridadRepository.findById(pregunta.getIdPregunta()).orElseThrow();
        assertThat(actualizada.getTextoPregunta()).isEqualTo("¿Cuál es tu comida favorita?");
    }

    @Test
    @DisplayName("Debe eliminar una pregunta de seguridad por ID")
    void testEliminarPreguntaSeguridad() {
        preguntaSeguridadRepository.deleteById(pregunta.getIdPregunta());

        Optional<PreguntaSeguridad> eliminada = preguntaSeguridadRepository.findById(pregunta.getIdPregunta());
        assertThat(eliminada).isEmpty();
    }
}
