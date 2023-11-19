package tpi_grupo_18.alquileres.servicios.alquileres;

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
import tpi_grupo_18.alquileres.entidades.Alquiler;
import tpi_grupo_18.alquileres.entidades.Estacion;
import tpi_grupo_18.alquileres.entidades.Tarifa;
import tpi_grupo_18.alquileres.repositorios.Alquileres_Repo;
import tpi_grupo_18.alquileres.servicios.EstacionService;
import tpi_grupo_18.alquileres.servicios.tarifas.Tarifas_Servicios;
import tpi_grupo_18.alquileres.utils.HaversineDistanceCalculator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Alquileres_Servicios_Imp implements Alquileres_Servicios{

    private final Alquileres_Repo alquileres_repo;
//    private final Estaciones_Servicios estaciones_servicios;
    private final EstacionService estacionService;
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
        Estacion estacion = this.estacionService.getById(estacionIdRetirar);
        Alquiler alquiler = new Alquiler(
                0,
                client,
                1,
                LocalDateTime.now(),
                null,
                0.0,
                estacion,
                null,
                null);
        return this.alquileres_repo.save(alquiler);
    }

    @Override
    public Alquiler devolver(String client, Long estacionIdDevolucion, Long tarifaId) {
        Alquiler alquiler = this.getByIdClient(client);
        Estacion estacion = this.estacionService.getById(estacionIdDevolucion);
        Tarifa tarifa = this.tarifas_servicios.getById(tarifaId);

        alquiler.setEstado(2);
        alquiler.setEstacionDevolucion(estacion);
        alquiler.setFechaHoraDevolucion(LocalDateTime.now());
        alquiler.setTarifa(tarifa);
        alquiler.setMonto(calcularMonto(alquiler,tarifa));

//        double monto = calcularMonto(alquiler, tarifa);
//
//        if (cambioMoneda(monto, moneda)!=0.0){
//            alquiler.setMonto(cambioMoneda(monto, moneda));
//        }
//        else {
//            alquiler.setMonto(monto);
//        }
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

    @Override
    public Map<String,Object> toHashMap (Alquiler alquiler, String moneda){
        // Crear un mapa para la respuesta
        Map<String, Object> respuesta = new HashMap<>();

        // Agrega información sobre el alquiler
        respuesta.put("id", alquiler.getId());
        respuesta.put("idCliente", alquiler.getIdCliente());
        respuesta.put("estado", alquiler.getEstado());
        respuesta.put("fechaHoraRetiro", alquiler.getFechaHoraRetiro());
        respuesta.put("fechaHoraDevolucion", alquiler.getFechaHoraDevolucion());

        // Agregar el tipo de moneda al mapa si se proporciona
        if (moneda != null && !moneda.isEmpty()) {
            respuesta.put("monto", this.cambioMoneda(alquiler.getMonto(), moneda));
            respuesta.put("tipoMoneda", moneda);
        } else {
            respuesta.put("monto", alquiler.getMonto());
            respuesta.put("tipoMoneda", "ARS");
        }

        // Agrega información sobre la estación de retiro al mapa
        if (alquiler.getEstacionRetiro() != null) {
            Map<String, Object> estacionRetiro = new HashMap<>();
            estacionRetiro.put("estaciones_id", alquiler.getEstacionRetiro().getEstaciones_id());
            estacionRetiro.put("nombre", alquiler.getEstacionRetiro().getNombre());
            estacionRetiro.put("fecha_hora_creacion", alquiler.getEstacionRetiro().getFecha_hora_creacion());
            estacionRetiro.put("latitud", alquiler.getEstacionRetiro().getLatitud());
            estacionRetiro.put("longitud",alquiler.getEstacionRetiro().getLongitud());
            respuesta.put("estacionRetiro", estacionRetiro);
        }

        // Agrega información sobre la estación de devolución al mapa
        if (alquiler.getEstacionDevolucion() != null) {
            Map<String, Object> estacionDevolucion = new HashMap<>();
            estacionDevolucion.put("estaciones_id", alquiler.getEstacionDevolucion().getEstaciones_id());
            estacionDevolucion.put("nombre", alquiler.getEstacionDevolucion().getNombre());
            estacionDevolucion.put("fecha_hora_creacion", alquiler.getEstacionDevolucion().getFecha_hora_creacion());
            estacionDevolucion.put("latitud", alquiler.getEstacionDevolucion().getLatitud());
            estacionDevolucion.put("longitud",alquiler.getEstacionDevolucion().getLongitud());
            respuesta.put("estacionDevolucion", estacionDevolucion);
        }

        // Agrega información sobre la tarifa al mapa
        if (alquiler.getTarifa() != null) {
            Map<String, Object> tarifa = new HashMap<>();
            tarifa.put("tarifa_id", alquiler.getTarifa().getTarifa_id());
            tarifa.put("tipo_tarifa", alquiler.getTarifa().getTipo_tarifa());
            tarifa.put("definicion", alquiler.getTarifa().getDefinicion());
            tarifa.put("dia_semana", alquiler.getTarifa().getDia_semana());
            tarifa.put("dia_mes", alquiler.getTarifa().getDia_mes());
            tarifa.put("mes", alquiler.getTarifa().getMes());
            tarifa.put("anio", alquiler.getTarifa().getAnio());
            tarifa.put("monto_fijo", alquiler.getTarifa().getMonto_fijo());
            tarifa.put("monto_minuto", alquiler.getTarifa().getMonto_minuto());
            tarifa.put("monto_hora", alquiler.getTarifa().getMonto_hora());
            tarifa.put("monto_km", alquiler.getTarifa().getMonto_km());
            respuesta.put("tarifa", tarifa);
        }

        return respuesta;
    }

    private double calcularMonto(Alquiler alquiler, Tarifa tarifa) {

        // Inicializamos las variables internas
        double monto_total_facturado = 0.0;
        double costoMin = 0.0;
        double costoHora = 0.0;
//        double latitud_retiro = this.estacionService.getEstacionById(alquiler.getEstacionRetiroId()).getLatitud();
//        double longitud_retiro = this.estacionService.getEstacionById(alquiler.getEstacionRetiroId()).getLongitud();
//        double latitud_devolucion = this.estacionService.getEstacionById(alquiler.getEstacionDevolucionId()).getLatitud();
//        double longitud_devolucion = this.estacionService.getEstacionById(alquiler.getEstacionDevolucionId()).getLongitud();

        // Calcular costo por kilómetro.
        double distanciaEnKm = HaversineDistanceCalculator.calculateDistance(
                alquiler.getEstacionRetiro().getLatitud(),
                alquiler.getEstacionRetiro().getLongitud(),
                alquiler.getEstacionDevolucion().getLatitud(),
                alquiler.getEstacionDevolucion().getLongitud());
//        double distanciaEnKm = HaversineDistanceCalculator.calculateDistance(
//                latitud_retiro,longitud_retiro,latitud_devolucion,longitud_devolucion
//        );
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
