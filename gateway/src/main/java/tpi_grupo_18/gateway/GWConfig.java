package tpi_grupo_18.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> {
            exchanges.pathMatchers(HttpMethod.POST, "/estaciones/**").hasRole("ADMINISTRADOR");
            exchanges.pathMatchers(HttpMethod.GET, "/alquileres/**").hasRole("ADMINISTRADOR");
            exchanges.pathMatchers(HttpMethod.GET, "/estaciones/**").hasRole("CLIENTE");
            exchanges.pathMatchers(HttpMethod.POST, "/alquileres/retirar").hasRole("CLIENTE");
            exchanges.pathMatchers(HttpMethod.PUT, "/alquileres/devolver").hasRole("CLIENTE");
            exchanges.anyExchange().authenticated();
        }).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter)
        );

        return jwtAuthenticationConverter;
    }

    @Bean
    public RouteLocator configRoutes(RouteLocatorBuilder builder, @Value("${api-url-microservicio}") String uri) {
        return builder.routes()
                .route(p -> p.path("/estaciones/**")
                        .filters(f -> f.rewritePath("/estaciones", "/api/tpi/Estaciones"))
                        .uri(uri))
                .route(p -> p.path("/alquileres/**")
                        .filters(f -> f.rewritePath("/alquileres", "/api/tpi/Alquileres"))
                        .uri(uri))
                .build();
    }

    /*@Bean
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
    }*/
}
