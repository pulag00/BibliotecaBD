package co.edu.javaeriana.biblioteca.service;
import co.edu.javaeriana.biblioteca.model.TipoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.repository.TipoUsuarioCatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TipoUsuarioCatalogoService {


    private final TipoUsuarioCatalogoRepository tipoUsuarioCatalogoRepository;

    @Autowired
    public TipoUsuarioCatalogoService(TipoUsuarioCatalogoRepository tipoUsuarioCatalogoRepository) {
        this.tipoUsuarioCatalogoRepository = tipoUsuarioCatalogoRepository;
    }

    public List<TipoUsuarioCatalogo> findAll() {
        return tipoUsuarioCatalogoRepository.findAll();
    }

    public TipoUsuarioCatalogo create(TipoUsuarioCatalogo tipoUsuarioCatalogo) {
        return tipoUsuarioCatalogoRepository.save(tipoUsuarioCatalogo);
    }

    public TipoUsuarioCatalogo update(Integer id, TipoUsuarioCatalogo tipoUsuarioCatalogo) {
        TipoUsuarioCatalogo existente = tipoUsuarioCatalogoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(tipoUsuarioCatalogo.getNombre());
            return tipoUsuarioCatalogoRepository.save(existente);
        }
        return null;
    }

    public void delete(Integer id) {
        tipoUsuarioCatalogoRepository.deleteById(id);
    }

}

