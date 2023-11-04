package tpi_grupo_18.ejercicio.servicios.alquileres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.entidades.dtos.TarifasDto;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_DTOMapper;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Mapper;
import tpi_grupo_18.ejercicio.servicios.tarifas.Tarifas_DTOMapper;
import tpi_grupo_18.ejercicio.servicios.tarifas.Tarifas_Mapper;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class Alquileres_Mapper implements Function<AlquileresDto, Alquiler> {
    private final Tarifas_Mapper tarifasMapper;
    private final Estaciones_Mapper estacionesMapper;

    @Override
    public Alquiler apply(AlquileresDto alquileresDto) {
        Optional<Estacion> estacionRetiro = Stream.of(alquileresDto.getEstacionRetiro()).map(estacionesMapper).findFirst();
        Optional<Estacion> estacionDevolucion = Stream.of(alquileresDto.getEstacionDevolucion()).map(estacionesMapper).findFirst();
        Optional<Tarifa> tarifas = Stream.of(alquileresDto.getTarifa()).map(tarifasMapper).findFirst();

        return new Alquiler(
                alquileresDto.getId(),
                alquileresDto.getIdCliente(),
                alquileresDto.getEstado(),
                alquileresDto.getFechaHoraRetiro(),
                alquileresDto.getFechaHoraDevolucion(),
                alquileresDto.getMonto(),
                estacionRetiro.get(),
                estacionDevolucion.get(),
                tarifas.get()
        );
    }
}
