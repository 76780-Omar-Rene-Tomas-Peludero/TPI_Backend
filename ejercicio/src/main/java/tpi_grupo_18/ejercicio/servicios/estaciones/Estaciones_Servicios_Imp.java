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
public class Estaciones_Servicios_Imp implements Estaciones_Servicios {

    private final Estaciones_Repo estaciones_repo;

    @Override
    public Estacion add(Estacion entity) {
        return this.estaciones_repo.save(entity);
    }

    @Override
    public Estacion update(Estacion entity) {
        /*Optional<Estacion> estacion = Stream.of(entity).map(mapper).findFirst();
        estacion.ifPresent(estaciones_repo::save);
        return estacion.map(DTOmapper).orElseThrow();*/
        return null;
    }

    @Override
    public Estacion delete(Long id) {
        Estacion estacion = this.getById(id);
        this.estaciones_repo.delete(estacion);
        return estacion;
    }

    @Override
    public Estacion getById(Long id) {
        return this.estaciones_repo.findById(id).orElseThrow();
    }

    @Override
    public List<Estacion> getAll() {
        return this.estaciones_repo.findAll();
    }
}
