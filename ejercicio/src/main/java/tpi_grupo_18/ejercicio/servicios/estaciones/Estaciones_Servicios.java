package tpi_grupo_18.ejercicio.servicios.estaciones;

import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.servicios.Servicios_grl;

public interface Estaciones_Servicios extends Servicios_grl<Estacion, Long> {
    Estacion getByLongAndLat(double Longitud, double Latitud);
    Estacion update(Long id, String nombre, Double latitud, Double longitud);
}
