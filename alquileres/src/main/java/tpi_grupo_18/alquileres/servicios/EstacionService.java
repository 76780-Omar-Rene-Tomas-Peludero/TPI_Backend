package tpi_grupo_18.alquileres.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tpi_grupo_18.alquileres.entidades.Estacion;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstacionService implements Servicios_grl<Estacion, Long> {

    private String estacionesApiUrl = "http://localhost:8080/api/tpi/Estaciones";  // URL de la API de Estaciones
    private final ObjectMapper objectMapper; // Necesitarás la biblioteca Jackson ObjectMapper
    private final RestTemplate restTemplate;

    @Override
    public Estacion add(Estacion entity) {
        return null;
    }

    @Override
    public Estacion update(Estacion entity) {
        return null;
    }

    @Override
    public Estacion delete(Long aLong) {
        return null;
    }

    @Override
    public Estacion getById(Long id) {
        // La URL completa del endpoint
        String url = estacionesApiUrl +"/"+ id;
        // Realizar la solicitud GET
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,null,String.class);
        // Verificar si la solicitud fue exitosa
        if (response.getStatusCode().equals(HttpStatus.FOUND)){
            try {
                // Obtener el cuerpo de la respuesta
                String response_body = response.getBody();
                Estacion estacion = objectMapper.readValue(response_body, Estacion.class);
                return estacion;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Estacion> getAll() {
        return null;
    }
}
