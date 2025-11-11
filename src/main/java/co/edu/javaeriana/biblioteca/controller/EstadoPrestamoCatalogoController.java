package co.edu.javaeriana.biblioteca.controller;

import co.edu.javaeriana.biblioteca.model.EstadoPrestamoCatalogo;
import co.edu.javaeriana.biblioteca.service.EstadoPrestamoCatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/estadoPrestamo")
public class EstadoPrestamoCatalogoController {

    private final EstadoPrestamoCatalogoService estadoPrestamoCatalogoService;

    @Autowired
    public  EstadoPrestamoCatalogoController( EstadoPrestamoCatalogoService estadoPrestamoCatalogoService) {
        this.estadoPrestamoCatalogoService = estadoPrestamoCatalogoService;
    }

    @GetMapping
    public List<EstadoPrestamoCatalogo> getEstadoPrestamoCatalogo() {
        return estadoPrestamoCatalogoService.findAll();
    }

    @PostMapping
    public ResponseEntity<EstadoPrestamoCatalogo> createEstadoPrestamoCatalogo(@RequestBody EstadoPrestamoCatalogo estadoPrestamoCatalogo) {
        return ResponseEntity.ok(estadoPrestamoCatalogoService.create(estadoPrestamoCatalogo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoPrestamoCatalogo> updateEstadoPrestamoCatalogo(@PathVariable Integer id, @RequestBody  EstadoPrestamoCatalogo estadoPrestamoCatalogo) {
        EstadoPrestamoCatalogo actualizada = estadoPrestamoCatalogoService.update(id, estadoPrestamoCatalogo);
        if (actualizada == null)
            return ResponseEntity.ok(actualizada);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadoPrestamoCatalogo(@PathVariable Integer id) {
        estadoPrestamoCatalogoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
