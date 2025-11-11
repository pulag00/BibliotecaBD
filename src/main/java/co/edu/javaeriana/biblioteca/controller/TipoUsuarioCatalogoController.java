package co.edu.javaeriana.biblioteca.controller;

import co.edu.javaeriana.biblioteca.model.TipoUsuarioCatalogo;
import co.edu.javaeriana.biblioteca.service.TipoUsuarioCatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/tipoUsuario")
public class TipoUsuarioCatalogoController {

    private final TipoUsuarioCatalogoService tipoUsuarioCatalogoService;

    @Autowired
    public TipoUsuarioCatalogoController(TipoUsuarioCatalogoService tipoUsuarioCatalogoService) {
        this.tipoUsuarioCatalogoService = tipoUsuarioCatalogoService;
    }

    @GetMapping
    public List<TipoUsuarioCatalogo> getTipoUsuarioCatalogo() {
        return tipoUsuarioCatalogoService.findAll();
    }

    @PostMapping
    public ResponseEntity<TipoUsuarioCatalogo> createTipoUsuarioCatalogo(@RequestBody TipoUsuarioCatalogo tipoUsuarioCatalogo) {
        return ResponseEntity.ok(tipoUsuarioCatalogoService.create(tipoUsuarioCatalogo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuarioCatalogo> updateTipoUsuarioCatalogo(@PathVariable Integer id, @RequestBody TipoUsuarioCatalogo tipoUsuarioCatalogo) {
        TipoUsuarioCatalogo actualizada = tipoUsuarioCatalogoService.update(id, tipoUsuarioCatalogo);
        if (actualizada == null)
            return ResponseEntity.ok(actualizada);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        tipoUsuarioCatalogoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
