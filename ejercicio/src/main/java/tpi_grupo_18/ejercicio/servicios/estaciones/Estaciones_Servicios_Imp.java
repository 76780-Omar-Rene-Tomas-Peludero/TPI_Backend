package tpi_grupo_18.ejercicio.servicios.estaciones;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.repositorios.Estaciones_Repo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class Estaciones_Servicios_Imp implements Estaciones_Servicios{

    private final Estaciones_Repo estaciones_repo;
    private final Estaciones_DTOMapper DTOmapper;
    private final Estaciones_Mapper mapper;

    @Override
    public EstacionesDto add(EstacionesDto entity) {
        Optional<Estacion> estacion = Stream.of(entity).map(mapper).findFirst();
        try {
            this.estaciones_repo.save(estacion.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return estacion.map(DTOmapper).orElseThrow();
    }

    @Override
    public EstacionesDto update(EstacionesDto entity) {
        Optional<Estacion> estacion = Stream.of(entity).map(mapper).findFirst();
        estacion.ifPresent(estaciones_repo::save);
        return estacion.map(DTOmapper).orElseThrow();
    }

    @Override
    public EstacionesDto delete(Long id) {
        EstacionesDto estacionesDto = this.getById(id);
        if (estacionesDto != null) {
            Optional<Estacion> estacion = Stream.of(estacionesDto).map(mapper).findFirst();
            estacion.ifPresent(estaciones_repo::delete);
        }
        return estacionesDto;
    }

    @Override
    public EstacionesDto getById(Long id) {
        Optional<Estacion> estacion = this.estaciones_repo.findById(id);
        return estacion.map(DTOmapper).orElseThrow();
    }

    @Override
    public List<EstacionesDto> getAll() {
        List<Estacion> estacionList = this.estaciones_repo.findAll();
        return estacionList.stream().map(DTOmapper).toList();
    }
}
