package co.edu.javaeriana.biblioteca.service;

import co.edu.javaeriana.biblioteca.model.EstadoPrestamoCatalogo;
import co.edu.javaeriana.biblioteca.model.EstadoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.repository.EstadoPrestamoCatalogoRepository;
import co.edu.javaeriana.biblioteca.repository.EstadoUsuarioCatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstadoPrestamoCatalogoService {

    private final EstadoPrestamoCatalogoRepository estadoPrestamoCatalogoRepository;

    @Autowired
    public EstadoPrestamoCatalogoService(EstadoPrestamoCatalogoRepository estadoPrestamoCatalogoRepository) {
        this.estadoPrestamoCatalogoRepository = estadoPrestamoCatalogoRepository;
    }

    public List<EstadoPrestamoCatalogo> findAll() {
        return estadoPrestamoCatalogoRepository.findAll();
    }

    public EstadoPrestamoCatalogo create(EstadoPrestamoCatalogo estadoPrestamoCatalogo) {
        return estadoPrestamoCatalogoRepository.save(estadoPrestamoCatalogo);
    }

    public EstadoPrestamoCatalogo update(Integer id, EstadoPrestamoCatalogo estadoPrestamoCatalogo) {
        EstadoPrestamoCatalogo existente = estadoPrestamoCatalogoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(estadoPrestamoCatalogo.getNombre());
            return estadoPrestamoCatalogoRepository.save(existente);
        }
        return null;
    }

    public void delete(Integer id) {
        estadoPrestamoCatalogoRepository.deleteById(id);
    }

}
