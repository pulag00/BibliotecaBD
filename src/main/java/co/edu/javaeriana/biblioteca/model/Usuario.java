package co.edu.javaeriana.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @Column(length = 255, unique = true)
    private String contrasena;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_usuario", nullable = false)
    private TipoUsuarioCatalogo tipoUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_usuario", nullable = false)
    private EstadoUsuarioCatalogo estadoUsuario;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "intentos_fallidos", nullable = false)
    private Integer intentosFallidos;

    @Column(name = "requiere_cambio_pass", nullable = false)
    private Boolean requiereCambioPass;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        if (this.intentosFallidos == null) this.intentosFallidos = 0;
        if (this.requiereCambioPass == null) this.requiereCambioPass = false;
    }
}
