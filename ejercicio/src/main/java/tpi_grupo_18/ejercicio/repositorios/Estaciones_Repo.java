package tpi_grupo_18.ejercicio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi_grupo_18.ejercicio.entidades.Estacion;

@Repository
public interface Estaciones_Repo extends JpaRepository<Estacion, Long> {
}
