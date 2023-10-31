package tpi_grupo_18.ejercicio.servicios.tarifas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.entidades.dtos.TarifasDto;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class Tarifas_Mapper implements Function<TarifasDto, Tarifa> {
    @Override
    public Tarifa apply(TarifasDto tarifasDto) {
        return new Tarifa(
                tarifasDto.getTarifa_id(),
                tarifasDto.getTipo_tarifa(),
                tarifasDto.getDefinicion(),
                tarifasDto.getDia_semana(),
                tarifasDto.getDia_mes(),
                tarifasDto.getMes(),
                tarifasDto.getAnio(),
                tarifasDto.getMonto_fijo(),
                tarifasDto.getMonto_minuto(),
                tarifasDto.getMonto_hora(),
                tarifasDto.getMonto_km(),
                new ArrayList<>()
        );
    }
}
