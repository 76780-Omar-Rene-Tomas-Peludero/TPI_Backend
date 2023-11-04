package tpi_grupo_18.ejercicio.servicios.alquileres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.entidades.dtos.TarifasDto;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_DTOMapper;
import tpi_grupo_18.ejercicio.servicios.tarifas.Tarifas_DTOMapper;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class Alquileres_DTOMapper implements Function<Alquiler, AlquileresDto> {
    private final Tarifas_DTOMapper tarifasDtoMapper;
    private final Estaciones_DTOMapper estacionesDtoMapper;
    @Override
    public AlquileresDto apply(Alquiler alquiler) {
        Optional<EstacionesDto> estacionRetiro = Stream.of(alquiler.getEstacionRetiro()).map(estacionesDtoMapper).findFirst();
        Optional<EstacionesDto> estacionDevolucion = Stream.of(alquiler.getEstacionDevolucion()).map(estacionesDtoMapper).findFirst();
        Optional<TarifasDto> tarifas = Stream.of(alquiler.getTarifa()).map(tarifasDtoMapper).findFirst();

        return new AlquileresDto(
                alquiler.getId(),
                alquiler.getIdCliente(),
                alquiler.getEstado(),
                alquiler.getFechaHoraRetiro(),
                alquiler.getFechaHoraDevolucion(),
                alquiler.getMonto(),
                estacionRetiro.get(),
                estacionDevolucion.get(),
                tarifas.get()
        );
    }
}
