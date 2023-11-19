package tpi_grupo_18.alquileres.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi_grupo_18.alquileres.entidades.Alquiler;

@Repository
public interface Alquileres_Repo extends JpaRepository<Alquiler,Long> {
    Alquiler findByIdCliente(String idCliente);
}
