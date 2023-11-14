package tpi_grupo_18.ejercicio.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Servicios;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tpi/Estaciones")
@RequiredArgsConstructor
public class Estaciones_Controller {

    private final Estaciones_Servicios estaciones_servicios;

    @GetMapping("/")
    public ResponseEntity<List<Estacion>> getAll(){
        List<Estacion> estacionesDtos = estaciones_servicios.getAll();
        return ResponseEntity.ok(estacionesDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estacion> getById(@PathVariable Long id){
        try {
            Estacion estaciones = estaciones_servicios.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Estacion> add(@RequestBody Estacion entity){
        Estacion estaciones = estaciones_servicios.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(estaciones);
    }

    @PutMapping
    public ResponseEntity<Estacion> update(@RequestBody Long id, @RequestBody String nombre, @RequestBody Double latitud, @RequestBody Double longitud){
        try {
            Estacion estaciones = estaciones_servicios.update(id, nombre, latitud, longitud);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estacion> delete(@PathVariable Long id){
        try {
            Estacion estaciones = estaciones_servicios.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cercana")
    public ResponseEntity<Estacion> cercana(@RequestParam double latitud, @RequestParam double longitud){
        try {
            Estacion estaciones = estaciones_servicios.getByLongAndLat(longitud, latitud);
            return ResponseEntity.status(HttpStatus.FOUND).body(estaciones);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
