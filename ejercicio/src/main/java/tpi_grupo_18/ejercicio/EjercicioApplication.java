package tpi_grupo_18.ejercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
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



	// Para probar funcionamientos se usa el Swagger --> http://localhost:8080/swagger-ui/index.html
	//https://labsys.frc.utn.edu.ar/aim/realms/backend-tps/protocol/openid-connect/auth?client_id=account-console
	// &redirect_uri=https%3A%2F%2Flocalhost%2Faim%2Frealms%2Fbackend-tps%2Faccount%2F%23%2F&state=39b5df03-5860-4bec-8bcb-b37243da3e72&response_mode=fragment&response_type=code&scope=openid&nonce=db46fdea-ec4a-4f55-8001-c056d9d65324&code_challenge=8UfvrxvFWIxYXq00KcVfhfL8XZWYR2tZLtkkEl_SNoU&code_challenge_method=S256
}
