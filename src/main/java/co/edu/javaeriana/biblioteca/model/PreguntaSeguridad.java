package co.edu.javaeriana.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pregunta_seguridad")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PreguntaSeguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta", nullable = false)
    private Integer idPregunta;

    @Column(name = "texto_pregunta", nullable = false, unique = true, length = 255)
    private String textoPregunta;
}
