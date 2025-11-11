package co.edu.javaeriana.biblioteca.controller;

import co.edu.javaeriana.biblioteca.model.Libro;
import co.edu.javaeriana.biblioteca.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/libro")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Integer id) {
        try {
            Libro libro = libroService.buscarPorId(id);
            return ResponseEntity.ok(libro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/libro
    @GetMapping
    public List<Libro> getLibros() {
        return libroService.findAll();
    }

    // POST /api/libro
    @PostMapping
    public ResponseEntity<Libro> createCategoria(@RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.create(libro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Integer id, @RequestBody Libro libro) {
        Libro actualizada = libroService.update(id, libro);
        if (actualizada == null)
            return ResponseEntity.ok(actualizada);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Integer id) {
        libroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
