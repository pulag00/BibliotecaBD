package co.edu.javaeriana.biblioteca.service;

import co.edu.javaeriana.biblioteca.model.EstadoReservaCatalogo;
import co.edu.javaeriana.biblioteca.repository.EstadoReservaCatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstadoReservaCatalogoService {
    private final EstadoReservaCatalogoRepository estadoReservaCatalogoRepository;

    @Autowired
    public EstadoReservaCatalogoService(EstadoReservaCatalogoRepository estadoReservaCatalogoRepository) {
        this.estadoReservaCatalogoRepository = estadoReservaCatalogoRepository;
    }

    public List<EstadoReservaCatalogo> findAll() {
        return estadoReservaCatalogoRepository.findAll();
    }

    public EstadoReservaCatalogo create(EstadoReservaCatalogo estadoReservaCatalogo) {
        return estadoReservaCatalogoRepository.save(estadoReservaCatalogo);
    }

    public EstadoReservaCatalogo update(Integer id, EstadoReservaCatalogo estadoReservaCatalogo) {
        EstadoReservaCatalogo existente = estadoReservaCatalogoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(estadoReservaCatalogo.getNombre());
            return estadoReservaCatalogoRepository.save(existente);
        }
        return null;
    }

    public void delete(Integer id) {
        estadoReservaCatalogoRepository.deleteById(id);
    }


}
