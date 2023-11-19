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
    public RouteLocator configRouteAlquiler(RouteLocatorBuilder builder, @Value("${api-url-microservicio_alquileres}") String uri) {
        return builder.routes()
                .route(p -> p.path("/alquileres/**")
                        .filters(f -> f.rewritePath("/alquileres", "/api/tpi/Alquileres"))
                        .uri(uri))
                .build();
    }

    @Bean
    public RouteLocator configRouteEstacion(RouteLocatorBuilder builder, @Value("${api-url-microservicio_estaciones}") String uri){
        return builder.routes()
                .route(p -> p.path("/estaciones/**")
                        .filters(f -> f.rewritePath("/estaciones", "/api/tpi/Estaciones"))
                        .uri(uri)).build();
    }
}
