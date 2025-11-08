package co.edu.javaeriana.biblioteca.controller;

import co.edu.javaeriana.biblioteca.model. EstadoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.service. EstadoUsuarioCatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/estadoUsuario")
public class EstadoUsuarioCatalogoController {

    private final  EstadoUsuarioCatalogoService estadoUsuarioCatalogoService;

    @Autowired
    public  EstadoUsuarioCatalogoController( EstadoUsuarioCatalogoService estadoUsuarioCatalogoService) {
        this.estadoUsuarioCatalogoService = estadoUsuarioCatalogoService;
    }

    @GetMapping
    public List< EstadoUsuarioCatalogo> getEstadoUsuarioCatalogo() {
        return estadoUsuarioCatalogoService.findAll();
    }

    @PostMapping
    public ResponseEntity<EstadoUsuarioCatalogo> createEstadoUsuarioCatalogo(@RequestBody EstadoUsuarioCatalogo estadoUsuarioCatalogo) {
        return ResponseEntity.ok(estadoUsuarioCatalogoService.create(estadoUsuarioCatalogo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoUsuarioCatalogo> updateEstadoUsuarioCatalogo(@PathVariable Integer id, @RequestBody  EstadoUsuarioCatalogo estadoUsuarioCatalogo) {
        EstadoUsuarioCatalogo actualizada = estadoUsuarioCatalogoService.update(id, estadoUsuarioCatalogo);
        if (actualizada == null)
            return ResponseEntity.ok(actualizada);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadoUsuarioCatalogo(@PathVariable Integer id) {
        estadoUsuarioCatalogoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
