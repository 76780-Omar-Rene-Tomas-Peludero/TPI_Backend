package tpi_grupo_18.ejercicio.servicios.alquileres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.repositorios.Alquileres_Repo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class Alquileres_Servicios_Imp implements Alquileres_Servicios{

    private final Alquileres_Repo alquileres_repo;
    private final Alquileres_DTOMapper DTOmapper;
    private final Alquileres_Mapper mapper;


    // http://localhost:8080/alquileres?estacionId=1&tarifaId=2
    @Override
    public AlquileresDto add(AlquileresDto entity) {
        /*Optional<Alquiler> alquiler = Stream.of(entity).map(mapper).findFirst();
        try {
            this.alquileres_repo.save(alquiler.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return alquiler.map(DTOmapper).orElseThrow();*/
        return null;
    }

    @Override
    public AlquileresDto update(AlquileresDto entity) {
        Optional<Alquiler> alquiler = Stream.of(entity).map(mapper).findFirst();
        alquiler.ifPresent(alquileres_repo::save);
        return alquiler.map(DTOmapper).orElseThrow();
    }

    @Override
    public AlquileresDto delete(Long id) {
        AlquileresDto alquileresDto = this.getById(id);
        if (alquileresDto != null) {
            Optional<Alquiler> alquiler = Stream.of(alquileresDto).map(mapper).findFirst();
            alquiler.ifPresent(alquileres_repo::delete);
        }
        return alquileresDto;
    }

    @Override
    public AlquileresDto getById(Long id) {
        Optional<Alquiler> alquiler = this.alquileres_repo.findById(id);
        return alquiler.map(DTOmapper).orElseThrow();
    }

    @Override
    public List<AlquileresDto> getAll() {
        List<Alquiler> alquilerList = this.alquileres_repo.findAll();
        return alquilerList.stream().map(DTOmapper).toList();
    }

    @Override
    public AlquileresDto retirar(AlquileresDto entity, Long estacionIdRetirar) {
        return null;
    }

    @Override
    public AlquileresDto devolver(AlquileresDto entity, Long estacionIdDevolucion, Long tarifaId) {
        return null;
    }
}
