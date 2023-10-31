package tpi_grupo_18.ejercicio.servicios.tarifas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tpi_grupo_18.ejercicio.entidades.Tarifa;
import tpi_grupo_18.ejercicio.entidades.dtos.TarifasDto;
import tpi_grupo_18.ejercicio.repositorios.Tarifa_Repo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class Tarifas_Servicios_Imp implements Tarifas_Servicios{

    private final Tarifa_Repo tarifas_repo;
    private final Tarifas_DTOMapper DTOmapper;
    private final Tarifas_Mapper mapper;

    @Override
    public TarifasDto add(TarifasDto entity) {
        Optional<Tarifa> tarifas = Stream.of(entity).map(mapper).findFirst();
        try {
            this.tarifas_repo.save(tarifas.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tarifas.map(DTOmapper).orElseThrow();
    }

    @Override
    public TarifasDto update(TarifasDto entity) {
        Optional<Tarifa> tarifas = Stream.of(entity).map(mapper).findFirst();
        tarifas.ifPresent(tarifas_repo::save);
        return tarifas.map(DTOmapper).orElseThrow();
    }

    @Override
    public TarifasDto delete(Long id) {
        TarifasDto tarifasDto = this.getById(id);
        if (tarifasDto != null) {
            Optional<Tarifa> tarifas = Stream.of(tarifasDto).map(mapper).findFirst();
            tarifas.ifPresent(tarifas_repo::delete);
        }
        return tarifasDto;
    }

    @Override
    public TarifasDto getById(Long id) {
        Optional<Tarifa> tarifa = this.tarifas_repo.findById(id);
        return tarifa.map(DTOmapper).orElseThrow();
    }

    @Override
    public List<TarifasDto> getAll() {
        List<Tarifa> tarifaList = this.tarifas_repo.findAll();
        return tarifaList.stream().map(DTOmapper).toList();
    }
}
