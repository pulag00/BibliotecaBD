package co.edu.javaeriana.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado_reserva_catalogo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EstadoReservaCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_reserva", nullable = false)
    private Integer idEstadoReserva;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
}
