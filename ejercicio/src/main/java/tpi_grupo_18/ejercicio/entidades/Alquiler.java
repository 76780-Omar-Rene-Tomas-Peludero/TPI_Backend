package tpi_grupo_18.ejercicio.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.entidades.dtos.TarifasDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALQUILERES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
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
    @JoinColumn(name = "ESTACION_RETIRO", insertable = false, updatable = false)
    private Estacion estacionRetiro;  // ID de la estación donde se retiró la bicicleta

    @ManyToOne
    @JoinColumn(name = "ESTACION_DEVOLUCION", insertable = false, updatable = false)
    private Estacion estacionDevolucion; // ID de la estación donde se devolvió la bicicleta

    @ManyToOne
    @JoinColumn(name = "ID_TARIFA", insertable = false, updatable = false)
    private Tarifa tarifa;  // ID de la tarifa que se utilizó para calcular el monto del alquiler
}
