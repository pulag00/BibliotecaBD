package co.edu.javaeriana.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_respuesta")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_respuesta", nullable = false)
    private Integer idRespuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PreguntaSeguridad pregunta;

    @Column(nullable = false)
    private String respuesta;
}
