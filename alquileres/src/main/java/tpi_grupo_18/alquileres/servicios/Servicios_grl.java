package tpi_grupo_18.alquileres.servicios;

import java.util.List;

public interface Servicios_grl<T,ID> {
    T add(T entity);
    T update(T entity);
    T delete(ID id);
    T getById(ID id);
    List<T> getAll();
}
