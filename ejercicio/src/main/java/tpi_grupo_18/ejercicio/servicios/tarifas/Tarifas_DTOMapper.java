package tpi_grupo_18.ejercicio.servicios.tarifas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.entidades.dtos.TarifasDto;
import java.util.function.Function;

@Service
public class Tarifas_DTOMapper implements Function<Tarifa, TarifasDto> {
    @Override
    public TarifasDto apply(Tarifa tarifa) {
        return new TarifasDto(
                tarifa.getTarifa_id(),
                tarifa.getTipo_tarifa(),
                tarifa.getDefinicion(),
                tarifa.getDia_semana(),
                tarifa.getDia_mes(),
                tarifa.getMes(),
                tarifa.getAnio(),
                tarifa.getMonto_fijo(),
                tarifa.getMonto_minuto(),
                tarifa.getMonto_hora(),
                tarifa.getMonto_km()
        );
    }
}
