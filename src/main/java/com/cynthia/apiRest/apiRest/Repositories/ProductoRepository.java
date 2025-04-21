package com.cynthia.apiRest.apiRest.Repositories;

import com.cynthia.apiRest.apiRest.Entities.Producto;
import com.cynthia.apiRest.apiRest.Entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombre(String nombre);

    List<Producto> findByCategoria(Categoria categoria);
}
