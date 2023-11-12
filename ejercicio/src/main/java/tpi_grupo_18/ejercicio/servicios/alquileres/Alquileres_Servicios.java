package tpi_grupo_18.ejercicio.servicios.alquileres;

import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.servicios.Servicios_grl;

public interface Alquileres_Servicios extends Servicios_grl<Alquiler,Long> {
    Alquiler getByIdClient(String client);
    Alquiler retirar(String client, Long estacionIdRetirar);
    Alquiler devolver(String client, Long estacionIdDevolucion, Long tarifaId);

    Alquiler devolverr(String client, Long estacionIdDevolucion, Long tarifaId, String moneda);

}
