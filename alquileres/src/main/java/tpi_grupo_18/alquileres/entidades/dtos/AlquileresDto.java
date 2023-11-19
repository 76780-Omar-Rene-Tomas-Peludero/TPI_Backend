package tpi_grupo_18.alquileres.entidades.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AlquileresDto implements Serializable {
    private final int id;
    private final String idCliente;
    private final int estado;
    private final LocalDateTime fechaHoraRetiro;
    private final LocalDateTime fechaHoraDevolucion;
    private final double monto;
//    private final EstacionesDto estacionRetiro;
//    private final EstacionesDto estacionDevolucion;
    private final TarifasDto tarifa;
}
