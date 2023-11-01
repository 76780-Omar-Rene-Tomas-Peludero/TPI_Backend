package tpi_grupo_18.ejercicio.servicios.estaciones;

import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;

import java.util.function.Function;

@Service
public class Estaciones_DTOMapper implements Function<Estacion, EstacionesDto> {
    @Override
    public EstacionesDto apply(Estacion estacion) {
        return new EstacionesDto(
                estacion.getEstaciones_id(),
                estacion.getNombre(),
                estacion.getFecha_hora_creacion(),
                estacion.getLongitud(),
                estacion.getLongitud()
        );
    }
}
