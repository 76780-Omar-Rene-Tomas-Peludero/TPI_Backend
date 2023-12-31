package tpi_grupo_18.alquileres.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi_grupo_18.alquileres.entidades.Alquiler;
import tpi_grupo_18.alquileres.servicios.alquileres.Alquileres_Servicios;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> devolver(@RequestParam String client, @RequestParam Long estacionId,
                                              @RequestParam Long tarifaId, @RequestParam(required = false) String moneda) {
        Alquiler alquiler = alquileres_servicios.devolver(client, estacionId, tarifaId);

        Map<String, Object> respuesta = alquileres_servicios.toHashMap(alquiler, moneda);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(respuesta);
    }
}
