package tpi_grupo_18.ejercicio.servicios.alquileres;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;

import java.util.function.Function;

@Service
public class Alquileres_Mapper implements Function<AlquileresDto, Alquiler> {
    @Override
    public Alquiler apply(AlquileresDto alquileresDto) {
        return new Alquiler(
                alquileresDto.getId(),
                alquileresDto.getIdCliente(),
                alquileresDto.getEstado(),
                alquileresDto.getFechaHoraRetiro(),
                alquileresDto.getFechaHoraDevolucion(),
                alquileresDto.getMonto(),
                new Estacion(),
                new Estacion(),
                new Tarifa()

//                alquileresDto.getEstacionRetiro(),
//                alquileresDto.getEstacionDevolucion(),
//                alquileresDto.getTarifa()
        );
    }
}
