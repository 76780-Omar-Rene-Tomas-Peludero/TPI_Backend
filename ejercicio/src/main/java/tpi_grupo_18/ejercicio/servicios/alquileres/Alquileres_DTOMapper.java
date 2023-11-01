package tpi_grupo_18.ejercicio.servicios.alquileres;

import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;

import java.util.function.Function;

@Service
public class Alquileres_DTOMapper implements Function<Alquiler, AlquileresDto> {
    @Override
    public AlquileresDto apply(Alquiler alquiler) {
        return new AlquileresDto(
                alquiler.getId(),
                alquiler.getIdCliente(),
                alquiler.getEstado(),
                alquiler.getFechaHoraRetiro(),
                alquiler.getFechaHoraDevolucion(),
                alquiler.getMonto()
        );
    }
}
