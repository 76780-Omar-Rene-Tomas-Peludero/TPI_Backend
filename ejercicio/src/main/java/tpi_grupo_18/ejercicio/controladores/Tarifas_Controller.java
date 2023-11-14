package tpi_grupo_18.ejercicio.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.servicios.tarifas.Tarifas_Servicios;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tpi/Tarifas")
@RequiredArgsConstructor
public class Tarifas_Controller {

    private final Tarifas_Servicios tarifas_servicios;

    @GetMapping("/")
    public ResponseEntity<List<Tarifa>> getAll(){
        List<Tarifa> tarifasDtoList = tarifas_servicios.getAll();
        return ResponseEntity.ok(tarifasDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getById(@PathVariable Long id){
        try {
            Tarifa tarifas = tarifas_servicios.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(tarifas);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Tarifa> add(@RequestBody Tarifa entity){
        Tarifa tarifas = tarifas_servicios.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifas);
    }

    @PutMapping
    public ResponseEntity<Tarifa> update(@RequestBody Tarifa entity){
        try {
            Tarifa tarifas = tarifas_servicios.update(entity);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(tarifas);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tarifa> delete(@PathVariable Long id){
        try {
            Tarifa tarifas = tarifas_servicios.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(tarifas);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
