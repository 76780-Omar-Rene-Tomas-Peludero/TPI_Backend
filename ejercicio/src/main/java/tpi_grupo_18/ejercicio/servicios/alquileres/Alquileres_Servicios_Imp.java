package tpi_grupo_18.ejercicio.servicios.alquileres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.entidades.dtos.AlquileresDto;
import tpi_grupo_18.ejercicio.entidades.dtos.EstacionesDto;
import tpi_grupo_18.ejercicio.repositorios.Alquileres_Repo;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Servicios;
import tpi_grupo_18.ejercicio.servicios.tarifas.Tarifas_Servicios;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static tpi_grupo_18.ejercicio.utils.HaversineDistanceCalculator.calculateDistance;

@Service
@RequiredArgsConstructor
public class Alquileres_Servicios_Imp implements Alquileres_Servicios{

    private final Alquileres_Repo alquileres_repo;
    private final Estaciones_Servicios estaciones_servicios;
    private final Tarifas_Servicios tarifas_servicios;

    @Override
    public Alquiler add(Alquiler entity) {
        return null;
    }

    @Override
    public Alquiler update(Alquiler entity) {
        return null;
    }

    @Override
    public Alquiler delete(Long id) {
        Alquiler alquiler = this.getById(id);
        this.alquileres_repo.delete(alquiler);
        return alquiler;
    }

    @Override
    public Alquiler getById(Long id) {
        return this.alquileres_repo.findById(id).orElseThrow();
    }

    @Override
    public List<Alquiler> getAll() {
        return this.alquileres_repo.findAll();
    }

    @Override
    public Alquiler getByIdClient(String client) {
        return this.alquileres_repo.findByIdCliente(client);
    }

    @Override
    public Alquiler retirar(String client, Long estacionIdRetirar) {
        Estacion estacion = this.estaciones_servicios.getById(estacionIdRetirar);

        Alquiler alquiler = new Alquiler(
                0,
                client,
                1,
                LocalDateTime.now(),
                null,
                0.0,
                estacion,
                null,
                null
        );

        return this.alquileres_repo.save(alquiler);
    }

    @Override
    public Alquiler devolver(String client, Long estacionIdDevolucion, Long tarifaId) {
        Alquiler alquiler = this.getByIdClient(client);
        Estacion estacion = this.estaciones_servicios.getById(estacionIdDevolucion);
        Tarifa tarifa = this.tarifas_servicios.getById(tarifaId);

        alquiler.setEstado(2);
        alquiler.setEstacionDevolucion(estacion);
        alquiler.setFechaHoraDevolucion(LocalDateTime.now());
        alquiler.setMonto(calcularMonto(alquiler, tarifa));
        alquiler.setTarifa(tarifa);

        return this.alquileres_repo.save(alquiler);
    }

    private double calcularMonto(Alquiler alquiler, Tarifa tarifa) {

        // Inicializamos las variables internas
        double monto_total_facturado = 0.0;
        double costoMin = 0.0;
        double costoHora = 0.0;

        // Calcular costo por kilómetro.
        double distanciaEnKm = calculateDistance(
                alquiler.getEstacionRetiro().getLatitud(),
                alquiler.getEstacionRetiro().getLongitud(),
                alquiler.getEstacionDevolucion().getLatitud(),
                alquiler.getEstacionDevolucion().getLongitud());
        double costoPorKm = tarifa.getMonto_km() * distanciaEnKm;

        //Calculamos los minutos y horas de diferencia entre el retiro y la devolucion.
        long minutos = ChronoUnit.MINUTES.between(alquiler.getFechaHoraRetiro(),alquiler.getFechaHoraDevolucion());
        long horas = ChronoUnit.HOURS.between(alquiler.getFechaHoraRetiro(),alquiler.getFechaHoraDevolucion());

        // Asigna el valor dependiendo del tiempo....
        if (minutos < 31 ){
            // Calculamos el costo por minuto
            costoMin = tarifa.getMonto_minuto() * minutos;
        } else {
            // Calculamos el costo por hora
            costoHora = tarifa.getMonto_hora() * horas;
        }

        // Calculamos el total...
        monto_total_facturado = tarifa.getMonto_fijo() + costoPorKm + costoMin + costoHora;

        return monto_total_facturado;
    }
}
