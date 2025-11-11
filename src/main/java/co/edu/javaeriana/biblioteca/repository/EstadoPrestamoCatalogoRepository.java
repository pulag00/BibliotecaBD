package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.EstadoPrestamoCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPrestamoCatalogoRepository extends JpaRepository<EstadoPrestamoCatalogo, Integer> {

}
