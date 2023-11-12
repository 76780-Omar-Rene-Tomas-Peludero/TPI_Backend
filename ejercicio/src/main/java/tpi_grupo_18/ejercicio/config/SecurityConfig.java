package tpi_grupo_18.ejercicio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> {
            exchanges.anyExchange().authenticated();
        });
        http.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> {
            jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor());
        }));
        return http.build();
    }
    /*public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
//            auth.requestMatchers("/api/tpi/**").hasAnyAuthority("ROLE_CLIENTE", "ROLE_ADMINISTRADOR");
            auth.anyRequest().permitAll();
        }).oauth2ResourceServer(oauth ->
                oauth.jwt(jwt -> {})
        ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }*/


    /*@Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");

            List<String> roles = (List<String>) realmAccess.get("roles");

            return roles.stream()
                    .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn))
                    .collect(Collectors.toList());
        };

        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtGrantedAuthoritiesConverter)
        );
        return jwtAuthenticationConverter;
    }*/

    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(new JwtAuthConvertor())
        );
        return jwtAuthenticationConverter;
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

    @Bean
    public RouteLocator alquileresRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Recuperar listado de alquileres
                .route(a -> a
                        // http://localhost:8081/listaAlquileres
                        .path("/listaAlquileres")
                        .filters(f -> f.rewritePath("/listaAlquileres", "/api/tpi/Alquileres/"))
                        .uri("http://localhost:8081"))
                // Consultar alquiler por ID
                .route(r -> r
                        // http://localhost:8081/alquilerPorId/valor
                        .path("/alquilerPorId/{id}")
                        .filters(f -> f.rewritePath("/alquilerPorId/(?<id>.*)", "/api/tpi/Alquileres/${id}"))
                        .uri("http://localhost:8081"))
                // Ruta para retirar un alquiler
                .route(r -> r
                        // http://localhost:8080/retirar
                        .path("/retirar")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/retirar", "/api/tpi/Alquileres/retirar"))
                        .uri("http://localhost:8081"))
                // Ruta para devolver un alquiler
                .route(r -> r
                        // http://localhost:8080/devolver
                        .path("/devolver")
                        .and()
                        .method(HttpMethod.PUT)
                        .filters(f -> f.rewritePath("/devolver", "/api/tpi/Alquileres/devolver"))
                        .uri("http://localhost:8081"))
                // Ruta para la conversión de moneda
                .route(r -> r
                        .path("/pasarMoneda")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/pasarMoneda", "/convertir"))
                        .uri("http://34.82.105.125:8080"))
                .build();
    }

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
}
