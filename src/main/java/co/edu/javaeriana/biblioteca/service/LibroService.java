// src/main/java/co/edu/javaeriana/biblioteca/service/LibroService.java
package co.edu.javaeriana.biblioteca.service;

import co.edu.javaeriana.biblioteca.model.Libro;
import co.edu.javaeriana.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Libro buscarPorId(Integer id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado: " + id));
    }

    public Libro create(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro update(Integer id, Libro libro) {
        Libro existente = libroRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setTitulo(libro.getTitulo());
            existente.setAutor(libro.getAutor());
            existente.setDescripcion(libro.getDescripcion());
            existente.setEditorial(libro.getEditorial());

            return libroRepository.save(existente);
        }
        return null;
    }

    public void delete(Integer id) {
        libroRepository.deleteById(id);
    }

}
