package tpi_grupo_18.alquileres.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi_grupo_18.alquileres.entidades.Tarifa;

@Repository
public interface Tarifa_Repo extends JpaRepository<Tarifa, Long> {
}
