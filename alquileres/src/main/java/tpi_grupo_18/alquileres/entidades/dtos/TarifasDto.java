package tpi_grupo_18.alquileres.entidades.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class TarifasDto implements Serializable {
    private final long tarifa_id;
    private final int tipo_tarifa;
    private final String definicion;
    private final Integer dia_semana;
    private final Integer dia_mes;
    private final Integer mes;
    private final Integer anio;
    private final double monto_fijo;
    private final double monto_minuto;
    private final double monto_hora;
    private final double monto_km;
}
