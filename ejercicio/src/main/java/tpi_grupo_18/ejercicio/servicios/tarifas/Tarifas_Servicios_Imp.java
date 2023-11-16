package tpi_grupo_18.ejercicio.servicios.tarifas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.repositorios.Tarifa_Repo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Tarifas_Servicios_Imp implements Tarifas_Servicios {

    private final Tarifa_Repo tarifas_repo;

    @Override
    public Tarifa add(Tarifa entity) {
        return this.tarifas_repo.save(entity);
    }

    @Override
    public Tarifa update(Tarifa entity) {
        return null;
    }

    @Override
    public Tarifa delete(Long id) {
        Tarifa tarifa = this.getById(id);
        this.tarifas_repo.delete(tarifa);
        return tarifa;
    }

    @Override
    public Tarifa getById(Long id) {
        return this.tarifas_repo.findById(id).orElseThrow();
    }

    @Override
    public List<Tarifa> getAll() {
        return this.tarifas_repo.findAll();
    }
}
