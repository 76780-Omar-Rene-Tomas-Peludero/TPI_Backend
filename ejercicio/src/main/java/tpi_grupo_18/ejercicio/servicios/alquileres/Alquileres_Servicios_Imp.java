package tpi_grupo_18.ejercicio.servicios.alquileres;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.repositorios.Alquileres_Repo;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Servicios;
import tpi_grupo_18.ejercicio.servicios.tarifas.Tarifas_Servicios;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
    public Alquiler devolver(String client, Long estacionIdDevolucion, Long tarifaId, String moneda) {
        Alquiler alquiler = this.getByIdClient(client);
        Estacion estacion = this.estaciones_servicios.getById(estacionIdDevolucion);
        Tarifa tarifa = this.tarifas_servicios.getById(tarifaId);

        alquiler.setEstado(2);
        alquiler.setEstacionDevolucion(estacion);
        alquiler.setFechaHoraDevolucion(LocalDateTime.now());
        alquiler.setTarifa(tarifa);

        double monto = calcularMonto(alquiler, tarifa);

        if (cambioMoneda(monto, moneda)!=0.0){
            alquiler.setMonto(cambioMoneda(monto, moneda));
        }
        else {
            alquiler.setMonto(monto);
        }
        return this.alquileres_repo.save(alquiler);
    }

    private double cambioMoneda(double monto , String moneda){
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://34.82.105.125:8080/convertir");

        List<String> listaMonedas = new ArrayList<>();
        listaMonedas.add("EUR");
        listaMonedas.add("CLP");
        listaMonedas.add("BRL");
        listaMonedas.add("COP");
        listaMonedas.add("PEN");
        listaMonedas.add("GBP");
        listaMonedas.add("USD");
        // Verifica que las opciones ingresadas son correctas, caso contrario devolvera siempre 0.0
        if (listaMonedas.contains(moneda)) {
            // Configurar el cuerpo de la solicitud POST
            String requestBody = "{\"moneda_destino\":\""+moneda+"\",\"importe\":"+monto+"}";
            StringEntity requestEntity = new StringEntity(requestBody, "UTF-8");
            httpPost.setEntity(requestEntity);
            httpPost.setHeader("Content-type", "application/json");

            try {
                // Enviar la solicitud POST
                HttpResponse response = httpClient.execute(httpPost);
                // Obtener la respuesta
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                JSONObject jsonObject = new JSONObject(responseBody);

                return jsonObject.getDouble("importe");
            } catch (Exception e) {
                e.printStackTrace();
                return 0.0;
            }
        } else {
            return 0.0;
        }
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
