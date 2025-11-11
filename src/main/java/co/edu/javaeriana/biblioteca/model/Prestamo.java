package co.edu.javaeriana.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prestamo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo", nullable = false)
    private Integer idPrestamo;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    // Relación con Libro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Libro libro;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo = LocalDateTime.now();

    @Column(name = "fecha_devolucion_esperada", nullable = false)
    private LocalDateTime fechaDevolucionEsperada;

    @Column(name = "fecha_devolucion_real")
    private LocalDateTime fechaDevolucionReal;

    // Relación con EstadoPrestamoCatalogo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_prestamo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private EstadoPrestamoCatalogo estadoPrestamo;
}
