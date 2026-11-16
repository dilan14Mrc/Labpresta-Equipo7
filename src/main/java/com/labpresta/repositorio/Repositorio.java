package com.labpresta.repositorio;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    void guardar(T entidad);
    T buscar(String clave);
}
