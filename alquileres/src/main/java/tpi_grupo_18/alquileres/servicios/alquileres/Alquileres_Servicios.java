package tpi_grupo_18.alquileres.servicios.alquileres;

import tpi_grupo_18.alquileres.entidades.Alquiler;
import tpi_grupo_18.alquileres.servicios.Servicios_grl;

import java.util.Map;

public interface Alquileres_Servicios extends Servicios_grl<Alquiler,Long> {
    Alquiler getByIdClient(String client);
    Alquiler retirar(String client, Long estacionIdRetirar);
    Alquiler devolver(String client, Long estacionIdDevolucion, Long tarifaId);
    Map<String,Object> toHashMap (Alquiler alquiler, String moneda);
}
