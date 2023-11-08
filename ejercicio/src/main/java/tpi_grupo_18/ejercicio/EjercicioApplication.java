package tpi_grupo_18.ejercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EjercicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercicioApplication.class, args);
	}

	// RouteLocatorBuilder permite crear rutas y añadir filtros a dichas rutas basadas en ciertas condiciones
	// ademas de posiblemente alterar las peticiones/respuestas de esa ruta
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.build();
	}

	// Para probar funcionamientos se usa el Swagger --> http://localhost:8080/swagger-ui/index.html
}
