package co.edu.javaeriana.biblioteca.controller;

import co.edu.javaeriana.biblioteca.model.EstadoReservaCatalogo;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javaeriana.biblioteca.service. EstadoReservaCatalogoService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/estadoReserva")
public class EstadoReservaCatalogoController {


    private final EstadoReservaCatalogoService estadoReservaCatalogoService;

    @Autowired
    public EstadoReservaCatalogoController( EstadoReservaCatalogoService estadoReservaCatalogoService) {
        this.estadoReservaCatalogoService = estadoReservaCatalogoService;
    }

    @GetMapping
    public List<EstadoReservaCatalogo> getEstadoReservaCatalogo() {
        return estadoReservaCatalogoService.findAll();
    }

    @PostMapping
    public ResponseEntity<EstadoReservaCatalogo> createEstadoReservaCatalogo(@RequestBody EstadoReservaCatalogo estadoReservaCatalogo) {
        return ResponseEntity.ok(estadoReservaCatalogoService.create(estadoReservaCatalogo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoReservaCatalogo> updateEstadoReservaCatalogo(@PathVariable Integer id, @RequestBody  EstadoReservaCatalogo estadoReservaCatalogo) {
        EstadoReservaCatalogo actualizada = estadoReservaCatalogoService.update(id, estadoReservaCatalogo);
        if (actualizada == null)
            return ResponseEntity.ok(actualizada);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadoUsuarioCatalogo(@PathVariable Integer id) {
        estadoReservaCatalogoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
