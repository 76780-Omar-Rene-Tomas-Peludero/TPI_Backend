package tpi_grupo_18.ejercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class EjercicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercicioApplication.class, args);
	}

	// RouteLocatorBuilder permite crear rutas y añadir filtros a dichas rutas basadas en ciertas condiciones
	// ademas de posiblemente alterar las peticiones/respuestas de esa ruta

	@Bean
	public RouteLocator estacionesRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Recuperar listado de estaciones
				.route(e -> e
						// Aca detecta si llega una peticion a http://localhost:8081/listaEstaciones, y luego
						// la reenvia a http://localhost:8081/api/tpi/Estaciones/
						.path("/listaEstaciones")
						.filters(f -> f.rewritePath("/listaEstaciones", "/api/tpi/Estaciones/"))
						.uri("http://localhost:8081"))
				// Buscar estación cercana por latitud y longitud
				.route(p -> p
						// http://localhost:8081/estacionCercana?latitud=valor&longitud=valor
						// http://localhost:8081/estacionCercana?latitud=-31.442961123175007&longitud=-64.189333639292
						.path("/estacionCercana")
						.filters(f -> f.rewritePath("/estacionCercana", "/api/tpi/Estaciones/cercana"))
						.uri("http://localhost:8081"))
				// Consultar estación por ID
				.route(r -> r
						// http://localhost:8081/estacionePorId/valor
						.path("/estacionePorId/{id}")
						// al laburar con path la "/" es un caracter especial asi que
						// para evitar la barra inclinada se usa (?<id>.*) para capturar el valor del ID
						// y pasarselo con $ a la funcion del controlador.
						.filters(f -> f.rewritePath("/estacionePorId/(?<id>.*)", "/api/tpi/Estaciones/${id}"))
						.uri("http://localhost:8081"))
				// Añadir una nueva estación
				.route(a -> a
						// http://localhost:8080/addEstacion
						.method(HttpMethod.POST)
						.and()
						.path("/addEstacion")
						.filters(f -> f.rewritePath("/addEstacion", "/api/tpi/Estaciones/"))
						.uri("http://localhost:8081"))

				// EN TEORIA LOS SIGUIENTES ROUTEOS FUNCIONAN PERO POR CUESTIONES DEL SPRING SECURITY NO LO PERMITE.....

				// Actualizar una estación existente
				.route(u -> u
						// http://localhost:8080/updateEstacion
						.method(HttpMethod.PUT)
						.and()
						.path("/updateEstacion")
						.filters(f -> f.rewritePath("/updateEstacion", "/api/tpi/Estaciones/"))
						.uri("http://localhost:8081"))
				// Borrar una estación existente
				.route(d -> d
						// http://localhost:8080/deleteEstacion/1
						.method(HttpMethod.DELETE)
						.and()
						.path("/deleteEstacion/{id}")
						.filters(f -> f.rewritePath("/deleteEstacion/(?<id>.*)", "/api/tpi/Estaciones/${id}"))
						.uri("http://localhost:8081"))
				.build();
	}

//	@Bean
//	public RouteLocator alquileresRoutes(RouteLocatorBuilder builder){
//
//	}

	// Esto funciona....
	@Bean
	public RouteLocator configurarRutas(RouteLocatorBuilder builder) {
		return builder.routes()
				// Si llega una petición a http://localhost:8082/echo, la misma se envía a https://postman-echo.com/get
				.route(p -> p
						.path("/echo")
						.filters(f -> f.rewritePath("/echo", "/get"))
						.uri("https://postman-echo.com")
				)
				.build();
	}

	// Para probar funcionamientos se usa el Swagger --> http://localhost:8080/swagger-ui/index.html
}
