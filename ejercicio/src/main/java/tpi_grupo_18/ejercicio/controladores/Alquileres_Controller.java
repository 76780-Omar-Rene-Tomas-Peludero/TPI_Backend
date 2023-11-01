package tpi_grupo_18.ejercicio.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.servicios.alquileres.Alquileres_Servicios;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tpi/Alquileres")
@RequiredArgsConstructor
public class Alquileres_Controller {

    private final Alquileres_Servicios alquileres_servicios;

    @GetMapping("/")
    public ResponseEntity<List<AlquileresDto>> getAll(){
        List<AlquileresDto> alquileresDtoList = alquileres_servicios.getAll();
        return ResponseEntity.ok(alquileresDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlquileresDto> getById(@PathVariable Long id){
        try {
            AlquileresDto alquileres = alquileres_servicios.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(alquileres);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<AlquileresDto> add(@RequestBody AlquileresDto entity){
        AlquileresDto alquileres = alquileres_servicios.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileres);
    }

    @PutMapping
    public ResponseEntity<AlquileresDto> update(@RequestBody AlquileresDto entity){
        try {
            AlquileresDto alquileres = alquileres_servicios.update(entity);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alquileres);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlquileresDto> delete(@PathVariable Long id){
        try {
            AlquileresDto alquileres = alquileres_servicios.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alquileres);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
