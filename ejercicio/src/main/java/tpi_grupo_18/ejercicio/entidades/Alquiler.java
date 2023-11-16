package tpi_grupo_18.ejercicio.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALQUILERES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alquiler {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ALQUILERES")
    @TableGenerator(name = "ALQUILERES", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "ALQUILERES", initialValue = 1, allocationSize = 1
    )
    private int id;  // Identificador del alquiler

    @Column(name = "ID_CLIENTE")
    private String idCliente; // Identificador del cliente que realizó el alquiler

    @Column(name = "ESTADO")
    private int estado; // 1-Iniciado, 2-Finalizado

    @Column(name = "FECHA_HORA_RETIRO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaHoraRetiro; // Fecha y Hora del retiro de la bicicleta

    @Column(name = "FECHA_HORA_DEVOLUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaHoraDevolucion; // Fecha y Hora en la que se devolvió la bicicleta

    @Column(name = "MONTO")
    private double monto;  // Monto cobrado por el alquiler

    @ManyToOne
    @JoinColumn(name = "ESTACION_RETIRO")
    private Estacion estacionRetiro;  // ID de la estación donde se retiró la bicicleta

    @ManyToOne
    @JoinColumn(name = "ESTACION_DEVOLUCION")
    private Estacion estacionDevolucion; // ID de la estación donde se devolvió la bicicleta

    @ManyToOne
    @JoinColumn(name = "ID_TARIFA")
    private Tarifa tarifa;  // ID de la tarifa que se utilizó para calcular el monto del alquiler
}
