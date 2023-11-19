package tpi_grupo_18.estaciones.entidades;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EstacionesDto implements Serializable {
    private final long estaciones_id;
    private final String nombre;
    private final LocalDateTime fecha_hora_creacion;
    private final BigDecimal latitud;
    private final BigDecimal longitud;
}
