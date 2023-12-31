package tpi_grupo_18.estaciones.servicios.estaciones;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.estaciones.entidades.Estacion;
import tpi_grupo_18.estaciones.repositorios.Estaciones_Repo;
import java.util.List;
import static tpi_grupo_18.estaciones.utils.HaversineDistanceCalculator.calculateDistance;

@Service
@RequiredArgsConstructor
public class Estaciones_Servicios_Imp implements Estaciones_Servicios {

    private final Estaciones_Repo estaciones_repo;

    @Override
    public Estacion add(Estacion entity) {
        return this.estaciones_repo.save(entity);
    }

    @Override
    public Estacion update(Estacion entity) {
        return null;
    }

    @Override
    public Estacion delete(Long id) {
        Estacion estacion = this.getById(id);
        this.estaciones_repo.delete(estacion);
        return estacion;
    }

    @Override
    public Estacion getById(Long id) {
        return this.estaciones_repo.findById(id).orElseThrow();
    }

    @Override
    public List<Estacion> getAll() {
        return this.estaciones_repo.findAll();
    }

    @Override
    public Estacion getByLongAndLat(double Longitud, double Latitud) {

        // Inicializamos la variable de almacenamiento...
        double menorDistancia = 0.0;
        boolean primer_valor_checkeado = false;
        long id_menorDistancia = 0;

        // Almacenamos una lista de todas las estaciones existentes...
        List<Estacion> estacionList = this.getAll();

        for (Estacion estacion : estacionList) {
            double distanciaKm = calculateDistance(Latitud,Longitud,estacion.getLatitud(),estacion.getLongitud());
            // Asigno el menor valor posible inicial, el cual es el primero que encuentre...
            if (!primer_valor_checkeado){
                // Esto se ejecuta 1 vez...
                primer_valor_checkeado = true;
                menorDistancia = distanciaKm;
            }
            if (distanciaKm < menorDistancia){
                // Si encuentra una distanca menor guarda su id
                // y obviamente almacena la menor distancia para futuras comparaciones
                menorDistancia = distanciaKm;
                id_menorDistancia = estacion.getEstaciones_id();
            }
        }
        return this.getById(id_menorDistancia);
    }

    @Override
    public Estacion update(Long id, String nombre, Double latitud, Double longitud) {
        Estacion exist = this.getById(id);
        exist.setNombre(nombre);
        exist.setLatitud(latitud);
        exist.setLongitud(longitud);

        return this.estaciones_repo.save(exist);
    }
}
