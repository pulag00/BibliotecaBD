package co.edu.javaeriana.biblioteca.service;

import co.edu.javaeriana.biblioteca.model.EstadoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.repository.EstadoUsuarioCatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstadoUsuarioCatalogoService {
    private final EstadoUsuarioCatalogoRepository estadoUsuarioCatalogoRepository;

    @Autowired
    public EstadoUsuarioCatalogoService(EstadoUsuarioCatalogoRepository estadoUsuarioCatalogoRepository) {
        this.estadoUsuarioCatalogoRepository = estadoUsuarioCatalogoRepository;
    }

    public List<EstadoUsuarioCatalogo> findAll() {
        return estadoUsuarioCatalogoRepository.findAll();
    }

    public EstadoUsuarioCatalogo create(EstadoUsuarioCatalogo estadoUsuarioCatalogo) {
        return estadoUsuarioCatalogoRepository.save(estadoUsuarioCatalogo);
    }

    public EstadoUsuarioCatalogo update(Integer id, EstadoUsuarioCatalogo estadoUsuarioCatalogo) {
        EstadoUsuarioCatalogo existente = estadoUsuarioCatalogoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(estadoUsuarioCatalogo.getNombre());
            return estadoUsuarioCatalogoRepository.save(existente);
        }
        return null;
    }

    public void delete(Integer id) {
        estadoUsuarioCatalogoRepository.deleteById(id);
    }


}
