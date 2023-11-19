package tpi_grupo_18.estaciones.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi_grupo_18.estaciones.entidades.Estacion;

@Repository
public interface Estaciones_Repo extends JpaRepository<Estacion, Long> {



}
