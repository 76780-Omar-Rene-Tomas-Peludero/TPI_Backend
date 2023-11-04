package tpi_grupo_18.ejercicio.servicios.alquileres;

import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.servicios.Servicios_grl;

public interface Alquileres_Servicios extends Servicios_grl<AlquileresDto,Long> {
    AlquileresDto retirar(AlquileresDto entity, Long estacionIdRetirar);
    AlquileresDto devolver(AlquileresDto entity, Long estacionIdDevolucion, Long tarifaId);
}
