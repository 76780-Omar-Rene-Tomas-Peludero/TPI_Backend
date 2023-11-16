package tpi_grupo_18.ejercicio.controladores;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;
import tpi_grupo_18.ejercicio.entidades.Alquiler;
import tpi_grupo_18.ejercicio.entidades.Estacion;
import tpi_grupo_18.ejercicio.repositorios.Alquileres_Repo;
import tpi_grupo_18.ejercicio.servicios.alquileres.Alquileres_Servicios;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tpi_grupo_18.ejercicio.servicios.estaciones.Estaciones_Servicios;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
class Alquileres_ControllerTest {

    private Alquileres_Servicios alquileres_servicios;
    private Alquileres_Repo alquileres_repo;
    private Estaciones_Servicios estaciones_servicios;

    @BeforeEach
    public void setup(){
        alquileres_repo = mock(Alquileres_Repo.class);
        alquileres_servicios = mock(Alquileres_Servicios.class);
        estaciones_servicios = mock(Estaciones_Servicios.class);
    }

    @Test
    public void testListaAlquiler_NotEmpty(){
        // Configuración del mock del repositorio
        List<Alquiler> alquileresList = Arrays.asList(new Alquiler(), new Alquiler());
        when(alquileres_repo.findAll()).thenReturn(alquileresList);
        Assertions.assertFalse(alquileresList.isEmpty());
    }

    @Test
    public void testAlquiler_Existente(){
        // Crear un Alquiler esperado con valores específicos
        Alquiler alquilerEsperado = new Alquiler(
                0,
                "Mockito",
                1,
                LocalDateTime.now(),
                null,
                0.0,
                estaciones_servicios.getById((long)1),
                null,
                null
        );
        // Configurar el mock para getById; cuando se llama con (long)1, devuelve alquilerEsperado
        when(alquileres_servicios.getById((long)1)).thenReturn(alquilerEsperado);
        // Llamar a getById con (long)1 y almacenar el resultado en alquiler
        Alquiler alquiler = alquileres_servicios.getById((long)1);
        // Verificar que el resultado es igual al Alquiler esperado
        Assertions.assertEquals(alquilerEsperado, alquiler);
    }

    @Test
    public void testAlquiler_Inexistente(){
        // Crear un Alquiler esperado con valores específicos
        Alquiler alquilerEsperado = new Alquiler(
                0,
                "Tomas",
                1,
                LocalDateTime.now(),
                null,
                0.0,
                estaciones_servicios.getById((long)1),
                null,
                null
        );
        // Configuración del mock para simular que no se encuentra ningún alquiler
        when(alquileres_servicios.getById((long)1)).thenReturn(null);
        // Ejecutar el método que estás probando
        Alquiler alquiler = alquileres_servicios.getById((long)1);
        // Verificar que el resultado es null
        Assertions.assertNull(alquiler);
    }

//    @Test
//    public void retirarTest() {
//        // Datos de prueba
//        String cliente = "cliente";
//        Long estacionIdRetirar = 1L;
//
//        Alquiler alquilerEspecifico = new Alquiler(
//                0,
//                cliente,
//                1,
//                LocalDateTime.now(),
//                null,
//                0.0,
//                estaciones_servicios.getById(estacionIdRetirar),
//                null,
//                null
//        );
//
//        // Llamada al método que quieres probar
//        alquileres_servicios.retirar(cliente, estacionIdRetirar);
//
//        // Print actual and expected Alquiler objects for debugging
//        System.out.println("Actual Alquiler: " + alquilerEspecifico.toString());
//        Mockito.verify(alquileres_repo).save(eq(alquilerEspecifico));
//    }
//    @InjectMocks
//    private Alquileres_Controller controlador;
//
//    @Mock
//    private Alquileres_Servicios alquileresServiciosMock;
//
//    @Test
//    public void testGetAll() {
//        // Configuración del mock
//        List<Alquiler> alquileresDtoList = Mockito.mock(ArrayList.class);
//        when(alquileresServiciosMock.getAll()).thenReturn(alquileresDtoList);
//
//        // Ejecutar el método que estás probando
//        ResponseEntity<List<Alquiler>> response = controlador.getAll();
//
//        // Verificar resultados
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(alquileresDtoList, response.getBody());
//    }
//
//    @Test
//    public void testGetById() {
//        // Configuración del mock
//        long id = 1; // Reemplaza con el ID que estás utilizando en tu test
//        Alquiler alquiler = Mockito.mock(Alquiler.class);
//        when(alquileresServiciosMock.getById(id)).thenReturn(alquiler);
//
//        // Ejecutar el método que estás probando
//        ResponseEntity<Alquiler> response = controlador.getById(id);
//
//        // Verificar resultados
//        assertEquals(HttpStatus.OK, response.getStatusCode()); // Puedes ajustar el código de estado según tu preferencia
//        assertEquals(alquiler, response.getBody());
//    }
//
//    @Test
//    public void testDelete() {
//        // Configuración del mock
//        long id = 1; // Reemplaza con el ID que estás utilizando en tu test
//        Alquiler alquilerMock = Mockito.mock(Alquiler.class);
//        when(alquileresServiciosMock.delete(id)).thenReturn(alquilerMock);
//
//        // Ejecutar el método que estás probando
//        ResponseEntity<Alquiler> response = controlador.delete(id);
//
//        // Verificar resultados
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertEquals(alquilerMock, response.getBody());
//    }
//
//    @Test
//    public void testRetirar() {
//        // Configuración del mock
//        String client = "clienteEjemplo";
//        long estacionId = 1;
//        Alquiler alquilerMock = Mockito.mock(Alquiler.class);
//        when(alquileresServiciosMock.retirar(client, estacionId)).thenReturn(alquilerMock);
//
//        // Ejecutar el método que estás probando
//        ResponseEntity<Alquiler> response = controlador.retirar(client, estacionId);
//
//        // Verificar resultados
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(alquilerMock, response.getBody());
//    }
//
//    @Test
//    public void testDevolver() {
//        // Configuración del mock
//        String client = "clienteEjemplo";
//        long estacionId = 1;
//        long tarifaId = 2;
//        String moneda = "USD";
//        Alquiler alquilerMock = Mockito.mock(Alquiler.class);
//        when(alquileresServiciosMock.devolver(client, estacionId, tarifaId, moneda)).thenReturn(alquilerMock);
//
//        // Ejecutar el método que estás probando
//        ResponseEntity<Alquiler> response = controlador.devolver(client, estacionId, tarifaId, moneda);
//
//        // Verificar resultados
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertEquals(alquilerMock, response.getBody());
//    }
}
