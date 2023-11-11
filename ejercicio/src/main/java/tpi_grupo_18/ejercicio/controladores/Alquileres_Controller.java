package tpi_grupo_18.ejercicio.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
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
    public ResponseEntity<List<Alquiler>> getAll(){
        List<Alquiler> alquileresDtoList = alquileres_servicios.getAll();
        return ResponseEntity.ok(alquileresDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alquiler> getById(@PathVariable Long id){
        try {
            Alquiler alquileres = alquileres_servicios.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(alquileres);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Alquiler> delete(@PathVariable Long id){
        try {
            Alquiler alquileres = alquileres_servicios.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alquileres);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/retirar")
    public ResponseEntity<Alquiler> retirar(@RequestParam String client, @RequestParam Long estacionId){
        Alquiler alquileres = alquileres_servicios.retirar(client, estacionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileres);
    }

    @PutMapping("/devolver")
    public ResponseEntity<Alquiler> devolver(@RequestParam String client, @RequestParam Long estacionId,
                                             @RequestParam Long tarifaId) {
        Alquiler alquiler = alquileres_servicios.devolver(client, estacionId, tarifaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alquiler);
    }

    /*@PutMapping("/{id_alquiler}/{id_tarifa}")
    public ResponseEntity<Double> calcularMonto(@PathVariable Long id_alquiler, @PathVariable Long id_tarifa){
        try {
            Double monto = alquileres_servicios.calcularMonto(id_alquiler, id_tarifa);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(monto);
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }*/
}
