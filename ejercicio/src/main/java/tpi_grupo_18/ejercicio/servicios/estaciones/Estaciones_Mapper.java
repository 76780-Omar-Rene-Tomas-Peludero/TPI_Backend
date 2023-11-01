package tpi_grupo_18.ejercicio.servicios.estaciones;

import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class Estaciones_Mapper implements Function<EstacionesDto, Estacion> {

    @Override
    public Estacion apply(EstacionesDto estacionesDto) {
        return new Estacion(
                estacionesDto.getEstaciones_id(),
                estacionesDto.getNombre(),
                estacionesDto.getFecha_hora_creacion(),
                estacionesDto.getLatitud(),
                estacionesDto.getLongitud(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
