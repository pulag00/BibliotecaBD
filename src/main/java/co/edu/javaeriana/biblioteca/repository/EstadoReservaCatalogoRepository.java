package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoReservaCatalogo;
import co.edu.javaeriana.biblioteca.model.EstadoUsuarioCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoReservaCatalogoRepository extends JpaRepository<EstadoReservaCatalogo, Integer> {

}
