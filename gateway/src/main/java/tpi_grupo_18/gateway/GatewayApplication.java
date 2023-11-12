package tpi_grupo_18.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator estacionesRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Recuperar listado de estaciones
				.route(e -> e
						// Aca detecta si llega una peticion a http://localhost:8081/listaEstaciones, y luego
						// la reenvia a http://localhost:8081/api/tpi/Estaciones/
						.path("/listaEstaciones")
						.filters(f -> f.rewritePath("/listaEstaciones", "/api/tpi/Estaciones/"))
						.uri("http://localhost:8080"))
				// Buscar estación cercana por latitud y longitud
				.route(p -> p
						// http://localhost:8081/estacionCercana?latitud=valor&longitud=valor
						// http://localhost:8081/estacionCercana?latitud=-31.442961123175007&longitud=-64.189333639292
						.path("/estacionCercana")
						.filters(f -> f.rewritePath("/estacionCercana", "/api/tpi/Estaciones/cercana"))
						.uri("http://localhost:8080"))
				// Consultar estación por ID
				.route(r -> r
						// http://localhost:8081/estacionePorId/valor
						.path("/estacionePorId/{id}")
						// al laburar con path la "/" es un caracter especial asi que
						// para evitar la barra inclinada se usa (?<id>.*) para capturar el valor del ID
						// y pasarselo con $ a la funcion del controlador.
						.filters(f -> f.rewritePath("/estacionePorId/(?<id>.*)", "/api/tpi/Estaciones/${id}"))
						.uri("http://localhost:8080"))
				// Añadir una nueva estación
				.route(a -> a
						// http://localhost:8080/addEstacion
						.method(HttpMethod.POST)
						.and()
						.path("/addEstacion")
						.filters(f -> f.rewritePath("/addEstacion", "/api/tpi/Estaciones/"))
						.uri("http://localhost:8080"))

				// EN TEORIA LOS SIGUIENTES ROUTEOS FUNCIONAN PERO POR CUESTIONES DEL SPRING SECURITY NO LO PERMITE.....

				// Actualizar una estación existente
				.route(u -> u
						// http://localhost:8080/updateEstacion
						.method(HttpMethod.PUT)
						.and()
						.path("/updateEstacion")
						.filters(f -> f.rewritePath("/updateEstacion", "/api/tpi/Estaciones/"))
						.uri("http://localhost:8080"))
				// Borrar una estación existente
				.route(d -> d
						// http://localhost:8080/deleteEstacion/1
						.method(HttpMethod.DELETE)
						.and()
						.path("/deleteEstacion/{id}")
						.filters(f -> f.rewritePath("/deleteEstacion/(?<id>.*)", "/api/tpi/Estaciones/${id}"))
						.uri("http://localhost:8080"))
				.build();
	}

	@Bean
	public RouteLocator alquileresRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Recuperar listado de alquileres
				.route(a -> a
						// http://localhost:8081/listaAlquileres
						.path("/listaAlquileres")
						.filters(f -> f.rewritePath("/listaAlquileres", "/api/tpi/Alquileres/"))
						.uri("http://localhost:8080"))
				// Consultar alquiler por ID
				.route(r -> r
						// http://localhost:8081/alquilerPorId/valor
						.path("/alquilerPorId/{id}")
						.filters(f -> f.rewritePath("/alquilerPorId/(?<id>.*)", "/api/tpi/Alquileres/${id}"))
						.uri("http://localhost:8080"))
				// Ruta para retirar un alquiler
				.route(r -> r
						// http://localhost:8080/retirar
						.path("/retirar")
						.and()
						.method(HttpMethod.POST)
						.filters(f -> f.rewritePath("/retirar", "/api/tpi/Alquileres/retirar"))
						.uri("http://localhost:8080"))
				// Ruta para devolver un alquiler
				.route(r -> r
						// http://localhost:8080/devolver
						.path("/devolver")
						.and()
						.method(HttpMethod.PUT)
						.filters(f -> f.rewritePath("/devolver", "/api/tpi/Alquileres/devolver"))
						.uri("http://localhost:8080"))
				// Ruta para devolver un alquiler
				.route(r -> r
						// http://localhost:8080/devolver
						.path("/devolverr")
						.and()
						.method(HttpMethod.PUT)
						.filters(f -> f.rewritePath("/devolverr", "/api/tpi/Alquileres/devolverr"))
						.uri("http://localhost:8080"))
				// Ruta para la conversión de moneda
				.route(r -> r
						.path("/pasarMoneda")
						.and()
						.method(HttpMethod.POST)
						.filters(f -> f.rewritePath("/pasarMoneda", "/convertir"))
						.uri("http://34.82.105.125:8080"))
				.build();
	}
	// Para probar funcionamientos se usa el Swagger --> http://localhost:8080/swagger-ui/index.html
	//https://labsys.frc.utn.edu.ar/aim/realms/backend-tps/protocol/openid-connect/auth?client_id=account-console
	// &redirect_uri=https%3A%2F%2Flocalhost%2Faim%2Frealms%2Fbackend-tps%2Faccount%2F%23%2F&state=39b5df03-5860-4bec-8bcb-b37243da3e72&response_mode=fragment&response_type=code&scope=openid&nonce=db46fdea-ec4a-4f55-8001-c056d9d65324&code_challenge=8UfvrxvFWIxYXq00KcVfhfL8XZWYR2tZLtkkEl_SNoU&code_challenge_method=S256
}
