package co.edu.javaeriana.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado_prestamo_catalogo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EstadoPrestamoCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_prestamo", nullable = false)
    private Integer idEstadoPrestamo;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
}
