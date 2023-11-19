package tpi_grupo_18.estaciones.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ESTACIONES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estacion {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ESTACIONES")
    @TableGenerator(name = "ESTACIONES", table = "sqlite_sequence",
                    pkColumnName = "name", valueColumnName = "seq",
                    pkColumnValue = "ESTACIONES", initialValue = 1, allocationSize = 1
    )
    private long estaciones_id; // Identificador de la estación

    @Column(name = "NOMBRE")
    private String nombre; // Nombre de la estación

    @Column(name = "FECHA_HORA_CREACION")
    private LocalDateTime fecha_hora_creacion; // Fecha y Hora en la que se creó la estación en el sistema

    @Column(name = "LATITUD")
    private double latitud; // Latitud donde se encuentra ubicada la estación

    @Column(name = "LONGITUD")
    private double longitud; // Longitud donde se encuentra ubicada la estación

//    @JsonIgnore
//    @OneToMany(mappedBy = "estacionRetiro", fetch = FetchType.LAZY)
//    public List<Alquiler> retiroList;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "estacionDevolucion", fetch = FetchType.LAZY)
//    public List<Alquiler> devoList;
}
