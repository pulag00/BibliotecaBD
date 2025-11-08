package co.edu.javaeriana.biblioteca.repository;

import co.edu.javaeriana.biblioteca.model.TipoUsuarioCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioCatalogoRepository  extends JpaRepository<TipoUsuarioCatalogo, Integer>{

}
