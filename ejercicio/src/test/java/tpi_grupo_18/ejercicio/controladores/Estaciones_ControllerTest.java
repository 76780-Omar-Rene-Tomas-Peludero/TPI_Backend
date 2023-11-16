package tpi_grupo_18.ejercicio.controladores;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.repositorios.Alquileres_Repo;
import tpi_grupo_18.ejercicio.repositorios.Estaciones_Repo;
import tpi_grupo_18.ejercicio.servicios.alquileres.Alquileres_Servicios;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Servicios;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Estaciones_ControllerTest {

    private Estaciones_Servicios estaciones_servicios;
    private Estaciones_Repo estaciones_repo;

    @BeforeEach
    public void setup(){
        estaciones_repo = mock(Estaciones_Repo.class);
        estaciones_servicios = mock(Estaciones_Servicios.class);
    }

    @Test
    void testListaEstacion_NotEmpty() {
        // Configuración del mock del repositorio
        List<Estacion> estacionList = Arrays.asList(new Estacion(), new Estacion());
        when(estaciones_repo.findAll()).thenReturn(estacionList);
        Assertions.assertFalse(estacionList.isEmpty());
    }

//    @Test
//    void testEstacio_Existente() {
//        // ID de ejemplo
//        Long idEstacion = (long) 1;
//        Estacion estacion = new Estacion();
//
//        // Configurar el comportamiento del mock para que devuelva un Optional con cualquier valor cuando se llame a findById con el ID especificado
//        when(estaciones_repo.findById(idEstacion)).thenReturn(Optional.of(estacion));
//
//        // Llamar al método getById con el ID de ejemplo
//        Estacion resultado = estaciones_servicios.getById(idEstacion);
//
//        // Verificar que el resultado es igual al Alquiler esperado
//        Assertions.assertEquals(resultado, estacion);
//    }
}