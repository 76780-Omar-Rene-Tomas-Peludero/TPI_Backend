package tpi_grupo_18.ejercicio.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Servicios;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tpi/Estaciones")
@RequiredArgsConstructor
public class Estaciones_Controller {

    private final Estaciones_Servicios estaciones_servicios;

    @GetMapping("/")
    public ResponseEntity<List<EstacionesDto>> getAll(){
        List<EstacionesDto> estacionesDtos = estaciones_servicios.getAll();
        return ResponseEntity.ok(estacionesDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionesDto> getById(@PathVariable Long id){
        try {
            EstacionesDto estaciones = estaciones_servicios.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<EstacionesDto> add(@RequestBody EstacionesDto entity){
        EstacionesDto estaciones = estaciones_servicios.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(estaciones);
    }

    @PutMapping
    public ResponseEntity<EstacionesDto> update(@RequestBody EstacionesDto entity){
        try {
            EstacionesDto estaciones = estaciones_servicios.update(entity);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EstacionesDto> delete(@PathVariable Long id){
        try {
            EstacionesDto estaciones = estaciones_servicios.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
