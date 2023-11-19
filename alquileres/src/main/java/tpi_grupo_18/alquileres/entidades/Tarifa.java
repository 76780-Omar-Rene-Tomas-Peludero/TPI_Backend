package tpi_grupo_18.alquileres.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TARIFAS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "TARIFAS")
    @TableGenerator(name = "TARIFAS", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "TARIFAS", initialValue = 1, allocationSize = 1
    )
    private long tarifa_id;   // ID: Identificador de la tarifa

    @Column(name = "TIPO_TARIFA")
    private int tipo_tarifa;  //  1 - Tarifa Normal, 2 - Tarifa de Descuento

    @Column(name = "DEFINICION")
    private String definicion; // ‘S’ - Definida por el día de la semana, ‘C’ definida por día, mes y año.

    @Column(name = "DIA_SEMANA")  // Si la tarifa se define por día de la semana, esta columna indica el día,
    private Integer dia_semana;  // comenzando por el Lunes (Valor: 1) y terminando en Domingo (Valor: 7)

    @Column(name = "DIA_MES")
    private Integer dia_mes;  // Si la tarifa se define por día, mes y año, esta columna indica el día del mes.

    @Column(name = "MES")
    private Integer mes; // Si la tarifa se define por día, mes y año, esta columna indica el mes.

    @Column(name = "ANIO")
    private Integer anio; // Si la tarifa se define por día, mes y año, esta columna indica el año.

    @Column(name = "MONTO_FIJO_ALQUILER")
    private double monto_fijo; // El monto fijo a cobrar por iniciar el alquiler

    @Column(name = "MONTO_MINUTO_FRACCION")
    private double monto_minuto; // El monto a cobrar por cada minuto extra cuando se tarifa fraccionado (antes del minuto 31)

    @Column(name = "MONTO_HORA")
    private double monto_hora; // El monto a cobrar por cada hora completa de alquiler

    @Column(name = "MONTO_KM")
    private double monto_km; // El monto a cobrar por cada KM que separa la estación de retiro y la de devolución

    @JsonIgnore
    @OneToMany(mappedBy = "tarifa", fetch = FetchType.LAZY)
    public List<Alquiler> alquilerList;
}
